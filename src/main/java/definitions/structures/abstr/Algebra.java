package definitions.structures.abstr;

public interface Algebra extends VectorSpace {

	Vector product(Vector vec1, Vector vec2);

	Vector getOne();

}
