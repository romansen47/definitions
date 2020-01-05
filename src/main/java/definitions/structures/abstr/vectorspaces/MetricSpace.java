package definitions.structures.abstr.vectorspaces;

import java.io.Serializable;

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

public interface MetricSpace extends Serializable {

	/**
	 * method for distance between elements
	 * 
	 * @param vec1 first element
	 * @param vec2 second element
	 * @return distance between first and second one
	 */
	Real getDistance(Vector vec1, Vector vec2);

}
