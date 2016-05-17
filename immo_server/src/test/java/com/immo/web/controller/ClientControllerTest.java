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
import com.immo.bean.Client;
import com.immo.test.ClientFactoryForTest;

//--- Services 
import com.immo.business.service.ClientService;


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
public class ClientControllerTest {
	
	@InjectMocks
	private ClientController clientController;
	@Mock
	private ClientService clientService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ClientFactoryForTest clientFactoryForTest = new ClientFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Client> list = new ArrayList<Client>();
		when(clientService.findAll()).thenReturn(list);
		
		// When
		String viewName = clientController.list(model);
		
		// Then
		assertEquals("client/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = clientController.formForCreate(model);
		
		// Then
		assertEquals("client/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Client)modelMap.get("client")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/client/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Client client = clientFactoryForTest.newClient();
		Long id = client.getId();
		when(clientService.findById(id)).thenReturn(client);
		
		// When
		String viewName = clientController.formForUpdate(model, id);
		
		// Then
		assertEquals("client/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(client, (Client) modelMap.get("client"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/client/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Client client = clientFactoryForTest.newClient();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Client clientCreated = new Client();
		when(clientService.create(client)).thenReturn(clientCreated); 
		
		// When
		String viewName = clientController.create(client, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/client/form/"+client.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(clientCreated, (Client) modelMap.get("client"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Client client = clientFactoryForTest.newClient();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = clientController.create(client, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("client/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(client, (Client) modelMap.get("client"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/client/create", modelMap.get("saveAction"));
		
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

		Client client = clientFactoryForTest.newClient();
		
		Exception exception = new RuntimeException("test exception");
		when(clientService.create(client)).thenThrow(exception);
		
		// When
		String viewName = clientController.create(client, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("client/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(client, (Client) modelMap.get("client"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/client/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "client.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Client client = clientFactoryForTest.newClient();
		Long id = client.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Client clientSaved = new Client();
		clientSaved.setId(id);
		when(clientService.update(client)).thenReturn(clientSaved); 
		
		// When
		String viewName = clientController.update(client, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/client/form/"+client.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(clientSaved, (Client) modelMap.get("client"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Client client = clientFactoryForTest.newClient();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = clientController.update(client, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("client/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(client, (Client) modelMap.get("client"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/client/update", modelMap.get("saveAction"));
		
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

		Client client = clientFactoryForTest.newClient();
		
		Exception exception = new RuntimeException("test exception");
		when(clientService.update(client)).thenThrow(exception);
		
		// When
		String viewName = clientController.update(client, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("client/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(client, (Client) modelMap.get("client"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/client/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "client.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Client client = clientFactoryForTest.newClient();
		Long id = client.getId();
		
		// When
		String viewName = clientController.delete(redirectAttributes, id);
		
		// Then
		verify(clientService).delete(id);
		assertEquals("redirect:/client", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Client client = clientFactoryForTest.newClient();
		Long id = client.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(clientService).delete(id);
		
		// When
		String viewName = clientController.delete(redirectAttributes, id);
		
		// Then
		verify(clientService).delete(id);
		assertEquals("redirect:/client", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "client.error.delete", exception);
	}
	
	
}
