package regression;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main2 extends regression.Main {

	public static void main(String[] args) throws IOException {

		values = readFile(PATH);
		final List<IRegression> linRegs = new ArrayList<>();
		final IRegression linreg = new LinReg(values, 1);
		final double[][] extendedValues = new double[2][values[0].length * 2];
		for (int i = 0; i < values[0].length; i++) {
			extendedValues[0][i] = values[0][i];
			extendedValues[1][i] = values[1][i];
		}
		for (int i = values[0].length; i < extendedValues[0].length; i++) {
			extendedValues[0][i] = values[0][values[0].length - 1] + i - values[0].length;
			extendedValues[1][i] = linreg.getPolynomial().eval(i);
		}
		for (int i = 0; i < DEGREE; i++) {
			final IRegression tmpReg = new LinReg(extendedValues, i);
			linRegs.add(tmpReg);
		}
		evaluateList(linRegs);
		printSortedList(linRegs);
		IRegression.preparePlot(extendedValues, DIMX, DIMY);
		IRegression.drawInput(extendedValues);
		System.out.println("");
		linRegs.get(0).solveAndDraw(Color.RED, values, 10.0);
		linRegs.get(0).solveAndDraw(Color.BLUE, extendedValues, 10.0);
	}
}
