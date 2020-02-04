package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.Mapping;
import definitions.structures.abstr.mappings.SelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.impl.Naturals;

public interface EvolutionSystem extends XmlPrintable {

	/**
	 * the evolution operator of the dynamic system.
	 * 
	 * @return the evolution operator
	 */
	Mapping getEvolutionOperator(Element time, Element start);
	
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
	default Group getTimeSpace() {
		return (Group) GroupGenerator.getInstance().getIntegers();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String toXml() {
		return "<dynamicSystem>\r" + "<timeSpace>\r" + this.getTimeSpace().toXml() + "</timeSpace>\r" + "<phaseSpace>\r"
				+ this.getPhaseSpace().toXml() + "</phaseSpace>\r" + "</dynamicSystem>\r";
	}

	SelfMapping getDefiningMapping(Element input);
	
}
