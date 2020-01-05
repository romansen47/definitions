package definitions.structures.abstr.algebra.rings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.groups.CyclicGroup;
import definitions.structures.abstr.algebra.groups.GroupElement;
import definitions.structures.abstr.algebra.monoids.AbelianSemiGroup;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoidElement;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.FiniteCommutativeRing;
import definitions.structures.abstr.algebra.rings.FiniteRingElement;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroupElement;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.FiniteVector;
import solver.StdDraw;

/**
 * 
 * @author RoManski
 *
 *         Concrete implementation of a finite residue class ring.
 *
 */
public class FiniteResidueClassRing implements FiniteCommutativeRing, CyclicGroup {

	private Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> operationMap = new HashMap<>();
	
	private class MuliplicativeMonoid implements FiniteMonoid, AbelianSemiGroup {
		private static final long serialVersionUID = 1L;

		private final Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> multiplicationMap = new HashMap<>();

		@Override
		public FiniteResidueClassElement get(final Integer representant) {
			return (FiniteResidueClassElement) FiniteResidueClassRing.this.getElements().get(representant + 1);
		}

		@Override
		public FiniteResidueClassElement getNeutralElement() {
			return FiniteResidueClassRing.this.getGenerator();
		}

		@Override
		public Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> getOperationMap() {
			return this.multiplicationMap;
		}

		@Override
		public Integer getOrder() {
			return FiniteResidueClassRing.this.order - 1;
		}

		@Override
		public FiniteMonoidElement operation(final SemiGroupElement first, final SemiGroupElement second) {
			Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> a=operationMap;
			Map<SemiGroupElement, SemiGroupElement> x=a.get(first);
			SemiGroupElement ans;
			if (x==null) { 
				x=new ConcurrentHashMap<>();
				a.put(first,x);
			}
			ans=x.get(second); 
			if (ans != null) {
				return (FiniteMonoidElement) ans;
			}
			if (first.equals(FiniteResidueClassRing.this.getElements().get(0))
					|| second.equals(FiniteResidueClassRing.this.getElements().get(0))) {
				ans = FiniteResidueClassRing.this.getElements().get(0);
			} else {
				ans = FiniteResidueClassRing.this.getElements().get((((FiniteResidueClassElement) first).getRepresentant()
						* ((FiniteResidueClassElement) second).getRepresentant()) % FiniteResidueClassRing.this.order);
			}
			if (first.equals(FiniteResidueClassRing.this.getElements().get(1))) {
				ans = second;
			} else if (second.equals(FiniteResidueClassRing.this.getElements().get(1))) {
				ans = first;
			}
			if (ans.equals(FiniteResidueClassRing.this.getGenerator())) {
				if (((FiniteResidueClassElement) first).getMultiplicativeInverseElement() == null) {
					((FiniteResidueClassElement) first).setMultiplicativeInverseElement((RingElement) second);
					if (((FiniteResidueClassElement) second).getMultiplicativeInverseElement() == null) {
						((FiniteResidueClassElement) second).setMultiplicativeInverseElement((RingElement) first);
					}
				}
			}
			if (this.multiplicationMap.get(first) == null) {
				this.multiplicationMap.put(first, new HashMap<>());
			}
			this.multiplicationMap.get(first).put(second, ans);
			if (this.multiplicationMap.get(second) == null) {
				this.multiplicationMap.put(second, new HashMap<>());
			}
			this.multiplicationMap.get(second).put(first, ans);
			return (FiniteMonoidElement) ans;
		}

