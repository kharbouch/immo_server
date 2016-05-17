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
import com.immo.bean.ReferenceType;
import com.immo.bean.jpa.ReferenceTypeEntity;
import com.immo.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ReferenceTypeServiceMapperTest {

	private ReferenceTypeServiceMapper referenceTypeServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		referenceTypeServiceMapper = new ReferenceTypeServiceMapper();
		referenceTypeServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ReferenceTypeEntity' to 'ReferenceType'
	 * @param referenceTypeEntity
	 */
	@Test
	public void testMapReferenceTypeEntityToReferenceType() {
		// Given
		ReferenceTypeEntity referenceTypeEntity = new ReferenceTypeEntity();
		referenceTypeEntity.setLibelle(mockValues.nextString(100));
		
		// When
		ReferenceType referenceType = referenceTypeServiceMapper.mapReferenceTypeEntityToReferenceType(referenceTypeEntity);
		
		// Then
		assertEquals(referenceTypeEntity.getLibelle(), referenceType.getLibelle());
	}
	
	/**
	 * Test : Mapping from 'ReferenceType' to 'ReferenceTypeEntity'
	 */
	@Test
	public void testMapReferenceTypeToReferenceTypeEntity() {
		// Given
		ReferenceType referenceType = new ReferenceType();
		referenceType.setLibelle(mockValues.nextString(100));

		ReferenceTypeEntity referenceTypeEntity = new ReferenceTypeEntity();
		
		// When
		referenceTypeServiceMapper.mapReferenceTypeToReferenceTypeEntity(referenceType, referenceTypeEntity);
		
		// Then
		assertEquals(referenceType.getLibelle(), referenceTypeEntity.getLibelle());
	}

}