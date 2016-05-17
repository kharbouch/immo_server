package com.immo.test;

import com.immo.bean.Profil;

public class ProfilFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Profil newProfil() {

		Long id = mockValues.nextLong();

		Profil profil = new Profil();
		profil.setId(id);
		return profil;
	}
	
}
