package definitions.structures.generic.finitedimensional.defs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IMappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;

public interface IGenerator {

	Map<Integer, EuclideanSpace> getCachedSpaces();

	default Vector getFiniteVector(int dim) {
		return getVectorgenerator().getFiniteVector(dim);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(double[][] genericMatrix) throws Throwable {
		return getMappinggenerator().getFiniteDimensionalLinearMapping(genericMatrix);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Double>> coordinates) throws Throwable {
		return getMappinggenerator().getFiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default VectorSpace getFiniteDimensionalVectorSpace(int dim) throws Throwable {
		return getSpacegenerator().getFiniteDimensionalVectorSpace(dim);
	}

	default VectorSpace getFiniteDimensionalFunctionSpace(List<Vector> genericBase, double left, double right)
			throws Throwable {
		return getSpacegenerator().getFiniteDimensionalFunctionSpace(genericBase, left, right);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(List<Vector> genericBase, double left, double right,final int degree)
			throws Throwable {
		return getSpacegenerator().getFiniteDimensionalSobolevSpace(genericBase, left, right,degree);
	}

	default VectorSpace getTrigonometricSpace(int n) throws Throwable {
		return getSpacegenerator().getTrigonometricSpace(n);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(IFiniteDimensionalFunctionSpace space,int degree) throws Throwable {
		return getSpacegenerator().getFiniteDimensionalSobolevSpace(space,degree);
	}

	default VectorSpace getTrigonometricFunctionSpaceWithLinearGrowth(int n, Function fun) throws Throwable {
		return getSpacegenerator().getTrigonometricFunctionSpaceWithLinearGrowth(n, fun);
	}

	IVectorGenerator getVectorgenerator();

	IMappingGenerator getMappinggenerator();

	ISpaceGenerator getSpacegenerator();

	void saveCoordinateSpaces() throws IOException;

	void loadCoordinateSpaces() throws IOException, ClassNotFoundException;

	void saveFunctionSpaces() throws IOException;

	void loadFunctionSpaces() throws IOException, ClassNotFoundException;
}
