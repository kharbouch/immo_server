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
import com.immo.bean.DossierClient;
import com.immo.bean.jpa.DossierClientEntity;
import com.immo.bean.jpa.ClientEntity;
import com.immo.bean.jpa.DossierEntity;
import com.immo.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class DossierClientServiceMapperTest {

	private DossierClientServiceMapper dossierClientServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		dossierClientServiceMapper = new DossierClientServiceMapper();
		dossierClientServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'DossierClientEntity' to 'DossierClient'
	 * @param dossierClientEntity
	 */
	@Test
	public void testMapDossierClientEntityToDossierClient() {
		// Given
		DossierClientEntity dossierClientEntity = new DossierClientEntity();
		dossierClientEntity.setQuote(mockValues.nextLong());
		dossierClientEntity.setClient(new ClientEntity());
		dossierClientEntity.getClient().setId(mockValues.nextLong());
		dossierClientEntity.setDossier(new DossierEntity());
		dossierClientEntity.getDossier().setId(mockValues.nextLong());
		
		// When
		DossierClient dossierClient = dossierClientServiceMapper.mapDossierClientEntityToDossierClient(dossierClientEntity);
		
		// Then
		assertEquals(dossierClientEntity.getQuote(), dossierClient.getQuote());
		assertEquals(dossierClientEntity.getClient().getId(), dossierClient.getRefClient());
		assertEquals(dossierClientEntity.getDossier().getId(), dossierClient.getRefDossier());
	}
	
	/**
	 * Test : Mapping from 'DossierClient' to 'DossierClientEntity'
	 */
	@Test
	public void testMapDossierClientToDossierClientEntity() {
		// Given
		DossierClient dossierClient = new DossierClient();
		dossierClient.setQuote(mockValues.nextLong());
		dossierClient.setRefClient(mockValues.nextLong());
		dossierClient.setRefDossier(mockValues.nextLong());

		DossierClientEntity dossierClientEntity = new DossierClientEntity();
		
		// When
		dossierClientServiceMapper.mapDossierClientToDossierClientEntity(dossierClient, dossierClientEntity);
		
		// Then
		assertEquals(dossierClient.getQuote(), dossierClientEntity.getQuote());
		assertEquals(dossierClient.getRefClient(), dossierClientEntity.getClient().getId());
		assertEquals(dossierClient.getRefDossier(), dossierClientEntity.getDossier().getId());
	}

}