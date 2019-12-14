package definitions.prototypes;

import org.junit.Before;
import org.junit.Test;

public interface ModuleTest {

	@Test
	void runTest();

	@Before
	void prepareTest();
	
}
