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
 * @author romansen
 *
 */
public class BinaryFieldTest extends FieldTest {

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
