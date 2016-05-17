/*
 * Created on 29 d�c. 2015 ( Time 20:53:43 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.immo.bean.UtilisateurTranche;
import com.immo.bean.jpa.UtilisateurTrancheEntity;
import java.util.Date;
import com.immo.business.service.mapping.UtilisateurTrancheServiceMapper;
import com.immo.data.repository.jpa.UtilisateurTrancheJpaRepository;
import com.immo.test.UtilisateurTrancheFactoryForTest;
import com.immo.test.UtilisateurTrancheEntityFactoryForTest;
import com.immo.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of UtilisateurTrancheService
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilisateurTrancheServiceImplTest {

	@InjectMocks
	private UtilisateurTrancheServiceImpl utilisateurTrancheService;
	@Mock
	private UtilisateurTrancheJpaRepository utilisateurTrancheJpaRepository;
	@Mock
	private UtilisateurTrancheServiceMapper utilisateurTrancheServiceMapper;
	
	private UtilisateurTrancheFactoryForTest utilisateurTrancheFactoryForTest = new UtilisateurTrancheFactoryForTest();

	private UtilisateurTrancheEntityFactoryForTest utilisateurTrancheEntityFactoryForTest = new UtilisateurTrancheEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		UtilisateurTrancheEntity utilisateurTrancheEntity = utilisateurTrancheJpaRepository.findOne(id);
		
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		when(utilisateurTrancheServiceMapper.mapUtilisateurTrancheEntityToUtilisateurTranche(utilisateurTrancheEntity)).thenReturn(utilisateurTranche);

		// When
		UtilisateurTranche utilisateurTrancheFound = utilisateurTrancheService.findById(id);

		// Then
		assertEquals(utilisateurTranche.getId(),utilisateurTrancheFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<UtilisateurTrancheEntity> utilisateurTrancheEntitys = new ArrayList<UtilisateurTrancheEntity>();
		UtilisateurTrancheEntity utilisateurTrancheEntity1 = utilisateurTrancheEntityFactoryForTest.newUtilisateurTrancheEntity();
		utilisateurTrancheEntitys.add(utilisateurTrancheEntity1);
		UtilisateurTrancheEntity utilisateurTrancheEntity2 = utilisateurTrancheEntityFactoryForTest.newUtilisateurTrancheEntity();
		utilisateurTrancheEntitys.add(utilisateurTrancheEntity2);
		when(utilisateurTrancheJpaRepository.findAll()).thenReturn(utilisateurTrancheEntitys);
		
		UtilisateurTranche utilisateurTranche1 = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		when(utilisateurTrancheServiceMapper.mapUtilisateurTrancheEntityToUtilisateurTranche(utilisateurTrancheEntity1)).thenReturn(utilisateurTranche1);
		UtilisateurTranche utilisateurTranche2 = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		when(utilisateurTrancheServiceMapper.mapUtilisateurTrancheEntityToUtilisateurTranche(utilisateurTrancheEntity2)).thenReturn(utilisateurTranche2);

		// When
		List<UtilisateurTranche> utilisateurTranchesFounds = utilisateurTrancheService.findAll();

		// Then
		assertTrue(utilisateurTranche1 == utilisateurTranchesFounds.get(0));
		assertTrue(utilisateurTranche2 == utilisateurTranchesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();

		UtilisateurTrancheEntity utilisateurTrancheEntity = utilisateurTrancheEntityFactoryForTest.newUtilisateurTrancheEntity();
		when(utilisateurTrancheJpaRepository.findOne(utilisateurTranche.getId())).thenReturn(null);
		
		utilisateurTrancheEntity = new UtilisateurTrancheEntity();
		utilisateurTrancheServiceMapper.mapUtilisateurTrancheToUtilisateurTrancheEntity(utilisateurTranche, utilisateurTrancheEntity);
		UtilisateurTrancheEntity utilisateurTrancheEntitySaved = utilisateurTrancheJpaRepository.save(utilisateurTrancheEntity);
		
		UtilisateurTranche utilisateurTrancheSaved = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		when(utilisateurTrancheServiceMapper.mapUtilisateurTrancheEntityToUtilisateurTranche(utilisateurTrancheEntitySaved)).thenReturn(utilisateurTrancheSaved);

		// When
		UtilisateurTranche utilisateurTrancheResult = utilisateurTrancheService.create(utilisateurTranche);

		// Then
		assertTrue(utilisateurTrancheResult == utilisateurTrancheSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();

		UtilisateurTrancheEntity utilisateurTrancheEntity = utilisateurTrancheEntityFactoryForTest.newUtilisateurTrancheEntity();
		when(utilisateurTrancheJpaRepository.findOne(utilisateurTranche.getId())).thenReturn(utilisateurTrancheEntity);

		// When
		Exception exception = null;
		try {
			utilisateurTrancheService.create(utilisateurTranche);
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
		UtilisateurTranche utilisateurTranche = utilisateurTrancheFactoryForTest.newUtilisateurTranche();

		UtilisateurTrancheEntity utilisateurTrancheEntity = utilisateurTrancheEntityFactoryForTest.newUtilisateurTrancheEntity();
		when(utilisateurTrancheJpaRepository.findOne(utilisateurTranche.getId())).thenReturn(utilisateurTrancheEntity);
		
		UtilisateurTrancheEntity utilisateurTrancheEntitySaved = utilisateurTrancheEntityFactoryForTest.newUtilisateurTrancheEntity();
		when(utilisateurTrancheJpaRepository.save(utilisateurTrancheEntity)).thenReturn(utilisateurTrancheEntitySaved);
		
		UtilisateurTranche utilisateurTrancheSaved = utilisateurTrancheFactoryForTest.newUtilisateurTranche();
		when(utilisateurTrancheServiceMapper.mapUtilisateurTrancheEntityToUtilisateurTranche(utilisateurTrancheEntitySaved)).thenReturn(utilisateurTrancheSaved);

		// When
		UtilisateurTranche utilisateurTrancheResult = utilisateurTrancheService.update(utilisateurTranche);

		// Then
		verify(utilisateurTrancheServiceMapper).mapUtilisateurTrancheToUtilisateurTrancheEntity(utilisateurTranche, utilisateurTrancheEntity);
		assertTrue(utilisateurTrancheResult == utilisateurTrancheSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		utilisateurTrancheService.delete(id);

		// Then
		verify(utilisateurTrancheJpaRepository).delete(id);
		
	}

}
