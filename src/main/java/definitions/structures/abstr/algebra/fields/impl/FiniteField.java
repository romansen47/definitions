package definitions.structures.abstr.algebra.fields.impl;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field, FiniteRing {

	@Override
	default boolean divides(final RingElement devisor, final RingElement devident) {
		return Field.super.divides(devisor, devident);
	}

	@Override
	default boolean isIrreducible(final RingElement element) {
		return Field.super.isIrreducible(element);
	}

	@Override
	default boolean isPrimeElement(final RingElement element) {
		return Field.super.isPrimeElement(element);
	}

	@Override
	default boolean isUnit(final RingElement element) {
		return Field.super.isUnit(element);
	}

	@Override
	default FiniteFieldElement operation(final MonoidElement first, final MonoidElement second) {
		return (FiniteFieldElement) Field.super.operation(first, second);
	}

	@Override
	default FiniteFieldElement product(final Vector vec1, final Vector vec2) {
		return (FiniteFieldElement) this.getMuliplicativeMonoid().operation(vec1, vec2);
	}

	@Override
	default String toXml() {
		String xmlString = "<FiniteField>";
		xmlString += "<characteristic>" + this.getCharacteristic() + "</characteristic>";
		if (this.getPrimeField() == this) {
			xmlString += "<isPrimeField>yes</isPrimeField>";
		} else {
			xmlString += "<primeField>" + this.getPrimeField().toXml() + "</primeField>";
		}
		return xmlString;
	}

}
