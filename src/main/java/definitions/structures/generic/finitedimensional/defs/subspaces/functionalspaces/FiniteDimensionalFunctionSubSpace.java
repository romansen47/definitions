/**
 * 
 */
package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.subspaces.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

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

		for (IFiniteVector vec : parametrization.getSource().genericBaseToList()) {
			IFiniteVector newBaseVec = (IFiniteVector) parametrization.get(vec);
			genericBase.add(newBaseVec);
			getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public List<IFiniteVector> genericBaseToList() throws Throwable {
		return getBase();
	}

	@Override
	public Set<IFiniteVector> getGenericBase() throws Throwable {
		return new HashSet<IFiniteVector>(getBase());
	}

	@Override
	public int dim() throws Throwable {
		return super.dim();
	}

	@Override
	public boolean contains(IVector vec) throws Throwable {
		return true;
	}

	@Override
	public double[] getIntervall() {
		return intervall;
	}

	@Override
	public IFunction stretch(IFunction vec, double r) throws Throwable {
		return (IFunction) getSuperSpace().stretch(vec, r);
	}

	@Override
	public List<IFiniteVector> getBase() {
		return base;
	}

	@Override
	public IVector add(IVector vec1, IVector vec2) throws Throwable {
		if (vec1 instanceof IFunction && vec2 instanceof IFunction) {
			return getSuperSpace().add(vec1, vec2);
		}
		return super.add(vec1, vec2);
	}

	@Override
	public double getEpsilon() {
		return eps;
	}

}
