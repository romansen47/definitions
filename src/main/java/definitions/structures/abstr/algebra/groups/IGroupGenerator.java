package definitions.structures.abstr.algebra.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

@SuppressWarnings("serial")
public interface IGroupGenerator {

	class Fraction extends ProductElement {
		private final Monoid baseMoniod;

		public Fraction(Element k, Element v, Monoid baseMoniod) {
			super(k, v);
			this.baseMoniod = baseMoniod;
		}

		@Override
		public boolean equals(Object other) {
			return baseMoniod.operation(getLeft(), ((Fraction) other).getRight())
					.equals(baseMoniod.operation(getRight(), ((Fraction) other).getLeft()));
		}

		public Monoid getBaseGroup() {
			return baseMoniod;
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
				return toXml();
			}

			class GroupElement extends Fraction {

				public GroupElement(Element k, Element v, Monoid baseGroup) {
					super(k, v, baseGroup);
				}

				@Override
				public String toString() {
					return "GroupElement (" + getLeft().toString() + "," + getRight().toString() + ")";
				}

			}

			final private Monoid monoid = m;
			GroupElement neutralElement;

			@Override
			public GroupElement getNeutralElement() {
				if (neutralElement == null) {
					final GroupElement tmp = (GroupElement) monoid.getNeutralElement();
					neutralElement = new GroupElement(tmp, tmp, monoid);
				}
				return neutralElement;
			}

			@Override
			public GroupElement operation(Element first, Element second) {
				final Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				final Element secondOp = monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new GroupElement(firstOp, secondOp, monoid);
			}

