package definitions.structures.abstr;

/**
 * 
 * @author ro
 *
 */
public interface NormedSpace extends VectorSpace {

	double norm(Vector vec) throws Throwable;

}
