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
import com.immo.bean.Reference;
import com.immo.bean.ReferenceType;
import com.immo.test.ReferenceFactoryForTest;
import com.immo.test.ReferenceTypeFactoryForTest;

//--- Services 
import com.immo.business.service.ReferenceService;
import com.immo.business.service.ReferenceTypeService;

//--- List Items 
import com.immo.web.listitem.ReferenceTypeListItem;

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
public class ReferenceControllerTest {
	
	@InjectMocks
	private ReferenceController referenceController;
	@Mock
	private ReferenceService referenceService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ReferenceTypeService referenceTypeService; // Injected by Spring

	private ReferenceFactoryForTest referenceFactoryForTest = new ReferenceFactoryForTest();
	private ReferenceTypeFactoryForTest referenceTypeFactoryForTest = new ReferenceTypeFactoryForTest();

	List<ReferenceType> referenceTypes = new ArrayList<ReferenceType>();

	private void givenPopulateModel() {
		ReferenceType referenceType1 = referenceTypeFactoryForTest.newReferenceType();
		ReferenceType referenceType2 = referenceTypeFactoryForTest.newReferenceType();
		List<ReferenceType> referenceTypes = new ArrayList<ReferenceType>();
		referenceTypes.add(referenceType1);
		referenceTypes.add(referenceType2);
		when(referenceTypeService.findAll()).thenReturn(referenceTypes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Reference> list = new ArrayList<Reference>();
		when(referenceService.findAll()).thenReturn(list);
		
		// When
		String viewName = referenceController.list(model);
		
		// Then
		assertEquals("reference/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = referenceController.formForCreate(model);
		
		// Then
		assertEquals("reference/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Reference)modelMap.get("reference")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/reference/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReferenceTypeListItem> referenceTypeListItems = (List<ReferenceTypeListItem>) modelMap.get("listOfReferenceTypeItems");
		assertEquals(2, referenceTypeListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Reference reference = referenceFactoryForTest.newReference();
		Long id = reference.getId();
		when(referenceService.findById(id)).thenReturn(reference);
		
		// When
		String viewName = referenceController.formForUpdate(model, id);
		
		// Then
		assertEquals("reference/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(reference, (Reference) modelMap.get("reference"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/reference/update", modelMap.get("saveAction"));
		
		List<ReferenceTypeListItem> referenceTypeListItems = (List<ReferenceTypeListItem>) modelMap.get("listOfReferenceTypeItems");
		assertEquals(2, referenceTypeListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Reference reference = referenceFactoryForTest.newReference();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Reference referenceCreated = new Reference();
		when(referenceService.create(reference)).thenReturn(referenceCreated); 
		
		// When
		String viewName = referenceController.create(reference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/reference/form/"+reference.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceCreated, (Reference) modelMap.get("reference"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Reference reference = referenceFactoryForTest.newReference();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = referenceController.create(reference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("reference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(reference, (Reference) modelMap.get("reference"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/reference/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReferenceTypeListItem> referenceTypeListItems = (List<ReferenceTypeListItem>) modelMap.get("listOfReferenceTypeItems");
		assertEquals(2, referenceTypeListItems.size());
		
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

		Reference reference = referenceFactoryForTest.newReference();
		
		Exception exception = new RuntimeException("test exception");
		when(referenceService.create(reference)).thenThrow(exception);
		
		// When
		String viewName = referenceController.create(reference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("reference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(reference, (Reference) modelMap.get("reference"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/reference/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "reference.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ReferenceTypeListItem> referenceTypeListItems = (List<ReferenceTypeListItem>) modelMap.get("listOfReferenceTypeItems");
		assertEquals(2, referenceTypeListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Reference reference = referenceFactoryForTest.newReference();
		Long id = reference.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Reference referenceSaved = new Reference();
		referenceSaved.setId(id);
		when(referenceService.update(reference)).thenReturn(referenceSaved); 
		
		// When
		String viewName = referenceController.update(reference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/reference/form/"+reference.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(referenceSaved, (Reference) modelMap.get("reference"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Reference reference = referenceFactoryForTest.newReference();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = referenceController.update(reference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("reference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(reference, (Reference) modelMap.get("reference"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/reference/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReferenceTypeListItem> referenceTypeListItems = (List<ReferenceTypeListItem>) modelMap.get("listOfReferenceTypeItems");
		assertEquals(2, referenceTypeListItems.size());
		
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

		Reference reference = referenceFactoryForTest.newReference();
		
		Exception exception = new RuntimeException("test exception");
		when(referenceService.update(reference)).thenThrow(exception);
		
		// When
		String viewName = referenceController.update(reference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("reference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(reference, (Reference) modelMap.get("reference"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/reference/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "reference.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ReferenceTypeListItem> referenceTypeListItems = (List<ReferenceTypeListItem>) modelMap.get("listOfReferenceTypeItems");
		assertEquals(2, referenceTypeListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Reference reference = referenceFactoryForTest.newReference();
		Long id = reference.getId();
		
		// When
		String viewName = referenceController.delete(redirectAttributes, id);
		
		// Then
		verify(referenceService).delete(id);
		assertEquals("redirect:/reference", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Reference reference = referenceFactoryForTest.newReference();
		Long id = reference.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(referenceService).delete(id);
		
		// When
		String viewName = referenceController.delete(redirectAttributes, id);
		
		// Then
		verify(referenceService).delete(id);
		assertEquals("redirect:/reference", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "reference.error.delete", exception);
	}
	
	
}
