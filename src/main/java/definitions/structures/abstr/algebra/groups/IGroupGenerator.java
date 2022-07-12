package definitions.structures.abstr.algebra.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.Ring;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

public interface IGroupGenerator {

	static Logger logger = LogManager.getLogger(IGroupGenerator.class);

	public class Fraction extends ProductElement {
		private final Monoid baseMonoid;

		public Fraction(Element k, Element v, Monoid baseMoniod) {
			super(k, v);
			this.baseMonoid = baseMoniod;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.baseMonoid, this.getLeft(), this.getRight());
		}

		@Override
		public boolean equals(Object other) {
			if ((other == null) || (this.getClass() != other.getClass())) {
				return false;
			}

			return this.baseMonoid.operation(this.getLeft(), ((Fraction) other).getRight())
					.equals(this.baseMonoid.operation(((Fraction) other).getLeft(), this.getRight()));
		}

		public Monoid getBaseGroup() {
			return this.baseMonoid;
		}

		@Override
		public String toString() {
			return "(" + this.getLeft().toString() + "," + this.getRight().toString() + ")";
		}

	}

	default Group completeToGroup(Monoid m) {
		return new Group() {

			@Override
			public String toXml() {
				return "\r<group>\r<completion>\r" + m.toXml() + "\r</completion>\r</group>\r";
			}

			@Override
			public String toString() {
				return this.toXml();
			}

			class GroupElement extends Fraction {

				public GroupElement(Element k, Element v, Monoid baseGroup) {
					super(k, v, baseGroup);
				}

				@Override
				public String toString() {
					return "GroupElement (" + this.getLeft().toString() + "," + this.getRight().toString() + ")";
				}

			}

			private Monoid monoid = m;
			private GroupElement neutralElement;

			@Override
			public GroupElement getNeutralElement() {
				if (this.neutralElement == null) {
					final GroupElement tmp = (GroupElement) this.monoid.getNeutralElement();
					this.neutralElement = new GroupElement(tmp, tmp, this.monoid);
				}
				return this.neutralElement;
			}

			@Override
			public GroupElement operation(Element first, Element second) {
				final Element firstOp = this.monoid.operation(((Fraction) first).getLeft(),
						((Fraction) second).getLeft());
				final Element secondOp = this.monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new GroupElement(firstOp, secondOp, this.monoid);
			}

			@Override
			public GroupElement getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new GroupElement(x.getRight(), x.getLeft(), this.monoid);
			}

		};
	}

	/**
	 * Method to complete a discreet monoid to a discreet group.
	 *
	 * In order to achieve this we define an equivalence relation on MxM where M is
	 * the given monoid
	 *
	 * (a,b) ~ (x,y) is equivalent to a*y=b*x
	 *
	 * Then this is a group with neutral element {(a,a):a element of M} and for
	 * given (x,y) the inverse element is given by (y,x).
	 *
	 * @param m the discreet monoid
	 * @return the discreet group
	 */
	default DiscreetGroup completeToGroup(DiscreetMonoid m) {
		return new DiscreetGroup() {

			@Override
			public String toXml() {
				return "\r<discreet_group>\r<completion>\r" + m.toXml() + "\r</completion>\r</discreet_group>\r";
			}

			private DiscreetMonoid monoid = m;

			@Override
			public Element getNeutralElement() {
				final Element neutralElement = this.monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, this.monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				final Element firstOp = this.monoid.operation(((Fraction) first).getLeft(),
						((Fraction) second).getLeft());
				final Element secondOp = this.monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, this.monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), this.monoid);
			}

			@Override
			public Element get(Number representant) {
				final Element neutral = this.monoid.getNeutralElement();
				if (representant.doubleValue() >= 0) {
					final Element element = this.monoid.get(representant);
					return new Fraction(element, neutral, this.monoid);
				} else {
					final Element element = this.monoid.get(-representant.doubleValue());
					return new Fraction(neutral, element, this.monoid);
				}
			}

		};
	}

	class ProductElement implements Element {

		private final Element left;
		private final Element right;

		public ProductElement(final Element element, final Element element2) {
			this.left = element;
			this.right = element2;
		}

		/**
		 * @return the left
		 */
		public Element getLeft() {
			return this.left;
		}

		/**
		 * @return the right
		 */
		public Element getRight() {
			return this.right;
		}

	}

	default FiniteMonoid outerProduct(final FiniteMonoid a, final FiniteMonoid b) {
		FiniteMonoid ans = null;
		if ((a.getOrder() != null) && (b.getOrder() != null)) {
			ans = new FiniteMonoid() {
				private final Map<Double, Element> elements = new ConcurrentHashMap<>();
				private Map<Element, Map<Element, Element>> operationMap = null;
				private final int order = a.getOrder() * b.getOrder();

				@Override
				public Element get(final Number representant) {
					return this.getElements().get(representant);
				}

				@Override
				public Element getNeutralElement() {
					return new ProductElement(a.getNeutralElement(), b.getNeutralElement());
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (this.operationMap == null) {
						for (double i = 0; i < a.getOrder(); i++) {
							for (double j = 0; j < b.getOrder(); j++) {
								final ProductElement tmp = new ProductElement(a.get(i), b.get(j));
								this.getElements().put((i * b.getOrder()) + j, tmp);
							}
						}
						this.operationMap = new ConcurrentHashMap<>();
						for (final Double key1 : this.getElements().keySet()) {
							for (final Double key2 : this.getElements().keySet()) {
								this.operation(this.getElements().get(key1), this.getElements().get(key2));
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
					final Map<Element, Map<Element, Element>> u = this.operationMap;
					Map<Element, Element> x = u.computeIfAbsent(first, element -> new ConcurrentHashMap<>());
					Element ans = x.get(second);
					if (ans != null) {
						return ans;
					}
					ans = new ProductElement(
							a.operation(((ProductElement) first).getLeft(), ((ProductElement) second).getLeft()),
							b.operation(((ProductElement) first).getRight(), ((ProductElement) second).getRight()));
					Map<Element, Element> tmpMap = this.operationMap.computeIfAbsent(first,
							element -> new ConcurrentHashMap<>());
					tmpMap.put(second, ans);
					this.operationMap.put(first, tmpMap);
					return ans;
				}

				/**
				 * @return the elements
				 */
				@Override
				public Map<Double, Element> getElements() {
					return this.elements;
				}

			};
		}
		return ans;
	}

	default Ring completeToRing(SemiRing semiRing) {
		return new Ring() {

			private SemiRing monoid = semiRing;
			private Monoid multiplicativeMonoid;

			@Override
			public Element getNeutralElement() {
				final Element neutralElement = this.monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, this.monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				final Element firstOp = this.monoid.operation(((Fraction) first).getLeft(),
						((Fraction) second).getLeft());
				final Element secondOp = this.monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, this.monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), this.monoid);
			}

			@Override
			public Monoid getMuliplicativeMonoid() {
				if (this.multiplicativeMonoid == null) {
					this.multiplicativeMonoid = new Monoid() {

						@Override
						public Element operation(Element first, Element second) {
							final Element firstOp = monoid.operation(
									monoid.getMuliplicativeMonoid().operation(((Fraction) first).getLeft(),
											((Fraction) second).getLeft()),
									monoid.getMuliplicativeMonoid().operation(((Fraction) first).getRight(),
											((Fraction) second).getRight()));
							final Element secondOp = monoid.operation(
									monoid.getMuliplicativeMonoid().operation(((Fraction) first).getLeft(),
											((Fraction) second).getRight()),
									monoid.getMuliplicativeMonoid().operation(((Fraction) first).getRight(),
											((Fraction) second).getLeft()));
							return new Fraction(firstOp, secondOp, monoid);
						}

						@Override
						public Element getNeutralElement() {
							return new Fraction(monoid.getMuliplicativeMonoid().getNeutralElement(),
									monoid.getNeutralElement(), monoid);
						}

					};
				}
				return this.multiplicativeMonoid;
			}

			@Override
			public boolean isUnit(Element element) {
				return false;
			}

			@Override
			public Element getOne() {
				final Element zero = this.monoid.getNeutralElement();
				final Element one = this.monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(one, zero, this.monoid);
			}

			@Override
			public Element getMinusOne() {
				final Element zero = this.monoid.getNeutralElement();
				final Element one = this.monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(zero, one, this.monoid);
			}

		};
	}

	DiscreetSemiRing getNaturals();

	default DiscreetDomain getIntegers() {
		if (this.getIntegers() == null) {
			final DiscreetDomain integers = this.completeToDiscreetRing(this.getNaturals());
			this.setIntegers(integers);
			return integers;
		}
		return null;
	}

	void setIntegers(DiscreetRing integers);

	default DiscreetDomain completeToDiscreetRing(DiscreetSemiRing semiRing) {
		return new DiscreetDomain() {

			private Element neutralElement;

			private DiscreetSemiRing monoid = semiRing;

			@Override
			public Element getNeutralElement() {
				if (this.neutralElement == null) {
					final Element oldNeutralElement = this.monoid.getNeutralElement();
					this.neutralElement = new Fraction(oldNeutralElement, oldNeutralElement, this.monoid);
				}
				return this.neutralElement;
			}

			@Override
			public Element operation(Element first, Element second) {
				// Do not do the following:
				// final Element ans = DiscreetDomain.super.operation(first, second);
				// if (ans != null) {
				// return ans;
				// }
				final Element firstOp = this.monoid.operation(((Fraction) first).getLeft(),
						((Fraction) second).getLeft());
				final Element secondOp = this.monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, this.monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), this.monoid);
			}

			DiscreetMonoid multiplicativeMonoid;

			@Override
			public DiscreetMonoid getMuliplicativeMonoid() {
				return (this.multiplicativeMonoid == null ? this.multiplicativeMonoid = new DiscreetMonoid() {
					@Override
					public Element operation(Element first, Element second) {
						final Element firstOp = monoid.addition(
								monoid.multiplication(((ProductElement) first).getLeft(),
										((Fraction) second).getLeft()),
								monoid.multiplication(((ProductElement) first).getRight(),
										((ProductElement) second).getRight()));
						final Element secondOp = monoid.addition(
								monoid.multiplication(((ProductElement) first).getLeft(),
										((Fraction) second).getRight()),
								monoid.multiplication(((ProductElement) first).getRight(),
										((ProductElement) second).getLeft()));
						return new Fraction(firstOp, secondOp, monoid);
					}

					@Override
					public Element getNeutralElement() {
						return new Fraction(monoid.getMuliplicativeMonoid().getNeutralElement(),
								monoid.getNeutralElement(), monoid);
					}

					@Override
					public Element get(Number representant) {
						return new Fraction(monoid.get(representant), monoid.getNeutralElement(), this);
					}

				} : this.multiplicativeMonoid);
			}

			@Override
			public boolean isUnit(Element element) {
				final Fraction asFrac = (Fraction) element; // TODO
				return false;
			}

			@Override
			public Element getOne() {
				final Element zero = this.monoid.getNeutralElement();
				final Element one = this.monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(one, zero, this.monoid);
			}

			@Override
			public Element get(Number representant) {
				return this.getMuliplicativeMonoid().get(representant);
			}

			@Override
			public boolean divides(Element divisor, Element divident) {
				return true;
			}

			@Override
			public boolean isIrreducible(Element element) {
				return true;
			}

			@Override
			public boolean isPrimeElement(Element element) {
				return false;
			}

			@Override
			public Element getMinusOne() {
				final Element zero = this.monoid.getNeutralElement();
				final Element one = this.monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(zero, one, this.monoid);
			}

		};
	}

	public class MultiplicationFraction extends Fraction implements FieldElement {

		protected Map<Vector, Scalar> coordinates;
		private Ring monoid;
		private Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap;

		public MultiplicationFraction(Element k, Element v, Ring baseField) {
			super(k, v, baseField);
			this.monoid = baseField;
			if (baseField.getNeutralElement().equals(v)) {
				IGroupGenerator.logger.info("denominator is zero! such an element does not exist...");
			}
		}

		@Override
		public Map<Vector, Scalar> getCoordinates() {
			return this.coordinates;
		}

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates) {
			this.coordinates = coordinates;
		}

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
			this.coordinatesMap.put(space, coordinates);
		}

