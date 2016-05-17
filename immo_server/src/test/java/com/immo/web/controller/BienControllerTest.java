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
import com.immo.bean.Bien;
import com.immo.bean.Reference;
import com.immo.bean.Tranche;
import com.immo.test.BienFactoryForTest;
import com.immo.test.ReferenceFactoryForTest;
import com.immo.test.TrancheFactoryForTest;

//--- Services 
import com.immo.business.service.BienService;
import com.immo.business.service.ReferenceService;
import com.immo.business.service.TrancheService;

//--- List Items 
import com.immo.web.listitem.ReferenceListItem;
import com.immo.web.listitem.TrancheListItem;

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
public class BienControllerTest {
	
	@InjectMocks
	private BienController bienController;
	@Mock
	private BienService bienService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ReferenceService referenceService; // Injected by Spring
	@Mock
	private TrancheService trancheService; // Injected by Spring

	private BienFactoryForTest bienFactoryForTest = new BienFactoryForTest();
	private ReferenceFactoryForTest referenceFactoryForTest = new ReferenceFactoryForTest();
	private TrancheFactoryForTest trancheFactoryForTest = new TrancheFactoryForTest();

	List<Reference> references = new ArrayList<Reference>();
	List<Tranche> tranches = new ArrayList<Tranche>();

	private void givenPopulateModel() {
		Reference reference1 = referenceFactoryForTest.newReference();
		Reference reference2 = referenceFactoryForTest.newReference();
		List<Reference> references = new ArrayList<Reference>();
		references.add(reference1);
		references.add(reference2);
		when(referenceService.findAll()).thenReturn(references);

		Tranche tranche1 = trancheFactoryForTest.newTranche();
		Tranche tranche2 = trancheFactoryForTest.newTranche();
		List<Tranche> tranches = new ArrayList<Tranche>();
		tranches.add(tranche1);
		tranches.add(tranche2);
		when(trancheService.findAll()).thenReturn(tranches);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Bien> list = new ArrayList<Bien>();
		when(bienService.findAll()).thenReturn(list);
		
		// When
		String viewName = bienController.list(model);
		
		// Then
		assertEquals("bien/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = bienController.formForCreate(model);
		
		// Then
		assertEquals("bien/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Bien)modelMap.get("bien")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bien/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReferenceListItem> referenceListItems = (List<ReferenceListItem>) modelMap.get("listOfReferenceItems");
		assertEquals(2, referenceListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Bien bien = bienFactoryForTest.newBien();
		Long id = bien.getId();
		when(bienService.findById(id)).thenReturn(bien);
		
		// When
		String viewName = bienController.formForUpdate(model, id);
		
		// Then
		assertEquals("bien/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bien, (Bien) modelMap.get("bien"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bien/update", modelMap.get("saveAction"));
		
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
		List<ReferenceListItem> referenceListItems = (List<ReferenceListItem>) modelMap.get("listOfReferenceItems");
		assertEquals(2, referenceListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Bien bien = bienFactoryForTest.newBien();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Bien bienCreated = new Bien();
		when(bienService.create(bien)).thenReturn(bienCreated); 
		
		// When
		String viewName = bienController.create(bien, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/bien/form/"+bien.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bienCreated, (Bien) modelMap.get("bien"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Bien bien = bienFactoryForTest.newBien();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = bienController.create(bien, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bien/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bien, (Bien) modelMap.get("bien"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bien/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReferenceListItem> referenceListItems = (List<ReferenceListItem>) modelMap.get("listOfReferenceItems");
		assertEquals(2, referenceListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
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

		Bien bien = bienFactoryForTest.newBien();
		
		Exception exception = new RuntimeException("test exception");
		when(bienService.create(bien)).thenThrow(exception);
		
		// When
		String viewName = bienController.create(bien, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bien/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bien, (Bien) modelMap.get("bien"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/bien/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "bien.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ReferenceListItem> referenceListItems = (List<ReferenceListItem>) modelMap.get("listOfReferenceItems");
		assertEquals(2, referenceListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Bien bien = bienFactoryForTest.newBien();
		Long id = bien.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Bien bienSaved = new Bien();
		bienSaved.setId(id);
		when(bienService.update(bien)).thenReturn(bienSaved); 
		
		// When
		String viewName = bienController.update(bien, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/bien/form/"+bien.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bienSaved, (Bien) modelMap.get("bien"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Bien bien = bienFactoryForTest.newBien();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = bienController.update(bien, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bien/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bien, (Bien) modelMap.get("bien"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bien/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ReferenceListItem> referenceListItems = (List<ReferenceListItem>) modelMap.get("listOfReferenceItems");
		assertEquals(2, referenceListItems.size());
		
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

		Bien bien = bienFactoryForTest.newBien();
		
		Exception exception = new RuntimeException("test exception");
		when(bienService.update(bien)).thenThrow(exception);
		
		// When
		String viewName = bienController.update(bien, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("bien/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(bien, (Bien) modelMap.get("bien"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/bien/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "bien.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<TrancheListItem> trancheListItems = (List<TrancheListItem>) modelMap.get("listOfTrancheItems");
		assertEquals(2, trancheListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ReferenceListItem> referenceListItems = (List<ReferenceListItem>) modelMap.get("listOfReferenceItems");
		assertEquals(2, referenceListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Bien bien = bienFactoryForTest.newBien();
		Long id = bien.getId();
		
		// When
		String viewName = bienController.delete(redirectAttributes, id);
		
		// Then
		verify(bienService).delete(id);
		assertEquals("redirect:/bien", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Bien bien = bienFactoryForTest.newBien();
		Long id = bien.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(bienService).delete(id);
		
		// When
		String viewName = bienController.delete(redirectAttributes, id);
		
		// Then
		verify(bienService).delete(id);
		assertEquals("redirect:/bien", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "bien.error.delete", exception);
	}
	
	
}
