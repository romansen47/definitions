package definitions.structures.field;

import definitions.structures.abstr.Algebra;
import definitions.structures.abstr.Vector;

public interface Field extends Algebra {

	Vector inverse(Vector factor);
	
}
