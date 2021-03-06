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
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace;
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
	private static int degree = 20;
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
	private int speed = (int) (1000*iterations * eps);

	public static void main(String[] args) {
		setSpringConfiguration(SpringConfiguration.getSpringConfiguration());
		setRealLine(RealLine.getInstance());
		test = new KdVLinearityAsDynamicSystemTest();
		setFunctionSpace(
				SpaceGenerator.getInstance().getTrigonometricSobolevSpace(getRealLine(), degree, sobolevDegree));
		VectorSpaceSelfMapping map = ((TrigonometricSobolevSpace) getFunctionSpace()).getDerivativeBuilder();
		VectorSpaceSelfMapping nonlinearity = new VectorSpaceSelfMapping() {

			@Override
			public Element get(Element vec) {
				return new GenericFunction() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -1105085341775793307L;

					@Override
					public Scalar value(Scalar input) {
						return (Scalar) getRealLine().product(((Function) vec).value(input),
								((Function) vec).value(input));
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
				Vector newVec = ((DerivativeOperator) map).get((Vector) vec, 2);
				newVec = getSource().stretch(newVec, getRealLine().get(-0.2));
				newVec = getSource().addition(getSource().stretch((Vector) vec, getRealLine().get(0.2)),newVec);
				if (!test.linear) {
					newVec = getSource().addition(newVec,
							((EuclideanSpace) getSource()).getCoordinates((Vector) nonlinearity.get(vec)));
				}
				return ((DerivativeOperator) map).get(newVec);
			}

			@Override
			public VectorSpace getSource() {
				return getFunctionSpace();
			}

		};
		
		setInitialCondition(new GenericFunction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4921080371690731611L;
			double support = 0.9;

			@Override
			public Scalar value(Scalar input) {
				double val = input.getDoubleValue();
				double a = 10.0;
				if (val <= -support / 2 || val >= support / 2) {
					return RealLine.getInstance().get(0);
				}
				return RealLine.getInstance().get(0.6 * Math.exp(-1 / (Math.pow(support / 2, 2) - Math.pow(val, 2))));
//				return RealLine.getInstance().get(0.5+0.2*Math.cos(input.getDoubleValue()));
			}

		});
		
		LinearMappingsSpace linearMappingsSpace = new LinearMappingsSpace(getFunctionSpace(), getFunctionSpace());
		
		setDifferentialEquation(new DynamicSystem() {

			@Override
			public Group getPhaseSpace() {
				return getFunctionSpace();
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return newMap;
			}

		});
		space = (EuclideanSpace) getDifferentialEquation().getPhaseSpace();
		tmp = (Function) space.getCoordinates(initialCondition);
		initialCondition.plotCompare(-Math.PI, Math.PI, tmp);
		((Gui) test).run("definitions.aspectjtest.KdVLinearityAsDynamicSystemTest");
	}

	@Override
	public void settings() {
		size(1000, 1000);
	}

	@Override
	public void setup() {
		frameRate(60);
		drawPreparationsWindows();
		int size = 20;
		int sizeOfRect = (xScale - deltaX) / size;
		int deltaIt = iterations / size;
		list.add(tmp);
		int count = 0;
		StdDraw.setPenColor(StdDraw.BLACK);
		double it = iteration;
		StdDraw.text(-xScale + deltaX, 1 * sizeOfRect, "loading... ");
		StdDraw.text(-xScale + deltaX, 1.5 * sizeOfRect, "loading... ");
		StdDraw.text(-xScale + deltaX, 2 * sizeOfRect, "loading... ");
		StdDraw.text(-xScale + deltaX, 2.5 * sizeOfRect, "loading... ");
//		StdDraw.text(-xScale + deltaX, 3 * sizeOfRect, "loading... ");
//		StdDraw.text(-xScale + deltaX, 3.5 * sizeOfRect, "loading... ");
		for (int i = 0; i < iterations; i++) {
			Function tmp2 = (Function) space.addition(tmp, space
					.stretch((Function) differentialEquation.getDefiningMapping().get(tmp), getRealLine().get(eps)));
			list.add(tmp2);
			tmp = tmp2;
			if (i % deltaIt == 0) {
				StdDraw.setPenColor(StdDraw.GREEN);
				StdDraw.filledSquare(-xScale + 2 * deltaX + count * sizeOfRect, sizeOfRect, sizeOfRect / 2);
				StdDraw.square(-xScale + 2 * deltaX + count * sizeOfRect, sizeOfRect, sizeOfRect / 2);
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
		stddraw.setCanvasSize(2 * xScale + deltaX, yScale);
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
		text("time: " + iteration + " * " + eps + " = " + iteration * eps, 200, 200, 15);
		Function tmp = list.get(iteration++);

		draw(tmp);
		if (tmp.getDerivative().equals(getFunctionSpace().nullVec()) || iteration + speed >= iterations) {
			iteration = 0;
		}
		iteration += speed;
	}

	private void draw(Function tmp2) {
		double x1 = -Math.PI;
		Real realX1;
		double x2 = x1 + eps;
		Real realX2;
		while (x1 + speed * eps < Math.PI) {
			x2 = x1 + eps * speed;
			realX1 = (Real) getRealLine().get(x1);
			realX2 = (Real) getRealLine().get(x2);
			line(500 + 100 * (float) x1, (float) (500 * (1 - 100 * ((Scalar) tmp2.value(realX1)).getDoubleValue())),
					500 + 100 * (float) x2, (float) (500 * (1 - 100 * ((Scalar) tmp2.value(realX2)).getDoubleValue())));
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
		KdVLinearityAsDynamicSystemTest.differentialEquation = differentialEquation;
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
		KdVLinearityAsDynamicSystemTest.functionSpace = functionSpace;
	}

	/**
	 * @return the initialCondition
	 */
	public static Function getInitialCondition() {
		return initialCondition;
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
		return realLine;
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
		return springConfiguration;
	}

	/**
	 * @param springConfiguration the springConfiguration to set
	 */
	public static void setSpringConfiguration(ApplicationContextAware springConfiguration) {
		KdVLinearityAsDynamicSystemTest.springConfiguration = springConfiguration;
	}

}
