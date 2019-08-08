/**
 * 
 */
package definitions.structures.abstr;

import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;

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
