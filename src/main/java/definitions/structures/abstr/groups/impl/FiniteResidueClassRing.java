package definitions.structures.abstr.groups.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.groups.CyclicGroup;
import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.FiniteRing;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.RingElement;
import settings.GlobalSettings;
import solver.StdDraw;

public class FiniteResidueClassRing implements FiniteRing, DiscreteGroup, CyclicGroup {

	private static final long serialVersionUID = 1L;

	private final static Map<Integer, FiniteResidueClassRing> finiteCyclicGroupMap = new HashMap<>();

	public static FiniteResidueClassRing getFiniteCyclicRing(int n) {
		FiniteResidueClassRing ring = finiteCyclicGroupMap.get(n);
		if (ring == null) {
			return new FiniteResidueClassRing(n);
		}
		return ring;
	}

	public FiniteResidueClassRing(int n) {
		setOrder(n);
		elements = new HashMap<>();
		createElements(n);

	}

	protected void createElements(int n) {
		for (int i = 0; i < n; i++) {
			get(i);
		}
		finiteCyclicGroupMap.put(n, this);
	}

	public FiniteResidueClassRing() {
	}

	private int order;

	protected Map<Integer, RingElement> elements = new HashMap<>();

	public RingElement get(Integer index) {
		RingElement ans = elements.get(index);
		if (ans == null) {
			ans = new FiniteResidueClassElement(index);
			elements.put(index, ans);
		}
		return ans;
	}

	@Override
	public GroupElement getInverseElement(GroupElement element) {
		return elements.get(getOrder() - ((FiniteResidueClassElement) element).getRepresentant());
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
		return elements.get((((FiniteResidueClassElement) first).getRepresentant()
				+ ((FiniteResidueClassElement) second).getRepresentant()) % getOrder());
	}

	@Override
	public GroupElement getGenerator() {
		return elements.get(1);
	}

	public void print() {
		System.out.println("\rAddition:\r");
		for (int i = 0; i < getOrder(); i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < order; j++) {
				System.out.print(((FiniteResidueClassElement) operation(get(i), get(j))).getRepresentant() + " ");
			}
			System.out.println();
		}

		System.out.println("\rMultiplication:\r");
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": ");
			for (int j = 0; j < getOrder(); j++) {
				System.out.print(((FiniteResidueClassElement) getMuliplicativeMonoid().operation(get(i), get(j)))
						.getRepresentant() + " ");
			}
			System.out.println();
		}

		System.out.println("\rUnits:\r");
		for (int i = 0; i < order; i++) {
			System.out.print(i + " is unit: " + isUnit(get(i)));
			System.out.println();
		}

		System.out.println("\rPrimes:\r");
		for (int i = 0; i < order; i++) {
			System.out.print(i + " is prime: " + isPrimeElement(get(i)));
			System.out.println();
		}

		System.out.println("\rIrreducibility:\r");
		for (int i = 0; i < order; i++) {
			System.out.print(i + ": is reducible: " + !isIrreducible(get(i)));
			System.out.println();
		}

		System.out.println("\rDevisions:\r");
		for (int i = 0; i < order; i++) {
			for (int j = i; j < getOrder(); j++) {
				System.out.println(i + " devides " + j + " = " + divides(get(i), get(j)));
			}
		}

		System.out.println("\r\r");

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
						return elements.get((((FiniteResidueClassElement) first).getRepresentant()
								* ((FiniteResidueClassElement) second).getRepresentant()) % order);
					}
				}

			};
		}
		return multiplicativeMonoid;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public boolean isUnit(RingElement element) {
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
	public boolean isPrimeElement(RingElement element) {
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
	public boolean isIrreducible(RingElement element) {
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

	static StdDraw stddraw;
	int size = 80;

	public void draw() {
		if (stddraw == null) {
			stddraw = new StdDraw();
		}
		stddraw.setCanvasSize(700, 700);
		StdDraw.setXscale(-100, 100);
		StdDraw.setYscale(-100, 100);
		StdDraw.setPenRadius(0.005);
		int radius = 5;
		for (int i = 0; i < getOrder(); i++) {
			if (isUnit(get(i))) {
				StdDraw.setPenColor(Color.blue);
			} else {
				if (isPrimeElement(get(i))) {
					StdDraw.setPenColor(Color.red);
				} else {
					StdDraw.setPenColor(Color.green);
				}
			}
			StdDraw.circle(-size * Math.cos(2 * Math.PI * i / getOrder()), size * Math.sin(2 * Math.PI * i / getOrder()),
					radius);
			StdDraw.text(-size * Math.cos(2 * Math.PI * i / getOrder()), size * Math.sin(2 * Math.PI * i / getOrder()),
					String.valueOf(((FiniteResidueClassElement) get(i)).getRepresentant()));
		}
		StdDraw.save(GlobalSettings.PLOTS + "Group.png");
	}

}
