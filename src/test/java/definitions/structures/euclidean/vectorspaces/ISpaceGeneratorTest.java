package definitions.structures.euclidean.vectorspaces;

///**
// *
// */
//package definitions.structures.finitedimensional.vectorspaces;
//
//import static org.junit.Assert.fail;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import definitions.structures.field.impl.RealLine;
//import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
//import definitions.structures.finitedimensional.vectors.Function;
//import definitions.structures.finitedimensional.vectors.specialfunctions.LinearFunction;
//import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;
//
///**
// * @author RoManski
// *
// */
//public class ISpaceGeneratorTest {
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//		ISpaceGenerator spGen = SpaceGenerator.getInstance();
//		EuclideanFunctionSpace tempSpace = spGen.getTrigonometricSobolevSpace(RealLine.getInstance(), 4, 5);
//		Function id = new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne());
//
//		EuclideanFunctionSpace trigSpace = (EuclideanFunctionSpace) spGen.extend(tempSpace, id);
////		trigSpace = (EuclideanFunctionSpace) spGen.extend(tempSpace, id);
//
//	}
//
//	@Test
//	public final void test() {
//		fail("Not yet implemented"); // TODO
//	}
//
//}
