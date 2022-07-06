package definitions.structures.abstr.algebra.fields;

import definitions.structures.abstr.algebra.groups.CyclicGroup;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * A finite prime field is a finite field that is also isomorphic to its prime
 * field. Q is a prime field. Z/2Z are prime field but no other finite field of
 * even order is. R and C are not prime fields either (their prime field is Q)
 * 
 * @author ro
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
