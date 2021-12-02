package definitions.structures.abstr.algebra.semigroups;

public interface Element {

	@Deprecated
	default Double getRepresentant() {
		return null;
	}

	default void setRepresentant(Double representant) {
	}

}
