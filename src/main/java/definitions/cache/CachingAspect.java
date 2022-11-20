package definitions.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

@Aspect
public class CachingAspect {

	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * the map containing the cached spaces
	 */
	private static final Map<Integer, EuclideanSpace> coordinatesSpaces = new ConcurrentHashMap<>();

	CachingAspect() {
		getLogger().info("instantiation of a caching aspect, id = {}", hashCode());
	}

	public Logger getLogger() {
		return this.logger;
	}

	/**
	 * the chasing advise
	 * 
	 * @param pjp zhe join point
	 *            {@link ISpaceGenerator#getFiniteDimensionalVectorSpace}
	 * @return
	 */
	@Around("execution(* definitions.structures.euclidean.vectorspaces.ISpaceGenerator.getFiniteDimensionalVectorSpace(definitions.structures.abstr.algebra.fields.Field,int))")
	public Object getCoordinateSpace(final ProceedingJoinPoint pjp) {
		return this.coordinateSpace(pjp);
	}

	/**
	 * method to get the cached space
	 * 
	 * @param pjp the join point containing the information which space to get
	 * @return the cached space
	 */
	private Object coordinateSpace(ProceedingJoinPoint pjp) {
		final Field field = (Field) (pjp.getArgs()[0]);
		final int dim = (int) (pjp.getArgs()[1]);
		if (field.equals(Generator.getInstance().getFieldGenerator().getRealLine())) {
			final EuclideanSpace ans = CachingAspect.coordinatesSpaces.get(dim);
			if (ans != null) {
				this.getLogger().debug("restored {}-dimensional euclidean space {} from cache! ", dim, ans);
				return ans;
			}
			if (dim == 1) {
				return Generator.getInstance().getFieldGenerator().getRealLine();
			}
		}
		final List<Vector> basetmp = new ArrayList<>();
		/*
		 * Direct usage of constructor instead of get method in order to avoid cycles.
		 * Don't touch this
		 */
		IntStream.range(0, dim).forEach(i -> basetmp.add(new Tuple(dim)));
		final FieldElement one = field.getOne();
		final Vector zero = field.getZero();
		IntStream.range(0, dim).forEach(i -> {
			final Vector baseVec = basetmp.get(i);
			final Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) baseVec).getCoordinates();
			IntStream.range(0, dim).forEach(j -> {
				if (i == j) {
					coordinates.put(baseVec, one);
				} else {
					coordinates.put(basetmp.get(j), (Scalar) zero);
				}
			});

		});

		final EuclideanSpace ans = new FiniteDimensionalVectorSpace(field, basetmp);
		this.getLogger().debug("Created new {}-dimensional space over {}: {}", dim, field, ans);
		if (field.equals(Generator.getInstance().getFieldGenerator().getRealLine())) {
			this.getLogger().debug("Saved {} to cached spaces on cachingAspect", ans);
			CachingAspect.coordinatesSpaces.put(dim, ans);
		}
		return ans;
	}

}
