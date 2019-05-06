package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Endomorphism;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.FiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.InvertibleFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Isomorphism;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.LinearSelfMapping;

public interface IGenerator {

	Map<Integer, IFiniteDimensionalVectorSpace> getCachedSpaces();

	default IFiniteDimensionalVectorSpace getFiniteDimensionalVectorSpace(int dim) throws Throwable {
		if (!getCachedSpaces().containsKey(dim)) {
			final List<IFiniteVector> basetmp = new ArrayList<IFiniteVector>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(new FiniteVector(dim));
				basetmp.get(i).getCoordinates().put(basetmp.get(i), 1.);
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i != j) {
						basetmp.get(i).getCoordinates().put(basetmp.get(j), 0.);
					}
				}
			}
			getCachedSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(basetmp));
		}
		return getCachedSpaces().get(dim);
	}

	default IFiniteDimensionalLinearMapping getComposition(IFiniteDimensionalLinearMapping A,
			IFiniteDimensionalLinearMapping B) throws Throwable {
		final double[][] matA = A.getGenericMatrix();
		final double[][] matB = B.getGenericMatrix();
		return getFiniteDimensionalLinearMapping(composition(matA, matB));
	}

	default double[][] composition(double[][] matA, double[][] matB) throws Throwable {
		if (matA[0].length != matB.length) {
			throw new Throwable();
		}
		final double[][] matC = new double[matA.length][matB[0].length];
		for (int i = 0; i < matA.length; i++) {
			for (int j = 0; j < matB[0].length; j++) {
				for (int k = 0; k < matA[0].length; k++) {
					matC[i][j] += matA[i][k] * matB[k][j];
				}
			}
		}
		return matC;
	}

	default IFiniteDimensionalLinearMapping getFiniteDimensionalLinearMapping(double[][] genericMatrix)
			throws Throwable {
		final int dimSource = genericMatrix[0].length;
		final int dimTarget = genericMatrix.length;
		final IFiniteDimensionalVectorSpace source = getFiniteDimensionalVectorSpace(dimSource);
		final IFiniteDimensionalVectorSpace target = getFiniteDimensionalVectorSpace(dimTarget);
		final Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates = new HashMap<>();
		int i = 0;
		for (final IFiniteVector vec1 : source.genericBaseToList()) {
			int j = 0;
			final Map<IFiniteVector, Double> tmp = new HashMap<>();
			for (final IFiniteVector vec2 : target.genericBaseToList()) {
				tmp.put(vec2, genericMatrix[j][i]);
				j++;
			}
			coordinates.put(vec1, tmp);
			i++;
		}
		if (dimSource < dimTarget) {
			IFiniteDimensionalLinearMapping tmp = new FiniteDimensionalLinearMapping(source, target, coordinates);
			if (dimSource == tmp.getRank()) {
				IFiniteDimensionalInjectiveLinearMapping ans = new FiniteDimensionalInjectiveLinearMapping(tmp);
				return ans;
			}
		} 
		else if (dimSource==dimTarget){
			final Endomorphism ans = new LinearSelfMapping(source, coordinates);
			if (ans.det() == 0) {
				return ans;
			} else {
				final Isomorphism iso = new InvertibleFiniteDimensionalLinearMapping(source, coordinates);
				return iso;
			}
		}
		return new FiniteDimensionalLinearMapping(source,target,coordinates);
	}

}
