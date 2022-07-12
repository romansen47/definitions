package definitions.structures.dynamicsystems;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.rings.Ring;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceMapping;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;

/**
 * Dynamic systems are given by a V-valued mapping F on V and the corresponding
 * first order system y' = F(y), y(0)=y_0.
 *
 * @author ro
 *
 */
public interface DynamicSystem extends EvolutionSystem, XmlPrintable {

	@Override
	default VectorSpaceSelfMapping getDefiningMapping(Element input) {
		return this.getDefiningMapping();
	}

	/**
	 * a dynamic system is an autonomous evolution system
	 *
	 * @return the defining mapping
	 */
	VectorSpaceSelfMapping getDefiningMapping();

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

	/**
	 * provides the evolution operator at time t
	 *
	 * @param t the time
	 * @return the evolution operator at time t
	 */
	default VectorSpaceMapping getEvolutionOperator(Element t) {
		return new VectorSpaceSelfMapping() {

			private Element time = t;

			@Override
			public String toString() {
				return "self mapping of " + DynamicSystem.this.getPhaseSpace().toString() + " at t = " + t.toString();
			}

			@Override
			public Element get(Element vec) {
				final Group timeSpace = DynamicSystem.this.getTimeSpace();
				Element ans;
				if (timeSpace.getNeutralElement().equals(this.time)) {
					ans = vec;
				} else {
					if (((SemiRing) timeSpace).getOne().equals(this.time)) {
						ans = DynamicSystem.this.getDefiningMapping().get(vec);
					} else {
						final Element newTime = timeSpace.operation(this.time, ((Ring) timeSpace).getMinusOne());
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

}
