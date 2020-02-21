package definitions.aspectjtest;

import java.util.ArrayList;
import java.util.List;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSobolevSpace;
import processing.template.Gui;

public class TranslationsAsDynamicSystemTest extends Gui {

	private static SpringConfiguration springConfiguration;
	private static DynamicSystem differentialEquation;
	private static EuclideanSpace functionSpace;
	private static Function initialCondition;
	private static int degree = 3;
	private static int sobolevDegree = 1;
	private static Field realLine;
	private static EuclideanSpace space;
	private static TranslationsAsDynamicSystemTest test;
	private static Function tmp;

	final int iterations = 100;
	final double eps = 1.e-1;
	int iteration = 0;

	public static void main(String[] args) {
		setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		setRealLine(RealLine.getInstance());
		test = new TranslationsAsDynamicSystemTest();
		setFunctionSpace(
				SpaceGenerator.getInstance().getTrigonometricSobolevSpace(getRealLine(), degree, sobolevDegree));
		VectorSpaceSelfMapping map = ((TrigonometricSobolevSpace) getFunctionSpace()).getDerivativeBuilder();
		VectorSpaceSelfMapping nonlinearity = new VectorSpaceSelfMapping() {

			@Override
			public Element get(Element vec) {
				return new GenericFunction() {

					@Override
					public Scalar value(Scalar input) {
						return (Scalar) getRealLine().product(((Function) vec).value(input),
								((Function) map.get(vec)).value(input));
					}

				};
			}

			@Override
			public VectorSpace getSource() {
				return getFunctionSpace();
			}

		};
		VectorSpaceSelfMapping newMap = new VectorSpaceSelfMapping() {

			@Override
			public Element get(Element vec) {
				Vector newVec = (Vector) map.get(map.get(map.get(vec)));
//				newVec =(Vector) map.get(vec);
//				newVec = getSource().addition((Vector) map.get(vec),
//						getSource().stretch((Vector) map.get(newVec), (Scalar) getRealLine().getMinusOne()));
				newVec = getSource()
						.addition(
								getSource().addition((Vector) map.get(vec),
										getSource().stretch((Vector) map.get(newVec),
												(Scalar) getRealLine().getMinusOne())),
								((EuclideanSpace) getSource()).getCoordinates((Vector) nonlinearity.get(vec)));
				return newVec;
			}

			@Override
			public VectorSpace getSource() {
				return getFunctionSpace();
			}

		};
//		setInitialCondition((Function) getFunctionSpace().addition(new Sine(1d, 0d, 1d) {
//		}, new Sine(1d, 0.5 * Math.PI, 1d) {
//		}));
		setInitialCondition((Function) getFunctionSpace().addition(new Sine(1d, 0d, 1d) {
		}, new Sine(1d, 0d, 1d) {
		}));
//		setInitialCondition(new GenericFunction() {
//
//			@Override
//			public Scalar value(Scalar input) {
//				return RealLine.getInstance().get(10*(-1+Math.exp(-1/Math.abs(Math.pow(Math.PI,2)-Math.pow(input.getDoubleValue(),2)))));
//			}
//			
//		});
		LinearMappingsSpace linearMappingsSpace = new LinearMappingsSpace(getFunctionSpace(), getFunctionSpace());
		setDifferentialEquation(new DynamicSystem() {

			@Override
			public Group getPhaseSpace() {
				return getFunctionSpace();
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return newMap;
//				return (VectorSpaceEndomorphism)linearMappingsSpace.addition((VectorSpaceEndomorphism) map, (VectorSpaceEndomorphism) map);
			}

		});
		space = (EuclideanSpace) getDifferentialEquation().getPhaseSpace();
		tmp = initialCondition;
		((Gui) test).run("definitions.aspectjtest.TranslationsAsDynamicSystemTest");
	}

	List<Function> list = new ArrayList<>();

	@Override
	public void settings() {
		size(1000, 1000);
	}

	@Override
	public void setup() {
		frameRate(2);
		list.add(tmp);
		for (int i = 0; i < iterations; i++) {
			Function tmp2 = (Function) space.addition(tmp, space
					.stretch((Function) differentialEquation.getDefiningMapping().get(tmp), getRealLine().get(eps)));
			list.add(tmp2);
			tmp = tmp2;
			System.out.println(i + "th iteration completed");
		}
	}

	@Override
	public void draw() {
		clear();
		background(255);
		line(500, 0, 500, 1000);
		line(0, 500, 1000, 500);

		stroke(0);
		fill(0);
		text("time: " + iteration + " * " + eps + " = " + iteration++ * eps, 200, 200, 15);
		Function tmp = list.get(iteration);
		draw(tmp);
		if (tmp.getDerivative().equals(getFunctionSpace().nullVec()) || iteration == iterations) {
			System.exit(0);
		}
	}

	private void draw(Function tmp2) {
		double x1 = -Math.PI;
		Real realX1;
		double x2 = x1 + eps;
		Real realX2;
		while (x2 < Math.PI) {
			x2 = x1 + eps;
			realX1 = (Real) getRealLine().get(x1);
			realX2 = (Real) getRealLine().get(x2);
			line(500 + 100 * (float) x1, (float) (500 + 100 * tmp2.value(realX1).getDoubleValue()),
					500 + 100 * (float) x2, (float) (500 + 100 * tmp2.value(realX2).getDoubleValue()));
			x1 = x2;
		}
	}

	/**
	 * @return the differentialEquation
	 */
	public static DynamicSystem getDifferentialEquation() {
		return differentialEquation;
	}

	/**
	 * @param differentialEquation the differentialEquation to set
	 */
	public static void setDifferentialEquation(DynamicSystem differentialEquation) {
		TranslationsAsDynamicSystemTest.differentialEquation = differentialEquation;
	}

	/**
	 * @return the functionSpace
	 */
	public static EuclideanSpace getFunctionSpace() {
		return functionSpace;
	}

	/**
	 * @param functionSpace the functionSpace to set
	 */
	public static void setFunctionSpace(EuclideanSpace functionSpace) {
		TranslationsAsDynamicSystemTest.functionSpace = functionSpace;
	}

	/**
	 * @return the initialCondition
	 */
	public static Function getInitialCondition() {
		return initialCondition;
	}

	/**
	 * @param initialCondition the initialCondition to set
	 */
	public static void setInitialCondition(Function initialCondition) {
		TranslationsAsDynamicSystemTest.initialCondition = initialCondition;
	}

	/**
	 * @return the realLine
	 */
	public static Field getRealLine() {
		return realLine;
	}

	/**
	 * @param realLine the realLine to set
	 */
	public static void setRealLine(Field realLine) {
		TranslationsAsDynamicSystemTest.realLine = realLine;
	}

	/**
	 * @return the springConfiguration
	 */
	public static SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(SpringConfiguration springConfiguration) {
		TranslationsAsDynamicSystemTest.springConfiguration = springConfiguration;
	}

}
