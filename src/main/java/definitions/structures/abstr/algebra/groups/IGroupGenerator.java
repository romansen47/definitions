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
import settings.GlobalSettings;

@SuppressWarnings("serial")
public interface IGroupGenerator {	
	
	class Fraction extends ProductElement {
		private final Monoid baseGroup;

		public Fraction(Element k, Element v, Monoid baseGroup) {
			super(k, v);
			this.baseGroup = baseGroup;
		}

		@Override
		public boolean equals(Object other) {
//			if (other instanceof Fraction) {
//				final Element a = getBaseGroup().operation(getLeft(), ((Fraction) other).getRight());
//				final Element b = getBaseGroup().operation(getRight(), ((Fraction) other).getLeft());
//				Boolean ans=a.equals(b);
//				return ans;
//			}
			return Math.abs(
					getRepresentant() - ((Element) other).getRepresentant()) < GlobalSettings.REAL_EQUALITY_FEINHEIT;
		}

		public Monoid getBaseGroup() {
			return baseGroup;
		}

		@Override
		public Double getRepresentant() {
			return getLeft().getRepresentant() - getRight().getRepresentant();
		}

		@Override
		public String toString() {
			try {
				return Double.toString(getRepresentant());
			} catch (Exception e) {
				return "(" + getLeft().getRepresentant() + "," + getRight().getRepresentant() + ")";
			}
		}
	}

	class AdditionFraction extends Fraction {
		public AdditionFraction(Element k, Element v, DiscreetMonoid baseGroup) {
			super(k, v, baseGroup);
		}
	}

	default Group completeToGroup(Monoid m) {
		return new Group() {

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
			public String toString() {
				return "the completion of " + m.toString() + " to a group";
			}

			@Override
			public GroupElement getNeutralElement() {
				if (neutralElement == null) {
					GroupElement tmp = (GroupElement) monoid.getNeutralElement();
					neutralElement = new GroupElement(tmp, tmp, monoid);
				}
				return neutralElement;
			}

			@Override
			public GroupElement operation(Element first, Element second) {
				Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				Element secondOp = monoid.operation(((Fraction) first).getRight(), ((Fraction) second).getRight());
				return new GroupElement(firstOp, secondOp, monoid);
			}

			@Override
			public GroupElement getInverseElement(Element element) {
				Fraction x = (Fraction) element;
				return new GroupElement(x.getRight(), x.getLeft(), monoid);
			}

		};
	}

	default DiscreetGroup completeToGroup(DiscreetMonoid m) {
		return new DiscreetGroup() {

			@Override
			public String toString() {
				return "the completion of " + m.toString() + " to a discreet group";
			}

			final private DiscreetMonoid monoid = m;

			@Override
			public Element getNeutralElement() {
				Element neutralElement = monoid.getNeutralElement();
				return new AdditionFraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				Element firstOp = monoid.operation(((AdditionFraction) first).getLeft(),
						((AdditionFraction) second).getLeft());
				Element secondOp = monoid.operation(((AdditionFraction) first).getRight(),
						((AdditionFraction) second).getRight());
				return new AdditionFraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				AdditionFraction x = (AdditionFraction) element;
				return new AdditionFraction((Element) x.getRight(), (Element) x.getLeft(), monoid);
			}

			public Element get(Double representant) {
				Element neutral = monoid.getNeutralElement();
				if (representant >= 0) {
					Element element = monoid.get(representant);
					return new AdditionFraction(element, neutral, monoid);
				} else {
					Element element = monoid.get(-representant);
					return new AdditionFraction(neutral, element, monoid);
				}
			}

		};
	}

	class ProductElement implements Element {

		private final Element left;
		private final Element right;

		private double representant;

		public ProductElement(final Element Element, final Element Element2) {
			this.left = Element;
			this.right = Element2;
			representant = left.getRepresentant() - right.getRepresentant();
		}

		@Override
		public String toString() {
			if (this.getLeft() instanceof Element && this.getRight() instanceof Element) {
				final Element u = (Element) this.getLeft();
				final Element w = (Element) this.getRight();
				return " ( " + u.getRepresentant() + " , " + w.getRepresentant() + " ) ";
			}
			return " ( " + this.getLeft().toString() + " , " + this.getRight().toString() + " ) ";
		}

		@Override
		public Double getRepresentant() {
			return representant;
		}

