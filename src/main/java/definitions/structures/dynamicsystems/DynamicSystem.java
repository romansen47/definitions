package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceMapping;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.VectorSpace;

public interface DynamicSystem extends EvolutionSystem, XmlPrintable {

	/**
	 * the evolution operator of the dynamic system.
	 *
	 * @return the evolution operator
	 */
	@Override
	default VectorSpaceMapping getEvolutionOperator(Element time, Element start) {
		final Group timeSpace = this.getTimeSpace();
		final Element diff = timeSpace.operation(time, timeSpace.getInverseElement(start));
		return this.getEvolutionOperator(diff);
	}

	default VectorSpaceMapping getEvolutionOperator(Element t) {
		return new VectorSpaceSelfMapping() {

			final Element time = t;

			@Override
			public String toString() {
				return "self mapping of " + DynamicSystem.this.getPhaseSpace().toString() + " at t = " + t.toString();
			}

			@Override
			public Element get(Element vec) {
				final Group timeSpace = DynamicSystem.this.getTimeSpace();
				Element ans;
				if (timeSpace.getNeutralElement().equals(time)) {
					ans = vec;
				} else {
					if (((SemiRing) timeSpace).getOne().equals(time)) {
						ans = DynamicSystem.this.getDefiningMapping().get(vec);
					} else {
						final Element newTime = timeSpace.operation(time, ((Ring) timeSpace).getMinusOne());
						ans = DynamicSystem.this.getEvolutionOperator(newTime)
								.get(DynamicSystem.this.getEvolutionOperator(((Ring) timeSpace).getOne()).get(vec));
					}
				}
				return ans;

			}

			@Override
			public VectorSpace getSource() {
				return (VectorSpace) DynamicSystem.this.getPhaseSpace();
			}

		};
	}

	@Override
	default VectorSpaceSelfMapping getDefiningMapping(Element input) {
		return this.getDefiningMapping();
	}

	VectorSpaceSelfMapping getDefiningMapping();

}
