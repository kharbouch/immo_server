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
import com.immo.bean.ModuleProfil;
import com.immo.bean.Module;
import com.immo.bean.Profil;
import com.immo.test.ModuleProfilFactoryForTest;
import com.immo.test.ModuleFactoryForTest;
import com.immo.test.ProfilFactoryForTest;

//--- Services 
import com.immo.business.service.ModuleProfilService;
import com.immo.business.service.ModuleService;
import com.immo.business.service.ProfilService;

//--- List Items 
import com.immo.web.listitem.ModuleListItem;
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
public class ModuleProfilControllerTest {
	
	@InjectMocks
	private ModuleProfilController moduleProfilController;
	@Mock
	private ModuleProfilService moduleProfilService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ModuleService moduleService; // Injected by Spring
	@Mock
	private ProfilService profilService; // Injected by Spring

	private ModuleProfilFactoryForTest moduleProfilFactoryForTest = new ModuleProfilFactoryForTest();
	private ModuleFactoryForTest moduleFactoryForTest = new ModuleFactoryForTest();
	private ProfilFactoryForTest profilFactoryForTest = new ProfilFactoryForTest();

	List<Module> modules = new ArrayList<Module>();
	List<Profil> profils = new ArrayList<Profil>();

	private void givenPopulateModel() {
		Module module1 = moduleFactoryForTest.newModule();
		Module module2 = moduleFactoryForTest.newModule();
		List<Module> modules = new ArrayList<Module>();
		modules.add(module1);
		modules.add(module2);
		when(moduleService.findAll()).thenReturn(modules);

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
		
		List<ModuleProfil> list = new ArrayList<ModuleProfil>();
		when(moduleProfilService.findAll()).thenReturn(list);
		
		// When
		String viewName = moduleProfilController.list(model);
		
		// Then
		assertEquals("moduleProfil/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = moduleProfilController.formForCreate(model);
		
		// Then
		assertEquals("moduleProfil/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ModuleProfil)modelMap.get("moduleProfil")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/moduleProfil/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ModuleListItem> moduleListItems = (List<ModuleListItem>) modelMap.get("listOfModuleItems");
		assertEquals(2, moduleListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		Long id = moduleProfil.getId();
		when(moduleProfilService.findById(id)).thenReturn(moduleProfil);
		
		// When
		String viewName = moduleProfilController.formForUpdate(model, id);
		
		// Then
		assertEquals("moduleProfil/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfil, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/moduleProfil/update", modelMap.get("saveAction"));
		
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
		List<ModuleListItem> moduleListItems = (List<ModuleListItem>) modelMap.get("listOfModuleItems");
		assertEquals(2, moduleListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ModuleProfil moduleProfilCreated = new ModuleProfil();
		when(moduleProfilService.create(moduleProfil)).thenReturn(moduleProfilCreated); 
		
		// When
		String viewName = moduleProfilController.create(moduleProfil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/moduleProfil/form/"+moduleProfil.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfilCreated, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = moduleProfilController.create(moduleProfil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("moduleProfil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfil, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/moduleProfil/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ModuleListItem> moduleListItems = (List<ModuleListItem>) modelMap.get("listOfModuleItems");
		assertEquals(2, moduleListItems.size());
		
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

		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		
		Exception exception = new RuntimeException("test exception");
		when(moduleProfilService.create(moduleProfil)).thenThrow(exception);
		
		// When
		String viewName = moduleProfilController.create(moduleProfil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("moduleProfil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfil, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/moduleProfil/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "moduleProfil.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ModuleListItem> moduleListItems = (List<ModuleListItem>) modelMap.get("listOfModuleItems");
		assertEquals(2, moduleListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		Long id = moduleProfil.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ModuleProfil moduleProfilSaved = new ModuleProfil();
		moduleProfilSaved.setId(id);
		when(moduleProfilService.update(moduleProfil)).thenReturn(moduleProfilSaved); 
		
		// When
		String viewName = moduleProfilController.update(moduleProfil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/moduleProfil/form/"+moduleProfil.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfilSaved, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = moduleProfilController.update(moduleProfil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("moduleProfil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfil, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/moduleProfil/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ModuleListItem> moduleListItems = (List<ModuleListItem>) modelMap.get("listOfModuleItems");
		assertEquals(2, moduleListItems.size());
		
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

		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		
		Exception exception = new RuntimeException("test exception");
		when(moduleProfilService.update(moduleProfil)).thenThrow(exception);
		
		// When
		String viewName = moduleProfilController.update(moduleProfil, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("moduleProfil/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleProfil, (ModuleProfil) modelMap.get("moduleProfil"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/moduleProfil/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "moduleProfil.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ProfilListItem> profilListItems = (List<ProfilListItem>) modelMap.get("listOfProfilItems");
		assertEquals(2, profilListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ModuleListItem> moduleListItems = (List<ModuleListItem>) modelMap.get("listOfModuleItems");
		assertEquals(2, moduleListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		Long id = moduleProfil.getId();
		
		// When
		String viewName = moduleProfilController.delete(redirectAttributes, id);
		
		// Then
		verify(moduleProfilService).delete(id);
		assertEquals("redirect:/moduleProfil", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ModuleProfil moduleProfil = moduleProfilFactoryForTest.newModuleProfil();
		Long id = moduleProfil.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(moduleProfilService).delete(id);
		
		// When
		String viewName = moduleProfilController.delete(redirectAttributes, id);
		
		// Then
		verify(moduleProfilService).delete(id);
		assertEquals("redirect:/moduleProfil", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "moduleProfil.error.delete", exception);
	}
	
	
}
