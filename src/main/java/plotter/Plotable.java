package plotter;

import definitions.structures.abstr.vectorspaces.vectors.Function;

public interface Plotable {

	/**
	 * Method to plot the function.
	 *
	 * @param left  the left.
	 * @param right the right.
	 */
	void plot(double left, double right);

	/**
	 * Method to plot the function against another function.
	 *
	 * @param left  the left.
	 * @param right the right.
	 * @param fun   the other function.
	 */
	void plotCompare(double left, double right, Function fun);

}
