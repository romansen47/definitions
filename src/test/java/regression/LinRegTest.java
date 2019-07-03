package regression;

import java.awt.Color;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import deprecated.regression.IRegression;
import deprecated.regression.LinReg;

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
		for (int degree = 1; degree <= this.finalDegree; degree++) {
			this.values = new double[2][degree];
			for (int i = 0; i < degree; i++) {
				this.values[0][i] = i;
				this.values[1][i] = this.random.nextInt(10);
			}
			final IRegression reg = new LinReg(this.values, degree - 1);
			try {
				IRegression.preparePlot(this.values, this.dimsX, this.dimsY);
				IRegression.drawInput(this.values);
				reg.solveAndDraw(Color.RED, this.values, this.correctness);
			} catch (final Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(reg.getDistance() < this.tolerance);
			System.out.println("Guete der Regression " + (degree - 1) + "-ter Ordnung: " + reg.getDistance() + ". ");
		}
	}
}
