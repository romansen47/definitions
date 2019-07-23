package definitions.structures.finitedimensional.real.mappings;

import definitions.structures.abstr.Homomorphism;

/**
 * Isomorphism between vector spaces.
 * 
 * @author ro
 */
public interface Isomorphism extends Homomorphism {

	/**
	 * Method to get the inverse isomorphism.
	 * 
	 * @return
	 * @throws Throwable
	 */
	
	Isomorphism getInverse() throws Throwable;
	 
}
