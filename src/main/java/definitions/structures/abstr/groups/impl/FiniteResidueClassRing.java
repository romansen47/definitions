package definitions.structures.abstr.groups.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.groups.CyclicGroup;
import definitions.structures.abstr.groups.FiniteRing;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;
import settings.GlobalSettings;
import solver.StdDraw;

public class FiniteResidueClassRing implements FiniteRing, CyclicGroup {

	private static final long serialVersionUID = 1L;
	private final static Map<Integer, FiniteResidueClassRing> finiteCyclicGroupMap = new HashMap<>();
	static StdDraw stddraw;

	public static FiniteResidueClassRing getFiniteCyclicRing(final int n) {
		final FiniteResidueClassRing ring = finiteCyclicGroupMap.get(n);
		if (ring == null) {
			return new FiniteResidueClassRing(n);
		}
		return ring;
	}

	protected Map<Integer, RingElement> elements = new HashMap<>();

	private int order;

	private Monoid multiplicativeMonoid;

	int size = 80;

	public FiniteResidueClassRing() {
	}

	public FiniteResidueClassRing(final int n) {
		this.setOrder(n);
		this.elements = new HashMap<>();
		this.createElements(n);
	}

	protected void createElements(final int n) {
		for (int i = 0; i < n; i++) {
			this.get(i);
		}
		finiteCyclicGroupMap.put(n, this);
	}

	public void draw() {
		if (stddraw == null) {
			stddraw = new StdDraw();
		}
		stddraw.setCanvasSize(700, 700);
		StdDraw.setXscale(-100, 100);
		StdDraw.setYscale(-100, 100);
		StdDraw.setPenRadius(0.005);
		final int radius = 5;
		for (int i = 0; i < this.getOrder(); i++) {
			if (this.isUnit(this.get(i))) {
				StdDraw.setPenColor(Color.blue);
			} else {
				if (this.isPrimeElement(this.get(i))) {
					StdDraw.setPenColor(Color.red);
				} else {
					StdDraw.setPenColor(Color.green);
				}
			}
			StdDraw.circle(-this.size * Math.cos(2 * Math.PI * i / this.getOrder()),
					this.size * Math.sin(2 * Math.PI * i / this.getOrder()), radius);
			StdDraw.text(-this.size * Math.cos(2 * Math.PI * i / this.getOrder()),
					this.size * Math.sin(2 * Math.PI * i / this.getOrder()),
					String.valueOf(((FiniteResidueClassElement) this.get(i)).getRepresentant()));
		}
		StdDraw.save(GlobalSettings.PLOTS + "Group.png");
	}

	@Override
	public RingElement get(final Integer index) {
		RingElement ans = this.elements.get(index);
		if (ans == null) {
			ans = new FiniteResidueClassElement(index);
			this.elements.put(index, ans);
		}
		return ans;
	}

	@Override
	public GroupElement getGenerator() {
		return this.elements.get(1);
	}

	@Override
	public MonoidElement getIdentityElement() {
		return this.elements.get(0);
	}

	@Override
	public GroupElement getInverseElement(final GroupElement element) {
		return this.elements.get(this.getOrder() - ((FiniteResidueClassElement) element).getRepresentant());
	}

	@Override
	public Monoid getMuliplicativeMonoid() {
		if (this.multiplicativeMonoid == null) {
			this.multiplicativeMonoid = new Monoid() {

				private static final long serialVersionUID = 1L;

				@Override
				public Integer getOrder() {
					return FiniteResidueClassRing.this.order;
				}

				@Override
				public MonoidElement operation(final GroupElement first, final GroupElement second) {
					if (first == FiniteResidueClassRing.this.elements.get(0)
							|| second == FiniteResidueClassRing.this.elements.get(0)) {
						return FiniteResidueClassRing.this.elements.get(0);
					} else {
						return FiniteResidueClassRing.this.elements
								.get((((FiniteResidueClassElement) first).getRepresentant()
										* ((FiniteResidueClassElement) second).getRepresentant())
										% FiniteResidueClassRing.this.order);
					}
				}

			};
		}
		return this.multiplicativeMonoid;
	}

