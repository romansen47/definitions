package definitions.structures.euclidean.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Endomorphism;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@Service
public class MappingGenerator implements IMappingGenerator, XmlPrintable {

	private static final long serialVersionUID = 1L;
	private static IMappingGenerator instance;

	public static IMappingGenerator getInstance() {
		if (instance == null) {
			instance = new MappingGenerator();
		}
		return instance;
	}

	public static void setInstance(final MappingGenerator mappingGenerator) {
		MappingGenerator.instance = mappingGenerator;
	}

	public MappingGenerator() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(final EuclideanSpace source,
			final EuclideanSpace target, final Map<Vector, Map<Vector, Scalar>> coordinates) {
		if (source instanceof EuclideanFunctionSpace) {
			return new InjectiveFunctionSpaceOperator((EuclideanFunctionSpace) source, (EuclideanFunctionSpace) target,
					coordinates);
		}
		final int dimSource = source.getDim();
		final int dimTarget = target.getDim();
		if (dimSource < dimTarget) {
			final FiniteDimensionalHomomorphism tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				return new InjectiveLinearMapping(tmp);
			}
		} else if (dimSource == dimTarget) {
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (((FiniteDimensionalHomomorphism) ans).getRank() == dimSource) {
				return new InvertibleSelfMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(final EuclideanSpace source,
			final EuclideanSpace target, final Scalar[][] genericMatrix) {
		final Map<Vector, Map<Vector, Scalar>> map = new HashMap<>();
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<Vector, Scalar> coordinates = new HashMap<>();
			for (final Vector vec2 : target.genericBaseToList()) {
				coordinates.put(vec2, genericMatrix[j][i]);
				j++;
			}
			map.put(vec1, coordinates);
			i++;
		}
		return this.getFiniteDimensionalLinearMapping(source, target, map);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpaceHomomorphism getFiniteDimensionalLinearMapping(final Scalar[][] genericMatrix) {
		final int dimSource = genericMatrix[0].length;
		final int dimTarget = genericMatrix.length;
		final EuclideanSpace source = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dimSource);
		final EuclideanSpace target = Generator.getInstance().getSpaceGenerator()
				.getFiniteDimensionalVectorSpace(dimTarget);
		final Map<Vector, Map<Vector, Scalar>> coordinates = new ConcurrentHashMap<>();
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<Vector, Scalar> tmp = new ConcurrentHashMap<>();
			for (final Vector vec2 : target.genericBaseToList()) {
				tmp.put(vec2, genericMatrix[j][i]);
				j++;
			}
			coordinates.put(vec1, tmp);
			i++;
		}
		if (dimSource < dimTarget) {
			final FiniteDimensionalHomomorphism tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				return new InjectiveLinearMapping(tmp);
			}
		} else if (dimSource == dimTarget) {
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (((FiniteDimensionalHomomorphism) ans).getRank() == dimSource) {
				return new InvertibleSelfMapping(source, coordinates);
			}
			return ans;
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "the mapping generator";
	}

}