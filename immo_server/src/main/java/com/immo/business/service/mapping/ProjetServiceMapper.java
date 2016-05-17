/*
 * Created on 29 d�c. 2015 ( Time 20:53:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.immo.bean.Projet;
import com.immo.bean.jpa.ProjetEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ProjetServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ProjetServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ProjetEntity' to 'Projet'
	 * @param projetEntity
	 */
	public Projet mapProjetEntityToProjet(ProjetEntity projetEntity) {
		if(projetEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Projet projet = map(projetEntity, Projet.class);

		return projet;
	}
	
	/**
	 * Mapping from 'Projet' to 'ProjetEntity'
	 * @param projet
	 * @param projetEntity
	 */
	public void mapProjetToProjetEntity(Projet projet, ProjetEntity projetEntity) {
		if(projet == null) {
			return;
		}

		//--- Generic mapping 
		map(projet, projetEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}