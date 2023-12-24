package com.calmdown.mobilePay;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//@EnableJpaAuditing
@SpringBootTest
@Disabled
@Transactional
class MobilePayApplicationTests {

	@Test
	void contextLoads() {
	}

}
