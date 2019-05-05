package definitions.structures.abstr;

public interface IHilbertSpace extends INormedSpace {

	double product(IVec vec1, IVec vec2) throws Throwable;

	@Override
	default double norm(IVec vec) throws Throwable {
		return Math.abs(product(vec, vec));
	}

}