//		@Override
//		public int hashCode() {
//			return Objects.hash(coordinates, coordinatesMap, monoid, getLeft(), getRight());
//		}

		@Override
		public boolean equals(Object o) {
			boolean ans = true;
			if (o instanceof MultiplicationFraction) {
				final MultiplicationFraction other = (MultiplicationFraction) o;
				final Element left = this.getLeft();
				final Element right = this.getRight();
				final Element otherLeft = other.getLeft();
				final Element otherRight = other.getRight();
				final Element leftSide = this.monoid.multiplication(left, otherRight);
				final Element rightSide = this.monoid.multiplication(right, otherLeft);
				ans = leftSide.equals(rightSide);
				return ans;
			} else {
				ans = false;
			}
			return ans;
		}

	}

	default PrimeField getPrimeField(DiscreetDomain domain) {
		if (domain instanceof Field) {
			return (PrimeField) domain;
		}
		return this.completeToDiscreetField(domain);
	}

	default PrimeField completeToDiscreetField(DiscreetDomain domain) {
		return new PrimeField() {

			private DiscreetDomain monoid = domain;
			private FieldElement neutralElement;
			private FieldElement one;

			@Override
			public FieldElement getNeutralElement() {
				final Element zero = this.monoid.getNeutralElement();
				if (this.neutralElement == null) {
					final Element monoidNeutralElement = this.monoid.getMuliplicativeMonoid().getNeutralElement();
					this.neutralElement = new MultiplicationFraction(zero, monoidNeutralElement, this.monoid);
					Map<Vector, Scalar> tmpCoordinates = this.neutralElement.getCoordinates();
					if (tmpCoordinates == null) {
						tmpCoordinates = new ConcurrentHashMap<>();
						tmpCoordinates.put(this.getOne(), this.neutralElement);
						this.neutralElement.setCoordinates(tmpCoordinates);
					}
				}
				return this.neutralElement;
			}

			@Override
			public FieldElement getOne() {
				Element tmp;
				if (this.one == null) {
					tmp = this.monoid.getMuliplicativeMonoid().getNeutralElement();
					this.one = new MultiplicationFraction(tmp, tmp, this.monoid) {
						@Override
						public Map<Vector, Scalar> getCoordinates() {
							if (this.coordinates == null) {
								this.coordinates = new ConcurrentHashMap<>();
								this.coordinates.put(this, this);
							}
							return this.coordinates;
						}
					};
					this.one.getCoordinates().put(this.one, this.one);
				}
				return this.one;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public FieldElement getInverseElement(final Element element) {
				return new MultiplicationFraction(this.monoid.getInverseElement(((ProductElement) element).getLeft()),
						((ProductElement) element).getRight(), this.monoid);
			}

			@Override
			public FieldElement operation(Element first, Element second) {
				FieldElement ans = PrimeField.super.operation(first, second);
				if (ans == null) {
					final Element a = ((MultiplicationFraction) first).getLeft();
					final Element b = ((MultiplicationFraction) first).getRight();
					final Element c = ((MultiplicationFraction) second).getLeft();
					final Element d = ((MultiplicationFraction) second).getRight();
					final Element mult1 = this.monoid.multiplication(a, d);
					final Element mult2 = this.monoid.multiplication(b, c);
					final Element firstOp = this.monoid.addition(mult1, mult2);
					final Element secondOp = this.monoid.multiplication(b, d);
					ans = new MultiplicationFraction(firstOp, secondOp, this.monoid);
				}
				return ans;
			}

			DiscreetGroup multiplicativeMonoid;

			@Override
			public DiscreetGroup getMuliplicativeMonoid() {
				if (this.multiplicativeMonoid == null) {
					this.multiplicativeMonoid = new DiscreetGroup() {

						@Override
						public MultiplicationFraction getNeutralElement() {
							return (MultiplicationFraction) getOne();
						}

						@Override
						public MultiplicationFraction get(Number representant) {
							final double rounded = representant.doubleValue() - (representant.doubleValue() % 1.);
							return new MultiplicationFraction(monoid.get(rounded), monoid.getNeutralElement(), monoid);
						}

						@Override
						public MultiplicationFraction operation(Element first, Element second) {
							Element ans = DiscreetGroup.super.operation(first, second);
							if (ans == null) {
								final Element l1 = ((MultiplicationFraction) first).getLeft();
								final Element l2 = ((MultiplicationFraction) second).getLeft();
								final Element a = monoid.multiplication(l1, l2);
								final Element r1 = ((MultiplicationFraction) first).getRight();
								final Element r2 = ((MultiplicationFraction) second).getRight();
								final Element b = monoid.multiplication(r1, r2);
								ans = new MultiplicationFraction(a, b, monoid);
							} else {
								if (!(ans instanceof FieldElement)) {
									ans = new MultiplicationFraction(ans, monoid.getOne(), monoid);
								}
							}
							return (MultiplicationFraction) ans;
						}

						@Override
						public MultiplicationFraction getInverseElement(Element element) {
							final MultiplicationFraction fieldElement = (MultiplicationFraction) element;
							if (fieldElement.getLeft().equals(monoid.getNeutralElement())) {
								return null;
							}
							return new MultiplicationFraction(fieldElement.getRight(), fieldElement.getLeft(), monoid);
						}

					};
				}
				return this.multiplicativeMonoid;
			}

			private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix;

			@Override
			public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
				if (this.multiplicationMatrix == null) {
					this.multiplicationMatrix = PrimeField.super.getMultiplicationMatrix();
				}
				return this.multiplicationMatrix;
			}

			@Override
			public void setMultiplicationMatrix(Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
				this.multiplicationMatrix = multiplicationMatrix;
			}

			private List<Vector> genericBaseToList;

			@Override
			public List<Vector> genericBaseToList() {
				if (this.genericBaseToList == null) {
					this.genericBaseToList = PrimeField.super.genericBaseToList();
				}
				return this.genericBaseToList;
			}

			@Override
			public Vector getCoordinates(Vector vec) {
				return vec;
			}

			private EuclideanSpace dualSpace;

			@Override
			public EuclideanSpace getDualSpace() {
				if (this.dualSpace == null) {
					this.dualSpace = new FunctionalSpace(this) {
					};
				}
				return this.dualSpace;
			}

			@Override
			public List<Vector> getOrthonormalBase(List<Vector> base) {
				final List<Vector> ans = new ArrayList<>();
				ans.add(this.getOne());
				return ans;
			}

			@Override
			public boolean contains(Vector vec) {
				return true;
			}

			@Override
			public Element getMinusOne() {
				return new MultiplicationFraction(this.monoid.getNeutralElement(),
						this.monoid.getMuliplicativeMonoid().getNeutralElement(), this.monoid);
			}

			@Override
			public Vector stretch(Vector vec1, Scalar r) {
				return (Vector) this.getMuliplicativeMonoid().operation(vec1, r);
			}

			@Override
			public FiniteVector addition(Vector a, Vector b) {
				return this.operation(a, b);
			}

			@Override
			public String toString() {
				return "Completion of " + domain.toString() + " to a discrete field";
			}

		};
	}

	void setNaturals(DiscreetSemiRing naturals);

	PrimeField getRationals();

	void setRationals(PrimeField rationals);

	PrimeField getConstructedBinaries();

}
