package definitions.structures.abstr;

import java.util.List;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Generator;

public interface IVector {

	int getDim();

	boolean elementOf(IVectorSpace space) throws Throwable;

	boolean equals(IVector vec) throws Throwable;

	default List<IFiniteVector> getGenericBase() throws Throwable {
		return Generator.getGenerator().getFiniteDimensionalVectorSpace(getDim()).genericBaseToList();
	}

}
