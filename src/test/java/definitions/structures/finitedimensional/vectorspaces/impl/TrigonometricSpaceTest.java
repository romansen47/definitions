/**
 * 
 */
package definitions.structures.finitedimensional.vectorspaces.impl;

import definitions.structures.abstr.FunctionSpaceTest;
import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class TrigonometricSpaceTest extends FunctionSpaceTest {

	final static Field field = RealLine.getInstance();
	final static EuclideanSpace trigSpace = SpaceGenerator.getInstance().getTrigonometricSpace(field, 30);

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) trigSpace;
	}

}
