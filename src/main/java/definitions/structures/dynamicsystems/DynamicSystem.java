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
		final Group timeSpace = getTimeSpace();
		final Element diff = timeSpace.operation(time, timeSpace.getInverseElement(start));
		return getEvolutionOperator(diff);
	}

	default VectorSpaceMapping getEvolutionOperator(Element t) {
		return new VectorSpaceSelfMapping() {

			final Element time = t;

			@Override
			public String toString() {
				return "self mapping of " + getPhaseSpace().toString() + " at t = " + t.toString();
			}

			@Override
			public Element get(Element vec) {
				final Group timeSpace = getTimeSpace();
				Element ans;
				if (time.equals(getTimeSpace().getNeutralElement())) {
					ans = vec;
				} else {
					if (time.equals(((SemiRing) getTimeSpace()).getOne())) {
						ans = getDefiningMapping().get(vec);
					} else {
						final Element newTime = timeSpace.operation(time, ((Ring) timeSpace).getMinusOne());
						ans = getEvolutionOperator(newTime)
								.get(getEvolutionOperator(((Ring) timeSpace).getOne()).get(vec));
					}
				}
				return ans;

			}

			@Override
			public VectorSpace getSource() {
				return (VectorSpace) getPhaseSpace();
			}

		};
	};

	@Override
	default VectorSpaceSelfMapping getDefiningMapping(Element input) {
		return getDefiningMapping();
	};

	VectorSpaceSelfMapping getDefiningMapping();

}
