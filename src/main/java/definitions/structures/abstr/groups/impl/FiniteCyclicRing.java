package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.groups.CyclicGroup;
import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;

public class FiniteCyclicRing implements FiniteRing, DiscreteGroup, CyclicGroup {

	private static final long serialVersionUID = 1L;

	private final static Map<Integer, FiniteCyclicRing> finiteCyclicGroupMap = new HashMap<>();

	public static FiniteCyclicRing getFiniteCyclicRing(int n) {
		FiniteCyclicRing ring = finiteCyclicGroupMap.get(n);
		if (ring == null) {
			return new FiniteCyclicRing(n); 
		}
		return ring; 
	}

	public FiniteCyclicRing(int n) {
		setOrder(n);
		elements = new HashMap<>();
		for (int i = 0; i < n; i++) {
			get(i);
		}
		finiteCyclicGroupMap.put(n, this);
	}

	public FiniteCyclicRing() {
	}

	private int order;

	protected Map<Integer, RingElement> elements = new HashMap<>();

	public RingElement get(Integer index) {
		RingElement ans = elements.get(index);
		if (ans == null) {
			ans = new CyclicRingElement(index);
			elements.put(index, ans);
		}
		return ans;
	}

	@Override
	public GroupElement getInverseElement(GroupElement element) {
		return elements.get(getOrder() - ((CyclicRingElement) element).getRepresentant());
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
		return elements
				.get((((CyclicRingElement) first).getRepresentant() + ((CyclicRingElement) second).getRepresentant())
						% getOrder());
	}

	@Override
	public GroupElement getGenerator() {
		return elements.get(1);
	}

	public void print() { 
		System.out.println("Addition:");
		for (int i = 0; i < getOrder(); i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < order; j++) {
				System.out
				.print(((CyclicRingElement) operation(get(i), get(j)))
						.getRepresentant() + " ");
			}
			System.out.println();
		}

		System.out.println("Multiplication:");
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < getOrder(); j++) {
				System.out
						.print(((CyclicRingElement) getMuliplicativeMonoid().operation(get(i), get(j)))
								.getRepresentant() + " ");
			}
			System.out.println();
		}
		
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": is reducible: "+!isIrreducible(get(i)));		
			System.out.println();
		}
		
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": is prime: "+!isPrimeElement(get(i)));		
			System.out.println();
		}
		
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": is unit: "+!isUnit(get(i)));		
			System.out.println();
		}
		
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": ");
			for (int j = i; j < getOrder(); j++) {
				System.out
						.println(i + " devides "+j+" = "+divides(get(i),get(j)));
			} 
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
						return elements.get((((CyclicRingElement) first).getRepresentant()
								* ((CyclicRingElement) second).getRepresentant()) % order);
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
