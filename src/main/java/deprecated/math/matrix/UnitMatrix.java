package deprecated.math.matrix;

public class UnitMatrix extends Matrix {

	public UnitMatrix(final IMatrix mat) throws Exception {
		super(mat.toUnit().getValues());
	}

}
