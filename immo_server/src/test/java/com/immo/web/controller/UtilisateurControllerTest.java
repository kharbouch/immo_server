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
import com.immo.bean.Utilisateur;
import com.immo.bean.Profil;
import com.immo.test.UtilisateurFactoryForTest;
import com.immo.test.ProfilFactoryForTest;

//--- Services 
import com.immo.business.service.UtilisateurService;
import com.immo.business.service.ProfilService;

//--- List Items 
import com.immo.web.listitem.ProfilListItem;

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
public class UtilisateurControllerTest {
	
	@InjectMocks
	private UtilisateurController utilisateurController;
	@Mock
	private UtilisateurService utilisateurService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ProfilService profilService; // Injected by Spring

	private UtilisateurFactoryForTest utilisateurFactoryForTest = new UtilisateurFactoryForTest();
	private ProfilFactoryForTest profilFactoryForTest = new ProfilFactoryForTest();

	List<Profil> profils = new ArrayList<Profil>();

	private void givenPopulateModel() {
		Profil profil1 = profilFactoryForTest.newProfil();
		Profil profil2 = profilFactoryForTest.newProfil();
		List<Profil> profils = new ArrayList<Profil>();
		profils.add(profil1);
		profils.add(profil2);
		when(profilService.findAll()).thenReturn(profils);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Utilisateur> list = new ArrayList<Utilisateur>();
		when(utilisateurService.findAll()).thenReturn(list);
		
		// When
		String viewName = utilisateurController.list(model);
		
		// Then
		assertEquals("utilisateur/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = utilisateurController.formForCreate(model);
		
		// Then
		assertEquals("utilisateur/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Utilisateur)modelMap.get("utilisateur")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/utilisateur/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		Long id = utilisateur.getId();
		when(utilisateurService.findById(id)).thenReturn(utilisateur);
		
		// When
		String viewName = utilisateurController.formForUpdate(model, id);
		
		// Then
		assertEquals("utilisateur/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateur, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/utilisateur/update", modelMap.get("saveAction"));
		
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Utilisateur utilisateurCreated = new Utilisateur();
		when(utilisateurService.create(utilisateur)).thenReturn(utilisateurCreated); 
		
		// When
		String viewName = utilisateurController.create(utilisateur, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/utilisateur/form/"+utilisateur.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurCreated, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = utilisateurController.create(utilisateur, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateur/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateur, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/utilisateur/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
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

		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		
		Exception exception = new RuntimeException("test exception");
		when(utilisateurService.create(utilisateur)).thenThrow(exception);
		
		// When
		String viewName = utilisateurController.create(utilisateur, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateur/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateur, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/utilisateur/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "utilisateur.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		Long id = utilisateur.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Utilisateur utilisateurSaved = new Utilisateur();
		utilisateurSaved.setId(id);
		when(utilisateurService.update(utilisateur)).thenReturn(utilisateurSaved); 
		
		// When
		String viewName = utilisateurController.update(utilisateur, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/utilisateur/form/"+utilisateur.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateurSaved, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = utilisateurController.update(utilisateur, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateur/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateur, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/utilisateur/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
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

		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		
		Exception exception = new RuntimeException("test exception");
		when(utilisateurService.update(utilisateur)).thenThrow(exception);
		
		// When
		String viewName = utilisateurController.update(utilisateur, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("utilisateur/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(utilisateur, (Utilisateur) modelMap.get("utilisateur"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/utilisateur/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "utilisateur.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		Long id = utilisateur.getId();
		
		// When
		String viewName = utilisateurController.delete(redirectAttributes, id);
		
		// Then
		verify(utilisateurService).delete(id);
		assertEquals("redirect:/utilisateur", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		Long id = utilisateur.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(utilisateurService).delete(id);
		
		// When
		String viewName = utilisateurController.delete(redirectAttributes, id);
		
		// Then
		verify(utilisateurService).delete(id);
		assertEquals("redirect:/utilisateur", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "utilisateur.error.delete", exception);
	}
	
	
}
