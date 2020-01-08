package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.util.Pair;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.Ring; 

public interface IGroupGenerator {

	class Fraction extends Pair<Element, Element> implements Element {
		private static final long serialVersionUID = -4136163547473234129L;
		private final Monoid baseGroup;
		private final Element k;
		private final Element v;

		public Fraction(Element k, Element v, Monoid baseGroup) {
			super(k, v);
			this.k = k;
			this.v = v;
			this.baseGroup = baseGroup;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Fraction) {
				return getBaseGroup().operation(getK(), ((Fraction) other).getSecond())
						.equals(getBaseGroup().operation(getV(), ((Fraction) other).getFirst()));
			}
			return false;
		}

		public Element getK() {
			return k;
		}

		public Element getV() {
			return v;
		}

		public Monoid getBaseGroup() {
			return baseGroup;
		}
		
		@Override
		public String toString() {
			return "("+getK().getRepresentant()+","+getV().getRepresentant()+")";
		}
	}

	class DiscreetFraction extends Fraction implements Element {
		public DiscreetFraction(Element k, Element v, DiscreetMonoid baseGroup) {
			super(k, v, baseGroup);
		}
	}

	default Group completeToGroup(Monoid m) {
		return new Group() {

			final private Monoid monoid = m;

			@Override
			public Element getNeutralElement() {
				Element neutralElement = monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				Element firstOp = monoid.operation(((Fraction) first).getK(), ((Fraction) second).getK());
				Element secondOp = monoid.operation(((Fraction) first).getV(), ((Fraction) second).getV());
				return new Fraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				Fraction x = (Fraction) element;
				return new Fraction(x.getV(), x.getK(), monoid);
			}

			public Element get(Element element) {
				return new Fraction(element, monoid.getNeutralElement(), monoid);
			}

		};
	}

	default DiscreetGroup completeToGroup(DiscreetMonoid m) {
		return new DiscreetGroup() {

			final private DiscreetMonoid monoid = m;

			@Override
			public Element getNeutralElement() {
				Element neutralElement = monoid.getNeutralElement();
				return new DiscreetFraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				Element firstOp = monoid.operation(((DiscreetFraction) first).getK(),
						((DiscreetFraction) second).getK());
				Element secondOp = monoid.operation(((DiscreetFraction) first).getV(),
						((DiscreetFraction) second).getV());
				return new DiscreetFraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				DiscreetFraction x = (DiscreetFraction) element;
				return new DiscreetFraction((Element) x.getV(), (Element) x.getK(), monoid);
			}

			public Element get(Integer representant) {
				Element neutral = monoid.getNeutralElement();
				if (representant >= 0) {
					Element element = monoid.get(representant);
					return new DiscreetFraction(element, neutral, monoid);
				} else {
					Element element = monoid.get(-representant);
					return new DiscreetFraction(neutral, element, monoid);
				}
			}

		};
	}
	
	class ProductElement extends Pair<Element, Element> implements Element { 

		private final Element k;
		private final Element v;

		private int representant;

		public ProductElement(final Element Element,
				final Element Element2) {
			super(Element, Element2);
			this.k = Element;
			this.v = Element2;
		}

		@Override
		public String toString() {
			if (this.k instanceof Element && this.v instanceof Element) {
				final Element u = (Element) this.k;
				final Element w = (Element) this.v;
				return " ( " + u.getRepresentant() + " , " + w.getRepresentant() + " ) ";
			}
			return " ( " + this.k.toString() + " , " + this.v.toString() + " ) ";
		}

		@Override
		public Integer getRepresentant() {
			return representant;
		}

	}

	default FiniteMonoid outerProduct(final FiniteMonoid a, final FiniteMonoid b) {
		FiniteMonoid ans = null;
		if (a.getOrder() != null && b.getOrder() != null) {
			ans = new FiniteMonoid() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6631649574912990607L;
				private Map<Integer, Element> elements = new ConcurrentHashMap<>();
				private Map<Element, Map<Element, Element>> operationMap = null;
				private int order = a.getOrder() * b.getOrder(); 

				@Override
				public Element get(final Integer representant) {
					return getElements().get(representant);
				}

				@Override
				public Element getNeutralElement() {
					return new ProductElement((Element) a.getNeutralElement(),
							(Element) b.getNeutralElement());
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (this.operationMap == null) {
						for (int i = 0; i < a.getOrder(); i++) {
							for (int j = 0; j < b.getOrder(); j++) {
								final ProductElement tmp = new ProductElement((Element) a.get(i),
										(Element) b.get(j));
								getElements().put(i * b.getOrder() + j, (Element) tmp);
							}
						}
						this.operationMap = new HashMap<>();
						for (final Integer key1 : getElements().keySet()) {
							for (final Integer key2 : getElements().keySet()) {
								this.operation(getElements().get(key1), getElements().get(key2));
							}
						}
					}
					return this.operationMap;
				}

				@Override
				public Integer getOrder() {
					return this.order;
				}

				@Override
				public Element operation(final Element first, final Element second) {
					Map<Element, Map<Element, Element>> u = operationMap;
					Map<Element, Element> x = u.get(first);
					Element ans;
					if (x == null) {
						x = new ConcurrentHashMap<>();
						u.put(first, x);
					}
					ans = x.get(second);
					if (ans != null) {
						return (Element) ans;
					}
					ans = new ProductElement(
							(Element) a.operation(((ProductElement) first).getFirst(),
									((ProductElement) second).getFirst()),
							(Element) b.operation(((ProductElement) first).getSecond(),
									((ProductElement) second).getSecond()));
					Map<Element, Element> tmpMap = this.operationMap.get(first);
					if (tmpMap == null) {
						tmpMap = new HashMap<>();
					}
					tmpMap.put(second, ans);
					this.operationMap.put(first, tmpMap);
					return (Element) ans;
				}

				/**
				 * @return the elements
				 */
				public Map<Integer, Element> getElements() {
					return elements;
				}

			};
		}
		return ans;
	}

	Ring completeToRing(SemiRing semiRing);
	
	Field completeToField(Domain domain);
	
}
