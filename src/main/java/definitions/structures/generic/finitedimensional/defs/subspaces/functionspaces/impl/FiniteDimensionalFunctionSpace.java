package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	protected double[] intervall;
	protected final double eps = 1.e-5;

	protected FiniteDimensionalFunctionSpace() throws Throwable {
	}

	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
//		if (vec1 instanceof FunctionTuple && vec2 instanceof FunctionTuple) {
//			return super.product(vec1, vec2);
//		}
//		else {
			return getIntegral((Function) vec1, (Function) vec2);
//		}
	}

	public FiniteDimensionalFunctionSpace(List<Vector> genericBase, double left, double right) throws Throwable {
		super(genericBase);
		intervall = new double[2];
		intervall[0] = left;
		intervall[1] = right;
		for (Vector vec : genericBase) {
			Map<Vector, Double> tmpCoord = new ConcurrentHashMap<>();
			for (Vector otherVec : genericBase) {
				if (vec == otherVec) {
					tmpCoord.put(otherVec, 1.0);// / norm(vec));
				} else {
					tmpCoord.put(otherVec, 0.0);
				}
			}
			((Tuple) vec).setCoordinates(tmpCoord);
		}
	}

	@Override
	public double[] getIntervall() {
		return intervall;
	}

	@Override
	public double getEpsilon() {
		return eps;
	}

	@Override
	public Function stretch(Function vec, double r) throws Throwable {
		final Map<Vector, Double> coordinates = vec.getCoordinates();
		final Map<Vector, Double> stretched = new ConcurrentHashMap<>();
		for (final Vector vec1 : getBase()) {
			stretched.put(vec1, coordinates.get(getBaseVec(vec1)) * r);
		}
		return new FunctionTuple(stretched);
	}

	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) throws Throwable {
		List<Vector> newBase = new ArrayList<>();
		for (Vector vec : base) {
			Vector tmp = nullVec();
			for (Vector vec2 : newBase) {
				tmp = add(tmp, projection(vec, vec2));
			}
			Vector ans = normalize(add(vec, stretch(tmp, -1)));
			newBase.add(ans);
		}
//		for (Vector baseVec:newBase) {
//			Map<Vector,Double> coordinates=new ConcurrentHashMap<>();
//			for (Vector otherBaseVec:newBase) {
//				if (baseVec.equals(otherBaseVec)) {
//					coordinates.put(baseVec, 1.);
//				}
//				else{
//					coordinates.put(otherBaseVec,0.);
//				}
//			}
//			baseVec.setCoordinates(coordinates);
//		}
		return newBase;
	}

	@Override
	public Vector nullVec() throws Throwable {
		return nullFunction();
	}

}
