package definitions.structures.abstr.fields.impl;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.groups.FiniteRing;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author RoManski
 *
 */
public interface FiniteField extends Field, FiniteRing {

	@Override
	default String toXml() {
		String xmlString = "<FiniteField>";
		xmlString += "<characteristic>" + getCharacteristic() + "</characteristic>";
		if (getPrimeField() == this) {
			xmlString += "<isPrimeField>yes</isPrimeField>";
		} else {
			xmlString += "<primeField>" + getPrimeField().toXml() + "</primeField>";
		}
		return xmlString;
	};

	@Override 
	default Vector product(Vector vec1, Vector vec2) {
		return (Vector) getMuliplicativeMonoid().operation(vec1, vec2);
	}

	@Override
	default boolean isPrimeElement(RingElement element) {
		return Field.super.isPrimeElement(element);
	}

	@Override
	default boolean divides(RingElement devisor, RingElement devident) { 
		return Field.super.divides(devisor, devident);
	}

	@Override
	default boolean isUnit(RingElement element) { 
		return Field.super.isUnit(element);
	}

	@Override
	default boolean isIrreducible(RingElement element) { 
		return Field.super.isIrreducible(element);
	}

}
