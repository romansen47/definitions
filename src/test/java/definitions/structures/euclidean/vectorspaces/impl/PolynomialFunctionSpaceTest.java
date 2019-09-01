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

	static final int sobolevDegree = 3;
	private static final int polynomialDegree = 7;

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) SpaceGenerator.getInstance().
				getPolynomialFunctionSpace(RealLine.getInstance(),polynomialDegree, Math.PI, true);
	}

}
