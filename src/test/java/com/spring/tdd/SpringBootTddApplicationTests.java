package com.spring.tdd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "test") 
class SpringBootTddApplicationTests {

	@Test
	void contextLoads() {
	}

}
