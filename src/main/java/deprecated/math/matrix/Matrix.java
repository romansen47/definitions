package deprecated.math.matrix;

public class Matrix implements IMatrix {

	private final double[][] values;

	public Matrix(final double[][] values) {
		this.values = values;
	}

	@Override
	public double[][] getValues() {
		return this.values;
	}

	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getLength(); j++) {
				try {
					ans += this.getEntry(i, j) + "   ";
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
			ans += "\r";
		}

		return ans;
	}

}
