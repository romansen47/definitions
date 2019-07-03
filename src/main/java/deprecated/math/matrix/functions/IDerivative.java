package deprecated.math.matrix.functions;

import deprecated.math.matrix.IMatrix;

public interface IDerivative {

	double eps = 1.e-7;

	IFunction jacobi(IMatrix mat);

	IMatrix jacobiMatrix(IMatrix mat) throws Exception;

}
