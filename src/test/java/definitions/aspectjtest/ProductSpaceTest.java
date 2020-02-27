/**
 * 
 */
package definitions.aspectjtest;

import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author RoManski
 *
 */
public class ProductSpaceTest extends AspectJTest {

	ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	IGroupGenerator groupGenerator = GroupGenerator.getInstance();
	Field complexNumbers = getComplexPlane();

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
		Vector tmp1 = testSpace(productLeftLeft); 
		Vector tmp2 = testSpace(productLeftRight); 
		Vector tmp3 = testSpace(compTimesComp); 
		Vector tmp4 = testSpace(compTimesThree); 
		int i = 0;
	}
	
	public Vector testSpace(EuclideanSpace space) {
		Vector tmp = space.nullVec();
		for (Vector vec : space.genericBaseToList()) {
			tmp = space.addition(tmp, vec);
		}
		return tmp;
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
