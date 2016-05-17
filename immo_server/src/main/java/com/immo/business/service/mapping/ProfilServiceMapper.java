/*
 * Created on 29 d�c. 2015 ( Time 20:53:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.immo.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.immo.bean.Profil;
import com.immo.bean.jpa.ProfilEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ProfilServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ProfilServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ProfilEntity' to 'Profil'
	 * @param profilEntity
	 */
	public Profil mapProfilEntityToProfil(ProfilEntity profilEntity) {
		if(profilEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Profil profil = map(profilEntity, Profil.class);

		return profil;
	}
	
	/**
	 * Mapping from 'Profil' to 'ProfilEntity'
	 * @param profil
	 * @param profilEntity
	 */
	public void mapProfilToProfilEntity(Profil profil, ProfilEntity profilEntity) {
		if(profil == null) {
			return;
		}

		//--- Generic mapping 
		map(profil, profilEntity);

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