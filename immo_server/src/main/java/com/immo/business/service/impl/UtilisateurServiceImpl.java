/*
 * Created on 29 d�c. 2015 ( Time 20:53:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import com.immo.bean.Utilisateur;
import com.immo.bean.jpa.UtilisateurEntity;
import java.util.Date;
import java.util.List;
import com.immo.business.service.UtilisateurService;
import com.immo.business.service.mapping.UtilisateurServiceMapper;
import com.immo.data.repository.jpa.UtilisateurJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UtilisateurService
 */
@Component
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

	@Resource
	private UtilisateurJpaRepository utilisateurJpaRepository;

	@Resource
	private UtilisateurServiceMapper utilisateurServiceMapper;
	
	@Override
	public Utilisateur findById(Long id) {
		UtilisateurEntity utilisateurEntity = utilisateurJpaRepository.findOne(id);
		return utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntity);
	}

	@Override
	public List<Utilisateur> findAll() {
		Iterable<UtilisateurEntity> entities = utilisateurJpaRepository.findAll();
		List<Utilisateur> beans = new ArrayList<Utilisateur>();
		for(UtilisateurEntity utilisateurEntity : entities) {
			beans.add(utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntity));
		}
		return beans;
	}

	@Override
	public Utilisateur save(Utilisateur utilisateur) {
		return update(utilisateur) ;
	}

	@Override
	public Utilisateur create(Utilisateur utilisateur) {
		/*UtilisateurEntity utilisateurEntity = utilisateurJpaRepository.findOne(utilisateur.getId());
		if( utilisateurEntity != null ) {
			throw new IllegalStateException("already.exists");
		}*/
		UtilisateurEntity utilisateurEntity = new UtilisateurEntity();
		utilisateurServiceMapper.mapUtilisateurToUtilisateurEntity(utilisateur, utilisateurEntity);
		UtilisateurEntity utilisateurEntitySaved = utilisateurJpaRepository.save(utilisateurEntity);
		return utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntitySaved);
	}

	@Override
	public Utilisateur update(Utilisateur utilisateur) {
		UtilisateurEntity utilisateurEntity = utilisateurJpaRepository.findOne(utilisateur.getId());
		utilisateurServiceMapper.mapUtilisateurToUtilisateurEntity(utilisateur, utilisateurEntity);
		UtilisateurEntity utilisateurEntitySaved = utilisateurJpaRepository.save(utilisateurEntity);
		return utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(utilisateurEntitySaved);
	}

	@Override
	public void delete(Long id) {
		utilisateurJpaRepository.delete(id);
	}

	public UtilisateurJpaRepository getUtilisateurJpaRepository() {
		return utilisateurJpaRepository;
	}

	public void setUtilisateurJpaRepository(UtilisateurJpaRepository utilisateurJpaRepository) {
		this.utilisateurJpaRepository = utilisateurJpaRepository;
	}

	public UtilisateurServiceMapper getUtilisateurServiceMapper() {
		return utilisateurServiceMapper;
	}

	public void setUtilisateurServiceMapper(UtilisateurServiceMapper utilisateurServiceMapper) {
		this.utilisateurServiceMapper = utilisateurServiceMapper;
	}

	@Override
	public Utilisateur findByUserNameAndPass(Utilisateur utilisateur){
		// TODO Auto-generated method stub
		List<UtilisateurEntity> entities = utilisateurJpaRepository.findByUserNameAndPass(utilisateur.getLogin(), utilisateur.getPassword());
		List<Utilisateur> beans = new ArrayList<Utilisateur>();
		return utilisateurServiceMapper.mapUtilisateurEntityToUtilisateur(entities.get(0));
	
	}

}
