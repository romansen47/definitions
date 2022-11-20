package definitions.prototypes;

import definitions.structures.abstr.algebra.fields.IFieldGenerator;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public interface IGenericTest {

	/**
	 * Getter for generator
	 * 
	 * @return the generator
	 */
	default IGenerator getGenerator() {
		return Generator.getInstance();
	}

	/**
	 * Getter for group generator
	 * 
	 * @return the group generator
	 */
	default IGroupGenerator getGroupGenerator() {
		return Generator.getInstance().getGroupGenerator();
	}

	/**
	 * Getter for space generator
	 * 
	 * @return the space generator
	 */
	default ISpaceGenerator getSpaceGenerator() {
		return Generator.getInstance().getSpaceGenerator();
	}

	/**
	 * Getter for field generator
	 * 
	 * @return the field generator
	 */
	default IFieldGenerator getFieldGenerator() {
		return Generator.getInstance().getFieldGenerator();
	}

	/**
	 * Getter for real line
	 * 
	 * @return the real line
	 */
	default RealLine getRealLine() {
		return Generator.getInstance().getFieldGenerator().getRealLine();
	}

	/**
	 * Getter for complex plane
	 * 
	 * @return the complex plane
	 */
	default ComplexPlane getComplexPlane() {
		return Generator.getInstance().getFieldGenerator().getComplexPlane();
	}

	/**
	 * @return the integers
	 */
	default DiscreetDomain getIntegers() {
		return Generator.getInstance().getGroupGenerator().getIntegers();
	}

	/**
	 * Getter for rationals
	 * 
	 * @return the rationals
	 */
	default PrimeField getRationals() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

	/**
	 * @return the naturals
	 */
	default DiscreetSemiRing getNaturals() {
		return Generator.getInstance().getGroupGenerator().getNaturals();
	}

}
