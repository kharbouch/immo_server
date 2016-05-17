package com.immo.test;

import com.immo.bean.jpa.UtilisateurEntity;

public class UtilisateurEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UtilisateurEntity newUtilisateurEntity() {

		Long id = mockValues.nextLong();

		UtilisateurEntity utilisateurEntity = new UtilisateurEntity();
		utilisateurEntity.setId(id);
		return utilisateurEntity;
	}
	
}
