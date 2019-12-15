/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.groups.ContinuousGroup;
import settings.annotations.Proceed;

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
