package com.immo.test;

import com.immo.bean.ModuleProfil;

public class ModuleProfilFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ModuleProfil newModuleProfil() {

		Long id = mockValues.nextLong();

		ModuleProfil moduleProfil = new ModuleProfil();
		moduleProfil.setId(id);
		return moduleProfil;
	}
	
}
