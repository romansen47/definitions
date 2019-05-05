package definitions.structures.generic;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IVec;

public class RealFiniteDimensionalSpace implements IFiniteDimensionalVectorSpace {

	final List<RealVec> base;

	final int dim;

	protected RealFiniteDimensionalSpace(List<RealVec> basetmp) throws Throwable {
		dim = basetmp.size();
		base = basetmp;
	}

	@Override
	public double product(IVec vec1, IVec vec2) throws Throwable {
		if (!(vec1 instanceof RealVec && vec2 instanceof RealVec)) {
			throw new Throwable();
		}
		double prod = 0;
		final Map<IVec, Double> vecCoord1 = ((RealVec) vec1).getGenericCoordinates();
		final Map<IVec, Double> vecCoord2 = ((RealVec) vec2).getGenericCoordinates();
		final List<RealVec> base = getGenericBase();
		for (final IVec vec : base) {
			prod += vecCoord1.get(vec) * vecCoord2.get(vec);
		}
		return prod;
	}

	@Override
	public boolean contains(IVec vec) {
		return (vec instanceof RealVec && vec.getDim() == dim());
	}

	@Override
	public IVec nullVec() throws Throwable {
		return new RealVec(new double[base.size()]);
	}

	@Override
	public final List<RealVec> getGenericBase() {
		return base;
	}

	@Override
	public final int dim() {
		return dim;
	}

	@Override
	public Set<IVec> baseToArray() {
		return new HashSet<>(getGenericBase());
	}

}
