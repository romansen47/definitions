package definitions.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import customaspects.CustomAspect;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

@Aspect
public class CachingAspect implements CustomAspect {

	private static final Map<Integer, EuclideanSpace> coordinatesSpaces = new ConcurrentHashMap<>();

	private final Logger logger;

	@Override
	public String getGenericName() {
		return "CachingAspect";
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	public CachingAspect() {
		logger = LogManager.getLogger(this.getGenericName());
	}

	@Around("execution(* definitions.structures.euclidean.vectorspaces.ISpaceGenerator.getFiniteDimensionalVectorSpace(definitions.structures.abstr.algebra.fields.Field,int))")
	public Object getCoordinateSpace(final ProceedingJoinPoint pjp) {
		return this.coordinateSpace(pjp);
	}

	private Object coordinateSpace(ProceedingJoinPoint pjp) {
		final Field field = (Field) (pjp.getArgs()[0]);
		final int dim = (int) (pjp.getArgs()[1]);
		if (field.equals(RealLine.getInstance())) {
			final EuclideanSpace ans = CachingAspect.coordinatesSpaces.get(dim);
			if (ans != null) {
				this.getLogger().info("Successfully restored {}-dimensional euclidean space {} from cache! ", dim, ans);
				return ans;
			}
			switch (dim) {
			case 1:
				return RealLine.getInstance();
			case 2:
				return ComplexPlane.getInstance();
			}
		}
		final List<Vector> basetmp = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			/*
			 * Direct usage of constructor instead of get method in order to avoid cycles.
			 * Don't touch this
			 */
			basetmp.add(new Tuple(dim));
		}
		final FieldElement one = field.getOne();
		final Vector zero = field.getZero();
		for (int i = 0; i < dim; i++) {
			final Vector baseVec = basetmp.get(i);
			final Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) baseVec).getCoordinates();
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					coordinates.put(baseVec, one);
				} else {
					coordinates.put(basetmp.get(j), (Scalar) zero);
				}
			}
		}
		final EuclideanSpace ans = new FiniteDimensionalVectorSpace(field, basetmp);
		this.getLogger().info("Created new {}-dimensional space over {}: {}", dim, field, ans);
		if (field.equals(RealLine.getInstance())) {
			CachingAspect.coordinatesSpaces.put(dim, ans);
		}
		return ans;
	}

}
