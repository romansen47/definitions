/**
 * 
 */
package definitions.structures.finitedimensional.real.functionspaces.impl;

import definitions.structures.abstr.FunctionSpaceTest;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FiniteDimensionalSobolevSpaceTestDepr extends FunctionSpaceTest {

	final static Field realSpace = RealLine.getInstance();
	final static int sobolevDegree = 1;
	final static int fourierDegree = 1;
	final static VectorSpace trigonometricSobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(realSpace, fourierDegree, sobolevDegree);

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) trigonometricSobolevSpace;
	}
}
