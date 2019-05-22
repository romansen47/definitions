package definitions.structures.abstr;

public interface Vector {

	int getDim();

	boolean elementOf(VectorSpace space) throws Throwable;

	boolean equals(Vector vec) throws Throwable;

}
