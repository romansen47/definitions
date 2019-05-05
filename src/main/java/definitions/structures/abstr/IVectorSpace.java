package definitions.structures.abstr;

public interface IVectorSpace {

	boolean contains(IVec vec);

	IVec nullVec() throws Throwable;

}
