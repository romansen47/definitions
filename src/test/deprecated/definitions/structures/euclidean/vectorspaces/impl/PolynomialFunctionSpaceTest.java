/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;

/**
 * @author RoManski
 *
 */
public class PolynomialFunctionSpaceTest  {

	private static final int polynomialDegree = 5;

	public EuclideanFunctionSpace getLinearSpace() {
		return SpaceGenerator.getInstance().getPolynomialFunctionSpace(RealLine.getInstance(), polynomialDegree,
				Math.PI, true);
	}

}
