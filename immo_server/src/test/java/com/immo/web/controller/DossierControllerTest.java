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
import com.immo.bean.Dossier;
import com.immo.test.DossierFactoryForTest;

//--- Services 
import com.immo.business.service.DossierService;


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
public class DossierControllerTest {
	
	@InjectMocks
	private DossierController dossierController;
	@Mock
	private DossierService dossierService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private DossierFactoryForTest dossierFactoryForTest = new DossierFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Dossier> list = new ArrayList<Dossier>();
		when(dossierService.findAll()).thenReturn(list);
		
		// When
		String viewName = dossierController.list(model);
		
		// Then
		assertEquals("dossier/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = dossierController.formForCreate(model);
		
		// Then
		assertEquals("dossier/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Dossier)modelMap.get("dossier")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/dossier/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		Long id = dossier.getId();
		when(dossierService.findById(id)).thenReturn(dossier);
		
		// When
		String viewName = dossierController.formForUpdate(model, id);
		
		// Then
		assertEquals("dossier/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossier, (Dossier) modelMap.get("dossier"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/dossier/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Dossier dossierCreated = new Dossier();
		when(dossierService.create(dossier)).thenReturn(dossierCreated); 
		
		// When
		String viewName = dossierController.create(dossier, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/dossier/form/"+dossier.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierCreated, (Dossier) modelMap.get("dossier"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = dossierController.create(dossier, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossier/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossier, (Dossier) modelMap.get("dossier"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/dossier/create", modelMap.get("saveAction"));
		
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

		Dossier dossier = dossierFactoryForTest.newDossier();
		
		Exception exception = new RuntimeException("test exception");
		when(dossierService.create(dossier)).thenThrow(exception);
		
		// When
		String viewName = dossierController.create(dossier, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossier/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossier, (Dossier) modelMap.get("dossier"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/dossier/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "dossier.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		Long id = dossier.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Dossier dossierSaved = new Dossier();
		dossierSaved.setId(id);
		when(dossierService.update(dossier)).thenReturn(dossierSaved); 
		
		// When
		String viewName = dossierController.update(dossier, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/dossier/form/"+dossier.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierSaved, (Dossier) modelMap.get("dossier"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = dossierController.update(dossier, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossier/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossier, (Dossier) modelMap.get("dossier"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/dossier/update", modelMap.get("saveAction"));
		
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

		Dossier dossier = dossierFactoryForTest.newDossier();
		
		Exception exception = new RuntimeException("test exception");
		when(dossierService.update(dossier)).thenThrow(exception);
		
		// When
		String viewName = dossierController.update(dossier, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossier/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossier, (Dossier) modelMap.get("dossier"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/dossier/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "dossier.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		Long id = dossier.getId();
		
		// When
		String viewName = dossierController.delete(redirectAttributes, id);
		
		// Then
		verify(dossierService).delete(id);
		assertEquals("redirect:/dossier", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Dossier dossier = dossierFactoryForTest.newDossier();
		Long id = dossier.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(dossierService).delete(id);
		
		// When
		String viewName = dossierController.delete(redirectAttributes, id);
		
		// Then
		verify(dossierService).delete(id);
		assertEquals("redirect:/dossier", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "dossier.error.delete", exception);
	}
	
	
}
