package definitions.structures.generic;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.IVec;

public interface IFiniteDimensionalLinearMapping {

	IFiniteDimensionalVectorSpace getSource();

	IFiniteDimensionalVectorSpace getTarget();

	Map<RealVec, Map<RealVec, Double>> getLinearity();

	default Map<RealVec, Double> getLinearity(RealVec vec) {
		return getLinearity().get(vec);
	};

	int getRang();

	default IVec get(IVec vec1) throws Throwable {
		if (vec1 instanceof RealVec) {
			final Map<IVec, Double> coordinates = ((RealVec) vec1).getGenericCoordinates();
			IVec ans = new RealVec(new double[getTarget().dim()]);
			for (final IVec src : getSource().getGenericBase()) {
				ans = getTarget().add(ans, getTarget().stretch(getColumn(src), coordinates.get(src)));
			}
			return ans;
		} else {
			throw new Throwable();
		}
	}

	default IVec getColumn(IVec vec) throws Throwable {
		if (vec.getDim() > getSource().dim()) {
			throw new Throwable();
		}
		final Map<IVec, Double> coordinates = new HashMap<>();
		for (final IVec vec1 : getTarget().getGenericBase()) {
			coordinates.put(vec1, getLinearity().get(vec).get(vec1));
		}
		return new RealVec(coordinates);
	}

	default double[][] getGenericMatrix() {
		final double[][] matrix = new double[getTarget().dim()][getSource().dim()];
		int i = 0;
		for (final RealVec vec1 : getSource().getGenericBase()) {
			int j = 0;
			for (final IVec vec2 : getTarget().getGenericBase()) {
				matrix[j][i] = getLinearity(vec1).get(vec2);
				j++;
			}
			i++;
		}
		return matrix;
	}
}
