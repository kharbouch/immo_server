/*
 * Created on 29 d�c. 2015 ( Time 20:53:42 )
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
import com.immo.bean.Reference;
import com.immo.bean.jpa.ReferenceEntity;
import com.immo.bean.jpa.ReferenceTypeEntity;
import com.immo.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ReferenceServiceMapperTest {

	private ReferenceServiceMapper referenceServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		referenceServiceMapper = new ReferenceServiceMapper();
		referenceServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ReferenceEntity' to 'Reference'
	 * @param referenceEntity
	 */
	@Test
	public void testMapReferenceEntityToReference() {
		// Given
		ReferenceEntity referenceEntity = new ReferenceEntity();
		referenceEntity.setLibelle(mockValues.nextString(100));
		referenceEntity.setOrdre(mockValues.nextLong());
		referenceEntity.setRefProjet(mockValues.nextLong());
		referenceEntity.setReferenceType(new ReferenceTypeEntity());
		referenceEntity.getReferenceType().setId(mockValues.nextLong());
		
		// When
		Reference reference = referenceServiceMapper.mapReferenceEntityToReference(referenceEntity);
		
		// Then
		assertEquals(referenceEntity.getLibelle(), reference.getLibelle());
		assertEquals(referenceEntity.getOrdre(), reference.getOrdre());
		assertEquals(referenceEntity.getRefProjet(), reference.getRefProjet());
		assertEquals(referenceEntity.getReferenceType().getId(), reference.getRefTypeReference());
	}
	
	/**
	 * Test : Mapping from 'Reference' to 'ReferenceEntity'
	 */
	@Test
	public void testMapReferenceToReferenceEntity() {
		// Given
		Reference reference = new Reference();
		reference.setLibelle(mockValues.nextString(100));
		reference.setOrdre(mockValues.nextLong());
		reference.setRefProjet(mockValues.nextLong());
		reference.setRefTypeReference(mockValues.nextLong());

		ReferenceEntity referenceEntity = new ReferenceEntity();
		
		// When
		referenceServiceMapper.mapReferenceToReferenceEntity(reference, referenceEntity);
		
		// Then
		assertEquals(reference.getLibelle(), referenceEntity.getLibelle());
		assertEquals(reference.getOrdre(), referenceEntity.getOrdre());
		assertEquals(reference.getRefProjet(), referenceEntity.getRefProjet());
		assertEquals(reference.getRefTypeReference(), referenceEntity.getReferenceType().getId());
	}

}