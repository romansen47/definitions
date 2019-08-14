package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface MetricSpace {

	Real getDistance(Vector vec1, Vector vec2);

}
