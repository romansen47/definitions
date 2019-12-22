package definitions.structures.euclidean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.FieldGenerator;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public interface IGenerator extends Serializable {

//	Map<Integer, EuclideanSpace> getCachedSpaces();

//	default Vector getFiniteVector(int dim) {
//		return getVectorgenerator().getFiniteVector(dim);
//	}

	FieldGenerator getFieldGenerator();

	default VectorSpace getFiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right) {
		return this.getSpacegenerator().getFiniteDimensionalFunctionSpace(field, genericBase, left, right);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates) {
		return this.getMappinggenerator().getFiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default Homomorphism getFiniteDimensionalLinearMapping(final Scalar[][] genericMatrix) {
		return this.getMappinggenerator().getFiniteDimensionalLinearMapping(genericMatrix);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(final Field field, final EuclideanFunctionSpace space,
			final int degree) {
		return this.getSpacegenerator().getFiniteDimensionalSobolevSpace(field, space, degree);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree) throws Exception {
		return this.getSpacegenerator().getFiniteDimensionalSobolevSpace(field, genericBase, left, right, degree);
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final int dim) {
		return this.getSpacegenerator().getFiniteDimensionalVectorSpace(dim);
	}

	IMappingGenerator getMappinggenerator();

//	IVectorGenerator getVectorgenerator();

	ISpaceGenerator getSpacegenerator();

	default VectorSpace getTrigonometricFunctionSpaceWithLinearGrowth(final Field field, final int n) {
		try {
			return this.getSpacegenerator().getTrigonometricFunctionSpaceWithLinearGrowth(field, n);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	default VectorSpace getTrigonometricSpace(final Field field, final int n) {
		return this.getSpacegenerator().getTrigonometricSpace(field, n);
	}

	void loadCoordinateSpaces() throws IOException, ClassNotFoundException;

	void saveCoordinateSpaces() throws IOException;

	void setFieldGenerator(FieldGenerator fieldGenerator);

}
