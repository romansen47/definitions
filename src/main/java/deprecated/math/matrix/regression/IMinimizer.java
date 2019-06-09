package deprecated.math.matrix.regression;

import deprecated.math.matrix.IMatrix;
import deprecated.math.matrix.functions.IFunction;

public interface IMinimizer {

	IMatrix find(IFunction function, IMatrix init) throws Exception;

}
