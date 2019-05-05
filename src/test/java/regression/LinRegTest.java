package regression;

import java.awt.Color;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class LinRegTest {

	final Random random = new Random();
	final int finalDegree = 11;
	double[][] values;
	final double tolerance = 5;
	final double correctness = 0.05;
	final int dimsX = 1000;
	final int dimsY = 700;

	@Test
	public void linRegTest() {
		for (int degree = 1; degree <= finalDegree; degree++) {
			values = new double[2][degree];
			for (int i = 0; i < degree; i++) {
				values[0][i] = i;
				values[1][i] = random.nextInt(10);
			}
			final IRegression reg = new LinReg(values, degree - 1);
			try {
				IRegression.preparePlot(values, dimsX, dimsY);
				IRegression.drawInput(values);
				reg.solveAndDraw(Color.RED, values, correctness);
			} catch (final Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(reg.getDistance() < tolerance);
			System.out.println("Guete der Regression " + (degree - 1) + "-ter Ordnung: " + reg.getDistance() + ". ");
		}
	}
}
