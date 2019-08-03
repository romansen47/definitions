package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.complex.Complex;

import definitions.structures.abstr.Algebra;
import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.abstr.impl.LinearMapping;
import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.mappings.impl.InvertibleSelfMapping;
import definitions.structures.finitedimensional.real.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.real.vectors.FiniteVector;
import definitions.structures.finitedimensional.real.vectors.impl.Tuple;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public final class ComplexPlane extends FiniteDimensionalVectorSpace implements Field {

	static private EuclideanSpace space;
	static private EuclideanSpace instance;

	private ComplexPlane() {
		base=space.genericBaseToList();
		dim=2;
		Scalar[][] genericMatrix = new Scalar[2][2];
		genericMatrix[0][0] = RealLine.getInstance().getZero();
		genericMatrix[0][1] = RealLine.getInstance().getOne();
		genericMatrix[1][0] = new Real(-1);
		genericMatrix[1][1] = RealLine.getInstance().getZero();
	}

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			space = (EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(2);
			instance=new ComplexPlane();
		}
		return instance;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		Map<Vector,Scalar> coordinates=new ConcurrentHashMap<>();
		Scalar re=new Real(vec1.getGenericCoordinates()[0].getValue()*vec2.getGenericCoordinates()[0].getValue()
				-vec1.getGenericCoordinates()[1].getValue()*vec2.getGenericCoordinates()[1].getValue());
		Scalar im=new Real(vec1.getGenericCoordinates()[0].getValue()*vec2.getGenericCoordinates()[1].getValue()
				+vec1.getGenericCoordinates()[1].getValue()*vec2.getGenericCoordinates()[0].getValue());
		coordinates.put(space.genericBaseToList().get(0),re);
		coordinates.put(space.genericBaseToList().get(1),im);
		return space.get(coordinates);
	}

	@Override
	public Vector get(Scalar[] coordinates) {
		return new Tuple(coordinates);
	}
	
	
	@Override
	public Field getField() {
		return RealLine.getInstance();
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		return space.add(vec1, vec2);
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		return space.stretch(vec1, r);
	}
	
	@Override
	public Vector inverse(Vector factor) {
		return stretch(conjugate(factor),new Real(Math.pow(norm(factor).getValue(), -2)));
	}

	private Vector conjugate(Vector factor) {
		Scalar neg=new Real(-factor.getGenericCoordinates()[1].getValue());
		return space.get(new Scalar[] {factor.getGenericCoordinates()[0],neg}) ;
	}

	@Override
	public Vector getOne() {
		return space.genericBaseToList().get(0);
	}

}
