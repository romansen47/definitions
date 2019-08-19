/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import definitions.structures.abstr.FunctionSpaceTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class TrigonometricSpaceTest extends FunctionSpaceTest {

	final static Field field = RealLine.getInstance();
	final static EuclideanSpace trigSpace = SpaceGenerator.getInstance().getTrigonometricSpace(field, 10);

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) trigSpace;
	}

}
