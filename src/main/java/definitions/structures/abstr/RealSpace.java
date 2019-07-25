/**
 * 
 */
package definitions.structures.abstr;

import definitions.structures.finitedimensional.field.impl.RealLine;

/**
 * @author RoManski
 *
 */
public interface RealSpace extends VectorSpace {

	@Override
	default Field getField() {
		return RealLine.getRealLine();
	}
	
}
