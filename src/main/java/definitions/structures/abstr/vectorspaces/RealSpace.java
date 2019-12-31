/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.groups.ContinuousGroup;

/**
 * @author RoManski
 *
 */
public interface RealSpace extends VectorSpace, ContinuousGroup {
	/**
	 * {@inheritDoc}
	 */
	@Override
	default Field getField() {
		return RealLine.getInstance();
	}

}
