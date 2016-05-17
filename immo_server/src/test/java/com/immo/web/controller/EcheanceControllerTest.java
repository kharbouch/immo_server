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
import com.immo.bean.Echeance;
import com.immo.bean.Dossier;
import com.immo.test.EcheanceFactoryForTest;
import com.immo.test.DossierFactoryForTest;

//--- Services 
import com.immo.business.service.EcheanceService;
import com.immo.business.service.DossierService;

//--- List Items 
import com.immo.web.listitem.DossierListItem;

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
public class EcheanceControllerTest {
	
	@InjectMocks
	private EcheanceController echeanceController;
	@Mock
	private EcheanceService echeanceService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DossierService dossierService; // Injected by Spring

	private EcheanceFactoryForTest echeanceFactoryForTest = new EcheanceFactoryForTest();
	private DossierFactoryForTest dossierFactoryForTest = new DossierFactoryForTest();

	List<Dossier> dossiers = new ArrayList<Dossier>();

	private void givenPopulateModel() {
		Dossier dossier1 = dossierFactoryForTest.newDossier();
		Dossier dossier2 = dossierFactoryForTest.newDossier();
		List<Dossier> dossiers = new ArrayList<Dossier>();
		dossiers.add(dossier1);
		dossiers.add(dossier2);
		when(dossierService.findAll()).thenReturn(dossiers);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Echeance> list = new ArrayList<Echeance>();
		when(echeanceService.findAll()).thenReturn(list);
		
		// When
		String viewName = echeanceController.list(model);
		
		// Then
		assertEquals("echeance/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = echeanceController.formForCreate(model);
		
		// Then
		assertEquals("echeance/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Echeance)modelMap.get("echeance")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/echeance/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		Long id = echeance.getId();
		when(echeanceService.findById(id)).thenReturn(echeance);
		
		// When
		String viewName = echeanceController.formForUpdate(model, id);
		
		// Then
		assertEquals("echeance/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeance, (Echeance) modelMap.get("echeance"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/echeance/update", modelMap.get("saveAction"));
		
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Echeance echeanceCreated = new Echeance();
		when(echeanceService.create(echeance)).thenReturn(echeanceCreated); 
		
		// When
		String viewName = echeanceController.create(echeance, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/echeance/form/"+echeance.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeanceCreated, (Echeance) modelMap.get("echeance"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = echeanceController.create(echeance, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("echeance/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeance, (Echeance) modelMap.get("echeance"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/echeance/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
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

		Echeance echeance = echeanceFactoryForTest.newEcheance();
		
		Exception exception = new RuntimeException("test exception");
		when(echeanceService.create(echeance)).thenThrow(exception);
		
		// When
		String viewName = echeanceController.create(echeance, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("echeance/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeance, (Echeance) modelMap.get("echeance"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/echeance/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "echeance.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		Long id = echeance.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Echeance echeanceSaved = new Echeance();
		echeanceSaved.setId(id);
		when(echeanceService.update(echeance)).thenReturn(echeanceSaved); 
		
		// When
		String viewName = echeanceController.update(echeance, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/echeance/form/"+echeance.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeanceSaved, (Echeance) modelMap.get("echeance"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = echeanceController.update(echeance, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("echeance/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeance, (Echeance) modelMap.get("echeance"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/echeance/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
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

		Echeance echeance = echeanceFactoryForTest.newEcheance();
		
		Exception exception = new RuntimeException("test exception");
		when(echeanceService.update(echeance)).thenThrow(exception);
		
		// When
		String viewName = echeanceController.update(echeance, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("echeance/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(echeance, (Echeance) modelMap.get("echeance"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/echeance/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "echeance.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		Long id = echeance.getId();
		
		// When
		String viewName = echeanceController.delete(redirectAttributes, id);
		
		// Then
		verify(echeanceService).delete(id);
		assertEquals("redirect:/echeance", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Echeance echeance = echeanceFactoryForTest.newEcheance();
		Long id = echeance.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(echeanceService).delete(id);
		
		// When
		String viewName = echeanceController.delete(redirectAttributes, id);
		
		// Then
		verify(echeanceService).delete(id);
		assertEquals("redirect:/echeance", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "echeance.error.delete", exception);
	}
	
	
}
