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
import com.immo.bean.DossierClient;
import com.immo.bean.Client;
import com.immo.bean.Dossier;
import com.immo.test.DossierClientFactoryForTest;
import com.immo.test.ClientFactoryForTest;
import com.immo.test.DossierFactoryForTest;

//--- Services 
import com.immo.business.service.DossierClientService;
import com.immo.business.service.ClientService;
import com.immo.business.service.DossierService;

//--- List Items 
import com.immo.web.listitem.ClientListItem;
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
public class DossierClientControllerTest {
	
	@InjectMocks
	private DossierClientController dossierClientController;
	@Mock
	private DossierClientService dossierClientService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ClientService clientService; // Injected by Spring
	@Mock
	private DossierService dossierService; // Injected by Spring

	private DossierClientFactoryForTest dossierClientFactoryForTest = new DossierClientFactoryForTest();
	private ClientFactoryForTest clientFactoryForTest = new ClientFactoryForTest();
	private DossierFactoryForTest dossierFactoryForTest = new DossierFactoryForTest();

	List<Client> clients = new ArrayList<Client>();
	List<Dossier> dossiers = new ArrayList<Dossier>();

	private void givenPopulateModel() {
		Client client1 = clientFactoryForTest.newClient();
		Client client2 = clientFactoryForTest.newClient();
		List<Client> clients = new ArrayList<Client>();
		clients.add(client1);
		clients.add(client2);
		when(clientService.findAll()).thenReturn(clients);

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
		
		List<DossierClient> list = new ArrayList<DossierClient>();
		when(dossierClientService.findAll()).thenReturn(list);
		
		// When
		String viewName = dossierClientController.list(model);
		
		// Then
		assertEquals("dossierClient/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = dossierClientController.formForCreate(model);
		
		// Then
		assertEquals("dossierClient/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((DossierClient)modelMap.get("dossierClient")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/dossierClient/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ClientListItem> clientListItems = (List<ClientListItem>) modelMap.get("listOfClientItems");
		assertEquals(2, clientListItems.size());
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		Long id = dossierClient.getId();
		when(dossierClientService.findById(id)).thenReturn(dossierClient);
		
		// When
		String viewName = dossierClientController.formForUpdate(model, id);
		
		// Then
		assertEquals("dossierClient/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClient, (DossierClient) modelMap.get("dossierClient"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/dossierClient/update", modelMap.get("saveAction"));
		
		List<ClientListItem> clientListItems = (List<ClientListItem>) modelMap.get("listOfClientItems");
		assertEquals(2, clientListItems.size());
		
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DossierClient dossierClientCreated = new DossierClient();
		when(dossierClientService.create(dossierClient)).thenReturn(dossierClientCreated); 
		
		// When
		String viewName = dossierClientController.create(dossierClient, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/dossierClient/form/"+dossierClient.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClientCreated, (DossierClient) modelMap.get("dossierClient"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = dossierClientController.create(dossierClient, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossierClient/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClient, (DossierClient) modelMap.get("dossierClient"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/dossierClient/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ClientListItem> clientListItems = (List<ClientListItem>) modelMap.get("listOfClientItems");
		assertEquals(2, clientListItems.size());
		
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

		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		
		Exception exception = new RuntimeException("test exception");
		when(dossierClientService.create(dossierClient)).thenThrow(exception);
		
		// When
		String viewName = dossierClientController.create(dossierClient, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossierClient/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClient, (DossierClient) modelMap.get("dossierClient"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/dossierClient/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "dossierClient.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ClientListItem> clientListItems = (List<ClientListItem>) modelMap.get("listOfClientItems");
		assertEquals(2, clientListItems.size());
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		Long id = dossierClient.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DossierClient dossierClientSaved = new DossierClient();
		dossierClientSaved.setId(id);
		when(dossierClientService.update(dossierClient)).thenReturn(dossierClientSaved); 
		
		// When
		String viewName = dossierClientController.update(dossierClient, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/dossierClient/form/"+dossierClient.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClientSaved, (DossierClient) modelMap.get("dossierClient"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = dossierClientController.update(dossierClient, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossierClient/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClient, (DossierClient) modelMap.get("dossierClient"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/dossierClient/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ClientListItem> clientListItems = (List<ClientListItem>) modelMap.get("listOfClientItems");
		assertEquals(2, clientListItems.size());
		
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

		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		
		Exception exception = new RuntimeException("test exception");
		when(dossierClientService.update(dossierClient)).thenThrow(exception);
		
		// When
		String viewName = dossierClientController.update(dossierClient, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("dossierClient/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(dossierClient, (DossierClient) modelMap.get("dossierClient"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/dossierClient/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "dossierClient.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ClientListItem> clientListItems = (List<ClientListItem>) modelMap.get("listOfClientItems");
		assertEquals(2, clientListItems.size());
		
		@SuppressWarnings("unchecked")
		List<DossierListItem> dossierListItems = (List<DossierListItem>) modelMap.get("listOfDossierItems");
		assertEquals(2, dossierListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		Long id = dossierClient.getId();
		
		// When
		String viewName = dossierClientController.delete(redirectAttributes, id);
		
		// Then
		verify(dossierClientService).delete(id);
		assertEquals("redirect:/dossierClient", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DossierClient dossierClient = dossierClientFactoryForTest.newDossierClient();
		Long id = dossierClient.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(dossierClientService).delete(id);
		
		// When
		String viewName = dossierClientController.delete(redirectAttributes, id);
		
		// Then
		verify(dossierClientService).delete(id);
		assertEquals("redirect:/dossierClient", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "dossierClient.error.delete", exception);
	}
	
	
}
