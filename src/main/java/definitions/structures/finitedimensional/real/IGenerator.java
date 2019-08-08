package definitions.structures.finitedimensional.real;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.Field;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.mappings.IMappingGenerator;
import definitions.structures.finitedimensional.real.vectors.IVectorGenerator;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.ISpaceGenerator;
import exceptions.WrongClassException;

public interface IGenerator {

	Map<Integer, EuclideanSpace> getCachedSpaces();

	default Vector getFiniteVector(int dim) {
		return getVectorgenerator().getFiniteVector(dim);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(Scalar[][] genericMatrix) {
		return getMappinggenerator().getFiniteDimensionalLinearMapping(genericMatrix);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target,
			Map<Vector, Map<Vector, Scalar>> coordinates) {
		return getMappinggenerator().getFiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default VectorSpace getFiniteDimensionalVectorSpace(int dim) {
		return getSpacegenerator().getFiniteDimensionalVectorSpace(dim);
	}

	default VectorSpace getFiniteDimensionalFunctionSpace(Field field, List<Vector> genericBase, double left,
			double right) {
		return getSpacegenerator().getFiniteDimensionalFunctionSpace(field, genericBase, left, right);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(Field field, List<Vector> genericBase, double left,
			double right, final int degree) {
		return getSpacegenerator().getFiniteDimensionalSobolevSpace(field, genericBase, left, right, degree);
	}

	default VectorSpace getTrigonometricSpace(Field field, int n) {
		return getSpacegenerator().getTrigonometricSpace(field, n);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(Field field, EuclideanFunctionSpace space, int degree) {
		return getSpacegenerator().getFiniteDimensionalSobolevSpace(field, space, degree);
	}

	default VectorSpace getTrigonometricFunctionSpaceWithLinearGrowth(Field field, int n) {
		try {
			return getSpacegenerator().getTrigonometricFunctionSpaceWithLinearGrowth(field, n);
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
