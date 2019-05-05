package definitions.structures.abstr;

public interface IVectorSpace {

	boolean contains(IVec vec) throws Throwable;

	IVec nullVec() throws Throwable;

}
