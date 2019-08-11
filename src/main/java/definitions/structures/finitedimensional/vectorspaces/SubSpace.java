/**
 * 
 */
package definitions.structures.finitedimensional.vectorspaces;

import definitions.structures.finitedimensional.mappings.FiniteDimensionalEmbedding;

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
