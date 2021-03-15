package com.aeq.transformers.impl.app;

import com.aeq.transformers.impl.app.controller.BattleController;
import com.aeq.transformers.impl.app.controller.TransformerController;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AeqTransformersImplApplicationTests {

	@Autowired
	private TransformerController transformerController;

	@Autowired
	private BattleController battleController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(transformerController).isNotNull();
		assertThat(battleController).isNotNull();
	}
}
