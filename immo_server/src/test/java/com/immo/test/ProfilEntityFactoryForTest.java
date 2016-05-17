package com.immo.test;

import com.immo.bean.jpa.ProfilEntity;

public class ProfilEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ProfilEntity newProfilEntity() {

		Long id = mockValues.nextLong();

		ProfilEntity profilEntity = new ProfilEntity();
		profilEntity.setId(id);
		return profilEntity;
	}
	
}
