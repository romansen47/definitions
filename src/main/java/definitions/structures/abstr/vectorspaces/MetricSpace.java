package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 *
 * @author RoManski
 *
 *         a metric space M is a set with elements in a way that a distance
 *         function called metric MxM-|R can be defined.
 */

/*
 * @TODO: triangle inequality check.
 */

public interface MetricSpace {

	/**
	 * method for measuring distance between elements
	 *
	 * @param vec1 first element
	 * @param vec2 second element
	 * @return distance between first and second one
	 */
	Real distance(Vector vec1, Vector vec2);

}
