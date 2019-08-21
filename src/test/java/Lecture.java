import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.impl.Integers;
import definitions.structures.abstr.vectorspaces.Ring;

/**
 * 
 */

/**
 * @author ro
 *
 */
public class Lecture {

	public static void main(String[] args) {

//		Monoid monoid = Naturals.getInstance();

		final Group group = Integers.getInstance();

		final Ring ring = (Ring) ComplexPlane.getInstance();

		// A field field is an algebra, where every element has an inverse:

		final Field field = (Field) QuaternionSpace.getInstance();

		// A group G is a set of elemnts, which can be added or stretched by

	}

}
