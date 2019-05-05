package definitions.structures.generic;

import definitions.structures.abstr.IVec;

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
	public boolean contains(IVec vec){
		try {
			return super.contains(vec) 
					&& ((RealVec)map.get(vec)).equals(new RealVec(new double[((RealVec)vec).getDim()]));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	} 

}
