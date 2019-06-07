package math.matrix;

public class Matrix implements IMatrix {

	private final double[][] values;

	public Matrix(double[][] values) {
		this.values = values;
	}

	@Override
	public double[][] getValues() {
		return values;
	}

	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getLength(); j++) {
				try {
					ans += getEntry(i, j) + "   ";
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			ans += "\r";
		}

		return ans;
	}

}
