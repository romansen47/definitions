package definitions.structures.field;

import definitions.structures.abstr.EuclideanAlgebra;
import definitions.structures.abstr.Vector;

public interface Field extends EuclideanAlgebra {

	Vector inverse(Vector factor);

	@Override
	Vector getOne();

	default Vector getZero() {
		return nullVec();
	};

}
