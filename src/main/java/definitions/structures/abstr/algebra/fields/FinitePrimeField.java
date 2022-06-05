package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.groups.CyclicGroup;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * @author ro
 *
 *         A finite prime field is a finite field that is is isomorphic to its
 *         prime field
 *
 */
public interface FinitePrimeField extends FiniteField, PrimeField, CyclicGroup {

	@Override
	default FieldElement getNeutralElement() {
		return (FieldElement) CyclicGroup.super.getNeutralElement();
	}

	@Override
	default Element get(Number representant) {
		return CyclicGroup.super.get(representant);
	}
}
