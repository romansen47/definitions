// package subspaces;
//
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import org.junit.Assert;
// import org.junit.BeforeClass;
// import org.junit.Test;
//
// import definitions.structures.abstr.Vector;
// import definitions.structures.finitedimensional.real.Generator;
// import
// definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
// import
// definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace;
// import
// definitions.structures.finitedimensional.real.mappings.FiniteDimensionalEmbedding;
// import
// definitions.structures.finitedimensional.real.subspaces.impl.FiniteDimensionalFunctionSubSpace;
// import definitions.structures.finitedimensional.real.vectors.Function;
// import
// definitions.structures.finitedimensional.real.vectors.specialfunctions.Constant;
// import
// definitions.structures.finitedimensional.real.vectors.specialfunctions.ExponentialFunction;
// import
// definitions.structures.finitedimensional.real.vectors.specialfunctions.LinearFunction;
// import
// definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
// import
// definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
// import
// definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;
//
// public class DistanceToSubSpacesTest {
//
// static double answer;
//
// static Function sin;
// static Function cos;
// static Function constant;
// static Function identity;
// static Function exp;
//
// static EuclideanSpace genericSpace;
//
// static EuclideanFunctionSpace space;
// static EuclideanFunctionSpace extendedSpace;
//
// static FiniteDimensionalFunctionSubSpace spaceAsSubSpace;
//
// @BeforeClass
// public static void setUpBeforeClass() throws Throwable {
//
// genericSpace = (EuclideanSpace)
// Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(5);
//
// sin = new Sine(1, 0, 1);
//
// cos = new Sine(1, 0.5 * Math.PI, 1);
//
// constant = new Constant(1 / Math.sqrt(2 * Math.PI));
//
// identity = new LinearFunction(0, 1);
//
// exp = new ExponentialFunction(0, 1);
//
// final List<Vector> list = new ArrayList<>();
//
// space = SpaceGenerator.getInstance().getTrigonometricSpace(1);
//
//// list.add(sin);
//// list.add(cos);
//// list.add(constant);
// list.add(identity);
// list.add(exp);
//
//// extendedSpace = new FiniteDimensionalFunctionSpace(list, -Math.PI,
// Math.PI);
//
// extendedSpace=(EuclideanFunctionSpace)
// SpaceGenerator.getInstance().extend(space,identity);
//
// extendedSpace=(EuclideanFunctionSpace)
// SpaceGenerator.getInstance().extend(extendedSpace,exp);
//
// final Map<Vector, Map<Vector, Double>> coordinates2 = new HashMap<>();
// for (final Vector fun1 : space.genericBaseToList()) {
// final Map<Vector, Double> tmp = new HashMap<>();
// for (final Vector fun2 : extendedSpace.genericBaseToList()) {
// tmp.put(fun2, extendedSpace.innerProduct(fun1, fun2));
// }
// coordinates2.put(fun1, tmp);
// }
//
// final FiniteDimensionalEmbedding parametrization =
// (FiniteDimensionalEmbedding) Generator.getGenerator()
// .getMappinggenerator().getFiniteDimensionalLinearMapping(space,
// extendedSpace, coordinates2);
//
// spaceAsSubSpace = new FiniteDimensionalFunctionSubSpace(parametrization,
// extendedSpace);
//
// final Vector id = identity.getProjection(spaceAsSubSpace);
// final Vector id2 = identity.getProjection(extendedSpace);
//
// answer = spaceAsSubSpace.getDistance(id,id2);
//
// }
//
// @Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
// public void first1() throws Throwable {
// System.out.println("answer: "+answer);
// System.out.println("difference: "+(answer - 2 * Math.sqrt(Math.PI)));
// Assert.assertTrue(Math.abs(answer - 2 * Math.sqrt(Math.PI)) < 1.e-3);
// }
//
// }
