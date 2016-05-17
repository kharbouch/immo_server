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
import com.immo.bean.Profil;
import com.immo.test.ProfilFactoryForTest;

//--- Services 
import com.immo.business.service.ProfilService;


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
public class ProfilControllerTest {
	
	@InjectMocks
	private ProfilController profilController;
	@Mock
	private ProfilService profilService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ProfilFactoryForTest profilFactoryForTest = new ProfilFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Profil> list = new ArrayList<Profil>();
		when(profilService.findAll()).thenReturn(list);
		
		// When
		String viewName = profilController.list(model);
		
		// Then
		assertEquals("profil/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = profilController.formForCreate(model);
		
		// Then
		assertEquals("profil/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Profil)modelMap.get("profil")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/profil/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Profil profil = profilFactoryForTest.newProfil();
		Long id = profil.getId();
		when(profilService.findById(id)).thenReturn(profil);
		
		// When
		String viewName = profilController.formForUpdate(model, id);
		
		// Then
		assertEquals("profil/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profil, (Profil) modelMap.get("profil"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/profil/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Profil profil = profilFactoryForTest.newProfil();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Profil profilCreated = new Profil();
		when(profilService.create(profil)).thenReturn(profilCreated); 
		
		// When
		String viewName = profilController.create(profil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/profil/form/"+profil.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profilCreated, (Profil) modelMap.get("profil"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Profil profil = profilFactoryForTest.newProfil();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = profilController.create(profil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("profil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profil, (Profil) modelMap.get("profil"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/profil/create", modelMap.get("saveAction"));
		
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

		Profil profil = profilFactoryForTest.newProfil();
		
		Exception exception = new RuntimeException("test exception");
		when(profilService.create(profil)).thenThrow(exception);
		
		// When
		String viewName = profilController.create(profil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("profil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profil, (Profil) modelMap.get("profil"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/profil/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "profil.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Profil profil = profilFactoryForTest.newProfil();
		Long id = profil.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Profil profilSaved = new Profil();
		profilSaved.setId(id);
		when(profilService.update(profil)).thenReturn(profilSaved); 
		
		// When
		String viewName = profilController.update(profil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/profil/form/"+profil.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profilSaved, (Profil) modelMap.get("profil"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Profil profil = profilFactoryForTest.newProfil();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = profilController.update(profil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("profil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profil, (Profil) modelMap.get("profil"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/profil/update", modelMap.get("saveAction"));
		
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

		Profil profil = profilFactoryForTest.newProfil();
		
		Exception exception = new RuntimeException("test exception");
		when(profilService.update(profil)).thenThrow(exception);
		
		// When
		String viewName = profilController.update(profil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("profil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(profil, (Profil) modelMap.get("profil"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/profil/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "profil.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Profil profil = profilFactoryForTest.newProfil();
		Long id = profil.getId();
		
		// When
		String viewName = profilController.delete(redirectAttributes, id);
		
		// Then
		verify(profilService).delete(id);
		assertEquals("redirect:/profil", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Profil profil = profilFactoryForTest.newProfil();
		Long id = profil.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(profilService).delete(id);
		
		// When
		String viewName = profilController.delete(redirectAttributes, id);
		
		// Then
		verify(profilService).delete(id);
		assertEquals("redirect:/profil", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "profil.error.delete", exception);
	}
	
	
}