	@Override
	public Integer getOrder() {
		return this.order;
	}

	@Override
	public boolean isIrreducible(final RingElement element) {
		Boolean ans = ((FiniteResidueClassElement) element).isIrreducible();
		if (ans == null) {
			if (((FiniteResidueClassElement) element).isUnit()) {
				ans = false;
			} else {
				if (((FiniteResidueClassElement) element).isPrime()) {
					ans = true;
				} else {
					ans = FiniteRing.super.isIrreducible(element);
				}
			}
			((FiniteResidueClassElement) element).setIrreducible(ans);
		}
		return ans;
	}

	@Override
	public boolean isPrimeElement(final RingElement element) {
		Boolean ans = ((FiniteResidueClassElement) element).isPrime();
		if (ans == null) {
			ans = FiniteRing.super.isPrimeElement(element);
			((FiniteResidueClassElement) element).setPrime(ans);
			if (ans) {
				((FiniteResidueClassElement) element).setIsUnit(false);
				((FiniteResidueClassElement) element).setIrreducible(true);
			}
		}
		return ans;
	}

	@Override
	public boolean isUnit(final RingElement element) {
		Boolean ans = ((FiniteResidueClassElement) element).isUnit();
		if (ans == null) {
			ans = FiniteRing.super.isUnit(element);
			((FiniteResidueClassElement) element).setIsUnit(ans);
			if (ans) {
				((FiniteResidueClassElement) element).setPrime(false);
				((FiniteResidueClassElement) element).setIrreducible(false);
			}
		}
		return ans;
	}

	@Override
	public MonoidElement operation(final GroupElement first, final GroupElement second) {
		Map<GroupElement, GroupElement> map = getOperationMap().get(first);
		GroupElement ans = null;
		if (map != null) {
			ans = map.get(second);
			if (ans != null) {
				return ans;
			}
		}
		Map<GroupElement, GroupElement> map2 = getOperationMap().get(second);
		map = new HashMap<>();
		if (map2 == null) {
			map2 = new HashMap<>();
		}
		ans = this.elements.get((((FiniteResidueClassElement) first).getRepresentant()
				+ ((FiniteResidueClassElement) second).getRepresentant()) % this.getOrder());
		map.put(second, ans);
		map2.put(first, ans);
		return ans;
	}

	public void print() {
		System.out.println("\rAddition:\r");
		for (int i = 0; i < this.getOrder(); i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < this.order; j++) {
				System.out.print(
						((FiniteResidueClassElement) this.operation(this.get(i), this.get(j))).getRepresentant() + " ");
			}
			System.out.println();
		}

		System.out.println("\rMultiplication:\r");
		for (int i = 0; i < this.order; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < this.getOrder(); j++) {
				System.out.print(
						((FiniteResidueClassElement) this.getMuliplicativeMonoid().operation(this.get(i), this.get(j)))
								.getRepresentant() + " ");
			}
			System.out.println();
		}

		System.out.println("\rUnits:\r");
		for (int i = 0; i < this.order; i++) {
			System.out.print(i + " is unit: " + this.isUnit(this.get(i)));
			System.out.println();
		}

		System.out.println("\rPrimes:\r");
		for (int i = 0; i < this.order; i++) {
			System.out.print(i + " is prime: " + this.isPrimeElement(this.get(i)));
			System.out.println();
		}

		System.out.println("\rIrreducibility:\r");
		for (int i = 0; i < this.order; i++) {
			System.out.print(i + ": is reducible: " + !this.isIrreducible(this.get(i)));
			System.out.println();
		}

		System.out.println("\rDevisions:\r");
		for (int i = 0; i < this.order; i++) {
			for (int j = i; j < this.getOrder(); j++) {
				System.out.println(i + " devides " + j + " = " + this.divides(this.get(i), this.get(j)));
			}
		}

		System.out.println("\r\r");

	}

	public void setOrder(final int order) {
		this.order = order;
	}

}
