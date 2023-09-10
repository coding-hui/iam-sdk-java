package top.wecoding.iam.sdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author wecoding
 * @since 0.8
 */
@Slf4j
class QuickstartTest {

	@Test
	void test() {
		try {
			String[] args = {};
			Quickstart.main(args);
		}
		catch (Exception e) {
			log.error("Failed to Run Quickstart test: {}", e.getMessage(), e);
		}
	}

}