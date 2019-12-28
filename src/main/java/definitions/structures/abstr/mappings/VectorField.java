/**
 * 
 */
package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public interface VectorField extends Mapping {

	@Override
	default Monoid getTarget() {
		return (EuclideanSpace) this.getSource();
	}

}
