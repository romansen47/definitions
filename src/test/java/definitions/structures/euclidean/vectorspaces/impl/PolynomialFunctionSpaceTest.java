/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import definitions.structures.abstr.FunctionSpaceTest;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;

/**
 * @author RoManski
 *
 */
public class PolynomialFunctionSpaceTest extends FunctionSpaceTest {

	static final int sobolevDegree = 0;
	private static final int polynomialDegree = 1;

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) SpaceGenerator.getInstance().getPolynomialSobolevSpace(RealLine.getInstance(),
				polynomialDegree, 1, sobolevDegree);
	}

}
