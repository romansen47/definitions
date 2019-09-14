/**
 * 
 */
package definitions.structures.abstr.fields.impl;

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

	@Override
	@settings.Trace(trace = false, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = false, transit = true)
	public void test() {
	}

	public static void main(String[] args) {
		new BinaryFieldTest().runTests();
	}

	@Override

	public VectorSpace getSpace() {
		return BinaryField.getInstance();
	}

	@Override

	public Vector getVec1() {
		return True.getInstance();
	}

	@Override

	public Vector getVec2() {
		return False.getInstance();
	}

	@Override

	public Scalar getFactor() {
		return (Scalar) this.getVec1();
	}

}
