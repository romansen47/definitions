package definitions.structures.euclidean.vectors;

import java.io.Serializable;

import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface IVectorGenerator extends Serializable {

	Vector getFiniteVector(int dim);

}
