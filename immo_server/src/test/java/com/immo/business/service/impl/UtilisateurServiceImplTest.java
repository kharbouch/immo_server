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

import com.immo.bean.Utilisateur;
import com.immo.bean.jpa.UtilisateurEntity;
import java.util.Date;
import java.util.List;
import com.immo.business.service.mapping.UtilisateurServiceMapper;
import com.immo.data.repository.jpa.UtilisateurJpaRepository;
import com.immo.test.UtilisateurFactoryForTest;
import com.immo.test.UtilisateurEntityFactoryForTest;
import com.immo.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of UtilisateurService
 */
@RunWith(MockitoJUnitRunner.class)
public class UtilisateurServiceImplTest {

	@InjectMocks
	private UtilisateurServiceImpl utilisateurService;
	@Mock
	private UtilisateurJpaRepository utilisateurJpaRepository;
	@Mock
	private UtilisateurServiceMapper utilisateurServiceMapper;
	
	private UtilisateurFactoryForTest utilisateurFactoryForTest = new UtilisateurFactoryForTest();

	private UtilisateurEntityFactoryForTest utilisateurEntityFactoryForTest = new UtilisateurEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		UtilisateurEntity utilisateurEntity = utilisateurJpaRepository.findOne(id);
		
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();
		when(utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntity)).thenReturn(utilisateur);

		// When
		Utilisateur utilisateurFound = utilisateurService.findById(id);

		// Then
		assertEquals(utilisateur.getId(),utilisateurFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<UtilisateurEntity> utilisateurEntitys = new ArrayList<UtilisateurEntity>();
		UtilisateurEntity utilisateurEntity1 = utilisateurEntityFactoryForTest.newUtilisateurEntity();
		utilisateurEntitys.add(utilisateurEntity1);
		UtilisateurEntity utilisateurEntity2 = utilisateurEntityFactoryForTest.newUtilisateurEntity();
		utilisateurEntitys.add(utilisateurEntity2);
		when(utilisateurJpaRepository.findAll()).thenReturn(utilisateurEntitys);
		
		Utilisateur utilisateur1 = utilisateurFactoryForTest.newUtilisateur();
		when(utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntity1)).thenReturn(utilisateur1);
		Utilisateur utilisateur2 = utilisateurFactoryForTest.newUtilisateur();
		when(utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntity2)).thenReturn(utilisateur2);

		// When
		List<Utilisateur> utilisateursFounds = utilisateurService.findAll();

		// Then
		assertTrue(utilisateur1 == utilisateursFounds.get(0));
		assertTrue(utilisateur2 == utilisateursFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();

		UtilisateurEntity utilisateurEntity = utilisateurEntityFactoryForTest.newUtilisateurEntity();
		when(utilisateurJpaRepository.findOne(utilisateur.getId())).thenReturn(null);
		
		utilisateurEntity = new UtilisateurEntity();
		utilisateurServiceMapper.mapUtilisateurToUtilisateurEntity(utilisateur, utilisateurEntity);
		UtilisateurEntity utilisateurEntitySaved = utilisateurJpaRepository.save(utilisateurEntity);
		
		Utilisateur utilisateurSaved = utilisateurFactoryForTest.newUtilisateur();
		when(utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntitySaved)).thenReturn(utilisateurSaved);

		// When
		Utilisateur utilisateurResult = utilisateurService.create(utilisateur);

		// Then
		assertTrue(utilisateurResult == utilisateurSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();

		UtilisateurEntity utilisateurEntity = utilisateurEntityFactoryForTest.newUtilisateurEntity();
		when(utilisateurJpaRepository.findOne(utilisateur.getId())).thenReturn(utilisateurEntity);

		// When
		Exception exception = null;
		try {
			utilisateurService.create(utilisateur);
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
		Utilisateur utilisateur = utilisateurFactoryForTest.newUtilisateur();

		UtilisateurEntity utilisateurEntity = utilisateurEntityFactoryForTest.newUtilisateurEntity();
		when(utilisateurJpaRepository.findOne(utilisateur.getId())).thenReturn(utilisateurEntity);
		
		UtilisateurEntity utilisateurEntitySaved = utilisateurEntityFactoryForTest.newUtilisateurEntity();
		when(utilisateurJpaRepository.save(utilisateurEntity)).thenReturn(utilisateurEntitySaved);
		
		Utilisateur utilisateurSaved = utilisateurFactoryForTest.newUtilisateur();
		when(utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntitySaved)).thenReturn(utilisateurSaved);

		// When
		Utilisateur utilisateurResult = utilisateurService.update(utilisateur);

		// Then
		verify(utilisateurServiceMapper).mapUtilisateurToUtilisateurEntity(utilisateur, utilisateurEntity);
		assertTrue(utilisateurResult == utilisateurSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		utilisateurService.delete(id);

		// Then
		verify(utilisateurJpaRepository).delete(id);
		
	}

}