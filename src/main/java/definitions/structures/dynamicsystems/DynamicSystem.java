package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public interface DynamicSystem extends XmlPrintable{
	
	Function getEvolutionOperator(MonoidElement timeelement);

	VectorSpace getPhaseSpace();

	OrderedMonoid getTimeSpace();
	
	@Override
	default String toXml() {
		return "<dynamicSystem>\r"
				+ "<timeSpace>\r"+getTimeSpace().toXml()+"</timeSpace>\r"
				+ "<phaseSpace>\r"+getPhaseSpace().toXml()+"</phaseSpace>\r" 
				+ "</dynamicSystem>\r";
	}

}
