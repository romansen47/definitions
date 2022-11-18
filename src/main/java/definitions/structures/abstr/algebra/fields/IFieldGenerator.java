package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;

public interface IFieldGenerator {

	RealLine getRealLine();

	ComplexPlane getComplexPlane();

}
