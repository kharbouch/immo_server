/*
 * Created on 29 d�c. 2015 ( Time 20:53:11 )
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
import com.immo.bean.Tranche;
import com.immo.business.service.TrancheService;
import com.immo.web.listitem.TrancheListItem;

/**
 * Spring MVC controller for 'Tranche' management.
 */
@Controller
public class TrancheRestController {

	@Resource
	private TrancheService trancheService;
	
	@RequestMapping( value="/items/tranche",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TrancheListItem> findAllAsListItems() {
		List<Tranche> list = trancheService.findAll();
		List<TrancheListItem> items = new LinkedList<TrancheListItem>();
		for ( Tranche tranche : list ) {
			items.add(new TrancheListItem( tranche ) );
		}
		return items;
	}
	
	@RequestMapping( value="/tranche",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Tranche> findAll() {
		return trancheService.findAll();
	}

	@RequestMapping( value="/tranche/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Tranche findOne(@PathVariable("id") Long id) {
		return trancheService.findById(id);
	}
	
	@RequestMapping( value="/tranche",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Tranche create(@RequestBody Tranche tranche) {
		return trancheService.create(tranche);
	}

	@RequestMapping( value="/tranche/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Tranche update(@PathVariable("id") Long id, @RequestBody Tranche tranche) {
		return trancheService.update(tranche);
	}

	@RequestMapping( value="/tranche/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Long id) {
		trancheService.delete(id);
	}
	
}
