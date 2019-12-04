/**
 * 
 */
package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.scalars.Scalar; 
import definitions.structures.abstr.vectorspaces.VectorSpace; 

/**
 * @author BAU12350
 *
 */
public class BinaryFieldTest extends FieldTest {

	@Override
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
	public Scalar getFactor() {
		return (Scalar) this.getVec1();
	}

}
