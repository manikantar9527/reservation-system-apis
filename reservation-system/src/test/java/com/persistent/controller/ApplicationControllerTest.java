package com.persistent.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationControllerTest {

	@InjectMocks
	private ApplicationController controller;

	@Test
	public void getPassengerDetailsTest() throws Exception {
		assertNotNull(controller.process());
	}
}
