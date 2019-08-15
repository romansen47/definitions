/**
 * 
 */
package definitions.structures.euclidean.functionspaces.impl;

import definitions.structures.abstr.FunctionSpaceTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

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
