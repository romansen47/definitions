//package definitions.structures.abstr.algebra.fields.impl;
//
//import java.util.Map;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import definitions.prototypes.AspectJTest;
//import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
//import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
//import definitions.structures.abstr.vectorspaces.vectors.Vector;
//import definitions.structures.euclidean.Generator;
//
//public class QuaternionSpaceMockitoTest extends AspectJTest {
//
//	QuaternionSpace quaternionSpace = (QuaternionSpace) QuaternionSpace.getInstance();
//	RealLine realLine = getRealLine();
//	Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = quaternionSpace.getMultiplicationMatrix();
//
//	@Test
//	public void getTest() {
//		for (double x = -10.; x < 10; x++) {
//			Quaternion y = (Quaternion) quaternionSpace.get(x);
//			Assert.assertTrue(y.getReal().equals(realLine.get(x)));
//		}
//	}
//
//
//	@Test
//	public void getPrimeFieldTest() {
//		Assert.assertTrue(
//				quaternionSpace.getPrimeField().equals(Generator.getInstance().getGroupGenerator().getRationals()));
//	}
//}
