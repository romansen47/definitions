/**
 * 
 */
package definitions.structures.euclidean.functionspaces.impl;

import org.junit.Test;

import definitions.structures.abstr.FunctionSpaceTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.MetricSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class FiniteDimensionalSobolevSpaceTestDepr extends FunctionSpaceTest {

	final static Field realSpace = RealLine.getInstance();
	final static int sobolevDegree = 3;
	final static int fourierDegree = 20;
	final static VectorSpace trigonometricSobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(realSpace, fourierDegree, sobolevDegree);

	final static VectorSpace trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSpace(realSpace,
			fourierDegree);

	@Override
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) trigonometricSobolevSpace;
	}

	@Test
	public void compareL2ToSobolev() {
		final Function staircaseFunction2Projection = staircaseFunction2.getProjection(this.getLinearSpace());
		final Function staircaseFunction3Projection = staircaseFunction2
				.getProjection((EuclideanSpace) trigonometricSpace);
		staircaseFunction2Projection.plotCompare(-1, 1, staircaseFunction3Projection);
		final Function x = staircaseFunction2Projection.getProjection((EuclideanSpace) trigonometricSpace).toGenericFunction();
		final Function y = staircaseFunction3Projection.getProjection((EuclideanSpace) trigonometricSobolevSpace).toGenericFunction();
		System.out.println(((MetricSpace) trigonometricSpace).getDistance(staircaseFunction2Projection, y));
		System.out.println(((MetricSpace) trigonometricSobolevSpace).getDistance(x, staircaseFunction3Projection));
	}
}
