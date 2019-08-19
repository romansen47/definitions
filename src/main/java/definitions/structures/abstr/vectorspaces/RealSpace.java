/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.groups.ContinuousGroup;

/**
 * @author RoManski
 *
 */
public interface RealSpace extends VectorSpace, ContinuousGroup {

	@Override
	default Field getField() {
		return RealLine.getInstance();
	}

}
