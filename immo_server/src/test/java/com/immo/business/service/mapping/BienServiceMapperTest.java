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
import com.immo.bean.Bien;
import com.immo.bean.jpa.BienEntity;
import com.immo.bean.jpa.ReferenceEntity;
import com.immo.bean.jpa.TrancheEntity;
import com.immo.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class BienServiceMapperTest {

	private BienServiceMapper bienServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		bienServiceMapper = new BienServiceMapper();
		bienServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'BienEntity' to 'Bien'
	 * @param bienEntity
	 */
	@Test
	public void testMapBienEntityToBien() {
		// Given
		BienEntity bienEntity = new BienEntity();
		bienEntity.setDenomination(mockValues.nextString(100));
		bienEntity.setSupProv(mockValues.nextLong());
		bienEntity.setSupFinale(mockValues.nextLong());
		bienEntity.setTf(mockValues.nextString(100));
		bienEntity.setTfMere(mockValues.nextString(100));
		bienEntity.setEtat(mockValues.nextString(100));
		bienEntity.setPrixHt(mockValues.nextLong());
		bienEntity.setPrixTtc(mockValues.nextLong());
		bienEntity.setDateLivraison(mockValues.nextDate());
		bienEntity.setIlot(mockValues.nextString(100));
		bienEntity.setTantieme(mockValues.nextString(100));
		bienEntity.setQuotePart(mockValues.nextString(100));
		bienEntity.setDatePermisHabiter(mockValues.nextDate());
		bienEntity.setMntTva(mockValues.nextLong());
		bienEntity.setEchMnt1(mockValues.nextLong());
		bienEntity.setEchMnt2(mockValues.nextLong());
		bienEntity.setEchMnt3(mockValues.nextLong());
		bienEntity.setEchMnt4(mockValues.nextLong());
		bienEntity.setEchDelai1(mockValues.nextLong());
		bienEntity.setEchDelai2(mockValues.nextLong());
		bienEntity.setEchDelai3(mockValues.nextLong());
		bienEntity.setEchDelai4(mockValues.nextLong());
		bienEntity.setTypeBien(mockValues.nextString(30));
		bienEntity.setNiveau(mockValues.nextString(30));
		bienEntity.setNumBien(mockValues.nextString(30));
		bienEntity.setImmeuble(mockValues.nextString(30));
		bienEntity.setOrientation(mockValues.nextString(30));
		bienEntity.setSupBalcon(mockValues.nextLong());
		bienEntity.setDateCreation(mockValues.nextDate());
		bienEntity.setDateMaj(mockValues.nextDate());
		bienEntity.setReference(new ReferenceEntity());
		bienEntity.getReference().setId(mockValues.nextLong());
		bienEntity.setTranche(new TrancheEntity());
		bienEntity.getTranche().setId(mockValues.nextLong());
		
		// When
		Bien bien = bienServiceMapper.mapBienEntityToBien(bienEntity);
		
		// Then
		assertEquals(bienEntity.getDenomination(), bien.getDenomination());
		assertEquals(bienEntity.getSupProv(), bien.getSupProv());
		assertEquals(bienEntity.getSupFinale(), bien.getSupFinale());
		assertEquals(bienEntity.getTf(), bien.getTf());
		assertEquals(bienEntity.getTfMere(), bien.getTfMere());
		assertEquals(bienEntity.getEtat(), bien.getEtat());
		assertEquals(bienEntity.getPrixHt(), bien.getPrixHt());
		assertEquals(bienEntity.getPrixTtc(), bien.getPrixTtc());
		assertEquals(bienEntity.getDateLivraison(), bien.getDateLivraison());
		assertEquals(bienEntity.getIlot(), bien.getIlot());
		assertEquals(bienEntity.getTantieme(), bien.getTantieme());
		assertEquals(bienEntity.getQuotePart(), bien.getQuotePart());
		assertEquals(bienEntity.getDatePermisHabiter(), bien.getDatePermisHabiter());
		assertEquals(bienEntity.getMntTva(), bien.getMntTva());
		assertEquals(bienEntity.getEchMnt1(), bien.getEchMnt1());
		assertEquals(bienEntity.getEchMnt2(), bien.getEchMnt2());
		assertEquals(bienEntity.getEchMnt3(), bien.getEchMnt3());
		assertEquals(bienEntity.getEchMnt4(), bien.getEchMnt4());
		assertEquals(bienEntity.getEchDelai1(), bien.getEchDelai1());
		assertEquals(bienEntity.getEchDelai2(), bien.getEchDelai2());
		assertEquals(bienEntity.getEchDelai3(), bien.getEchDelai3());
		assertEquals(bienEntity.getEchDelai4(), bien.getEchDelai4());
		assertEquals(bienEntity.getTypeBien(), bien.getTypeBien());
		assertEquals(bienEntity.getNiveau(), bien.getNiveau());
		assertEquals(bienEntity.getNumBien(), bien.getNumBien());
		assertEquals(bienEntity.getImmeuble(), bien.getImmeuble());
		assertEquals(bienEntity.getOrientation(), bien.getOrientation());
		assertEquals(bienEntity.getSupBalcon(), bien.getSupBalcon());
		assertEquals(bienEntity.getDateCreation(), bien.getDateCreation());
		assertEquals(bienEntity.getDateMaj(), bien.getDateMaj());
		assertEquals(bienEntity.getReference().getId(), bien.getBienReference());
		assertEquals(bienEntity.getTranche().getId(), bien.getRefTranche());
	}
	
	/**
	 * Test : Mapping from 'Bien' to 'BienEntity'
	 */
	@Test
	public void testMapBienToBienEntity() {
		// Given
		Bien bien = new Bien();
		bien.setDenomination(mockValues.nextString(100));
		bien.setSupProv(mockValues.nextLong());
		bien.setSupFinale(mockValues.nextLong());
		bien.setTf(mockValues.nextString(100));
		bien.setTfMere(mockValues.nextString(100));
		bien.setEtat(mockValues.nextString(100));
		bien.setPrixHt(mockValues.nextLong());
		bien.setPrixTtc(mockValues.nextLong());
		bien.setDateLivraison(mockValues.nextDate());
		bien.setIlot(mockValues.nextString(100));
		bien.setTantieme(mockValues.nextString(100));
		bien.setQuotePart(mockValues.nextString(100));
		bien.setDatePermisHabiter(mockValues.nextDate());
		bien.setMntTva(mockValues.nextLong());
		bien.setEchMnt1(mockValues.nextLong());
		bien.setEchMnt2(mockValues.nextLong());
		bien.setEchMnt3(mockValues.nextLong());
		bien.setEchMnt4(mockValues.nextLong());
		bien.setEchDelai1(mockValues.nextLong());
		bien.setEchDelai2(mockValues.nextLong());
		bien.setEchDelai3(mockValues.nextLong());
		bien.setEchDelai4(mockValues.nextLong());
		bien.setTypeBien(mockValues.nextString(30));
		bien.setNiveau(mockValues.nextString(30));
		bien.setNumBien(mockValues.nextString(30));
		bien.setImmeuble(mockValues.nextString(30));
		bien.setOrientation(mockValues.nextString(30));
		bien.setSupBalcon(mockValues.nextLong());
		bien.setDateCreation(mockValues.nextDate());
		bien.setDateMaj(mockValues.nextDate());
		bien.setBienReference(mockValues.nextLong());
		bien.setRefTranche(mockValues.nextLong());

		BienEntity bienEntity = new BienEntity();
		
		// When
		bienServiceMapper.mapBienToBienEntity(bien, bienEntity);
		
		// Then
		assertEquals(bien.getDenomination(), bienEntity.getDenomination());
		assertEquals(bien.getSupProv(), bienEntity.getSupProv());
		assertEquals(bien.getSupFinale(), bienEntity.getSupFinale());
		assertEquals(bien.getTf(), bienEntity.getTf());
		assertEquals(bien.getTfMere(), bienEntity.getTfMere());
		assertEquals(bien.getEtat(), bienEntity.getEtat());
		assertEquals(bien.getPrixHt(), bienEntity.getPrixHt());
		assertEquals(bien.getPrixTtc(), bienEntity.getPrixTtc());
		assertEquals(bien.getDateLivraison(), bienEntity.getDateLivraison());
		assertEquals(bien.getIlot(), bienEntity.getIlot());
		assertEquals(bien.getTantieme(), bienEntity.getTantieme());
		assertEquals(bien.getQuotePart(), bienEntity.getQuotePart());
		assertEquals(bien.getDatePermisHabiter(), bienEntity.getDatePermisHabiter());
		assertEquals(bien.getMntTva(), bienEntity.getMntTva());
		assertEquals(bien.getEchMnt1(), bienEntity.getEchMnt1());
		assertEquals(bien.getEchMnt2(), bienEntity.getEchMnt2());
		assertEquals(bien.getEchMnt3(), bienEntity.getEchMnt3());
		assertEquals(bien.getEchMnt4(), bienEntity.getEchMnt4());
		assertEquals(bien.getEchDelai1(), bienEntity.getEchDelai1());
		assertEquals(bien.getEchDelai2(), bienEntity.getEchDelai2());
		assertEquals(bien.getEchDelai3(), bienEntity.getEchDelai3());
		assertEquals(bien.getEchDelai4(), bienEntity.getEchDelai4());
		assertEquals(bien.getTypeBien(), bienEntity.getTypeBien());
		assertEquals(bien.getNiveau(), bienEntity.getNiveau());
		assertEquals(bien.getNumBien(), bienEntity.getNumBien());
		assertEquals(bien.getImmeuble(), bienEntity.getImmeuble());
		assertEquals(bien.getOrientation(), bienEntity.getOrientation());
		assertEquals(bien.getSupBalcon(), bienEntity.getSupBalcon());
		assertEquals(bien.getDateCreation(), bienEntity.getDateCreation());
		assertEquals(bien.getDateMaj(), bienEntity.getDateMaj());
		assertEquals(bien.getBienReference(), bienEntity.getReference().getId());
		assertEquals(bien.getRefTranche(), bienEntity.getTranche().getId());
	}

}