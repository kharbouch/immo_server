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
import com.immo.bean.ReferenceType;
import com.immo.test.ReferenceTypeFactoryForTest;

//--- Services 
import com.immo.business.service.ReferenceTypeService;


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
public class ReferenceTypeControllerTest {
	
	@InjectMocks
	private ReferenceTypeController referenceTypeController;
	@Mock
	private ReferenceTypeService referenceTypeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ReferenceTypeFactoryForTest referenceTypeFactoryForTest = new ReferenceTypeFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<ReferenceType> list = new ArrayList<ReferenceType>();
		when(referenceTypeService.findAll()).thenReturn(list);
		
		// When
		String viewName = referenceTypeController.list(model);
		
		// Then
		assertEquals("referenceType/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = referenceTypeController.formForCreate(model);
		
		// Then
		assertEquals("referenceType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ReferenceType)modelMap.get("referenceType")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/referenceType/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		Long id = referenceType.getId();
		when(referenceTypeService.findById(id)).thenReturn(referenceType);
		
		// When
		String viewName = referenceTypeController.formForUpdate(model, id);
		
		// Then
		assertEquals("referenceType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceType, (ReferenceType) modelMap.get("referenceType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/referenceType/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReferenceType referenceTypeCreated = new ReferenceType();
		when(referenceTypeService.create(referenceType)).thenReturn(referenceTypeCreated); 
		
		// When
		String viewName = referenceTypeController.create(referenceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/referenceType/form/"+referenceType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceTypeCreated, (ReferenceType) modelMap.get("referenceType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = referenceTypeController.create(referenceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("referenceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceType, (ReferenceType) modelMap.get("referenceType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/referenceType/create", modelMap.get("saveAction"));
		
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

		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		
		Exception exception = new RuntimeException("test exception");
		when(referenceTypeService.create(referenceType)).thenThrow(exception);
		
		// When
		String viewName = referenceTypeController.create(referenceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("referenceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceType, (ReferenceType) modelMap.get("referenceType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/referenceType/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "referenceType.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		Long id = referenceType.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReferenceType referenceTypeSaved = new ReferenceType();
		referenceTypeSaved.setId(id);
		when(referenceTypeService.update(referenceType)).thenReturn(referenceTypeSaved); 
		
		// When
		String viewName = referenceTypeController.update(referenceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/referenceType/form/"+referenceType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceTypeSaved, (ReferenceType) modelMap.get("referenceType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = referenceTypeController.update(referenceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("referenceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceType, (ReferenceType) modelMap.get("referenceType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/referenceType/update", modelMap.get("saveAction"));
		
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

		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		
		Exception exception = new RuntimeException("test exception");
		when(referenceTypeService.update(referenceType)).thenThrow(exception);
		
		// When
		String viewName = referenceTypeController.update(referenceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("referenceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceType, (ReferenceType) modelMap.get("referenceType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/referenceType/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "referenceType.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		Long id = referenceType.getId();
		
		// When
		String viewName = referenceTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(referenceTypeService).delete(id);
		assertEquals("redirect:/referenceType", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReferenceType referenceType = referenceTypeFactoryForTest.newReferenceType();
		Long id = referenceType.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(referenceTypeService).delete(id);
		
		// When
		String viewName = referenceTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(referenceTypeService).delete(id);
		assertEquals("redirect:/referenceType", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "referenceType.error.delete", exception);
	}
	
	
}
