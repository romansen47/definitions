package math.matrix;

public class UnitMatrix extends Matrix {

	public UnitMatrix(IMatrix mat) throws Exception {
		super(mat.toUnit().getValues());
	}

}
