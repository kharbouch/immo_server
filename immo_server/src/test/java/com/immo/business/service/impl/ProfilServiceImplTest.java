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

import com.immo.bean.Profil;
import com.immo.bean.jpa.ProfilEntity;
import java.util.Date;
import java.util.List;
import com.immo.business.service.mapping.ProfilServiceMapper;
import com.immo.data.repository.jpa.ProfilJpaRepository;
import com.immo.test.ProfilFactoryForTest;
import com.immo.test.ProfilEntityFactoryForTest;
import com.immo.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ProfilService
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilServiceImplTest {

	@InjectMocks
	private ProfilServiceImpl profilService;
	@Mock
	private ProfilJpaRepository profilJpaRepository;
	@Mock
	private ProfilServiceMapper profilServiceMapper;
	
	private ProfilFactoryForTest profilFactoryForTest = new ProfilFactoryForTest();

	private ProfilEntityFactoryForTest profilEntityFactoryForTest = new ProfilEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Long id = mockValues.nextLong();
		
		ProfilEntity profilEntity = profilJpaRepository.findOne(id);
		
		Profil profil = profilFactoryForTest.newProfil();
		when(profilServiceMapper.mapProfilEntityToProfil(profilEntity)).thenReturn(profil);

		// When
		Profil profilFound = profilService.findById(id);

		// Then
		assertEquals(profil.getId(),profilFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<ProfilEntity> profilEntitys = new ArrayList<ProfilEntity>();
		ProfilEntity profilEntity1 = profilEntityFactoryForTest.newProfilEntity();
		profilEntitys.add(profilEntity1);
		ProfilEntity profilEntity2 = profilEntityFactoryForTest.newProfilEntity();
		profilEntitys.add(profilEntity2);
		when(profilJpaRepository.findAll()).thenReturn(profilEntitys);
		
		Profil profil1 = profilFactoryForTest.newProfil();
		when(profilServiceMapper.mapProfilEntityToProfil(profilEntity1)).thenReturn(profil1);
		Profil profil2 = profilFactoryForTest.newProfil();
		when(profilServiceMapper.mapProfilEntityToProfil(profilEntity2)).thenReturn(profil2);

		// When
		List<Profil> profilsFounds = profilService.findAll();

		// Then
		assertTrue(profil1 == profilsFounds.get(0));
		assertTrue(profil2 == profilsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Profil profil = profilFactoryForTest.newProfil();

		ProfilEntity profilEntity = profilEntityFactoryForTest.newProfilEntity();
		when(profilJpaRepository.findOne(profil.getId())).thenReturn(null);
		
		profilEntity = new ProfilEntity();
		profilServiceMapper.mapProfilToProfilEntity(profil, profilEntity);
		ProfilEntity profilEntitySaved = profilJpaRepository.save(profilEntity);
		
		Profil profilSaved = profilFactoryForTest.newProfil();
		when(profilServiceMapper.mapProfilEntityToProfil(profilEntitySaved)).thenReturn(profilSaved);

		// When
		Profil profilResult = profilService.create(profil);

		// Then
		assertTrue(profilResult == profilSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Profil profil = profilFactoryForTest.newProfil();

		ProfilEntity profilEntity = profilEntityFactoryForTest.newProfilEntity();
		when(profilJpaRepository.findOne(profil.getId())).thenReturn(profilEntity);

		// When
		Exception exception = null;
		try {
			profilService.create(profil);
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
		Profil profil = profilFactoryForTest.newProfil();

		ProfilEntity profilEntity = profilEntityFactoryForTest.newProfilEntity();
		when(profilJpaRepository.findOne(profil.getId())).thenReturn(profilEntity);
		
		ProfilEntity profilEntitySaved = profilEntityFactoryForTest.newProfilEntity();
		when(profilJpaRepository.save(profilEntity)).thenReturn(profilEntitySaved);
		
		Profil profilSaved = profilFactoryForTest.newProfil();
		when(profilServiceMapper.mapProfilEntityToProfil(profilEntitySaved)).thenReturn(profilSaved);

		// When
		Profil profilResult = profilService.update(profil);

		// Then
		verify(profilServiceMapper).mapProfilToProfilEntity(profil, profilEntity);
		assertTrue(profilResult == profilSaved);
	}

	@Test
	public void delete() {
		// Given
		Long id = mockValues.nextLong();

		// When
		profilService.delete(id);

		// Then
		verify(profilJpaRepository).delete(id);
		
	}

}