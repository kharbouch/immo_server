/*
 * Created on 29 d�c. 2015 ( Time 20:53:41 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.immo.bean.Client;
import com.immo.bean.Dossier;
import com.immo.bean.Echeance;
import com.immo.bean.jpa.ClientEntity;
import com.immo.bean.jpa.DossierEntity;
import com.immo.bean.jpa.EcheanceEntity;
import java.util.Date;
import com.immo.business.service.EcheanceService;
import com.immo.business.service.mapping.DossierServiceMapper;
import com.immo.business.service.mapping.EcheanceServiceMapper;
import com.immo.data.repository.jpa.EcheanceJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of EcheanceService
 */
@Component
@Transactional
public class EcheanceServiceImpl implements EcheanceService {

	@Resource
	private EcheanceJpaRepository echeanceJpaRepository;

	@Resource
	private EcheanceServiceMapper echeanceServiceMapper;
	
	@Resource
	private DossierServiceMapper dossierServiceMapper;
	
	@Override
	public Echeance findById(Long id) {
		EcheanceEntity echeanceEntity = echeanceJpaRepository.findOne(id);
		return echeanceServiceMapper.mapEcheanceEntityToEcheance(echeanceEntity);
	}

	@Override
	public List<Echeance> findAll() {
		Iterable<EcheanceEntity> entities = echeanceJpaRepository.findAll();
		List<Echeance> beans = new ArrayList<Echeance>();
		for(EcheanceEntity echeanceEntity : entities) {
			beans.add(echeanceServiceMapper.mapEcheanceEntityToEcheance(echeanceEntity));
		}
		return beans;
	}
	
	@Override
	public List<Echeance> findByDossier(Long id) {
		Iterable<EcheanceEntity> entities = echeanceJpaRepository.findAll();
		List<Echeance> beans = new ArrayList<Echeance>();
		for(EcheanceEntity echeanceEntity : entities) {
			if(echeanceEntity.getDossier().getId()==id)
			beans.add(echeanceServiceMapper.mapEcheanceEntityToEcheance(echeanceEntity));
		}
		return beans;
	}

	@Override
	public Echeance save(Echeance echeance) {
		return update(echeance) ;
	}

	@Override
	public Echeance create(Echeance echeance) {
		/*EcheanceEntity echeanceEntity = echeanceJpaRepository.findOne(echeance.getId());
		if( echeanceEntity != null ) {
			throw new IllegalStateException("already.exists");
		}*/
		EcheanceEntity echeanceEntity = new EcheanceEntity();
		echeanceServiceMapper.mapEcheanceToEcheanceEntity(echeance, echeanceEntity);
		EcheanceEntity echeanceEntitySaved = echeanceJpaRepository.save(echeanceEntity);
		return echeanceServiceMapper.mapEcheanceEntityToEcheance(echeanceEntitySaved);
	}

	@Override
	public Echeance update(Echeance echeance) {
		EcheanceEntity echeanceEntity = echeanceJpaRepository.findOne(echeance.getId());
		echeanceServiceMapper.mapEcheanceToEcheanceEntity(echeance, echeanceEntity);
		EcheanceEntity echeanceEntitySaved = echeanceJpaRepository.save(echeanceEntity);
		return echeanceServiceMapper.mapEcheanceEntityToEcheance(echeanceEntitySaved);
	}

	@Override
	public void delete(Long id) {
		echeanceJpaRepository.delete(id);
	}

	public EcheanceJpaRepository getEcheanceJpaRepository() {
		return echeanceJpaRepository;
	}

	public void setEcheanceJpaRepository(EcheanceJpaRepository echeanceJpaRepository) {
		this.echeanceJpaRepository = echeanceJpaRepository;
	}

	public EcheanceServiceMapper getEcheanceServiceMapper() {
		return echeanceServiceMapper;
	}

	public void setEcheanceServiceMapper(EcheanceServiceMapper echeanceServiceMapper) {
		this.echeanceServiceMapper = echeanceServiceMapper;
	}

	@Override
	public List<Echeance> findEcheanceByDossier(Dossier d) {
		DossierEntity dossierEntity = new DossierEntity();
		dossierServiceMapper.mapDossierToDossierEntity(d, dossierEntity);
		Iterable<EcheanceEntity> entities = echeanceJpaRepository.findEcheanceByDossier(dossierEntity);
		List<Echeance> beans = new ArrayList<Echeance>();
		for(EcheanceEntity echeanceEntity : entities) {
			beans.add(echeanceServiceMapper.mapEcheanceEntityToEcheance(echeanceEntity));
		}
		return beans;
	}
}