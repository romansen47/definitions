package definitions.structures.dynamicsystems;

import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.semigroups.impl.Naturals;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;

public class DiscreetDynamicSystem implements DynamicSystem {

	final private VectorSpace phaseSpace;
	final private Function evolutionOperator;

	public DiscreetDynamicSystem(VectorSpace phaseSpace,Function evolutionOperator) {
		this.phaseSpace = phaseSpace;
		this.evolutionOperator=evolutionOperator;
	}

	@Override
	public OrderedMonoid getTimeSpace() {
		return Naturals.getInstance();
	}

	@Override
	public String toXml() {
		return "discreet dynamic system.";
	}

	@Override
	public Function getEvolutionOperator(MonoidElement timeelement) { 
		return evolutionOperator;
	}

	@Override
	public VectorSpace getPhaseSpace() {
		return phaseSpace;
	}

}
