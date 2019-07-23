package definitions.structures.abstr;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.field.impl.RealLine;
import definitions.structures.finitedimensional.real.vectors.Real;

public class VectorSpaceTest {

	final VectorSpace space=RealLine.getRealLine();
	
	final Vector nul=space.nullVec();

	@Test
	public void testContains() {
		Assert.assertTrue(space.contains(nul));
	}

	@Test
	public void testNullVec() {
		Assert.assertTrue(nul.equals(nul));
	}

	@Test
	public void testAdd() {
		Assert.assertTrue(space.add(nul,nul).equals(nul));
	}

	@Test
	public void testStretch() {
		Assert.assertTrue(space.stretch(nul,new Real(-11.1)).equals(nul));
	}

}
