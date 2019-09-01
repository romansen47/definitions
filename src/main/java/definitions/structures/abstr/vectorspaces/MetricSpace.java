package definitions.structures.abstr.vectorspaces;

import java.io.Serializable;

import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface MetricSpace extends Serializable{

	Real getDistance(Vector vec1, Vector vec2);

}
