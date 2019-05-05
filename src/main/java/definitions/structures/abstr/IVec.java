package definitions.structures.abstr;

import java.util.List;

import definitions.structures.generic.Generator;
import definitions.structures.generic.RealVec;

public interface IVec {

	int getDim();

	boolean elementOf(IVectorSpace space);

	boolean equals(IVec vec) throws Throwable;

	default List<RealVec> getGenericBase() throws Throwable {
		return Generator.getGenerator().getFiniteDimensionalVectorSpace(getDim()).getGenericBase();
	}

}
