package definitions.structures.finitedimensional.mappings;

import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.mappings.impl.InvertibleSelfMapping;
import deprecated.math.matrix.MatrixOperator;

/**
 * Isomorphism between vector spaces.
 * 
 * @author ro
 */
public interface Isomorphism extends FiniteDimensionalEmbedding {

	/**
	 * Method to get the inverse isomorphism.
	 * 
	 * @return
	 * @throws Throwable
	 */
	default Isomorphism getInverse() throws Throwable {
		return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
	}

}
