/**
 * 
 */
package definitions.structures.finitedimensional.real.vectorspaces;

import definitions.structures.finitedimensional.real.mappings.FiniteDimensionalEmbedding;

/**
 * @author ro
 *
 */
public interface SubSpace extends EuclideanSpace {

	EuclideanSpace getSuperSpace();
	
	FiniteDimensionalEmbedding getEmbedding();
	
	@Override
	Integer getDim();
}
