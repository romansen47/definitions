package definitions.structures.abstr;

import definitions.structures.field.scalar.impl.Real;

public interface MetricSpace {

	Real getDistance(Vector vec1,Vector vec2);
	
}
