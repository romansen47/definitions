package definitions.structures.generic;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.IVec;
import definitions.structures.abstr.IVectorSpace;

public class RealVec implements IVec {

	final int dim;

	@Override
	public final int getDim() {
		return dim;
	}

	private Map<IVec, Double> coordinates;

	protected RealVec(double[] coordinates) throws Throwable {
		dim = coordinates.length;
		setCoordinates(new HashMap<>());
		int i = 0;
		for (final IVec vec : Generator.getGenerator().getFiniteDimensionalVectorSpace(dim).getGenericBase()) {
			getCoordinates().put(vec, coordinates[i++]);
		}
	}

	protected RealVec(Map<IVec, Double> coordinates) throws Throwable {
		setCoordinates(coordinates);
		dim = coordinates.keySet().size();
	}

	protected RealVec(int dim) {
		this.dim = dim;
		coordinates = new HashMap<>();
	}

	/**
	 * @return the coordinates
	 */
	public final Map<IVec, Double> getGenericCoordinates() {
		return getCoordinates();
	}

	@Override
	public boolean elementOf(IVectorSpace space) {
		if (space instanceof RealFiniteDimensionalSpace && ((RealFiniteDimensionalSpace) space).dim() == dim) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(IVec vec) throws Throwable {
		if (vec instanceof RealVec && ((RealVec) vec).dim == dim) {
			return getGenericCoordinates().equals(((RealVec) vec).getGenericCoordinates());
		}
		throw new Throwable();
	}

	@Override
	public String toString() {
		String str = "";
		try {
			for (final IVec vec : getGenericBase()) {
				str += getCoordinates().get(vec) + "\r";
			}
			return str;
		} catch (final Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Problems occured...";
		}
	}

	public Map<IVec, Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Map<IVec, Double> coordinates) {
		this.coordinates = coordinates;
	}

}
