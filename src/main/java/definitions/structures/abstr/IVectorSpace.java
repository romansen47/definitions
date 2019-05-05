package definitions.structures.abstr;

public interface IVectorSpace {

	boolean contains(IVector vec) throws Throwable;

	IVector nullVec() throws Throwable;

}
