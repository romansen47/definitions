package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceMapping;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;

public interface EvolutionSystem extends XmlPrintable {

	/**
	 * the evolution operator of the dynamic system.
	 *
	 * @param time  the time element
	 * @param start the start element
	 *
	 * @return the evolution operator
	 */
	VectorSpaceMapping getEvolutionOperator(Element time, Element start);

	/**
	 * the phase space of the dynamic system.
	 *
	 * @return the time phase
	 */
	Group getPhaseSpace();

	/**
	 * the time space of the dynamic system.
	 *
	 * @return the time space
	 */
	Group getTimeSpace();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String toXml() {
		return "<dynamicSystem>\r" + "<timeSpace>\r" + this.getTimeSpace().toXml() + "</timeSpace>\r" + "<phaseSpace>\r"
				+ this.getPhaseSpace().toXml() + "</phaseSpace>\r" + "</dynamicSystem>\r";
	}

	/**
	 * the right hand side function F the defining equation y'=F(y)
	 *
	 * @param input the input vector
	 * @return the evaluation of y under F
	 */
	VectorSpaceSelfMapping getDefiningMapping(Element input);

}
