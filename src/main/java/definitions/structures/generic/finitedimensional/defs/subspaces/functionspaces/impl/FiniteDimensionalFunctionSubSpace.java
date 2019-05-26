/**
 * 
 */
package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.impl.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

/**
 * @author ro
 *
 */
public class FiniteDimensionalFunctionSubSpace extends FiniteDimensionalSubSpace
		implements IFiniteDimensionalFunctionSpace {

	final double[] intervall;
	final double eps;

	public FiniteDimensionalFunctionSubSpace(IFiniteDimensionalLinearMapping map,
			IFiniteDimensionalFunctionSpace superSpace) throws Throwable {
		super(map);
		intervall = superSpace.getIntervall();
		eps = superSpace.getEpsilon();
		this.parametrization = (IFiniteDimensionalInjectiveLinearMapping) map;
		genericBase.clear();
		for (Vector vec : parametrization.getSource().genericBaseToList()) {
			Vector newBaseVec = (FiniteVector) parametrization.get(vec);
			genericBase.add(newBaseVec);
			getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public List<Vector> genericBaseToList() throws Throwable {
		return getBase();
	}

	@Override
	public Set<Vector> getGenericBase() throws Throwable {
		return new HashSet<>(getBase());
	}

	@Override
	public int dim() throws Throwable {
		return super.dim();
	}

	@Override
	public boolean contains(Vector vec) throws Throwable {
		return true;
	}

	@Override
	public double[] getIntervall() {
		return intervall;
	}

	@Override
	public Function stretch(Function vec, double r) throws Throwable {
		return (Function) getSuperSpace().stretch(vec, r);
	}

	@Override
	public List<Vector> getBase() {
		return base;
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function) {
			return getSuperSpace().add(vec1, vec2);
		}
		return super.add(vec1, vec2);
	}

	@Override
	public double getEpsilon() {
		return eps;
	}
	
	@Override
	public Vector getCoordinates(Vector vec) throws Throwable {
		Map<Vector, Double> coordinates = new HashMap<>();
		for (Vector baseVec : genericBase) {
			coordinates.put(baseVec,product(vec, baseVec));
		}
		return get(coordinates);
	}

	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function) {
			return getIntegral((Function) vec1, (Function) vec2);
		}
		throw new Throwable();
	}
	
	@Override
	public Function nullFunction() throws Throwable {
		Map<Vector, Double> nul = new HashMap<>();
		for (Vector baseVec : getSuperSpace().getGenericBase()) {
			nul.put(baseVec, 0.0);
		}
		return new FunctionTuple(nul);
	}
}
