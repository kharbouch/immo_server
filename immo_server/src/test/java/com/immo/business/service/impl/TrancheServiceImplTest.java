/*
 * Created on 29 d�c. 2015 ( Time 20:53:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.immo.bean.Tranche;
import com.immo.bean.jpa.TrancheEntity;
import java.util.Date;
import java.util.List;
import com.immo.business.service.mapping.TrancheServiceMapper;
import com.immo.data.repository.jpa.TrancheJpaRepository;
import com.immo.test.TrancheFactoryForTest;
import com.immo.test.TrancheEntityFactoryForTest;
import com.immo.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of TrancheService
 */
@RunWith(MockitoJUnitRunner.class)
public class TrancheServiceImplTest {

	@InjectMocks
	private TrancheServiceImpl trancheService;
	@Mock
	private TrancheJpaRepository trancheJpaRepository;
	@Mock
	private TrancheServiceMapper trancheServiceMapper;
	
	private TrancheFactoryForTest trancheFactoryForTest = new TrancheFactoryForTest();

	private TrancheEntityFactoryForTest trancheEntityFactoryForTest = new TrancheEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		TrancheEntity trancheEntity = trancheJpaRepository.findOne(id);
		
		Tranche tranche = trancheFactoryForTest.newTranche();
		when(trancheServiceMapper.mapTrancheEntityToTranche(trancheEntity)).thenReturn(tranche);

		// When
		Tranche trancheFound = trancheService.findById(id);

		// Then
		assertEquals(tranche.getId(),trancheFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<TrancheEntity> trancheEntitys = new ArrayList<TrancheEntity>();
		TrancheEntity trancheEntity1 = trancheEntityFactoryForTest.newTrancheEntity();
		trancheEntitys.add(trancheEntity1);
		TrancheEntity trancheEntity2 = trancheEntityFactoryForTest.newTrancheEntity();
		trancheEntitys.add(trancheEntity2);
		when(trancheJpaRepository.findAll()).thenReturn(trancheEntitys);
		
		Tranche tranche1 = trancheFactoryForTest.newTranche();
		when(trancheServiceMapper.mapTrancheEntityToTranche(trancheEntity1)).thenReturn(tranche1);
		Tranche tranche2 = trancheFactoryForTest.newTranche();
		when(trancheServiceMapper.mapTrancheEntityToTranche(trancheEntity2)).thenReturn(tranche2);

		// When
		List<Tranche> tranchesFounds = trancheService.findAll();

		// Then
		assertTrue(tranche1 == tranchesFounds.get(0));
		assertTrue(tranche2 == tranchesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Tranche tranche = trancheFactoryForTest.newTranche();

		TrancheEntity trancheEntity = trancheEntityFactoryForTest.newTrancheEntity();
		when(trancheJpaRepository.findOne(tranche.getId())).thenReturn(null);
		
		trancheEntity = new TrancheEntity();
		trancheServiceMapper.mapTrancheToTrancheEntity(tranche, trancheEntity);
		TrancheEntity trancheEntitySaved = trancheJpaRepository.save(trancheEntity);
		
		Tranche trancheSaved = trancheFactoryForTest.newTranche();
		when(trancheServiceMapper.mapTrancheEntityToTranche(trancheEntitySaved)).thenReturn(trancheSaved);

		// When
		Tranche trancheResult = trancheService.create(tranche);

		// Then
		assertTrue(trancheResult == trancheSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Tranche tranche = trancheFactoryForTest.newTranche();

		TrancheEntity trancheEntity = trancheEntityFactoryForTest.newTrancheEntity();
		when(trancheJpaRepository.findOne(tranche.getId())).thenReturn(trancheEntity);

		// When
		Exception exception = null;
		try {
			trancheService.create(tranche);
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
		Tranche tranche = trancheFactoryForTest.newTranche();

		TrancheEntity trancheEntity = trancheEntityFactoryForTest.newTrancheEntity();
		when(trancheJpaRepository.findOne(tranche.getId())).thenReturn(trancheEntity);
		
		TrancheEntity trancheEntitySaved = trancheEntityFactoryForTest.newTrancheEntity();
		when(trancheJpaRepository.save(trancheEntity)).thenReturn(trancheEntitySaved);
		
		Tranche trancheSaved = trancheFactoryForTest.newTranche();
		when(trancheServiceMapper.mapTrancheEntityToTranche(trancheEntitySaved)).thenReturn(trancheSaved);

		// When
		Tranche trancheResult = trancheService.update(tranche);

		// Then
		verify(trancheServiceMapper).mapTrancheToTrancheEntity(tranche, trancheEntity);
		assertTrue(trancheResult == trancheSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		trancheService.delete(id);

		// Then
		verify(trancheJpaRepository).delete(id);
		
	}

}
