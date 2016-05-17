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
import com.immo.bean.Recette;
import com.immo.bean.Dossier;
import com.immo.test.RecetteFactoryForTest;
import com.immo.test.DossierFactoryForTest;

//--- Services 
import com.immo.business.service.RecetteService;
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
public class RecetteControllerTest {
	
	@InjectMocks
	private RecetteController recetteController;
	@Mock
	private RecetteService recetteService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DossierService dossierService; // Injected by Spring

	private RecetteFactoryForTest recetteFactoryForTest = new RecetteFactoryForTest();
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
		
		List<Recette> list = new ArrayList<Recette>();
		when(recetteService.findAll()).thenReturn(list);
		
		// When
		String viewName = recetteController.list(model);
		
		// Then
		assertEquals("recette/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = recetteController.formForCreate(model);
		
		// Then
		assertEquals("recette/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Recette)modelMap.get("recette")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/recette/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Recette recette = recetteFactoryForTest.newRecette();
		Long id = recette.getId();
		when(recetteService.findById(id)).thenReturn(recette);
		
		// When
		String viewName = recetteController.formForUpdate(model, id);
		
		// Then
		assertEquals("recette/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recette, (Recette) modelMap.get("recette"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/recette/update", modelMap.get("saveAction"));
		
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Recette recette = recetteFactoryForTest.newRecette();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Recette recetteCreated = new Recette();
		when(recetteService.create(recette)).thenReturn(recetteCreated); 
		
		// When
		String viewName = recetteController.create(recette, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/recette/form/"+recette.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recetteCreated, (Recette) modelMap.get("recette"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Recette recette = recetteFactoryForTest.newRecette();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = recetteController.create(recette, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("recette/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recette, (Recette) modelMap.get("recette"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/recette/create", modelMap.get("saveAction"));
		
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

		Recette recette = recetteFactoryForTest.newRecette();
		
		Exception exception = new RuntimeException("test exception");
		when(recetteService.create(recette)).thenThrow(exception);
		
		// When
		String viewName = recetteController.create(recette, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("recette/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recette, (Recette) modelMap.get("recette"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/recette/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "recette.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Recette recette = recetteFactoryForTest.newRecette();
		Long id = recette.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Recette recetteSaved = new Recette();
		recetteSaved.setId(id);
		when(recetteService.update(recette)).thenReturn(recetteSaved); 
		
		// When
		String viewName = recetteController.update(recette, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/recette/form/"+recette.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recetteSaved, (Recette) modelMap.get("recette"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Recette recette = recetteFactoryForTest.newRecette();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = recetteController.update(recette, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("recette/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recette, (Recette) modelMap.get("recette"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/recette/update", modelMap.get("saveAction"));
		
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

		Recette recette = recetteFactoryForTest.newRecette();
		
		Exception exception = new RuntimeException("test exception");
		when(recetteService.update(recette)).thenThrow(exception);
		
		// When
		String viewName = recetteController.update(recette, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("recette/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(recette, (Recette) modelMap.get("recette"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/recette/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "recette.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Recette recette = recetteFactoryForTest.newRecette();
		Long id = recette.getId();
		
		// When
		String viewName = recetteController.delete(redirectAttributes, id);
		
		// Then
		verify(recetteService).delete(id);
		assertEquals("redirect:/recette", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Recette recette = recetteFactoryForTest.newRecette();
		Long id = recette.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(recetteService).delete(id);
		
		// When
		String viewName = recetteController.delete(redirectAttributes, id);
		
		// Then
		verify(recetteService).delete(id);
		assertEquals("redirect:/recette", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "recette.error.delete", exception);
	}
	
	
}
