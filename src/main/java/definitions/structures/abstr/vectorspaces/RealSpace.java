/**
 *
 */
package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.groups.ContinuousGroup;
import definitions.structures.euclidean.Generator;

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
		return Generator.getInstance().getFieldGenerator().getRealLine();
	}

}
