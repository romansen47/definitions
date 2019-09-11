/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import com.aop.lib.Trace;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.False;
import definitions.structures.abstr.fields.scalars.impl.True;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author BAU12350
 *
 */
public class BinaryFieldTest extends FieldTest {

	// @Test
	// public void test() {
	// fail("Not yet implemented");
	// }

	public static void main(String[]args) {
		new BinaryFieldTest().runTests();
	}
	
	@Override
	@com.aop.lib.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = false, transit = true)
	public VectorSpace getSpace() {
		return BinaryField.getInstance();
	}

	@Override
	@com.aop.lib.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = false, transit = true)
	public Vector getVec1() {
		return True.getInstance();
	}

	@Override
	@com.aop.lib.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = false, transit = true)
	public Vector getVec2() {
		return False.getInstance();
	}

	@Override
	@com.aop.lib.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = false, transit = true)
	public Scalar getFactor() {
		return (Scalar) this.getVec1();
	}

}
