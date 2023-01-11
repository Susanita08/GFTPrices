package com.gft.es.prices.gftprices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class GftPricesApplicationTests {

	@Test
	void startsApplicationAndLoadsContext() {
		assertDoesNotThrow(()-> GftPricesApplication.main(new String[]{}));
	}

}
