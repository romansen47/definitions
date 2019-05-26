package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.HilbertSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	protected double[] intervall;
	protected final double eps = 1.e-5;

	protected FiniteDimensionalFunctionSpace() throws Throwable{}
	
	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function) {
			double lengthOfIntervall = intervall[1] - intervall[0];
			return getIntegral((Function) vec1, (Function) vec2);/// lengthOfIntervall;///Math.PI;
		}
		throw new Throwable();
	}

	public FiniteDimensionalFunctionSpace(List<Vector> genericBase, double left, double right) throws Throwable {
		super(genericBase);
		intervall = new double[2];
		intervall[0] = left;
		intervall[1] = right;
		for (Vector vec : genericBase) {
			Map<Vector, Double> tmpCoord = new HashMap<>();
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
		final Map<Vector, Double> stretched = new HashMap<>();
		for (final Vector vec1 : getBase()) {
			stretched.put(vec1, coordinates.get(getBaseVec(vec1)) * r);
		}
		return new FunctionTuple(stretched);
	}
	
	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) throws Throwable{
		List<Vector> newBase=new ArrayList<>();
		for (Vector vec:base) {
			Vector tmp=nullVec();
			for (Vector vec2:newBase) {
				tmp=add(tmp,normedProjection(vec,vec2));
			}
			Vector ans=normalize(add(vec,stretch(tmp,-1)));
			newBase.add(ans);
		}
		return newBase;
	}
	
	@Override
	public Vector nullVec() throws Throwable {
		return nullFunction();
	}

}
