/*
 * Created on 29 d�c. 2015 ( Time 20:53:41 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.immo.bean.Module;
import com.immo.bean.jpa.ModuleEntity;
import java.util.Date;
import java.util.List;
import com.immo.business.service.ModuleService;
import com.immo.business.service.mapping.ModuleServiceMapper;
import com.immo.data.repository.jpa.ModuleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ModuleService
 */
@Component
@Transactional
public class ModuleServiceImpl implements ModuleService {

	@Resource
	private ModuleJpaRepository moduleJpaRepository;

	@Resource
	private ModuleServiceMapper moduleServiceMapper;
	
	@Override
	public Module findById(Long id) {
		ModuleEntity moduleEntity = moduleJpaRepository.findOne(id);
		return moduleServiceMapper.mapModuleEntityToModule(moduleEntity);
	}

	@Override
	public List<Module> findAll() {
		Iterable<ModuleEntity> entities = moduleJpaRepository.findAll();
		List<Module> beans = new ArrayList<Module>();
		for(ModuleEntity moduleEntity : entities) {
			beans.add(moduleServiceMapper.mapModuleEntityToModule(moduleEntity));
		}
		return beans;
	}

	@Override
	public Module save(Module module) {
		return update(module) ;
	}

	@Override
	public Module create(Module module) {
		ModuleEntity moduleEntity = moduleJpaRepository.findOne(module.getId());
		if( moduleEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		moduleEntity = new ModuleEntity();
		moduleServiceMapper.mapModuleToModuleEntity(module, moduleEntity);
		ModuleEntity moduleEntitySaved = moduleJpaRepository.save(moduleEntity);
		return moduleServiceMapper.mapModuleEntityToModule(moduleEntitySaved);
	}

	@Override
	public Module update(Module module) {
		ModuleEntity moduleEntity = moduleJpaRepository.findOne(module.getId());
		moduleServiceMapper.mapModuleToModuleEntity(module, moduleEntity);
		ModuleEntity moduleEntitySaved = moduleJpaRepository.save(moduleEntity);
		return moduleServiceMapper.mapModuleEntityToModule(moduleEntitySaved);
	}

	@Override
	public void delete(Long id) {
		moduleJpaRepository.delete(id);
	}

	public ModuleJpaRepository getModuleJpaRepository() {
		return moduleJpaRepository;
	}

	public void setModuleJpaRepository(ModuleJpaRepository moduleJpaRepository) {
		this.moduleJpaRepository = moduleJpaRepository;
	}

	public ModuleServiceMapper getModuleServiceMapper() {
		return moduleServiceMapper;
	}

	public void setModuleServiceMapper(ModuleServiceMapper moduleServiceMapper) {
		this.moduleServiceMapper = moduleServiceMapper;
	}

}
