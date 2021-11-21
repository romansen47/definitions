package definitions.structures.abstr.mappings;

/**
 * Isomorphism between vector spaces.
 *
 * @author ro
 */
public interface VectorSpaceIsomorphism extends GroupIsomorphism, VectorSpaceMonomorphism, VectorSpaceEpimorphism {

	/**
	 * Method to get the inverse isomorphism.
	 *
	 * @return the inverse
	 */
	VectorSpaceIsomorphism getInverse();

}
