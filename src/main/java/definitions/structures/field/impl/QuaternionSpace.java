/**
 * 
 */
package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.RealSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.scalar.impl.Complex;
import definitions.structures.field.scalar.impl.Quaternion;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * @author RoManski
 *
 */
public class QuaternionSpace extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	static private EuclideanSpace instance;

	private final Vector zero;
	private final Vector one;
	private final Vector i;
	private final Vector j;
	private final Vector k;

	private Map<Vector,Homomorphism> multiplicationMatrix = null;

	public Vector getI() {
		return this.i;
	}

	private QuaternionSpace() {
		super(RealLine.getInstance());
		
		final Scalar realOne=RealLine.getInstance().getOne();
		final Scalar realZero=RealLine.getInstance().getZero();
		this.dim = 4;
		this.base = new ArrayList<>();
		this.one = new Quaternion(realOne, realZero,realZero,realZero);
		this.zero = new Quaternion(realZero,realZero,realZero,realZero);
		this.i = new Quaternion(realZero,realOne,realZero,realZero);
		this.j = new Quaternion(realZero, realZero,realOne,realZero);
		this.k = new Quaternion(realZero,realZero,realZero, realOne);
		this.base.add(this.one);
		this.base.add(this.i);
		this.base.add(this.j);
		this.base.add(this.k);
	}

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new QuaternionSpace();
		}
		return instance;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		return Field.super.product(vec1, vec2); 
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		Scalar newRe = (Scalar) RealLine.getInstance().add(((Quaternion) vec1).getReal(),((Quaternion) vec2).getReal());
		Scalar newI = (Scalar) RealLine.getInstance().add(((Quaternion) vec1).getI(),((Quaternion) vec2).getI());
		Scalar newJ = (Scalar) RealLine.getInstance().add(((Quaternion) vec1).getJ(),((Quaternion) vec2).getJ());
		Scalar newK = (Scalar) RealLine.getInstance().add(((Quaternion) vec1).getK(),((Quaternion) vec2).getK());
		return new Quaternion(newRe, newI,newJ,newK);
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		Scalar newRe = (Scalar) RealLine.getInstance().product(((Quaternion) vec1).getReal(), r);
		Scalar newI = (Scalar) RealLine.getInstance().product(((Quaternion) vec1).getI(), r);
		Scalar newJ = (Scalar) RealLine.getInstance().product(((Quaternion) vec1).getJ(), r);
		Scalar newK = (Scalar) RealLine.getInstance().product(((Quaternion) vec1).getK(), r);
		return new Quaternion(newRe, newI,newJ,newK);
	}

	@Override
	public Vector inverse(Vector factor) {
		return null;// this.stretch(this.conjugate(factor), new Real(Math.pow(this.norm(factor).getValue(), -2)));
	}

	@Override
	public Vector getOne() {
		return this.one;
	}

	@Override
	public String toString() {
		return "QuaternionSpace as 4-dimensional real vector space";
	}

	@Override
	public boolean contains(Vector vec) {
		return vec instanceof Quaternion || vec == this.zero || vec == this.one || vec == null;
	}

	@Override
	public Vector nullVec() {
		return this.zero;
	}

	@Override
	public Integer getDim() {
		return this.dim;
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		return null;
	}

	@Override
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

	/**
	 * @return the multiplicationMatrix
	 */
	@Override
	public Map<Vector,Homomorphism> getMultiplicationMatrix() {
		final Scalar oneTmp=RealLine.getInstance().getOne();
		final Scalar zeroTmp=RealLine.getInstance().getZero();
		if (this.multiplicationMatrix == null) {

			final Scalar realOne=RealLine.getInstance().getOne();
			final Scalar realZero=RealLine.getInstance().getZero();
			final Scalar neg=new Real(-1);
			
			
			Scalar[][] oneMat=new Scalar[][]{
				{realOne,realZero,realZero,realZero},
				{realZero,realOne,realZero,realZero},
				{realZero,realZero,realOne,realZero},
				{realZero,realZero,realZero,realOne}
			};
			
			Homomorphism oneHom=MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, oneMat);
			
			Scalar[][] iMat=new Scalar[][]{
				{realZero,neg,realZero,realZero},
				{realOne,realZero,realZero,realZero},
				{realZero,realZero,realZero,realOne},
				{realZero,realZero,neg,realOne}
			};
			
			Homomorphism iHom=MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, iMat);
			
			Scalar[][] jMat=new Scalar[][]{
				{realZero,realZero,neg,realZero},
				{realZero,realZero,realZero,neg},
				{realOne,realZero,realZero,realZero},
				{realZero,realOne,realZero,realZero}
			};
			
			Homomorphism jHom=MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, jMat);
			
			Scalar[][] kMat=new Scalar[][]{
				{realZero,realZero,realZero,neg},
				{realZero,realZero,realOne,realZero},
				{realZero,neg,realZero,realZero},
				{realOne,realZero,realZero,realZero}
			};
			
			Homomorphism kHom=MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, kMat);
			
			Map<Vector,Homomorphism> newMap=new HashMap<>();

			newMap.put(one,oneHom);
			newMap.put(i,iHom);
			newMap.put(j,jHom);
			newMap.put(k,kHom);
			
			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(Map<Vector,Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	/**
	 * @return the j
	 */
	public Vector getJ() {
		return j;
	}

	/**
	 * @return the k
	 */
	public Vector getK() {
		return k;
	}

}

