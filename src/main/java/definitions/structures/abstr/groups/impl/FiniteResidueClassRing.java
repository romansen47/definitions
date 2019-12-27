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
import definitions.structures.euclidean.Generator;
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

	int size = 50;

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
		Generator.getInstance().getLogger().info("Creating elements.");
		Generator.getInstance().getLogger().info("Creating additions matrix.");
		for (int i = 0; i < this.getOrder(); i++) {
			for (int j = 0; j < this.order; j++) {
				((FiniteResidueClassElement) this.addition(this.get(i), this.get(j))).getRepresentant();
			}
		}
		Generator.getInstance().getLogger().info("Creating multiplications matrix.");
		for (int i = 0; i < this.order; i++) {
			for (int j = 0; j < this.getOrder(); j++) {
				((FiniteResidueClassElement) this.multiplication(this.get(i), this.get(j))).getRepresentant();
			}
		}
		for (int i = 0; i < this.order; i++) {
			this.isUnit(this.get(i));
		}
		for (int i = 0; i < this.order; i++) {
			this.isPrimeElement(this.get(i));
		}
		for (int i = 0; i < this.order; i++) {
			this.isIrreducible(this.get(i));
		}
	}

	@Override
	public FiniteResidueClassElement get(final Integer index) {
		FiniteResidueClassElement ans = (FiniteResidueClassElement) this.elements.get(index);
		if (ans == null) {
			ans = new FiniteResidueClassElement(index);
			this.elements.put(index, ans);
		}
		return ans;
	}

	@Override
	public Map<Integer, RingElement> getElements() {
		return elements;
	}

	@Override
	public GroupElement getGenerator() {
		return this.elements.get(1);
	}

	@Override
	public MonoidElement getNeutralElement() {
		return this.elements.get(0);
	}

	@Override
	public FiniteResidueClassElement getInverseElement(final GroupElement element) {
		return (FiniteResidueClassElement) this.elements.get(this.getOrder() - ((FiniteResidueClassElement) element).getRepresentant());
	}

	@Override
	public Monoid getMuliplicativeMonoid() {
		if (this.multiplicativeMonoid == null) {
			this.multiplicativeMonoid = new FiniteMonoid() {
				private static final long serialVersionUID = 1L;

				private final Map<MonoidElement, Map<MonoidElement, MonoidElement>> multiplicationMap = new HashMap<>();

				@Override
				public Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap() {
					return this.multiplicationMap;
				}

				@Override
				public Integer getOrder() {
					return FiniteResidueClassRing.this.order;
				}

				@Override
				public MonoidElement operation(final MonoidElement first, final MonoidElement second) {
					MonoidElement ans = FiniteMonoid.super.operation(first, second);
					if (ans != null) {
						return ans;
					}
					ans = FiniteResidueClassRing.this.elements
							.get((((FiniteResidueClassElement) first).getRepresentant()
									* ((FiniteResidueClassElement) second).getRepresentant())
									% FiniteResidueClassRing.this.order);
					if (ans.equals(FiniteResidueClassRing.this.getGenerator())) {
						if (((FiniteResidueClassElement) first).getMultiplicativeInverseElement() == null) {
							((FiniteResidueClassElement) first).setMultiplicativeInverseElement((RingElement) second);
							if (((FiniteResidueClassElement) second).getMultiplicativeInverseElement() == null) {
								((FiniteResidueClassElement) second)
										.setMultiplicativeInverseElement((RingElement) first);
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
					return ans;
				}

				@Override
				public MonoidElement get(Integer representant) {
					return elements.get(representant);
				}

				@Override
				public MonoidElement getNeutralElement() { 
					return FiniteResidueClassRing.this.getGenerator();
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
			if (ans == false) {
				((FiniteResidueClassElement) element).setPrime(false);
			}
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
		if (stddraw == null) {
			stddraw = new StdDraw();
		}
		stddraw.setCanvasSize(700, 700);
		StdDraw.setXscale(-100, 100);
		StdDraw.setYscale(-100, 100);
		StdDraw.setPenRadius(0.005);
		final int radius = (int) Math.round(Math.PI * this.size / this.order);
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
			StdDraw.text(-2 * this.size * Math.cos(2 * Math.PI * i / this.getOrder()),
					2 * this.size * Math.sin(2 * Math.PI * i / this.getOrder()),
					String.valueOf(((FiniteResidueClassElement) this.get(i)).getRepresentant()));
		}
		StdDraw.save(GlobalSettings.PLOTS + "Group_of_order_" + this.order + ".png");
	}

	public void setOrder(final int order) {
		this.order = order;
	}

}
