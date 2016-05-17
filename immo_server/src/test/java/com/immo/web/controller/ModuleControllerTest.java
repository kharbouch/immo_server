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
import com.immo.bean.Module;
import com.immo.test.ModuleFactoryForTest;

//--- Services 
import com.immo.business.service.ModuleService;


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
public class ModuleControllerTest {
	
	@InjectMocks
	private ModuleController moduleController;
	@Mock
	private ModuleService moduleService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ModuleFactoryForTest moduleFactoryForTest = new ModuleFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Module> list = new ArrayList<Module>();
		when(moduleService.findAll()).thenReturn(list);
		
		// When
		String viewName = moduleController.list(model);
		
		// Then
		assertEquals("module/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = moduleController.formForCreate(model);
		
		// Then
		assertEquals("module/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Module)modelMap.get("module")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/module/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Module module = moduleFactoryForTest.newModule();
		Long id = module.getId();
		when(moduleService.findById(id)).thenReturn(module);
		
		// When
		String viewName = moduleController.formForUpdate(model, id);
		
		// Then
		assertEquals("module/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(module, (Module) modelMap.get("module"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/module/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Module module = moduleFactoryForTest.newModule();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Module moduleCreated = new Module();
		when(moduleService.create(module)).thenReturn(moduleCreated); 
		
		// When
		String viewName = moduleController.create(module, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/module/form/"+module.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleCreated, (Module) modelMap.get("module"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Module module = moduleFactoryForTest.newModule();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = moduleController.create(module, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("module/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(module, (Module) modelMap.get("module"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/module/create", modelMap.get("saveAction"));
		
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

		Module module = moduleFactoryForTest.newModule();
		
		Exception exception = new RuntimeException("test exception");
		when(moduleService.create(module)).thenThrow(exception);
		
		// When
		String viewName = moduleController.create(module, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("module/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(module, (Module) modelMap.get("module"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/module/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "module.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Module module = moduleFactoryForTest.newModule();
		Long id = module.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Module moduleSaved = new Module();
		moduleSaved.setId(id);
		when(moduleService.update(module)).thenReturn(moduleSaved); 
		
		// When
		String viewName = moduleController.update(module, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/module/form/"+module.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(moduleSaved, (Module) modelMap.get("module"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Module module = moduleFactoryForTest.newModule();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = moduleController.update(module, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("module/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(module, (Module) modelMap.get("module"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/module/update", modelMap.get("saveAction"));
		
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

		Module module = moduleFactoryForTest.newModule();
		
		Exception exception = new RuntimeException("test exception");
		when(moduleService.update(module)).thenThrow(exception);
		
		// When
		String viewName = moduleController.update(module, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("module/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(module, (Module) modelMap.get("module"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/module/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "module.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Module module = moduleFactoryForTest.newModule();
		Long id = module.getId();
		
		// When
		String viewName = moduleController.delete(redirectAttributes, id);
		
		// Then
		verify(moduleService).delete(id);
		assertEquals("redirect:/module", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Module module = moduleFactoryForTest.newModule();
		Long id = module.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(moduleService).delete(id);
		
		// When
		String viewName = moduleController.delete(redirectAttributes, id);
		
		// Then
		verify(moduleService).delete(id);
		assertEquals("redirect:/module", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "module.error.delete", exception);
	}
	
	
}
