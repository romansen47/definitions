package definitions.structures.abstr.fields;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface Field extends EuclideanAlgebra {

	default Vector inverse(Vector factor) {
		return ((Scalar) factor).getInverse();
	}

	@Override
	Vector getOne();

	default Vector getZero() {
		return nullVec();
	}

}
