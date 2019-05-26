package definitions.structures.abstr;

/**
 * 
 * @author ro
 *
 */
public interface VectorSpace {

	boolean contains(Vector vec) throws Throwable;

	Vector nullVec() throws Throwable;

	Vector add(Vector vec1, Vector vec2) throws Throwable;

	Vector stretch(Vector vec1, double r) throws Throwable;

}
