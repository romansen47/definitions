package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;

public class RealFiniteDimensionalSubSpace extends RealFiniteDimensionalSpace implements IFiniteDimensionalSubSpace {

	private final IFiniteDimensionalLinearMapping map;
	
	protected RealFiniteDimensionalSubSpace(IFiniteDimensionalLinearMapping map) throws Throwable {
		super(map.getSource().getGenericBase());
		this.map=map;
	}

	@Override
	public IFiniteDimensionalVectorSpace getSuperSpace() {
		return map.getSource();
	}

	@Override
	public final IFiniteDimensionalLinearMapping getMap() { 
		return map;
	}
	
	@Override
	public boolean contains(IVector vec){
		try {
			return super.contains(vec) 
					&& ((FiniteVector)map.get((IFiniteVector) vec)).
						equals(new FiniteVector(new double[((FiniteVector)vec).getDim()]));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	} 

}
