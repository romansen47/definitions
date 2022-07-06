/**
 *
 */
package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * Not clear what this is supposed to show, maybe just remained after debugging
 * 
 * @author RoManski
 *
 */
public class ProductSpaceTest extends AspectJTest {

	public static final Logger logger = LogManager.getLogger(ProductSpaceTest.class);

	ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	IGroupGenerator groupGenerator = GroupGenerator.getInstance();
	Field complexNumbers = AspectJTest.getComplexPlane();

	private final EuclideanSpace leftSpace = spaceGenerator.getFiniteDimensionalVectorSpace(2);
	private final EuclideanSpace rightSpace = spaceGenerator.getFiniteDimensionalVectorSpace(3);

	private final EuclideanSpace complexLeftSpace = spaceGenerator.getFiniteDimensionalVectorSpace(complexNumbers, 2);
	private final EuclideanSpace complexRightSpace = spaceGenerator.getFiniteDimensionalVectorSpace(complexNumbers, 3);

	private final EuclideanSpace productLeftLeft = spaceGenerator.getOuterProduct(leftSpace, leftSpace);
	private final EuclideanSpace productLeftRight = spaceGenerator.getOuterProduct(leftSpace, rightSpace);

	private final EuclideanSpace compTimesComp = spaceGenerator.getOuterProduct(complexLeftSpace, complexRightSpace);
	private final EuclideanSpace compTimesThree = spaceGenerator.getOuterProduct(complexLeftSpace, complexRightSpace);

	@Test
	public void testproductSpaces() {
		Assert.assertTrue(testSpace(productLeftLeft));
		Assert.assertTrue(testSpace(productLeftRight));
		Assert.assertTrue(testSpace(compTimesComp));
		Assert.assertTrue(testSpace(compTimesThree));
	}

	public boolean testSpace(EuclideanSpace space) {
		Vector tmp = space.nullVec();
		for (final Vector vec : space.genericBaseToList()) {
			tmp = space.addition(tmp, vec);
		}
		boolean onlyOnes = true;
		FiniteVector newTmp = ((FiniteVector) tmp);
		for (final Vector vec : space.genericBaseToList()) {
			onlyOnes = onlyOnes && newTmp.getCoordinates().get(vec).equals(space.getField().getOne());
		}
		return onlyOnes;
	}

	/**
	 * @return the complexLeftSpace
	 */
	public EuclideanSpace getComplexLeftSpace() {
		return complexLeftSpace;
	}

	/**
	 * @return the complexRightSpace
	 */
	public EuclideanSpace getComplexRightSpace() {
		return complexRightSpace;
	}

	/**
	 * @return the productLeftLeft
	 */
	public EuclideanSpace getProductLeftLeft() {
		return productLeftLeft;
	}

	/**
	 * @return the productLeftRight
	 */
	public EuclideanSpace getProductLeftRight() {
		return productLeftRight;
	}

	/**
	 * @return the compTimesComp
	 */
	public EuclideanSpace getCompTimesComp() {
		return compTimesComp;
	}

	/**
	 * @return the compTimesThree
	 */
	public EuclideanSpace getCompTimesThree() {
		return compTimesThree;
	}

}
