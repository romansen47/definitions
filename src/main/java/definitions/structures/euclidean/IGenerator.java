package definitions.structures.euclidean;

import java.util.List;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.FieldGenerator;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import exceptions.DevisionByZeroException;

public interface IGenerator {

	FieldGenerator getFieldGenerator();

	default VectorSpace getFiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right) throws DevisionByZeroException {
		return this.getSpaceGenerator().getFiniteDimensionalFunctionSpace(field, genericBase, left, right);
	}

	default VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(final EuclideanSpace source,
			final EuclideanSpace target, final Map<Vector, Map<Vector, Scalar>> coordinates) {
		return this.getMappingGenerator().getFiniteDimensionalLinearMapping(source, target, coordinates);
	}

	default VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(final Scalar[][] genericMatrix) {
		return this.getMappingGenerator().getFiniteDimensionalLinearMapping(genericMatrix);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(final Field field, final EuclideanFunctionSpace space,
			final int degree) throws DevisionByZeroException {
		return this.getSpaceGenerator().getFiniteDimensionalSobolevSpace(field, space, degree);
	}

	default VectorSpace getFiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree) throws Exception {
		return this.getSpaceGenerator().getFiniteDimensionalSobolevSpace(field, genericBase, left, right, degree);
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final int dim) {
		return this.getSpaceGenerator().getFiniteDimensionalVectorSpace(dim);
	}

	IMappingGenerator getMappingGenerator();

	ISpaceGenerator getSpaceGenerator();

	IGroupGenerator getGroupGenerator();

	default VectorSpace getTrigonometricFunctionSpaceWithLinearGrowth(final Field field, final int n) {
		try {
			return this.getSpaceGenerator().getTrigonometricFunctionSpaceWithLinearGrowth(field, n);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	default VectorSpace getTrigonometricSpace(final Field field, final int n) {
		return this.getSpaceGenerator().getNormedTrigonometricSpace(field, n);
	}

	void setFieldGenerator(FieldGenerator fieldGenerator);

}
