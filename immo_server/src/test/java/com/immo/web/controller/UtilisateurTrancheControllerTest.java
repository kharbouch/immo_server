package com.immo.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import com.immo.bean.UtilisateurTranche;
import com.immo.bean.Tranche;
import com.immo.bean.Utilisateur;
import com.immo.test.UtilisateurTrancheFactoryForTest;
import com.immo.test.TrancheFactoryForTest;
import com.immo.test.UtilisateurFactoryForTest;

//--- Services 
import com.immo.business.service.UtilisateurTrancheService;
import com.immo.business.service.TrancheService;
import com.immo.business.service.UtilisateurService;

//--- List Items 
import com.immo.web.listitem.TrancheListItem;
import com.immo.web.listitem.UtilisateurListItem;

import com.immo.web.common.Message;
import com.immo.web.common.MessageHelper;
import com.immo.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class UtilisateurTrancheControllerTest {
	
	@InjectMocks
	private UtilisateurTrancheController utilisateurTrancheController;
	@Mock
	private UtilisateurTrancheService utilisateurTrancheService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private TrancheService trancheService; // Injected by Spring
	@Mock
	private UtilisateurService utilisateurService; // Injected by Spring

	private UtilisateurTrancheFactoryForTest utilisateurTrancheFactoryForTest = new UtilisateurTrancheFactoryForTest();
	private TrancheFactoryForTest trancheFactoryForTest = new TrancheFactoryForTest();
	private UtilisateurFactoryForTest utilisateurFactoryForTest = new UtilisateurFactoryForTest();

	List<Tranche> tranches = new ArrayList<Tranche>();
	List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

	private void givenPopulateModel() {
		Tranche tranche1 = trancheFactoryForTest.newTranche();
		Tranche tranche2 = trancheFactoryForTest.newTranche();
		List<Tranche> tranches = new ArrayList<Tranche>();
		tranches.add(tranche1);
		tranches.add(tranche2);
		when(trancheService.findAll()).thenReturn(tranches);

		Utilisateur utilisateur1 = utilisateurFactoryForTest.newUtilisateur();
		Utilisateur utilisateur2 = utilisateurFactoryForTest.newUtilisateur();
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		utilisateurs.add(utilisateur1);
		utilisateurs.add(utilisateur2);
		when(utilisateurService.findAll()).thenReturn(utilisateurs);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<UtilisateurTranche> list = new ArrayList<UtilisateurTranche>();
		when(utilisateurTrancheService.findAll()).thenReturn(list);
		
		// When
		String viewName = utilisateurTrancheController.list(model);
		
		// Then
		assertEquals("utilisateurTranche/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = utilisateurTrancheController.formForCreate(model);
		
		// Then
		assertEquals("utilisateurTranche/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((UtilisateurTranche)modelMap.get("utilisateurTranche")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/utilisateurTranche/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UtilisateurListItem> utilisateurListItems = (List<UtilisateurListItem>) modelMap.get("listOfUtilisateurItems");
		assertEquals(2, utilisateurListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		Long id = utilisateurTranche.getId();
		when(utilisateurTrancheService.findById(id)).thenReturn(utilisateurTranche);
		
		// When
		String viewName = utilisateurTrancheController.formForUpdate(model, id);
		
		// Then
		assertEquals("utilisateurTranche/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTranche, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/utilisateurTranche/update", modelMap.get("saveAction"));
		
		List<UtilisateurListItem> utilisateurListItems = (List<UtilisateurListItem>) modelMap.get("listOfUtilisateurItems");
		assertEquals(2, utilisateurListItems.size());
		
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		UtilisateurTranche utilisateurTrancheCreated = new UtilisateurTranche();
		when(utilisateurTrancheService.create(utilisateurTranche)).thenReturn(utilisateurTrancheCreated); 
		
		// When
		String viewName = utilisateurTrancheController.create(utilisateurTranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/utilisateurTranche/form/"+utilisateurTranche.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTrancheCreated, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = utilisateurTrancheController.create(utilisateurTranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateurTranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTranche, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/utilisateurTranche/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UtilisateurListItem> utilisateurListItems = (List<UtilisateurListItem>) modelMap.get("listOfUtilisateurItems");
		assertEquals(2, utilisateurListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		
		Exception exception = new RuntimeException("test exception");
		when(utilisateurTrancheService.create(utilisateurTranche)).thenThrow(exception);
		
		// When
		String viewName = utilisateurTrancheController.create(utilisateurTranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateurTranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTranche, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/utilisateurTranche/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "utilisateurTranche.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UtilisateurListItem> utilisateurListItems = (List<UtilisateurListItem>) modelMap.get("listOfUtilisateurItems");
		assertEquals(2, utilisateurListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		Long id = utilisateurTranche.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		UtilisateurTranche utilisateurTrancheSaved = new UtilisateurTranche();
		utilisateurTrancheSaved.setId(id);
		when(utilisateurTrancheService.update(utilisateurTranche)).thenReturn(utilisateurTrancheSaved); 
		
		// When
		String viewName = utilisateurTrancheController.update(utilisateurTranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/utilisateurTranche/form/"+utilisateurTranche.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTrancheSaved, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = utilisateurTrancheController.update(utilisateurTranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateurTranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTranche, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/utilisateurTranche/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UtilisateurListItem> utilisateurListItems = (List<UtilisateurListItem>) modelMap.get("listOfUtilisateurItems");
		assertEquals(2, utilisateurListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		
		Exception exception = new RuntimeException("test exception");
		when(utilisateurTrancheService.update(utilisateurTranche)).thenThrow(exception);
		
		// When
		String viewName = utilisateurTrancheController.update(utilisateurTranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateurTranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurTranche, (UtilisateurTranche) modelMap.get("utilisateurTranche"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/utilisateurTranche/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "utilisateurTranche.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<UtilisateurListItem> utilisateurListItems = (List<UtilisateurListItem>) modelMap.get("listOfUtilisateurItems");
		assertEquals(2, utilisateurListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		Long id = utilisateurTranche.getId();
		
		// When
		String viewName = utilisateurTrancheController.delete(redirectAttributes, id);
		
		// Then
		verify(utilisateurTrancheService).delete(id);
		assertEquals("redirect:/utilisateurTranche", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		Long id = utilisateurTranche.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(utilisateurTrancheService).delete(id);
		
		// When
		String viewName = utilisateurTrancheController.delete(redirectAttributes, id);
		
		// Then
		verify(utilisateurTrancheService).delete(id);
		assertEquals("redirect:/utilisateurTranche", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "utilisateurTranche.error.delete", exception);
	}
	
	
}
