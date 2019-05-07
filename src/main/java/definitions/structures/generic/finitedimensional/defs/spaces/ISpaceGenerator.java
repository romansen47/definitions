package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.VectorGenerator;

public interface ISpaceGenerator {

	Map<Integer, IFiniteDimensionalVectorSpace> getCachedSpaces();

	default IFiniteDimensionalVectorSpace getFiniteDimensionalVectorSpace(int dim) throws Throwable {
		if (!getCachedSpaces().containsKey(dim)) {
			final List<IFiniteVector> basetmp = new ArrayList<IFiniteVector>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(VectorGenerator.getInstance().getFiniteVector(dim));
				basetmp.get(i).getCoordinates().put(basetmp.get(i), 1.);
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i != j) {
						basetmp.get(i).getCoordinates().put(basetmp.get(j), 0.);
					}
				}
			}
			getCachedSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(basetmp));
		}
		return getCachedSpaces().get(dim);
	}

}
