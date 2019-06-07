package regression;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

	protected static final String PATH = "src/main/resources/test2.csv";
	static final int DEGREE = 11;
	static final int DIMX = 500;
	static final int DIMY = 300;
	protected static double[][] values;

	public static void main(String[] args) throws IOException {

		values = readFile(PATH);
		final List<IRegression> linRegs = new ArrayList<>();
		for (int i = 0; i < DEGREE; i++) {
			final IRegression tmpReg = new LinReg(values, i);
			linRegs.add(tmpReg);
		}
		evaluateList(linRegs);
		printSortedList(linRegs);
		IRegression.preparePlot(values, DIMX, DIMY);
		IRegression.drawInput(values);
		System.out.println("");
		linRegs.get(0).solveAndDraw(Color.RED, values, 10.0);
	}

	protected static void printSortedList(List<IRegression> linRegs) {
		for (int i = 0; i < DEGREE; i++) {
			System.out.println("Regression " + linRegs.get(i).getPolynomial().getDegree() + "-ter Ordnung: Abstand="
					+ linRegs.get(i).getDistance());
		}
	}

	public static List<Integer> evaluateList(List<IRegression> linRegs) {
		class SortByEval implements Comparator<IRegression> {
			@Override
			public int compare(IRegression o1, IRegression o2) {
				return (int) (o1.getDistance() - o2.getDistance());
			}
		}
		Collections.sort(linRegs, new SortByEval());
		final List<Integer> sortedMoves = new ArrayList<>();
		for (final IRegression reg : linRegs) {
			sortedMoves.add(((int) reg.getDistance()));
		}
		return sortedMoves;
	}

	protected static double[][] readFile(String string) throws IOException {
		final List<double[]> values = new ArrayList<>();
		final BufferedReader br = new BufferedReader(new FileReader(string));
		String line = "";
		LocalDate firstDate = null;
		try {
			while ((line = br.readLine()) != null) {
				final String[] parts = line.split(";");
				final String[] tmpdate = parts[0].split("-");
				final LocalDate date = LocalDate.of(Integer.parseInt(tmpdate[0]), Integer.parseInt(tmpdate[1]),
						Integer.parseInt(tmpdate[2]));
				if (firstDate == null) {
					firstDate = date;
				}
				final double[] tmp = new double[2];
				tmp[0] = firstDate.until(date, ChronoUnit.DAYS);
				tmp[1] = Double.parseDouble(parts[1]);
				values.add(tmp);
			}
		} finally {
			br.close();
		}
		final double[][] ans = new double[2][values.size()];
		for (int i = 0; i < values.size(); i++) {
			ans[0][i] = values.get(i)[0];
			ans[1][i] = values.get(i)[1];
		}
		return ans;
	}

}
