package com.immo.test;

import com.immo.bean.Client;

public class ClientFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Client newClient() {

		Long id = mockValues.nextLong();

		Client client = new Client();
		client.setId(id);
		return client;
	}
	
}
