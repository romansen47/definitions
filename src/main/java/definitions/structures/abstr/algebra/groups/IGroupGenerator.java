package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.util.Pair;

import definitions.structures.abstr.algebra.fields.DiscreetField;
import definitions.structures.abstr.algebra.fields.DiscreetFieldElement;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoidElement;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.rings.DiscreetRingElement;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroupElement;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroupElement;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface IGroupGenerator {

	class Fraction extends Pair<MonoidElement, MonoidElement> implements GroupElement {
		private static final long serialVersionUID = -4136163547473234129L;
		private final Monoid baseGroup;
		private final MonoidElement k;
		private final MonoidElement v;

		public Fraction(MonoidElement k, MonoidElement v, Monoid baseGroup) {
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

		public MonoidElement getK() {
			return k;
		}

		public MonoidElement getV() {
			return v;
		}

		public Monoid getBaseGroup() {
			return baseGroup;
		}
	}

	class DiscreetFraction extends Fraction implements DiscreetGroupElement {
		public DiscreetFraction(DiscreetMonoidElement k, DiscreetMonoidElement v, DiscreetMonoid baseGroup) {
			super(k, v, baseGroup);
		}

		@Override
		public int getRepresentant() {
			return ((DiscreetSemiGroupElement) getK()).getRepresentant()
					- ((DiscreetSemiGroupElement) getV()).getRepresentant();
		}

		@Override
		public String toString() {
			return String.valueOf(getRepresentant());
		}
	}
	
	class DiscreetRingFraction extends DiscreetFraction implements DiscreetFieldElement {
		public DiscreetRingFraction(DiscreetRingElement k, DiscreetRingElement v, DiscreetRing baseGroup) {
			super(k, v, baseGroup);
		}

		@Override
		public Scalar getInverse() { 
			return new DiscreetRingFraction((DiscreetRingElement)getV(),(DiscreetRingElement)getK(),(DiscreetRing)getBaseGroup());
		}

		Map<Vector, Scalar> coordinates=new HashMap<>();
		
		@Override
		public Map<Vector, Scalar> getCoordinates() { 
			return null;
		}

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates) {
			this.coordinates=coordinates;
		}

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) { 
		} 
	}

	default Group completion(Monoid m) {
		return new Group() {

			final private Monoid monoid = m;

			@Override
			public GroupElement getNeutralElement() {
				MonoidElement neutralElement = monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public MonoidElement operation(SemiGroupElement first, SemiGroupElement second) {
				MonoidElement firstOp = monoid.operation(((Fraction) first).getK(), ((Fraction) second).getK());
				MonoidElement secondOp = monoid.operation(((Fraction) first).getV(), ((Fraction) second).getV());
				return new Fraction(firstOp, secondOp, monoid);
			}

			@Override
			public GroupElement getInverseElement(GroupElement element) {
				Fraction x = (Fraction) element;
				return new Fraction(x.getV(), x.getK(), monoid);
			}

			public MonoidElement get(MonoidElement element) {
				return new Fraction(element, monoid.getNeutralElement(), monoid);
			}

		};
	}

	default DiscreetGroup completion(DiscreetMonoid m) {
		return new DiscreetGroup() {

			final private DiscreetMonoid monoid = m;

			@Override
			public DiscreetGroupElement getNeutralElement() {
				DiscreetMonoidElement neutralElement = monoid.getNeutralElement();
				return new DiscreetFraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public DiscreetMonoidElement operation(SemiGroupElement first, SemiGroupElement second) {
				DiscreetMonoidElement firstOp = monoid.operation(((DiscreetFraction) first).getK(),
						((DiscreetFraction) second).getK());
				DiscreetMonoidElement secondOp = monoid.operation(((DiscreetFraction) first).getV(),
						((DiscreetFraction) second).getV());
				return new DiscreetFraction(firstOp, secondOp, monoid);
			}

			@Override
			public DiscreetGroupElement getInverseElement(GroupElement element) {
				DiscreetFraction x = (DiscreetFraction) element;
				return new DiscreetFraction((DiscreetMonoidElement) x.getV(), (DiscreetMonoidElement) x.getK(), monoid);
			}

			public DiscreetGroupElement get(Integer representant) {
				DiscreetMonoidElement neutral = monoid.getNeutralElement();
				if (representant >= 0) {
					DiscreetMonoidElement element = monoid.get(representant);
					return new DiscreetFraction(element, neutral, monoid);
				} else {
					DiscreetMonoidElement element = monoid.get(-representant);
					return new DiscreetFraction(neutral, element, monoid);
				}
			}
		};
	}
	
	default DiscreetField completion(DiscreetRing m) {
		return new DiscreetField() {

			final private DiscreetMonoid monoid = m;

			@Override
			public DiscreetFieldElement getNeutralElement() {
				DiscreetFieldElement neutralElement = monoid.getNeutralElement();
				return new DiscreetRingFraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public DiscreetFieldElement operation(SemiGroupElement first, SemiGroupElement second) {
				DiscreetFieldElement firstOp = monoid.operation(((DiscreetFraction) first).getK(),
						((DiscreetFraction) second).getK());
				DiscreetFieldElement secondOp = monoid.operation(((DiscreetFraction) first).getV(),
						((DiscreetFraction) second).getV());
				return new DiscreetFraction(firstOp, secondOp, monoid);
			}

			@Override
			public DiscreetGroupElement getInverseElement(GroupElement element) {
				DiscreetFraction x = (DiscreetFraction) element;
				return new DiscreetFraction((DiscreetMonoidElement) x.getV(), (DiscreetMonoidElement) x.getK(), monoid);
			}

			public DiscreetGroupElement get(Integer representant) {
				DiscreetMonoidElement neutral = monoid.getNeutralElement();
				if (representant >= 0) {
					DiscreetMonoidElement element = monoid.get(representant);
					return new DiscreetFraction(element, neutral, monoid);
				} else {
					DiscreetMonoidElement element = monoid.get(-representant);
					return new DiscreetFraction(neutral, element, monoid);
				}
			}
		};
	}

	class ProductElement extends Pair<FiniteMonoidElement, FiniteMonoidElement> implements FiniteMonoidElement {
		private static final long serialVersionUID = 1L;

		private final DiscreetSemiGroupElement k;
		private final DiscreetSemiGroupElement v;

		private int representant;

		public ProductElement(final FiniteMonoidElement finiteSemiGroupElement,
				final FiniteMonoidElement finiteSemiGroupElement2) {
			super(finiteSemiGroupElement, finiteSemiGroupElement2);
			this.k = finiteSemiGroupElement;
			this.v = finiteSemiGroupElement2;
		}

		@Override
		public String toString() {
			if (this.k instanceof FiniteGroupElement && this.v instanceof FiniteGroupElement) {
				final FiniteGroupElement u = (FiniteGroupElement) this.k;
				final FiniteGroupElement w = (FiniteGroupElement) this.v;
				return " ( " + u.getRepresentant() + " , " + w.getRepresentant() + " ) ";
			}
			return " ( " + this.k.toString() + " , " + this.v.toString() + " ) ";
		}

		@Override
		public int getRepresentant() {
			return representant;
		}

	}

	FiniteResidueClassRing getFiniteResidueClassRing(int order);

	default FiniteMonoid outerProduct(final FiniteMonoid a, final FiniteMonoid b) {
		FiniteMonoid ans = null;
		if (a.getOrder() != null && b.getOrder() != null) {
			ans = new FiniteMonoid() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6631649574912990607L;
				private Map<Integer, FiniteSemiGroupElement> elements = new ConcurrentHashMap<>();
				private Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> operationMap = null;
				private int order = a.getOrder() * b.getOrder();

				@Override
				public DiscreetSemiGroupElement get(final Integer representant) {
					return getElements().get(representant);
				}

				@Override
				public FiniteMonoidElement getNeutralElement() {
					return new ProductElement((FiniteMonoidElement) a.getNeutralElement(),
							(FiniteMonoidElement) b.getNeutralElement());
				}

				@Override
				public Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> getOperationMap() {
					if (this.operationMap == null) {
						for (int i = 0; i < a.getOrder(); i++) {
							for (int j = 0; j < b.getOrder(); j++) {
								final ProductElement tmp = new ProductElement((FiniteMonoidElement) a.get(i),
										(FiniteMonoidElement) b.get(j));
								getElements().put(i * b.getOrder() + j, (FiniteSemiGroupElement) tmp);
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
				public FiniteMonoidElement operation(final SemiGroupElement first, final SemiGroupElement second) {
					Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> u = operationMap;
					Map<SemiGroupElement, SemiGroupElement> x = u.get(first);
					SemiGroupElement ans;
					if (x == null) {
						x = new ConcurrentHashMap<>();
						u.put(first, x);
					}
					ans = x.get(second);
					if (ans != null) {
						return (FiniteMonoidElement) ans;
					}
					ans = new ProductElement(
							(FiniteMonoidElement) a.operation(((ProductElement) first).getFirst(),
									((ProductElement) second).getFirst()),
							(FiniteMonoidElement) b.operation(((ProductElement) first).getSecond(),
									((ProductElement) second).getSecond()));
					Map<SemiGroupElement, SemiGroupElement> tmpMap = this.operationMap.get(first);
					if (tmpMap == null) {
						tmpMap = new HashMap<>();
					}
					tmpMap.put(second, ans);
					this.operationMap.put(first, tmpMap);
					return (FiniteMonoidElement) ans;
				}

				/**
				 * @return the elements
				 */
				public Map<Integer, FiniteSemiGroupElement> getElements() {
					return elements;
				}

			};
		}
//		else {
//			ans = new FiniteMonoid() {
//				private long serialVersionUID = 1L;
//
//				@Override
//				public MonoidElement getNeutralElement() {
//					return new ProductElement((DiscreetSemiGroupElement)a.getNeutralElement(), (DiscreetSemiGroupElement)b.getNeutralElement());
//				}
//
//				@Override
//				public Integer getOrder() {
//					return null;
//				}
//
//				@Override
//				public SemiGroupElement operation(final SemiGroupElement first, final SemiGroupElement second) {
//					return new ProductElement(
//							(DiscreetSemiGroupElement)a.operation(((ProductElement) first).getFirst(), ((ProductElement) first).getSecond()),
//							(DiscreetSemiGroupElement)b.operation(((ProductElement) second).getFirst(), ((ProductElement) second).getSecond()));
//				}
//
//				@Override
//				public DiscreetSemiGroupElement get(Integer representant) {
//					// TODO Auto-generated method stub
//					return null;
//				}
// 
//			};
//		}
		return ans;
	}

}
