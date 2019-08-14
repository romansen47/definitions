package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface Algebra extends VectorSpace {

	Vector product(Vector vec1, Vector vec2);

	Vector getOne();

}
