/**
 * 
 */
package definitions.structures.abstr.dynamicalsystems;

import definitions.structures.abstr.mappings.VectorField;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public interface DynamicalSystem {

	EuclideanSpace getPhysicalSpace();
	
	VectorField getVectorField();
	
}
