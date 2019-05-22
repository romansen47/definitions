package definitions.structures.abstr;

public interface HilbertSpace extends NormedSpace {

	double product(Vector vec1, Vector vec2) throws Throwable;

	@Override
	default double norm(Vector vec) throws Throwable {
		return Math.sqrt(product(vec, vec));
	}

}
