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

	@Override
	Integer getDim();

	FiniteDimensionalInjection getEmbedding();

	EuclideanSpace getSuperSpace();
}
