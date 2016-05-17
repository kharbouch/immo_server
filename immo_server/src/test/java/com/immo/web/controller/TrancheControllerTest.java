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
import com.immo.bean.Tranche;
import com.immo.bean.Projet;
import com.immo.test.TrancheFactoryForTest;
import com.immo.test.ProjetFactoryForTest;

//--- Services 
import com.immo.business.service.TrancheService;
import com.immo.business.service.ProjetService;

//--- List Items 
import com.immo.web.listitem.ProjetListItem;

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
public class TrancheControllerTest {
	
	@InjectMocks
	private TrancheController trancheController;
	@Mock
	private TrancheService trancheService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ProjetService projetService; // Injected by Spring

	private TrancheFactoryForTest trancheFactoryForTest = new TrancheFactoryForTest();
	private ProjetFactoryForTest projetFactoryForTest = new ProjetFactoryForTest();

	List<Projet> projets = new ArrayList<Projet>();

	private void givenPopulateModel() {
		Projet projet1 = projetFactoryForTest.newProjet();
		Projet projet2 = projetFactoryForTest.newProjet();
		List<Projet> projets = new ArrayList<Projet>();
		projets.add(projet1);
		projets.add(projet2);
		when(projetService.findAll()).thenReturn(projets);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Tranche> list = new ArrayList<Tranche>();
		when(trancheService.findAll()).thenReturn(list);
		
		// When
		String viewName = trancheController.list(model);
		
		// Then
		assertEquals("tranche/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = trancheController.formForCreate(model);
		
		// Then
		assertEquals("tranche/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Tranche)modelMap.get("tranche")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/tranche/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProjetListItem> projetListItems = (List<ProjetListItem>) modelMap.get("listOfProjetItems");
		assertEquals(2, projetListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		Long id = tranche.getId();
		when(trancheService.findById(id)).thenReturn(tranche);
		
		// When
		String viewName = trancheController.formForUpdate(model, id);
		
		// Then
		assertEquals("tranche/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(tranche, (Tranche) modelMap.get("tranche"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/tranche/update", modelMap.get("saveAction"));
		
		List<ProjetListItem> projetListItems = (List<ProjetListItem>) modelMap.get("listOfProjetItems");
		assertEquals(2, projetListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Tranche trancheCreated = new Tranche();
		when(trancheService.create(tranche)).thenReturn(trancheCreated); 
		
		// When
		String viewName = trancheController.create(tranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/tranche/form/"+tranche.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(trancheCreated, (Tranche) modelMap.get("tranche"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = trancheController.create(tranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("tranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(tranche, (Tranche) modelMap.get("tranche"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/tranche/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProjetListItem> projetListItems = (List<ProjetListItem>) modelMap.get("listOfProjetItems");
		assertEquals(2, projetListItems.size());
		
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

		Tranche tranche = trancheFactoryForTest.newTranche();
		
		Exception exception = new RuntimeException("test exception");
		when(trancheService.create(tranche)).thenThrow(exception);
		
		// When
		String viewName = trancheController.create(tranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("tranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(tranche, (Tranche) modelMap.get("tranche"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/tranche/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "tranche.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ProjetListItem> projetListItems = (List<ProjetListItem>) modelMap.get("listOfProjetItems");
		assertEquals(2, projetListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		Long id = tranche.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Tranche trancheSaved = new Tranche();
		trancheSaved.setId(id);
		when(trancheService.update(tranche)).thenReturn(trancheSaved); 
		
		// When
		String viewName = trancheController.update(tranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/tranche/form/"+tranche.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(trancheSaved, (Tranche) modelMap.get("tranche"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = trancheController.update(tranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("tranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(tranche, (Tranche) modelMap.get("tranche"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/tranche/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProjetListItem> projetListItems = (List<ProjetListItem>) modelMap.get("listOfProjetItems");
		assertEquals(2, projetListItems.size());
		
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

		Tranche tranche = trancheFactoryForTest.newTranche();
		
		Exception exception = new RuntimeException("test exception");
		when(trancheService.update(tranche)).thenThrow(exception);
		
		// When
		String viewName = trancheController.update(tranche, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("tranche/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(tranche, (Tranche) modelMap.get("tranche"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/tranche/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "tranche.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ProjetListItem> projetListItems = (List<ProjetListItem>) modelMap.get("listOfProjetItems");
		assertEquals(2, projetListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		Long id = tranche.getId();
		
		// When
		String viewName = trancheController.delete(redirectAttributes, id);
		
		// Then
		verify(trancheService).delete(id);
		assertEquals("redirect:/tranche", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		Long id = tranche.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(trancheService).delete(id);
		
		// When
		String viewName = trancheController.delete(redirectAttributes, id);
		
		// Then
		verify(trancheService).delete(id);
		assertEquals("redirect:/tranche", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "tranche.error.delete", exception);
	}
	
	
}
