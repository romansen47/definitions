package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.Mapping;
import definitions.structures.abstr.mappings.SelfMapping;
import definitions.structures.abstr.vectorspaces.Ring;

public interface DynamicSystem extends EvolutionSystem, XmlPrintable {

	/**
	 * the evolution operator of the dynamic system.
	 * 
	 * @return the evolution operator
	 */
	@Override
	default Mapping getEvolutionOperator(Element time, Element start) {
		Group timeSpace = getTimeSpace();
		Element diff = timeSpace.operation(time, timeSpace.getInverseElement(start));
		return getEvolutionOperator(diff);
	}

	default Mapping getEvolutionOperator(Element t) {
		return new SelfMapping() {

			final Element time = t;

			@Override
			public String toString() {
				return "self mapping of " + getPhaseSpace().toString() + " at t = " + t.toString();
			}

			@Override
			public Element get(Element vec) {
				Group timeSpace = getTimeSpace();
				Element ans;
				if (time.equals(getTimeSpace().getNeutralElement())) {
					ans = vec;
				} else {
					if (time.equals(((SemiRing) getTimeSpace()).getOne())) {
						ans = getDefiningMapping().get(vec);
					} else {
						Element newTime = timeSpace.operation(time, ((Ring) timeSpace).getMinusOne());
						ans = getEvolutionOperator(newTime)
								.get(getEvolutionOperator(((Ring) timeSpace).getOne()).get(vec));
					}
				}
				return ans;

			}

			@Override
			public Monoid getSource() {
				return getPhaseSpace();
			}

		};
	};

	@Override
	default SelfMapping getDefiningMapping(Element input) {
		return getDefiningMapping();
	};

	SelfMapping getDefiningMapping();

}
