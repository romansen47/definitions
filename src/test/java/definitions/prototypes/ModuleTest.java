package definitions.prototypes;

import org.junit.Before;
import org.junit.Test;

public interface ModuleTest {

	@Before
	void prepareTest();

	@Test
	void runTest();

}
