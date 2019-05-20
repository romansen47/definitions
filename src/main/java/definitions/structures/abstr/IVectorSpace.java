package definitions.structures.abstr;

public interface IVectorSpace {

	boolean contains(IVector vec) throws Throwable;

	IVector nullVec() throws Throwable;

	IVector add(IVector vec1, IVector vec2) throws Throwable;

	IVector stretch(IVector vec1, double r) throws Throwable;

}
