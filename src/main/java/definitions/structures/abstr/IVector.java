package definitions.structures.abstr;

public interface IVector {

	int getDim();

	boolean elementOf(IVectorSpace space) throws Throwable;

	boolean equals(IVector vec) throws Throwable;

}
