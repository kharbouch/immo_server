/*
 * Created on 29 d�c. 2015 ( Time 20:53:43 )
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
import com.immo.bean.UtilisateurTranche;
import com.immo.bean.jpa.UtilisateurTrancheEntity;
import com.immo.bean.jpa.TrancheEntity;
import com.immo.bean.jpa.UtilisateurEntity;
import com.immo.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class UtilisateurTrancheServiceMapperTest {

	private UtilisateurTrancheServiceMapper utilisateurTrancheServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		utilisateurTrancheServiceMapper = new UtilisateurTrancheServiceMapper();
		utilisateurTrancheServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'UtilisateurTrancheEntity' to 'UtilisateurTranche'
	 * @param utilisateurTrancheEntity
	 */
	@Test
	public void testMapUtilisateurTrancheEntityToUtilisateurTranche() {
		// Given
		UtilisateurTrancheEntity utilisateurTrancheEntity = new UtilisateurTrancheEntity();
		utilisateurTrancheEntity.setDateCreation(mockValues.nextDate());
		utilisateurTrancheEntity.setDateMaj(mockValues.nextDate());
		utilisateurTrancheEntity.setTranche(new TrancheEntity());
		utilisateurTrancheEntity.getTranche().setId(mockValues.nextLong());
		utilisateurTrancheEntity.setUtilisateur(new UtilisateurEntity());
		utilisateurTrancheEntity.getUtilisateur().setId(mockValues.nextLong());
		
		// When
		UtilisateurTranche utilisateurTranche = utilisateurTrancheServiceMapper.mapUtilisateurTrancheEntityToUtilisateurTranche(utilisateurTrancheEntity);
		
		// Then
		assertEquals(utilisateurTrancheEntity.getDateCreation(), utilisateurTranche.getDateCreation());
		assertEquals(utilisateurTrancheEntity.getDateMaj(), utilisateurTranche.getDateMaj());
		assertEquals(utilisateurTrancheEntity.getTranche().getId(), utilisateurTranche.getRefTranche());
		assertEquals(utilisateurTrancheEntity.getUtilisateur().getId(), utilisateurTranche.getRefUtilisateur());
	}
	
	/**
	 * Test : Mapping from 'UtilisateurTranche' to 'UtilisateurTrancheEntity'
	 */
	@Test
	public void testMapUtilisateurTrancheToUtilisateurTrancheEntity() {
		// Given
		UtilisateurTranche utilisateurTranche = new UtilisateurTranche();
		utilisateurTranche.setDateCreation(mockValues.nextDate());
		utilisateurTranche.setDateMaj(mockValues.nextDate());
		utilisateurTranche.setRefTranche(mockValues.nextLong());
		utilisateurTranche.setRefUtilisateur(mockValues.nextLong());

		UtilisateurTrancheEntity utilisateurTrancheEntity = new UtilisateurTrancheEntity();
		
		// When
		utilisateurTrancheServiceMapper.mapUtilisateurTrancheToUtilisateurTrancheEntity(utilisateurTranche, utilisateurTrancheEntity);
		
		// Then
		assertEquals(utilisateurTranche.getDateCreation(), utilisateurTrancheEntity.getDateCreation());
		assertEquals(utilisateurTranche.getDateMaj(), utilisateurTrancheEntity.getDateMaj());
		assertEquals(utilisateurTranche.getRefTranche(), utilisateurTrancheEntity.getTranche().getId());
		assertEquals(utilisateurTranche.getRefUtilisateur(), utilisateurTrancheEntity.getUtilisateur().getId());
	}

}