package com.droveda.bugtrackercli;

import com.droveda.bugtrackercli.config.TestOauth2BeansConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestOauth2BeansConfig.class)
class BugtrackercliApplicationTests {

	@Test
	void contextLoads() {
	}

}
