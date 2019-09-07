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

	private static final int polynomialDegree = 5;

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return SpaceGenerator.getInstance().getPolynomialFunctionSpace(RealLine.getInstance(), polynomialDegree,
				Math.PI, true);
	}

}
