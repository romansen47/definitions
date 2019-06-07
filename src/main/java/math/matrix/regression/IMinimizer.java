package math.matrix.regression;

import math.matrix.IMatrix;
import math.matrix.functions.IFunction;

public interface IMinimizer {

	IMatrix find(IFunction function, IMatrix init) throws Exception;

}
