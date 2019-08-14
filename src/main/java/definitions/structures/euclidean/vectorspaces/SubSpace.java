/**
 * 
 */
package definitions.structures.euclidean.vectorspaces;

import definitions.structures.euclidean.mappings.FiniteDimensionalInjection;

/**
 * @author ro
 *
 */
public interface SubSpace extends EuclideanSpace {

	EuclideanSpace getSuperSpace();

	FiniteDimensionalInjection getEmbedding();

	@Override
	Integer getDim();
}
