/**
 * 
 */
package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.impl.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

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

		for (FiniteVector vec : parametrization.getSource().genericBaseToList()) {
			FiniteVector newBaseVec = (FiniteVector) parametrization.get(vec);
			genericBase.add(newBaseVec);
			getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public List<FiniteVector> genericBaseToList() throws Throwable {
		return getBase();
	}

	@Override
	public Set<FiniteVector> getGenericBase() throws Throwable {
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
	public List<FiniteVector> getBase() {
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

}
