package definitions.structures.abstr.algebra.semigroups;

public interface DiscreetSemiGroupElement extends SemiGroupElement {

	/**
	 * Elements of finite groups can be indexed by integers
	 * 
	 * @return the integer representant
	 */
	int getRepresentant();
	 
	default boolean equals(DiscreetSemiGroupElement other) {
		return other.getRepresentant()==getRepresentant();
	}
}
