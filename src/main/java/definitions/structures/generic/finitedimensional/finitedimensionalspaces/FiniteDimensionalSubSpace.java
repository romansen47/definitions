package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;

public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements IFiniteDimensionalSubSpace {

	private final IFiniteDimensionalLinearMapping parametrization;
	
	protected FiniteDimensionalSubSpace(IFiniteDimensionalLinearMapping map) throws Throwable {
		super(map.getSource().getGenericBase());
		this.parametrization=map;
	}
	
	@Override
	public IFiniteDimensionalVectorSpace getSuperSpace() {
		return parametrization.getTarget();
	}

	@Override
	public int dim() {
		return parametrization.getRank();
	}
	
	@Override
	public final IFiniteDimensionalLinearMapping getParametrization() { 
		return parametrization;
	}
	
	@Override
	public boolean contains(IVector vec){
		try {
			return super.contains(vec) 
					&& ((FiniteVector)getParametrization().get((IFiniteVector) vec)).
						equals(new FiniteVector(new double[((FiniteVector)vec).getDim()]));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	} 

}
