/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;

/**
 * @author RoManski
 *
 */
public interface RealSpace extends VectorSpace {

	@Override
	default Field getField() {
		return RealLine.getInstance();
	}

}
