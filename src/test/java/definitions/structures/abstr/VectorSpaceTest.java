package definitions.structures.abstr;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;

public class VectorSpaceTest {

	final VectorSpace space = RealLine.getInstance();

	final Vector nul = this.space.nullVec();

	@Test
	public void testContains() {
		Assert.assertTrue(this.space.contains(this.nul));
	}

	@Test
	public void testNullVec() {
		Assert.assertTrue(this.nul.equals(this.nul));
	}

	@Test
	public void testAdd() {
		Assert.assertTrue(this.space.add(this.nul, this.nul).equals(this.nul));
	}

	@Test
	public void testStretch() {
		Assert.assertTrue(this.space.stretch(this.nul, new Real(-11.1)).equals(this.nul));
	}

}