			@Override
			public GroupElement getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new GroupElement(x.getRight(), x.getLeft(), monoid);
			}

		};
	}

	/**
	 * Method to complete a discreet monoid to a discreet group.
	 *
	 * In order to achieve this we define an equivalence relation on MxM where M is
	 * the given monoid
	 *
	 * (a,b) ~ (x,y) <=> a*y=b*x
	 *
	 * Then this is a group with neutral element {(a,a):a element of M} and for
	 * given (x,y) the inverse element is given by (y,x).
	 *
	 * @param m
	 * @return
	 */
	default DiscreetGroup completeToGroup(DiscreetMonoid m) {
		return new DiscreetGroup() {

			@Override
			public String toXml() {
				return "\r<discreet_group>\r<completion>\r" + m.toXml() + "\r</completion>\r</discreet_group>\r";
			}

			final private DiscreetMonoid monoid = m;

			@Override
			public Element getNeutralElement() {
				final Element neutralElement = monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				final Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				final Element secondOp = monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), monoid);
			}

			@Override
			public Element get(Number representant) {
				final Element neutral = monoid.getNeutralElement();
				if (representant.doubleValue() >= 0) {
					final Element element = monoid.get(representant);
					return new Fraction(element, neutral, monoid);
				} else {
					final Element element = monoid.get(-representant.doubleValue());
					return new Fraction(neutral, element, monoid);
				}
			}

		};
	}

	class ProductElement implements Element {

		private final Element left;
		private final Element right;

		// protected double representant;

		public ProductElement(final Element Element, final Element Element2) {
			left = Element;
			right = Element2;
		}

		/**
		 * @return the left
		 */
		public Element getLeft() {
			return left;
		}

		/**
		 * @return the right
		 */
		public Element getRight() {
			return right;
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
					return getElements().get(representant);
				}

				@Override
				public Element getNeutralElement() {
					return new ProductElement(a.getNeutralElement(), b.getNeutralElement());
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (operationMap == null) {
						for (double i = 0; i < a.getOrder(); i++) {
							for (double j = 0; j < b.getOrder(); j++) {
								final ProductElement tmp = new ProductElement(a.get(i), b.get(j));
								getElements().put((i * b.getOrder()) + j, tmp);
							}
						}
						operationMap = new HashMap<>();
						for (final Double key1 : getElements().keySet()) {
							for (final Double key2 : getElements().keySet()) {
								operation(getElements().get(key1), getElements().get(key2));
							}
						}
					}
					return operationMap;
				}

				@Override
				public Integer getOrder() {
					return order;
				}

				@Override
				public Element operation(final Element first, final Element second) {
					final Map<Element, Map<Element, Element>> u = operationMap;
					Map<Element, Element> x = u.get(first);
					Element ans;
					if (x == null) {
						x = new ConcurrentHashMap<>();
						u.put(first, x);
					}
					ans = x.get(second);
					if (ans != null) {
						return ans;
					}
					ans = new ProductElement(
							a.operation(((ProductElement) first).getLeft(), ((ProductElement) second).getLeft()),
							b.operation(((ProductElement) first).getRight(), ((ProductElement) second).getRight()));
					Map<Element, Element> tmpMap = operationMap.get(first);
					if (tmpMap == null) {
						tmpMap = new HashMap<>();
					}
					tmpMap.put(second, ans);
					operationMap.put(first, tmpMap);
					return ans;
				}

				/**
				 * @return the elements
				 */
				@Override
				public Map<Double, Element> getElements() {
					return elements;
				}

			};
		}
		return ans;
	}

	default Ring completeToRing(SemiRing semiRing) {
		return new Ring() {

			final private SemiRing monoid = semiRing;
			Monoid multiplicativeMonoid;

			@Override
			public Element getNeutralElement() {
				final Element neutralElement = monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				final Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				final Element secondOp = monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), monoid);
			}

			@Override
			public Monoid getMuliplicativeMonoid() {
				if (multiplicativeMonoid == null) {
					multiplicativeMonoid = new Monoid() {

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
				return multiplicativeMonoid;
			}

			@Override
			public boolean isUnit(Element element) {
				return false;
			}

			@Override
			public Element getOne() {
				final Element zero = monoid.getNeutralElement();
				final Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(one, zero, monoid);
			}

			@Override
			public Element getMinusOne() {
				final Element zero = monoid.getNeutralElement();
				final Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(zero, one, monoid);
			}

		};
	}

	DiscreetSemiRing getNaturals();

	default DiscreetDomain getIntegers() {
		if (getIntegers() == null) {
			final DiscreetDomain integers = completeToDiscreetRing(getNaturals());
			setIntegers(integers);
			return integers;
		}
		return null;
	}

	void setIntegers(DiscreetRing integers);

	default DiscreetDomain completeToDiscreetRing(DiscreetSemiRing semiRing) {
		return new DiscreetDomain() {

			Element neutralElement;

			final private DiscreetSemiRing monoid = semiRing;

			@Override
			public Element getNeutralElement() {
				if (neutralElement == null) {
					final Element oldNeutralElement = monoid.getNeutralElement();
					neutralElement = new Fraction(oldNeutralElement, oldNeutralElement, monoid);
				}
				return neutralElement;
			}

			@Override
			public Element operation(Element first, Element second) {
				// Do not do the following:
				// final Element ans = DiscreetDomain.super.operation(first, second);
				// if (ans != null) {
				// return ans;
				// }
				final Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				final Element secondOp = monoid.operation(((Fraction) first).getRight(),
						((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				final Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), monoid);
			}

			DiscreetMonoid multiplicativeMonoid;

			@Override
			public DiscreetMonoid getMuliplicativeMonoid() {
				return (multiplicativeMonoid == null ? multiplicativeMonoid = new DiscreetMonoid() {
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

				} : multiplicativeMonoid);
			}

			@Override
			public boolean isUnit(Element element) {
				final Fraction asFrac = (Fraction) element;
				return false;
			}

			@Override
			public Element getOne() {
				final Element zero = monoid.getNeutralElement();
				final Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(one, zero, monoid);
			}

			@Override
			public Element get(Number representant) {
				return getMuliplicativeMonoid().get(representant);
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
				final Element zero = monoid.getNeutralElement();
				final Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(zero, one, monoid);
			}

		};
	}

	class MultiplicationFraction extends Fraction implements FieldElement {

		Map<Vector, Scalar> coordinates;
		Ring monoid;
		Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap;

		public MultiplicationFraction(Element k, Element v, Ring baseField) {
			super(k, v, baseField);
			monoid = baseField;
			if (v.equals(baseField.getNeutralElement())) {
				System.out.println("denominator is zero! such an element does not exist...");
			}
		}

		@Override
		public Map<Vector, Scalar> getCoordinates() {
			return coordinates;
		}

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates) {
			this.coordinates = coordinates;
		}

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
			coordinatesMap.put(space, coordinates);
		}

		@Override
		public boolean equals(Object o) {
			boolean ans = true;
			if (o instanceof MultiplicationFraction) {
				final MultiplicationFraction other = (MultiplicationFraction) o;
				final Element left = getLeft();
				final Element right = getRight();
				final Element otherLeft = other.getLeft();
				final Element otherRight = other.getRight();
				final Element leftSide = monoid.multiplication(left, otherRight);
				final Element rightSide = monoid.multiplication(right, otherLeft);
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
		return completeToDiscreetField(domain);
	}

	default PrimeField completeToDiscreetField(DiscreetDomain domain) {
		return new PrimeField() {

			final private DiscreetDomain monoid = domain;
			private FieldElement neutralElement;
			private FieldElement one;

			@Override
			public FieldElement getNeutralElement() {
				final Element zero = monoid.getNeutralElement();
				if (neutralElement == null) {
					final Element monoidNeutralElement = monoid.getMuliplicativeMonoid().getNeutralElement();
					neutralElement = new MultiplicationFraction(zero, monoidNeutralElement, monoid);
					Map<Vector, Scalar> tmpCoordinates = neutralElement.getCoordinates();
					if (tmpCoordinates == null) {
						tmpCoordinates = new ConcurrentHashMap<>();
						tmpCoordinates.put(getOne(), neutralElement);
						neutralElement.setCoordinates(tmpCoordinates);
					}
				}
				return neutralElement;
			}

			@Override
			public FieldElement getOne() {
				Element tmp;
				if (one == null) {
					tmp = monoid.getMuliplicativeMonoid().getNeutralElement();
					one = new MultiplicationFraction(tmp, tmp, monoid) {
						@Override
						public Map<Vector, Scalar> getCoordinates() {
							if (coordinates == null) {
								coordinates = new ConcurrentHashMap<>();
								coordinates.put(this, this);
							}
							return coordinates;
						}
					};
					one.getCoordinates().put(one, one);
				}
				return one;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public FieldElement getInverseElement(final Element element) {
				return new MultiplicationFraction(monoid.getInverseElement(((ProductElement) element).getLeft()),
						((ProductElement) element).getRight(), monoid);
			}

			@Override
			public FieldElement operation(Element first, Element second) {
				FieldElement ans = PrimeField.super.operation(first, second);
				if (ans == null) {
					final Element a = ((MultiplicationFraction) first).getLeft();
					final Element b = ((MultiplicationFraction) first).getRight();
					final Element c = ((MultiplicationFraction) second).getLeft();
					final Element d = ((MultiplicationFraction) second).getRight();
					final Element mult1 = monoid.multiplication(a, d);
					final Element mult2 = monoid.multiplication(b, c);
					final Element firstOp = monoid.addition(mult1, mult2);
					final Element secondOp = monoid.multiplication(b, d);
					ans = new MultiplicationFraction(firstOp, secondOp, monoid);
				}
				return ans;
			}

			DiscreetGroup multiplicativeMonoid;

			@Override
			public DiscreetGroup getMuliplicativeMonoid() {
				if (multiplicativeMonoid == null) {
					multiplicativeMonoid = new DiscreetGroup() {

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
				return multiplicativeMonoid;
			}

			Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix;

			@Override
			public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
				if (multiplicationMatrix == null) {
					multiplicationMatrix = PrimeField.super.getMultiplicationMatrix();
				}
				return multiplicationMatrix;
			}

			@Override
			public void setMultiplicationMatrix(Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
				this.multiplicationMatrix = multiplicationMatrix;
			}

			List<Vector> genericBaseToList;

			@Override
			public List<Vector> genericBaseToList() {
				if (genericBaseToList == null) {
					genericBaseToList = PrimeField.super.genericBaseToList();
				}
				return genericBaseToList;
			}

			@Override
			public Vector getCoordinates(Vector vec) {
				return vec;
			}

			private EuclideanSpace dualSpace;

			@Override
			public EuclideanSpace getDualSpace() {
				if (dualSpace == null) {
					dualSpace = new FunctionalSpace(this) {
					};
				}
				return dualSpace;
			}

			@Override
			public List<Vector> getOrthonormalBase(List<Vector> base) {
				final List<Vector> ans = new ArrayList<>();
				ans.add(getOne());
				return ans;
			}

			@Override
			public boolean contains(Vector vec) {
				return true;
			}

			@Override
			public Element getMinusOne() {
				final Element zero = monoid.getNeutralElement();
				final Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new MultiplicationFraction(zero, one, monoid);
			}

			@Override
			public Vector stretch(Vector vec1, Scalar r) {
				return (Vector) getMuliplicativeMonoid().operation(vec1, r);
			}

			@Override
			public FiniteVector addition(Vector a, Vector b) {
				return operation(a, b);
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
