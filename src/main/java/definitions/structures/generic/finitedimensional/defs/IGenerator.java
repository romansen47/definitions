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
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.EuclideanFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;
import exceptions.WrongClassException;

public interface IGenerator {

	Map<Integer, EuclideanSpace> getCachedSpaces();

	default Vector getFiniteVector(int dim) {
		return getVectorgenerator().getFiniteVector(dim);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(double[][] genericMatrix) {
		return getMappinggenerator().getFiniteDimensionalLinearMapping(genericMatrix);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Double>> coordinates) {
		return getMappinggenerator().getFiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default VectorSpace getFiniteDimensionalVectorSpace(int dim) {
		return getSpacegenerator().getFiniteDimensionalVectorSpace(dim);
	}

	default VectorSpace getFiniteDimensionalFunctionSpace(List<Vector> genericBase, double left, double right) {
		return getSpacegenerator().getFiniteDimensionalFunctionSpace(genericBase, left, right);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(List<Vector> genericBase, double left, double right,
			final int degree) {
		return getSpacegenerator().getFiniteDimensionalSobolevSpace(genericBase, left, right, degree);
	}

	default VectorSpace getTrigonometricSpace(int n) {
		return getSpacegenerator().getTrigonometricSpace(n);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(EuclideanFunctionSpace space, int degree) {
		return getSpacegenerator().getFiniteDimensionalSobolevSpace(space, degree);
	}

	default VectorSpace getTrigonometricFunctionSpaceWithLinearGrowth(int n) {
		try {
			return getSpacegenerator().getTrigonometricFunctionSpaceWithLinearGrowth(n);
		} catch (final WrongClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	IVectorGenerator getVectorgenerator();

	IMappingGenerator getMappinggenerator();

	ISpaceGenerator getSpacegenerator();

	void saveCoordinateSpaces() throws IOException;

	void loadCoordinateSpaces() throws IOException, ClassNotFoundException;

	void saveFunctionSpaces() throws IOException;

	void loadFunctionSpaces() throws IOException, ClassNotFoundException;
}
