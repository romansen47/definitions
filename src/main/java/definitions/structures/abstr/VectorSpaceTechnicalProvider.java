package definitions.structures.abstr;

public interface VectorSpaceTechnicalProvider {

	/**
	 * Not yet implemented.
	 * 
	 * @param vec the vector to check for.
	 * @return whether vec is an element of the space.
	 */
	boolean contains(Vector vec);

	/**
	 * Vector space is not empty.
	 * 
	 * @return the zero vector.
	 */
	default Vector nullVec() {
		return null;
	};

	/**
	 * For debugging purposes.
	 * 
	 * @return The string.
	 */
	@Override
	String toString();

	default Integer getDim() {
		return null;
	}
	
}
