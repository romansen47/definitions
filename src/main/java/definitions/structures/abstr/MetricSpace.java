package definitions.structures.abstr;

import definitions.structures.field.scalar.Real;

public interface MetricSpace {

	Real getDistance(Vector vec1,Vector vec2);
	
}
