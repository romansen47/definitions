package definitions.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

@Aspect
public class CachingAspect {

	final static private Logger logger = LogManager.getLogger(CachingAspect.class);

	final static Map<Integer, EuclideanSpace> coordinatesSpaces = new HashMap<>();

	@Around("execution(* definitions.structures.euclidean.vectorspaces.ISpaceGenerator.getFiniteDimensionalVectorSpace(definitions.structures.abstr.algebra.fields.Field,int))")
	public Object getCoordinateSpace(final ProceedingJoinPoint pjp) {
		return coordinateSpace(pjp);
	}

	private Object coordinateSpace(ProceedingJoinPoint pjp) {
		final Field field = (Field) (pjp.getArgs()[0]);
		final int dim = (int) (pjp.getArgs()[1]);
		if (field.equals(RealLine.getInstance())) {
			EuclideanSpace ans = coordinatesSpaces.get(dim);
			if (ans != null) {
				logger.info(
						"Successfully restored from cache! " + dim + "-dimensional euclidean space " + ans.toString());
				return ans;
			}
			switch (dim) {
			case 1:
				return RealLine.getInstance();
			case 2:
				return ComplexPlane.getInstance();
			case 4:
				return QuaternionSpace.getInstance();
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
		FieldElement one = field.getOne();
		Vector zero = field.getZero();
		for (int i = 0; i < dim; i++) {
			Vector baseVec = basetmp.get(i);
			Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) baseVec).getCoordinates();
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					coordinates.put(baseVec, one);
				} else {
					coordinates.put(basetmp.get(j), (Scalar) zero);
				}
			}
		} 
		EuclideanSpace ans = new FiniteDimensionalVectorSpace(field, basetmp);
		logger.info("Created new space: " + ans.toString());
		if (field.equals(RealLine.getInstance())) {
			coordinatesSpaces.put(dim, ans);
		}
		return ans;
	}

}
