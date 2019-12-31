/**
 * 
 */
package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.monoids.Monoid;

/**
 * @author ro
 *
 */
public interface VectorField extends Mapping {

	@Override
	default Monoid getTarget() {
		return this.getSource();
	}

}
