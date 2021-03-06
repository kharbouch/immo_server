/*
 * Created on 29 d�c. 2015 ( Time 20:53:41 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.immo.bean.Client;
import com.immo.bean.jpa.ClientEntity;
import java.util.Date;
import java.util.List;
import com.immo.business.service.mapping.ClientServiceMapper;
import com.immo.data.repository.jpa.ClientJpaRepository;
import com.immo.test.ClientFactoryForTest;
import com.immo.test.ClientEntityFactoryForTest;
import com.immo.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ClientService
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

	@InjectMocks
	private ClientServiceImpl clientService;
	@Mock
	private ClientJpaRepository clientJpaRepository;
	@Mock
	private ClientServiceMapper clientServiceMapper;
	
	private ClientFactoryForTest clientFactoryForTest = new ClientFactoryForTest();

	private ClientEntityFactoryForTest clientEntityFactoryForTest = new ClientEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		ClientEntity clientEntity = clientJpaRepository.findOne(id);
		
		Client client = clientFactoryForTest.newClient();
		when(clientServiceMapper.mapClientEntityToClient(clientEntity)).thenReturn(client);

		// When
		Client clientFound = clientService.findById(id);

		// Then
		assertEquals(client.getId(),clientFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<ClientEntity> clientEntitys = new ArrayList<ClientEntity>();
		ClientEntity clientEntity1 = clientEntityFactoryForTest.newClientEntity();
		clientEntitys.add(clientEntity1);
		ClientEntity clientEntity2 = clientEntityFactoryForTest.newClientEntity();
		clientEntitys.add(clientEntity2);
		when(clientJpaRepository.findAll()).thenReturn(clientEntitys);
		
		Client client1 = clientFactoryForTest.newClient();
		when(clientServiceMapper.mapClientEntityToClient(clientEntity1)).thenReturn(client1);
		Client client2 = clientFactoryForTest.newClient();
		when(clientServiceMapper.mapClientEntityToClient(clientEntity2)).thenReturn(client2);

		// When
		List<Client> clientsFounds = clientService.findAll();

		// Then
		assertTrue(client1 == clientsFounds.get(0));
		assertTrue(client2 == clientsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Client client = clientFactoryForTest.newClient();

		ClientEntity clientEntity = clientEntityFactoryForTest.newClientEntity();
		when(clientJpaRepository.findOne(client.getId())).thenReturn(null);
		
		clientEntity = new ClientEntity();
		clientServiceMapper.mapClientToClientEntity(client, clientEntity);
		ClientEntity clientEntitySaved = clientJpaRepository.save(clientEntity);
		
		Client clientSaved = clientFactoryForTest.newClient();
		when(clientServiceMapper.mapClientEntityToClient(clientEntitySaved)).thenReturn(clientSaved);

		// When
		Client clientResult = clientService.create(client);

		// Then
		assertTrue(clientResult == clientSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Client client = clientFactoryForTest.newClient();

		ClientEntity clientEntity = clientEntityFactoryForTest.newClientEntity();
		when(clientJpaRepository.findOne(client.getId())).thenReturn(clientEntity);

		// When
		Exception exception = null;
		try {
			clientService.create(client);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Client client = clientFactoryForTest.newClient();

		ClientEntity clientEntity = clientEntityFactoryForTest.newClientEntity();
		when(clientJpaRepository.findOne(client.getId())).thenReturn(clientEntity);
		
		ClientEntity clientEntitySaved = clientEntityFactoryForTest.newClientEntity();
		when(clientJpaRepository.save(clientEntity)).thenReturn(clientEntitySaved);
		
		Client clientSaved = clientFactoryForTest.newClient();
		when(clientServiceMapper.mapClientEntityToClient(clientEntitySaved)).thenReturn(clientSaved);

		// When
		Client clientResult = clientService.update(client);

		// Then
		verify(clientServiceMapper).mapClientToClientEntity(client, clientEntity);
		assertTrue(clientResult == clientSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		clientService.delete(id);

		// Then
		verify(clientJpaRepository).delete(id);
		
	}

}
