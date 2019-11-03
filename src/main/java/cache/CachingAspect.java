package cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

@Aspect
@Configuration
public class CachingAspect {

	@Autowired
	public static CachingAspect aspect;
	
	@Bean(name="cachingAspect")
	public static CachingAspect getAspect() {
//		if (aspect==null) {
//			aspect=new CachingAspect();
//		}
		return aspect;
	}
	
	@Bean(name="cachingAspect")
	public static void setAspect(CachingAspect aspect) {
		CachingAspect.aspect= aspect;
	}
	
	@Autowired
	final Map<Integer, EuclideanSpace> coordinatesSpaces = new HashMap<>();

	@Around("execution(* definitions.structures.euclidean.vectorspaces.ISpaceGenerator.getFiniteDimensionalVectorSpace(Field,int))")
	public Object getCoordinateSpace(ProceedingJoinPoint pjp) {
		System.out.println(pjp.getArgs());
		Field field = (Field) (pjp.getArgs()[0]);
		int dim = (int) (pjp.getArgs()[1]);
		EuclideanSpace ans = coordinatesSpaces.get(dim);
		if (ans != null) {
			System.out.println(
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
		final List<Vector> basetmp = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			/*
			 * Direct usage of constructor instead of get method in order to avoid cycles.
			 * Don't touch this
			 */
			basetmp.add(new Tuple(dim));
		}
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(i),
							(Scalar) field.getOne());
				} else {
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(j),
							(Scalar) field.getZero());
				}
			}
		}
		ans = new FiniteDimensionalVectorSpace(field, basetmp);
		coordinatesSpaces.put(dim, ans);
		return ans;
	}

}
