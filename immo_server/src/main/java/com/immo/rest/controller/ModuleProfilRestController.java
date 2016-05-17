/*
 * Created on 29 d�c. 2015 ( Time 20:53:07 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.immo.bean.Dossier;
import com.immo.bean.Module;
import com.immo.bean.ModuleProfil;
import com.immo.bean.Profil;
import com.immo.bean.Recette;
import com.immo.business.service.ModuleProfilService;
import com.immo.web.listitem.ModuleProfilListItem;

/**
 * Spring MVC controller for 'ModuleProfil' management.
 */
@Controller
public class ModuleProfilRestController {

	ProfilRestController pp ;
	
	@Resource
	private ModuleProfilService moduleProfilService;
	
	@RequestMapping( value="/items/moduleProfil",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ModuleProfilListItem> findAllAsListItems() {
		List<ModuleProfil> list = moduleProfilService.findAll();
		List<ModuleProfilListItem> items = new LinkedList<ModuleProfilListItem>();
		for ( ModuleProfil moduleProfil : list ) {
			items.add(new ModuleProfilListItem( moduleProfil ) );
		}
		return items;
	}
	
	@RequestMapping( value="/moduleProfil",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ModuleProfil> findAll() {
		return moduleProfilService.findAll();
	}

	@RequestMapping( value="/moduleProfil/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ModuleProfil findOne(@PathVariable("id") Long id) {
		return moduleProfilService.findById(id);
	}
	
	@RequestMapping( value="/moduleProfil",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ModuleProfil create(@RequestBody ModuleProfil moduleProfil) {
		return moduleProfilService.create(moduleProfil);
	}

	@RequestMapping( value="/moduleProfil/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ModuleProfil update(@PathVariable("id") Long id, @RequestBody ModuleProfil moduleProfil) {
		return moduleProfilService.update(moduleProfil);
	}

	@RequestMapping( value="/moduleProfil/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		moduleProfilService.delete(id);
	}
	
	@RequestMapping( value="/moduleByProfil",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Module> findByDossier(@RequestBody Profil p) {		
		System.out.println("la val de profil" +p.getDescription());
		System.out.println("dossier val:"+ p.getId());
		List<Module> m = moduleProfilService.findModuleByProfil(p);
		return m;
	}
	
}
