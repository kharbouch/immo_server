/*
 * Created on 29 d�c. 2015 ( Time 20:53:41 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service;

import java.util.List;

import com.immo.bean.Module;

/**
 * Business Service Interface for entity Module.
 */
public interface ModuleService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Module findById( Long id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Module> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Module save(Module entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Module update(Module entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Module create(Module entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Long id );


}
