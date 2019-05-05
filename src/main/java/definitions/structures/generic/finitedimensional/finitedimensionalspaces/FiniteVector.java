package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.abstr.IVectorSpace;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Generator;

public class FiniteVector implements IFiniteVector {

	final int dim;

	@Override
	public final int getDim() {
		return dim;
	}

	private Map<IFiniteVector, Double> coordinates;

	public FiniteVector(double[] coordinates) throws Throwable {
		dim = coordinates.length;
		setCoordinates(new HashMap<>());
		int i = 0;
		for (final IFiniteVector vec : Generator.getGenerator().getFiniteDimensionalVectorSpace(dim).getGenericBase()) {
			getCoordinates().put(vec, coordinates[i++]);
		}
	}

	public FiniteVector(Map<IFiniteVector, Double> coordinates) throws Throwable {
		setCoordinates(coordinates);
		dim = coordinates.keySet().size();
	}

	protected FiniteVector(int dim) {
		this.dim = dim;
		coordinates = new HashMap<>();
	}

	public final Map<IFiniteVector, Double> getGenericCoordinates() {
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
	public boolean equals(IVector vec) throws Throwable {
		if (vec instanceof FiniteVector && ((FiniteVector) vec).dim == dim) {
			return getGenericCoordinates().equals(((FiniteVector) vec).getGenericCoordinates());
		}
		throw new Throwable();
	}

	@Override
	public String toString() {
		String str = "";
		try {
			for (final IVector vec : getGenericBase()) {
				str += getCoordinates().get(vec) + "\r";
			}
			return str;
		} catch (final Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Problems occured...";
		}
	}

	@Override
	public Map<IFiniteVector, Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Map<IFiniteVector, Double> coordinates) {
		this.coordinates = coordinates;
	}

}
