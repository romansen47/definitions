package definitions.structures.abstr.mappings;

/**
 * Isomorphism between vector spaces.
 * 
 * @author ro
 */
public interface Isomorphism extends Monomorphism, Epimorphism {

	/**
	 * Method to get the inverse isomorphism.
	 * 
	 * @return
	 * @throws Throwable
	 */

	Isomorphism getInverse() throws Throwable;

}
