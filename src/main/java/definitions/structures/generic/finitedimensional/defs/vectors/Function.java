package definitions.structures.generic.finitedimensional.defs.vectors;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;
import functions.IFunction;
import proprietary.StdDraw;
import regression.DataSet;
import regression.IRegression;

public interface Function extends FiniteVector {

	default double getLeft() {
		return -Math.PI;
	}

	default double getRight() {
		return Math.PI;
	}
	
	double value(double input) throws Throwable;

	default boolean equals(Function other, IFiniteDimensionalFunctionSpace source) throws Throwable {
		final int n = 100;
		double a = source.getIntervall()[0];
		double b = source.getIntervall()[1];
		for (int i = 0; i < n; i++) {
			if (value(a + i * (b - a) / 99.) != other.value(a + i * (b - a) / 99.)) {
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	default void plot(double left,double right)
			throws Throwable {
		
		int count=10000;
		
		
		double delta=(right-left)/count;
		double x=0;
		double min=value((right-left)/2.);
		double max=min;
		for (double i = 0; i < count-1; i += 1) {
			x=left+delta*i;
			double y=value(x);
			if (y>max) {
				max=y;
			}
			if (y<min) {
				min=y;
			}
		}
		
		final StdDraw stddraw = new StdDraw();
		stddraw.setCanvasSize(1000, 700);
		StdDraw.setXscale(left, right);
		StdDraw.setYscale(1.5*min,1.5*max);

		double z=0;
		StdDraw.setPenRadius(0.001);
		for (double i = 0; i < count-1; i += 1) {
			z=left+delta*i;
			StdDraw.setPenColor(Color.black);
			StdDraw.line(z, value(z), z+delta, value(z+delta));
		}
	}
	
	@SuppressWarnings("unused")
	default void plotCompare(double left,double right,Function fun)
			throws Throwable {
		
		int count=1000;
				
		double delta=(right-left)/count;
		double x=0;
		double min=value((right-left)/2.);
		double max=min;
		for (double i = 0; i < count-1; i += 1) {
			x=left+delta*i;
			double y=value(x);
			if (y>max) {
				max=y;
			}
			if (y<min) {
				min=y;
			}
		}
		
		final StdDraw stddraw = new StdDraw();
		stddraw.setCanvasSize(1000, 700);
		StdDraw.setXscale(left, right);
		StdDraw.setYscale(min-0.5*Math.abs(min),max+0.5*Math.abs(max));

		double z=0;
		StdDraw.setPenRadius(0.003);
		for (double i = 0; i < count-1; i += 1) {
			z=left+delta*i;
			StdDraw.setPenColor(Color.blue);
			StdDraw.line(z, value(z), z+delta, value(z+delta));
			StdDraw.setPenColor(Color.red);
			StdDraw.line(z, fun.value(z), z+delta, fun.value(z+delta));
		}
	}
}