		@Override
		public Map<Integer, FiniteSemiGroupElement> getElements() {
			return FiniteResidueClassRing.this.getElements();
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * The group map for caching purposes.
	 */
	private final static Map<Integer, FiniteResidueClassRing> finiteCyclicGroupMap = new HashMap<>();

	@Deprecated
	static StdDraw stddraw;

//	/**
//	 * Map for the finitely many elements of the ring.
//	 */
//	protected Map<Integer, SemiGroupElement> elements = new HashMap<>();

	/**
	 * order of the ring.
	 */
	private int order;

	/**
	 * the multiplicative monoid of the ring.
	 */
	private Monoid multiplicativeMonoid;

	protected FiniteResidueClassRing() {
	}

	public FiniteResidueClassRing(final int n) {
		this.setOrder(n);
		this.createElements(n);
	}

	Map<Integer, FiniteSemiGroupElement> elements = new ConcurrentHashMap<>();

	/**
	 * 
	 * Method to obtain a map to the elements.
	 * 
	 * @return the map.
	 */
	public Map<Integer, FiniteSemiGroupElement> getElements() {
		return elements;
	}
	
	protected void createElements(final int n) {
		for (int i = 0; i < n; i++) {
			this.get(i);
		}
		finiteCyclicGroupMap.put(n, this);
		Generator.getInstance().getLogger().info("Creating elements.");
		Generator.getInstance().getLogger().info("Creating additions matrix.");
		for (int i = 0; i < this.getOrder(); i++) {
			for (int j = 0; j < this.order; j++) {
				((FiniteResidueClassElement) this.addition(this.get(i), this.get(j))).getRepresentant();
			}
		}
		Generator.getInstance().getLogger().info("Creating multiplications matrix.");
		for (int i = 0; i < this.order; i++) {
			int k = 0;
			if (this.getMuliplicativeMonoid() instanceof AbelianSemiGroup) {
				k = i;
			}
			for (int j = k; j < this.getOrder(); j++) {
				((FiniteResidueClassElement) this.multiplication(this.get(i), this.get(j))).getRepresentant();
			}
		}
		for (int i = 0; i < this.order; i++) {
			final RingElement e = this.get(i);
			if (e instanceof FiniteVector) {
				((FiniteVector) e).getCoordinates();
			}
			this.isUnit(this.get(i));
		}
	}

	@Override
	public FiniteResidueClassElement get(final Integer index) {
		FiniteResidueClassElement ans = (FiniteResidueClassElement) this.getElements().get(index);
		if (ans == null) {
			ans = new FiniteResidueClassElement(index);
			this.getElements().put(index, ans);
		}
		return ans;
	}

	@Override
	public FiniteResidueClassElement getGenerator() {
		return (FiniteResidueClassElement) this.getElements().get(1);
	}

	@Override
	public FiniteResidueClassElement getInverseElement(final GroupElement element) {
		return (FiniteResidueClassElement) this.getElements()
				.get(this.getOrder() - ((FiniteResidueClassElement) element).getRepresentant());
	}

	@Override
	public Monoid getMuliplicativeMonoid() {
		if (this.multiplicativeMonoid == null) {
			this.multiplicativeMonoid = new MuliplicativeMonoid();
		}
		return this.multiplicativeMonoid;

	}

	@Override
	public FiniteResidueClassElement getNeutralElement() {
		return (FiniteResidueClassElement) this.getElements().get(0);
	}

	@Override
	public FiniteRingElement getOne() {
		if (getOrder() > 0) {
			return (FiniteRingElement) this.elements.get(1);
		}
		return (FiniteRingElement) this.elements.get(0);
	}

	@Override
	public Integer getOrder() {
		return this.order;
	}

	@Override
	public boolean isUnit(final RingElement element) {
		Boolean ans = ((FiniteResidueClassElement) element).isUnit();
		if (ans == null) {
			ans = FiniteCommutativeRing.super.isUnit(element);
			((FiniteResidueClassElement) element).setIsUnit(ans);
			if (ans) {
				((FiniteResidueClassElement) element).setPrime(false);
				((FiniteResidueClassElement) element).setIrreducible(false);
			}
		}
		return ans;
	}

	@Override
	public void print() {
		System.out.println("\rAddition:\r");
		for (int i = 0; i < this.getOrder(); i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < this.order; j++) {
				System.out.print(
						((FiniteResidueClassElement) this.addition(this.get(i), this.get(j))).getRepresentant() + " ");
			}
			System.out.println();
		}
		System.out.println("\rMultiplication:\r");
		for (int i = 0; i < this.order; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < this.getOrder(); j++) {
				System.out.print(
						((FiniteResidueClassElement) this.multiplication(this.get(i), this.get(j))).getRepresentant()
								+ " ");
			}
			System.out.println();
		}
		System.out.println("\rUnits:\r");
		for (int i = 0; i < this.order; i++) {
			System.out.print(i + " is unit: " + this.isUnit(this.get(i)));
			final FiniteRingElement tmp = this.get(i);
			final boolean isUnit = this.isUnit(tmp);
			if (isUnit) {
				final FiniteRingElement invElement = this.getMultiplicativeInverseElement(tmp);
				System.out.print(" inverse: " + invElement.getRepresentant());
			}
			System.out.println();
		}
//		System.out.println("\rDevisions:\r");
//		for (int i = 0; i < this.order; i++) {
//			for (int j = i; j < this.getOrder(); j++) {
//				System.out.println(i + " devides " + j + " = " + this.divides(this.get(i), this.get(j)));
//			}
//		}
	}

	/**
	 * setter for the order of the ring.
	 * 
	 * @param order the order of the ring.
	 */
	public void setOrder(final int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Finite residue class ring of order " + this.getOrder().toString();
	}

	/**
	 * @return the operationMap
	 */
	@Override
	public Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> getOperationMap() {
		return operationMap;
	}

	/**
	 * @param operationMap the operationMap to set
	 */
	public void setOperationMap(Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> operationMap) {
		this.operationMap = operationMap;
	}

}
