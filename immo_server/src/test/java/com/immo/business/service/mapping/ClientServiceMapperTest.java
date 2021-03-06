/*
 * Created on 29 d�c. 2015 ( Time 20:53:41 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.immo.bean.Client;
import com.immo.bean.jpa.ClientEntity;
import com.immo.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ClientServiceMapperTest {

	private ClientServiceMapper clientServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		clientServiceMapper = new ClientServiceMapper();
		clientServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ClientEntity' to 'Client'
	 * @param clientEntity
	 */
	@Test
	public void testMapClientEntityToClient() {
		// Given
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setNomClient(mockValues.nextString(200));
		clientEntity.setPrenomClient(mockValues.nextString(200));
		clientEntity.setCinClient(mockValues.nextString(200));
		clientEntity.setDateExpiration(mockValues.nextDate());
		clientEntity.setAdresse(mockValues.nextString(200));
		clientEntity.setRefProfession(mockValues.nextLong());
		clientEntity.setDateNaissance(mockValues.nextDate());
		clientEntity.setLieuNaissance(mockValues.nextString(30));
		clientEntity.setRefEtatCivil(mockValues.nextLong());
		clientEntity.setTel1(mockValues.nextString(100));
		clientEntity.setTel2(mockValues.nextString(100));
		clientEntity.setRevenu(mockValues.nextLong());
		clientEntity.setEmail(mockValues.nextString(100));
		clientEntity.setRefNationalite(mockValues.nextLong());
		clientEntity.setRefVille(mockValues.nextLong());
		clientEntity.setQuote(mockValues.nextLong());
		
		// When
		Client client = clientServiceMapper.mapClientEntityToClient(clientEntity);
		
		// Then
		assertEquals(clientEntity.getNomClient(), client.getNomClient());
		assertEquals(clientEntity.getPrenomClient(), client.getPrenomClient());
		assertEquals(clientEntity.getCinClient(), client.getCinClient());
		assertEquals(clientEntity.getDateExpiration(), client.getDateExpiration());
		assertEquals(clientEntity.getAdresse(), client.getAdresse());
		assertEquals(clientEntity.getRefProfession(), client.getRefProfession());
		assertEquals(clientEntity.getDateNaissance(), client.getDateNaissance());
		assertEquals(clientEntity.getLieuNaissance(), client.getLieuNaissance());
		assertEquals(clientEntity.getRefEtatCivil(), client.getRefEtatCivil());
		assertEquals(clientEntity.getTel1(), client.getTel1());
		assertEquals(clientEntity.getTel2(), client.getTel2());
		assertEquals(clientEntity.getRevenu(), client.getRevenu());
		assertEquals(clientEntity.getEmail(), client.getEmail());
		assertEquals(clientEntity.getRefNationalite(), client.getRefNationalite());
		assertEquals(clientEntity.getRefVille(), client.getRefVille());
		assertEquals(clientEntity.getQuote(), client.getQuote());
	}
	
	/**
	 * Test : Mapping from 'Client' to 'ClientEntity'
	 */
	@Test
	public void testMapClientToClientEntity() {
		// Given
		Client client = new Client();
		client.setNomClient(mockValues.nextString(200));
		client.setPrenomClient(mockValues.nextString(200));
		client.setCinClient(mockValues.nextString(200));
		client.setDateExpiration(mockValues.nextDate());
		client.setAdresse(mockValues.nextString(200));
		client.setRefProfession(mockValues.nextLong());
		client.setDateNaissance(mockValues.nextDate());
		client.setLieuNaissance(mockValues.nextString(30));
		client.setRefEtatCivil(mockValues.nextLong());
		client.setTel1(mockValues.nextString(100));
		client.setTel2(mockValues.nextString(100));
		client.setRevenu(mockValues.nextLong());
		client.setEmail(mockValues.nextString(100));
		client.setRefNationalite(mockValues.nextLong());
		client.setRefVille(mockValues.nextLong());
		client.setQuote(mockValues.nextLong());

		ClientEntity clientEntity = new ClientEntity();
		
		// When
		clientServiceMapper.mapClientToClientEntity(client, clientEntity);
		
		// Then
		assertEquals(client.getNomClient(), clientEntity.getNomClient());
		assertEquals(client.getPrenomClient(), clientEntity.getPrenomClient());
		assertEquals(client.getCinClient(), clientEntity.getCinClient());
		assertEquals(client.getDateExpiration(), clientEntity.getDateExpiration());
		assertEquals(client.getAdresse(), clientEntity.getAdresse());
		assertEquals(client.getRefProfession(), clientEntity.getRefProfession());
		assertEquals(client.getDateNaissance(), clientEntity.getDateNaissance());
		assertEquals(client.getLieuNaissance(), clientEntity.getLieuNaissance());
		assertEquals(client.getRefEtatCivil(), clientEntity.getRefEtatCivil());
		assertEquals(client.getTel1(), clientEntity.getTel1());
		assertEquals(client.getTel2(), clientEntity.getTel2());
		assertEquals(client.getRevenu(), clientEntity.getRevenu());
		assertEquals(client.getEmail(), clientEntity.getEmail());
		assertEquals(client.getRefNationalite(), clientEntity.getRefNationalite());
		assertEquals(client.getRefVille(), clientEntity.getRefVille());
		assertEquals(client.getQuote(), clientEntity.getQuote());
	}

}