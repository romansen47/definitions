package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.VectorGenerator;

public interface ISpaceGenerator {

	Map<Integer, CoordinateSpace> getCachedCoordinateSpaces();
	Map<Integer, IFiniteDimensionalFunctionSpace> getCachedFunctionSpaces();

	default CoordinateSpace getFiniteDimensionalVectorSpace(int dim) throws Throwable {
		if (!getCachedCoordinateSpaces().containsKey(dim)) {
			final List<Vector> basetmp = new ArrayList<Vector>();
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
			getCachedCoordinateSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(basetmp));
		}
		return getCachedCoordinateSpaces().get(dim);
	}
	
	default IFiniteDimensionalFunctionSpace getFiniteDimensionalFunctionSpace(
			List<Vector> genericBase,double left,double right) 
					throws Throwable {
		IFiniteDimensionalFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if (space!=null && space.getIntervall()[0]==left && space.getIntervall()[1]==right) {
			return space;
		}
		FiniteDimensionalFunctionSpace newSpace=new FiniteDimensionalFunctionSpace(genericBase,left,right);
		getCachedFunctionSpaces().put(genericBase.hashCode(),newSpace);
		return newSpace;
	}

}
