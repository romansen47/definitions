package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable; 
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public interface DynamicSystem extends XmlPrintable {

	/**
	 * the evolution operator of the dynamic system.
	 * 
	 * @return the evolution operator
	 */
	Function getEvolutionOperator(Element tmp);

	/**
	 * the phase space of the dynamic system.
	 * 
	 * @return the time phase
	 */
	VectorSpace getPhaseSpace();

	/**
	 * the time space of the dynamic system.
	 * 
	 * @return the time space
	 */
	OrderedMonoid getTimeSpace();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String toXml() {
		return "<dynamicSystem>\r" + "<timeSpace>\r" + this.getTimeSpace().toXml() + "</timeSpace>\r" + "<phaseSpace>\r"
				+ this.getPhaseSpace().toXml() + "</phaseSpace>\r" + "</dynamicSystem>\r";
	}

}
