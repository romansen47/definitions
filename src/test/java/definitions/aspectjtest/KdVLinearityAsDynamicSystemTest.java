package definitions.aspectjtest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContextAware;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSobolevSpace;
import processing.template.impl.Gui;
import solver.StdDraw;

public class KdVLinearityAsDynamicSystemTest extends Gui {

	private static ApplicationContextAware springConfiguration;
	private static DynamicSystem differentialEquation;
	private static EuclideanSpace functionSpace;
	private static Function initialCondition;
	private static int degree = 10;
	private static int sobolevDegree = 1;
	private static Field realLine;
	private static EuclideanSpace space;
	private static KdVLinearityAsDynamicSystemTest test;
	private static Function tmp;
	boolean linear = true;

	final int iterations = (int) 1.e4;
	final double eps = 1.e-5;
	int iteration = 0;

	List<Function> list = new ArrayList<>();
	private final int speed = (int) (500 * iterations * eps);

	public static void main(String[] args) {
		KdVLinearityAsDynamicSystemTest.setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		KdVLinearityAsDynamicSystemTest.setRealLine(RealLine.getInstance());
		KdVLinearityAsDynamicSystemTest.test = new KdVLinearityAsDynamicSystemTest();
		KdVLinearityAsDynamicSystemTest.setFunctionSpace(
				SpaceGenerator.getInstance().getTrigonometricSobolevSpace(KdVLinearityAsDynamicSystemTest.getRealLine(), KdVLinearityAsDynamicSystemTest.degree, KdVLinearityAsDynamicSystemTest.sobolevDegree));
		final VectorSpaceSelfMapping map = ((TrigonometricSobolevSpace) KdVLinearityAsDynamicSystemTest.getFunctionSpace()).getDerivativeBuilder();
		final VectorSpaceSelfMapping nonlinearity = new VectorSpaceSelfMapping() {

			@Override
			public Element get(Element vec) {
				return new GenericFunction() {
					private static final long serialVersionUID = -1105085341775793307L;

					@Override
					public Field getField() {
						return KdVLinearityAsDynamicSystemTest.realLine;
					}

					@Override
					public Scalar value(Scalar input) {
						return (Scalar) KdVLinearityAsDynamicSystemTest.getRealLine().product(((Function) vec).value(input),
								((Function) vec).value(input));
					}

				};
			}

			@Override
			public VectorSpace getSource() {
				return KdVLinearityAsDynamicSystemTest.getFunctionSpace();
			}

		};
		final VectorSpaceSelfMapping newMap = new VectorSpaceSelfMapping() {

			@Override
			public Element get(Element vec) {
				Vector newVec = ((DerivativeOperator) map).get((Vector) vec, 2);
				newVec = getSource().stretch(newVec, KdVLinearityAsDynamicSystemTest.getRealLine().get(-0.5));
				newVec = getSource().addition(getSource().stretch((Vector) vec, KdVLinearityAsDynamicSystemTest.getRealLine().get(0.9)), newVec);
				if (!KdVLinearityAsDynamicSystemTest.test.linear) {
					newVec = getSource().addition(newVec,
							((EuclideanSpace) getSource()).getCoordinates((Vector) nonlinearity.get(vec)));
				}
				return ((DerivativeOperator) map).get(newVec);
			}

			@Override
			public VectorSpace getSource() {
				return KdVLinearityAsDynamicSystemTest.getFunctionSpace();
			}

		};

		KdVLinearityAsDynamicSystemTest.setInitialCondition(new GenericFunction() {

			private static final long serialVersionUID = -4921080371690731611L;
			double support = 0.9;

			@Override
			public Field getField() {
				return KdVLinearityAsDynamicSystemTest.realLine;
			}

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Real) input).getDoubleValue();
				if ((val <= (-support / 2)) || (val >= (support / 2))) {
					return RealLine.getInstance().get(0);
				}
				return RealLine.getInstance().get(0.6 * Math.exp(-1 / (Math.pow(support / 2, 2) - Math.pow(val, 2))));
			}

		});

		KdVLinearityAsDynamicSystemTest.setDifferentialEquation(new DynamicSystem() {

			@Override
			public Group getPhaseSpace() {
				return KdVLinearityAsDynamicSystemTest.getFunctionSpace();
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return newMap;
			}

			@Override
			public Group getTimeSpace() {
				return GroupGenerator.getInstance().getIntegers();
			}

		});
		KdVLinearityAsDynamicSystemTest.space = (EuclideanSpace) KdVLinearityAsDynamicSystemTest.getDifferentialEquation().getPhaseSpace();
		KdVLinearityAsDynamicSystemTest.tmp = (Function) KdVLinearityAsDynamicSystemTest.space.getCoordinates(KdVLinearityAsDynamicSystemTest.initialCondition);
		KdVLinearityAsDynamicSystemTest.initialCondition.plotCompare(-Math.PI, Math.PI, KdVLinearityAsDynamicSystemTest.tmp);
		((Gui) KdVLinearityAsDynamicSystemTest.test).run("definitions.aspectjtest.KdVLinearityAsDynamicSystemTest");
	}

	@Override
	public void settings() {
		size(1000, 1000);
	}

	@Override
	public void setup() {
		frameRate(60);
		drawPreparationsWindows();
		final int size = 50;
		final int sizeOfRect = (xScale - deltaX) / size;
		final int deltaIt = iterations / size;
		list.add(KdVLinearityAsDynamicSystemTest.tmp);
		int count = 0;
		StdDraw.setPenColor(StdDraw.BLACK);
		final double it = iteration;
		//		StdDraw.text(-xScale + deltaX, 1 * sizeOfRect, "loading... ");
		//		StdDraw.text(-xScale + deltaX, 1.5 * sizeOfRect, "loading... ");
		//		StdDraw.text(-xScale + deltaX, 2 * sizeOfRect, "loading... ");
		//		StdDraw.text(-xScale + deltaX, 3 * sizeOfRect, "loading... ");
		//		StdDraw.text(-xScale + deltaX, 3.5 * sizeOfRect, "loading... ");
		for (int i = 0; i < iterations; i++) {
			final Function tmp2 = (Function) KdVLinearityAsDynamicSystemTest.space.addition(KdVLinearityAsDynamicSystemTest.tmp, KdVLinearityAsDynamicSystemTest.space
					.stretch((Function) KdVLinearityAsDynamicSystemTest.differentialEquation.getDefiningMapping().get(KdVLinearityAsDynamicSystemTest.tmp), KdVLinearityAsDynamicSystemTest.getRealLine().get(eps)));
			list.add(tmp2);

			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledSquare(-xScale + deltaX, 5 * sizeOfRect, 5*sizeOfRect);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(-xScale + deltaX, 2.5 * sizeOfRect,(int)100.*i/iterations+" %");
			KdVLinearityAsDynamicSystemTest.tmp = tmp2;
			if ((i % deltaIt) == 0) {
				StdDraw.setPenColor(StdDraw.GREEN);
				StdDraw.filledSquare(-xScale + (2 * deltaX) + (count * sizeOfRect), sizeOfRect, sizeOfRect / 2);
				StdDraw.square(-xScale + (2 * deltaX) + (count * sizeOfRect), sizeOfRect, sizeOfRect / 2);
				count++;
			}
		}
		//		getInitialCondition().plot(-Math.PI, Math.PI);
	}

	StdDraw stddraw;
	int xScale = 400;
	int yScale = xScale / 2;
	int deltaX = 100;

	private void drawPreparationsWindows() {
		stddraw = new StdDraw();
		stddraw.setCanvasSize((2 * xScale) + deltaX, yScale);
		StdDraw.setXscale(-xScale, xScale);
		StdDraw.setYscale(-yScale, yScale);
		StdDraw.setPenRadius(0.001);
	}

	@Override
	public void draw() {
		clear();
		background(255);
		line(500, 0, 500, 1000);
		line(0, 500, 1000, 500);

		stroke(0);
		fill(0);
		text("time: " + iteration + " * " + eps + " = " + (iteration * eps), 200, 200, 15);
		final Function tmp = list.get(iteration++);

		draw(tmp);
		if (tmp.getDerivative().equals(KdVLinearityAsDynamicSystemTest.getFunctionSpace().nullVec()) || ((iteration + speed) >= iterations)) {
			iteration = 0;
		}
		iteration += speed;
	}

	private void draw(Function tmp2) {
		double x1 = -Math.PI;
		Real realX1;
		double x2 = x1 + eps;
		Real realX2;
		while ((x1 + (speed * eps)) < Math.PI) {
			x2 = x1 + (eps * speed);
			realX1 = (Real) KdVLinearityAsDynamicSystemTest.getRealLine().get(x1);
			realX2 = (Real) KdVLinearityAsDynamicSystemTest.getRealLine().get(x2);
			line(500 + (100 * (float) x1), (float) (500 * (1 - (100 * ((Real) tmp2.value(realX1)).getDoubleValue()))),
					500 + (100 * (float) x2),
					(float) (500 * (1 - (100 * ((Real) tmp2.value(realX2)).getDoubleValue()))));
			x1 = x2;
		}
	}

	/**
	 * @return the differentialEquation
	 */
	public static DynamicSystem getDifferentialEquation() {
		return KdVLinearityAsDynamicSystemTest.differentialEquation;
	}

	/**
	 * @param differentialEquation the differentialEquation to set
	 */
	public static void setDifferentialEquation(DynamicSystem differentialEquation) {
		KdVLinearityAsDynamicSystemTest.differentialEquation = differentialEquation;
	}

	/**
	 * @return the functionSpace
	 */
	public static EuclideanSpace getFunctionSpace() {
		return KdVLinearityAsDynamicSystemTest.functionSpace;
	}

	/**
	 * @param functionSpace the functionSpace to set
	 */
	public static void setFunctionSpace(EuclideanSpace functionSpace) {
		KdVLinearityAsDynamicSystemTest.functionSpace = functionSpace;
	}

	/**
	 * @return the initialCondition
	 */
	public static Function getInitialCondition() {
		return KdVLinearityAsDynamicSystemTest.initialCondition;
	}

	/**
	 * @param initialConditio)n the initialCondition to set
	 */
	public static void setInitialCondition(Function initialCondition) {
		KdVLinearityAsDynamicSystemTest.initialCondition = initialCondition;
	}

	/**
	 * @return the realLine
	 */
	public static Field getRealLine() {
		return KdVLinearityAsDynamicSystemTest.realLine;
	}

	/**
	 * @param realLine the realLine to set
	 */
	public static void setRealLine(Field realLine) {
		KdVLinearityAsDynamicSystemTest.realLine = realLine;
	}

	/**
	 * @return the springConfiguration
	 */
	public static ApplicationContextAware getSpringConfiguration() {
		return KdVLinearityAsDynamicSystemTest.springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(ApplicationContextAware springConfiguration) {
		KdVLinearityAsDynamicSystemTest.springConfiguration = springConfiguration;
	}

}
