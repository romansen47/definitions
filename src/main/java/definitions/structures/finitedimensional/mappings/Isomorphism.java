package definitions.structures.finitedimensional.mappings;

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
//	default Isomorphism getInverse() throws Throwable {
//		return (InvertibleSelfMapping) Generator.getGenerator().getMappinggenerator()
//				.getFiniteDimensionalLinearMapping(MatrixOperator.getInstance().inverse(getGenericMatrix()));
//	}

	Isomorphism getInverse() throws Throwable;
}
