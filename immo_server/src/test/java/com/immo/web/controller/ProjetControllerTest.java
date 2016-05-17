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
import com.immo.bean.Projet;
import com.immo.test.ProjetFactoryForTest;

//--- Services 
import com.immo.business.service.ProjetService;


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
public class ProjetControllerTest {
	
	@InjectMocks
	private ProjetController projetController;
	@Mock
	private ProjetService projetService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ProjetFactoryForTest projetFactoryForTest = new ProjetFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Projet> list = new ArrayList<Projet>();
		when(projetService.findAll()).thenReturn(list);
		
		// When
		String viewName = projetController.list(model);
		
		// Then
		assertEquals("projet/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = projetController.formForCreate(model);
		
		// Then
		assertEquals("projet/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Projet)modelMap.get("projet")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/projet/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Projet projet = projetFactoryForTest.newProjet();
		Long id = projet.getId();
		when(projetService.findById(id)).thenReturn(projet);
		
		// When
		String viewName = projetController.formForUpdate(model, id);
		
		// Then
		assertEquals("projet/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projet, (Projet) modelMap.get("projet"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/projet/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Projet projet = projetFactoryForTest.newProjet();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Projet projetCreated = new Projet();
		when(projetService.create(projet)).thenReturn(projetCreated); 
		
		// When
		String viewName = projetController.create(projet, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/projet/form/"+projet.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projetCreated, (Projet) modelMap.get("projet"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Projet projet = projetFactoryForTest.newProjet();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = projetController.create(projet, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projet/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projet, (Projet) modelMap.get("projet"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/projet/create", modelMap.get("saveAction"));
		
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

		Projet projet = projetFactoryForTest.newProjet();
		
		Exception exception = new RuntimeException("test exception");
		when(projetService.create(projet)).thenThrow(exception);
		
		// When
		String viewName = projetController.create(projet, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projet/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projet, (Projet) modelMap.get("projet"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/projet/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "projet.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Projet projet = projetFactoryForTest.newProjet();
		Long id = projet.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Projet projetSaved = new Projet();
		projetSaved.setId(id);
		when(projetService.update(projet)).thenReturn(projetSaved); 
		
		// When
		String viewName = projetController.update(projet, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/projet/form/"+projet.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projetSaved, (Projet) modelMap.get("projet"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Projet projet = projetFactoryForTest.newProjet();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = projetController.update(projet, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projet/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projet, (Projet) modelMap.get("projet"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/projet/update", modelMap.get("saveAction"));
		
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

		Projet projet = projetFactoryForTest.newProjet();
		
		Exception exception = new RuntimeException("test exception");
		when(projetService.update(projet)).thenThrow(exception);
		
		// When
		String viewName = projetController.update(projet, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projet/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projet, (Projet) modelMap.get("projet"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/projet/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "projet.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Projet projet = projetFactoryForTest.newProjet();
		Long id = projet.getId();
		
		// When
		String viewName = projetController.delete(redirectAttributes, id);
		
		// Then
		verify(projetService).delete(id);
		assertEquals("redirect:/projet", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Projet projet = projetFactoryForTest.newProjet();
		Long id = projet.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(projetService).delete(id);
		
		// When
		String viewName = projetController.delete(redirectAttributes, id);
		
		// Then
		verify(projetService).delete(id);
		assertEquals("redirect:/projet", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "projet.error.delete", exception);
	}
	
	
}
