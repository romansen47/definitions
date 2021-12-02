package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.groups.CyclicGroup;

public interface FinitePrimeField extends FiniteField, PrimeField, CyclicGroup {

	@Override
	default FieldElement getNeutralElement() {
		return (FieldElement) CyclicGroup.super.getNeutralElement();
	}

}
