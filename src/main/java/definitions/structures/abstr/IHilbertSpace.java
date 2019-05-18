package definitions.structures.abstr;

public interface IHilbertSpace extends INormedSpace {

	double product(IVector vec1, IVector vec2) throws Throwable;

	@Override
	default double norm(IVector vec) throws Throwable {
		return product(vec, vec);
	}

}
