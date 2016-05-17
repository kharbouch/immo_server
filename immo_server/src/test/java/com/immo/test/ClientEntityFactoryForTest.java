package com.immo.test;

import com.immo.bean.jpa.ClientEntity;

public class ClientEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ClientEntity newClientEntity() {

		Long id = mockValues.nextLong();

		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setId(id);
		return clientEntity;
	}
	
}
