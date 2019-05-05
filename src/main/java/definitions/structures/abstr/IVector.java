package definitions.structures.abstr;

import java.util.List;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Generator;

public interface IVector {

	int getDim();

	boolean elementOf(IVectorSpace space);

	boolean equals(IVector vec) throws Throwable;

	default List<FiniteVector> getGenericBase() throws Throwable {
		return Generator.getGenerator().getFiniteDimensionalVectorSpace(getDim()).getGenericBase();
	}

}