		@Override
		public void setRepresentant(Double representant) {
			this.representant = representant;
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
		if (a.getOrder() != null && b.getOrder() != null) {
			ans = new FiniteMonoid() {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6631649574912990607L;
				private Map<Double, Element> elements = new ConcurrentHashMap<>();
				private Map<Element, Map<Element, Element>> operationMap = null;
				private int order = a.getOrder() * b.getOrder();

				@Override
				public Element get(final Double representant) {
					return getElements().get(representant);
				}

				@Override
				public Element getNeutralElement() {
					return new ProductElement((Element) a.getNeutralElement(), (Element) b.getNeutralElement());
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (this.operationMap == null) {
						for (double i = 0; i < a.getOrder(); i++) {
							for (double j = 0; j < b.getOrder(); j++) {
								final ProductElement tmp = new ProductElement((Element) a.get(i), (Element) b.get(j));
								getElements().put(i * b.getOrder() + j, (Element) tmp);
							}
						}
						this.operationMap = new HashMap<>();
						for (final Double key1 : getElements().keySet()) {
							for (final Double key2 : getElements().keySet()) {
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
							(Element) a.operation(((ProductElement) first).getLeft(),
									((ProductElement) second).getLeft()),
							(Element) b.operation(((ProductElement) first).getRight(),
									((ProductElement) second).getRight()));
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
				public Map<Double, Element> getElements() {
					return elements;
				}

			};
		}
		return ans;
	}

	default Ring completeToRing(SemiRing semiRing) {
		return new Ring() {

			@Override
			public String toString() {
				return "the completion of " + semiRing.toString() + " to a Ring";
			}

			final private SemiRing monoid = semiRing;

			@Override
			public Element getNeutralElement() {
				Element neutralElement = monoid.getNeutralElement();
				return new Fraction(neutralElement, neutralElement, monoid);
			}

			@Override
			public Element operation(Element first, Element second) {
				Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				Element secondOp = monoid.operation(((Fraction) first).getRight(), ((Fraction) second).getRight());
				return new Fraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				Fraction x = (Fraction) element;
				return new Fraction(x.getRight(), x.getLeft(), monoid);
			}

			Monoid multiplicativeMonoid;

			@Override
			public Monoid getMuliplicativeMonoid() {
				if (multiplicativeMonoid == null) {
					multiplicativeMonoid = new Monoid() {

						@Override
						public Element operation(Element first, Element second) {
							Element firstOp = monoid.operation(
									monoid.getMuliplicativeMonoid().operation(((Fraction) first).getLeft(),
											((Fraction) second).getLeft()),
									monoid.getMuliplicativeMonoid().operation(((Fraction) first).getRight(),
											((Fraction) second).getRight()));
							Element secondOp = monoid.operation(
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
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Element getOne() {
				Element zero = monoid.getNeutralElement();
				Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(one, zero, monoid);
			}

			@Override
			public Element getMinusOne() {
				Element zero = monoid.getNeutralElement();
				Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(zero, one, monoid);
			}

		};
	}

	DiscreetSemiRing getNaturals();

	default DiscreetDomain getIntegers() {
		if (getIntegers() == null) {
			DiscreetDomain integers = completeToDiscreetRing(getNaturals());
			setIntegers(integers);
			return integers;
		}
		return null;
	}

	void setIntegers(DiscreetRing integers);

	default DiscreetDomain completeToDiscreetRing(DiscreetSemiRing semiRing) {
		return new DiscreetDomain() {

			Element neutralElement;

			@Override
			public String toString() {
				return "the completion of " + semiRing.toString() + " to a discreet domain";
			}

			final private DiscreetSemiRing monoid = semiRing;

			@Override
			public Element getNeutralElement() {
				if (neutralElement == null) {
					Element oldNeutralElement = monoid.getNeutralElement();
					neutralElement = new AdditionFraction(oldNeutralElement, oldNeutralElement, monoid);
				}
				return neutralElement;
			}

			@Override
			public Element operation(Element first, Element second) {
				Element ans = DiscreetDomain.super.operation(first, second);
				if (ans != null) {
					return ans;
				}
				Element firstOp = monoid.operation(((Fraction) first).getLeft(), ((Fraction) second).getLeft());
				Element secondOp = monoid.operation(((Fraction) first).getRight(), ((Fraction) second).getRight());
				return new AdditionFraction(firstOp, secondOp, monoid);
			}

			@Override
			public Element getInverseElement(Element element) {
				Fraction x = (Fraction) element;
				return new AdditionFraction(x.getRight(), x.getLeft(), monoid);
			}

			DiscreetMonoid multiplicativeMonoid;

			@Override
			public DiscreetMonoid getMuliplicativeMonoid() {
				if (multiplicativeMonoid == null) {
					multiplicativeMonoid = new DiscreetMonoid() {

						@Override
						public Element operation(Element first, Element second) {
							Element firstOp = monoid.addition(
									monoid.multiplication(((ProductElement) first).getLeft(),
											((Fraction) second).getLeft()),
									monoid.multiplication(((ProductElement) first).getRight(),
											((ProductElement) second).getRight()));
							Element secondOp = monoid.addition(
									monoid.multiplication(((ProductElement) first).getLeft(),
											((Fraction) second).getRight()),
									monoid.multiplication(((ProductElement) first).getRight(),
											((ProductElement) second).getLeft()));
							return new AdditionFraction(firstOp, secondOp, monoid);
						}

						@Override
						public Element getNeutralElement() {
							return new AdditionFraction(monoid.getMuliplicativeMonoid().getNeutralElement(),
									monoid.getNeutralElement(), monoid);
						}

						@Override
						public Element get(Double representant) {
							return new AdditionFraction(monoid.get(representant), monoid.getNeutralElement(), this);
						}

					};
				}
				return multiplicativeMonoid;
			}

			@Override
			public boolean isUnit(Element element) {
				AdditionFraction asFrac = (AdditionFraction) element;
				return false;
			}

			@Override
			public Element getOne() {
				Element zero = monoid.getNeutralElement();
				Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new AdditionFraction(one, zero, monoid);
			}

			@Override
			public Element get(Double representant) {
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
				Element zero = monoid.getNeutralElement();
				Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new Fraction(zero, one, monoid);
			}

		};
	}

	class MultiplicationFraction extends ProductElement implements FieldElement {

		Map<Vector, Scalar> coordinates;
		Ring monoid;
		Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap;

		public MultiplicationFraction(Element k, Element v, Ring baseField) {
			super(k, v);
			monoid = baseField;
			this.setRepresentant(k.getRepresentant() / v.getRepresentant());
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
		public String toString() {
			String ans = "";
			if (Math.abs(getRight().getRepresentant()) > GlobalSettings.REAL_EQUALITY_FEINHEIT) {
				ans = Double.toString(getLeft().getRepresentant() / getRight().getRepresentant());
			} else {
				ans = "Quotient " + getLeft().toString() + " / " + getRight().toString();
			}
			return ans;
		}

		@Override
		public boolean equals(Object o) {
			boolean ans = true;
			if (o instanceof MultiplicationFraction) {
				MultiplicationFraction other = (MultiplicationFraction) o;
				Element left = getLeft();
				Element right = getRight();
				Element otherLeft = other.getLeft();
				Element otherRight = other.getRight();
				Element leftSide = monoid.multiplication(left, otherRight);
				Element rightSide = monoid.multiplication(right, otherLeft);
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

			final private DiscreetDomain monoid = domain;
			private FieldElement neutralElement;
			private FieldElement one;

			@Override
			public FieldElement getNeutralElement() {
				Element zero = monoid.getNeutralElement();
				if (neutralElement == null) {
					Element monoidNeutralElement = monoid.getMuliplicativeMonoid().getNeutralElement();
					neutralElement = new MultiplicationFraction(zero, monoidNeutralElement, monoid);
					Map<Vector, Scalar> tmpCoordinates = neutralElement.getCoordinates();
					if (tmpCoordinates == null) {
						tmpCoordinates = new ConcurrentHashMap<>();
						tmpCoordinates.put(getOne(), (Scalar) neutralElement);
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
					one.getCoordinates().put(one, (Scalar) one);
				}
				return (MultiplicationFraction) one;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public FieldElement getInverseElement(final Element element) {
				final Field field = this.getField();
				Element monoidNeutralElement = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new MultiplicationFraction(monoid.getInverseElement(((ProductElement) element).getLeft()),
						((ProductElement) element).getRight(), monoid);
			}

			@Override
			public FieldElement operation(Element first, Element second) {
				FieldElement ans = PrimeField.super.operation(first, second);
				if (ans == null) {
					Element a = ((MultiplicationFraction) first).getLeft();
					Element b = ((MultiplicationFraction) first).getRight();
					Element c = ((MultiplicationFraction) second).getLeft();
					Element d = ((MultiplicationFraction) second).getRight();
					Element mult1 = monoid.multiplication(a, d);
					Element mult2 = monoid.multiplication(b, c);
					Element firstOp = monoid.addition(mult1, mult2);
					Element secondOp = monoid.multiplication(b, d);
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
						public MultiplicationFraction get(Double representant) {
							double rounded = representant - (representant % 1.);
							return new MultiplicationFraction(monoid.get(rounded), monoid.getNeutralElement(), monoid);
						}

						@Override
						public MultiplicationFraction operation(Element first, Element second) {
							Element ans = DiscreetGroup.super.operation(first, second);
							if (ans == null) {
								Element l1 = ((MultiplicationFraction) first).getLeft();
								Element l2 = ((MultiplicationFraction) second).getLeft();
								Element a = monoid.multiplication(l1, l2);
								Element r1 = ((MultiplicationFraction) first).getRight();
								Element r2 = ((MultiplicationFraction) second).getRight();
								Element b = monoid.multiplication(r1, r2);
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
							MultiplicationFraction fieldElement = (MultiplicationFraction) element;
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
				List<Vector> ans = new ArrayList<>();
				ans.add(getOne());
				return ans;
			}

			@Override
			public boolean contains(Vector vec) {
				return true;
			}

			@Override
			public Element getMinusOne() {
				Element zero = monoid.getNeutralElement();
				Element one = monoid.getMuliplicativeMonoid().getNeutralElement();
				return new MultiplicationFraction(zero, one, monoid);
			}

			@Override
			public Vector stretch(Vector vec1, Scalar r) {
				return (Vector) getMuliplicativeMonoid().operation(vec1, r);
			}

			@Override
			public FiniteVector addition(Vector a, Vector b) {
				return (FiniteVector) operation(a, b);
			}
			
			@Override
			public String toString() {
				return "the completion of "+monoid.toString()+" to a prime field";
			}

		};
	}

	void setNaturals(DiscreetSemiRing naturals);

	PrimeField getRationals();

	void setRationals(PrimeField rationals);

	PrimeField getConstructedBinaries();

}
