package definitions.structures.dynamicsystems;

import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.groups.OrderedMonoid;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public interface DynamicSystem {

	OrderedMonoid getTimeSpace();
	
	VectorSpace getPhaseSpace();
	
	Function getEvolutionOperator(MonoidElement timeelement);

}
