package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.groups.CyclicGroup;
import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

@Configurable
@Configuration
@ComponentScan
public class FiniteCyclicRing implements Ring, DiscreteGroup, CyclicGroup {

	private static final long serialVersionUID = 1L;

	public class Element implements RingElement {

		private static final long serialVersionUID = 1L;
		int representant;

		Element(int r) {
			representant = r;
		}

		@Override
		public String toXml() {
			return "<representant>" + representant + " </representant>";
		}

		public int getRepresentant() {
			return representant;
		}

	}

	public FiniteCyclicRing(int n) {
		setOrder(n);
		elements = new HashMap<>();
		for (int i = 0; i < n; i++) {
			get(i);
		}
	}

	private int order;

	private Map<Integer, GroupElement> elements;

	public GroupElement get(Integer index) {
		GroupElement ans = elements.get(index);
		if (ans == null) {
			ans = new Element(index);
			elements.put(index, ans);
		}
		return ans;
	}

	@Override
	public GroupElement getInverseElement(GroupElement element) {
		return elements.get(getOrder() - ((Element) element).getRepresentant());
	}

	@Override
	public MonoidElement getIdentityElement() {
		return elements.get(0);
	}

	@Override
	public Integer getOrder() {
		return order;
	}

	@Override
	public MonoidElement operation(GroupElement first, GroupElement second) {
		return elements.get((((Element) first).getRepresentant() + ((Element) second).getRepresentant()) % order);
	}

	@Override
	public GroupElement getGenerator() {
		return elements.get(1);
	}
 
	public void print() {

		System.out.println("Addition:");
		for (int i = 0; i < getOrder(); i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < getOrder(); j++) {
				System.out.print(((FiniteCyclicRing.Element) operation(get(i), get(j))).getRepresentant() + " ");
			}
			System.out.println();
		}

		System.out.println("Multiplication:");
		for (int i = 0; i < getOrder(); i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < getOrder(); j++) {
				System.out.print(((FiniteCyclicRing.Element) getMuliplicativeMonoid().operation(get(i), get(j)))
						.getRepresentant() + " ");
			}
			System.out.println();
		}
	}

	private Monoid multiplicativeMonoid;

	@Override
	public Monoid getMuliplicativeMonoid() {
		if (multiplicativeMonoid == null) {
			multiplicativeMonoid = new Monoid() {

				private static final long serialVersionUID = 1L;

				@Override
				public Integer getOrder() {
					return order;
				}

				@Override
				public MonoidElement operation(GroupElement first, GroupElement second) {
					if (first == elements.get(0) || second == elements.get(0)) {
						return elements.get(0);
					} else {
						return elements.get(
								(((Element) first).getRepresentant() * ((Element) second).getRepresentant()) % order);
					}
				}

			};
		}
		return multiplicativeMonoid;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
