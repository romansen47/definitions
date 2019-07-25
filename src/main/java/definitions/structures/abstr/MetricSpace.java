package definitions.structures.abstr;

import definitions.structures.finitedimensional.real.vectors.Real;

public interface MetricSpace {

	Real getDistance(Vector vec1,Vector vec2);
	
}
