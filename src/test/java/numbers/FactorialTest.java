package numbers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FactorialTest {

	static Functions fun;
	static int NUMBER = 5000;
	static Binary bin;

	@BeforeClass
	public static void prepare() {
		fun = new Functions();
		bin = new Binary(NUMBER);
	}

	@Test
	public void test() {
		final Functions fun = new Functions();
		final int NUMBER = 1000;
		final Binary bin = new Binary(NUMBER);

		// fun.faculty(new natural(NUMBER)).present();

		final Binary x = fun.fak(NUMBER);
		final Binary y = fun.fak(bin);

		Assert.assertTrue(x.toString().equals(y.toString()));
		//x.present();
	}

}
