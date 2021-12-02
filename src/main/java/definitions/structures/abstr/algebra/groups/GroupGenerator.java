package definitions.structures.abstr.algebra.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.FinitePrimeField;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.LinearSelfMapping;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.impl.Naturals;

@Service
public class GroupGenerator implements IGroupGenerator, XmlPrintable, Unweavable {

	public static GroupGenerator instance;
	private DiscreetSemiRing naturals;
	private DiscreetDomain integers;
	private PrimeField rationals;
	private FinitePrimeField binaries;

	public static GroupGenerator getInstance() {
		return instance;
	}

	public static void setInstance(final GroupGenerator groupGenerator) {
		instance = groupGenerator;
	}

	Map<Integer, FiniteRing> map = new HashMap<>();
	private PrimeField constructedBinaries;

	public void setIntegers(final DiscreetDomain integers) {
		this.integers = integers;
	}

	@Override
	public DiscreetDomain getIntegers() {
		if (integers == null) {
			setIntegers(completeToDiscreetRing(getNaturals()));
		}
		return integers;
	}

	@Override
	public DiscreetSemiRing getNaturals() {
		if (naturals == null) {
			setNaturals(new Naturals());
		}
		return naturals;
	}

	@Override
	public void setNaturals(DiscreetSemiRing naturals) {
		this.naturals = naturals;
	}

	/**
	 * @return the rationals
	 */
	@Override
	public PrimeField getRationals() {
		if (rationals == null) {
			setRationals(completeToDiscreetField(integers));
		}
		return rationals;
	}

	/**
	 * @param rationals the rationals to set
	 */
	@Override
	public void setRationals(PrimeField rationals) {
		this.rationals = rationals;
	}

	@Override
	public void setIntegers(DiscreetRing integers) {
		this.integers = (DiscreetDomain) integers;
	}

	public FinitePrimeField getBinaries() {
		if (binaries == null) {
			binaries = new FinitePrimeField() {
				private static final long serialVersionUID = 1L;

				@Override
				public String toXml() {
					return "<binaries />";
				}

				class Binary implements FieldElement {

					private Map<Vector, Scalar> coordinates;
					private final boolean value;

					Binary(boolean value) {
						this.value = value;
					}

					@Override
					public Map<Vector, Scalar> getCoordinates() {
						if (coordinates == null) {
							coordinates = new ConcurrentHashMap<>();
							if (this == one) {
								coordinates.put(one, one);
							} else {
								coordinates.put(one, zero);
							}
						}
						return coordinates;
					}

					@Override
					public void setCoordinates(Map<Vector, Scalar> coordinates) {
						this.coordinates = coordinates;
					}

					@Override
					public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
					}

					@Override
					public Double getRepresentant() {
						if (this == one) {
							return 1.;
						}
						return 0.;
					}

					@Override
					public String toString() {
						return "<binary>" + value + "</binary>";
					}

				};

				private DiscreetGroup multiplicativeMonoid = this.getMuliplicativeMonoid();
				
				@Override
				public DiscreetGroup getMuliplicativeMonoid() {
					return multiplicativeMonoid;
				};

				private final Binary zero = new Binary(false);
				private final Binary one = new Binary(true);
				private List<Vector> base;
				private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix;
				private Map<Element, Map<Element, Element>> operationMap;
				private Map<Double, Element> elements;

				@Override
				public Map<Double, Element> getElements() {
					if (elements == null) {
						elements = new ConcurrentHashMap<>();
						elements.put(0d, zero);
						elements.put(1d, one);
					}
					return elements;
				}

				@Override
				public Binary getOne() {
					return one;
				}

				@Override
				public Binary getZero() {
					return zero;
				}

				@Override
				public Element getMinusOne() {
					return one;
				}

				@Override
				public void setMultiplicationMatrix(Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
					this.multiplicationMatrix = multiplicationMatrix;
				}

				@Override
				public Vector getCoordinates(Vector vec) {
					return ((FiniteVector) vec).getProjection(this);
				}

				@Override
				public EuclideanSpace getDualSpace() {
					return binaries;
				}

				@Override
				public Vector stretch(Vector vec, Scalar r) {
					return multiplication(vec, r);
				}

				@Override
				public List<Vector> getOrthonormalBase(List<Vector> base) {
					return this.base;
				}

				@Override
				public boolean contains(Vector vec) {
					return (vec == one) || (vec == zero);
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (operationMap == null) {
						operationMap = new ConcurrentHashMap<>();
						final Map<Element, Element> zeroMap = new ConcurrentHashMap<>();
						final Map<Element, Element> oneMap = new ConcurrentHashMap<>();
						zeroMap.put(zero, zero);
						zeroMap.put(one, one);
						oneMap.put(zero, one);
						oneMap.put(one, zero);
						operationMap.put(zero, zeroMap);
						operationMap.put(one, oneMap);
					}
					return operationMap;
				}

				@Override
				public FiniteVector addition(Vector a, Vector b) {
					Vector ans = FinitePrimeField.super.operation(a, b);
					if (ans == null) {
						final boolean tmp = ((Binary) a).value && ((Binary) b).value;
						ans = get(!tmp);
					}
					return (FiniteVector) ans;
				}

				@Override
				public Vector multiplication(Element a, Element b) {
					final boolean ans = ((Binary) a).value && ((Binary) b).value;
					return get(ans);
				}

				private Vector get(boolean b) {
					Binary ans = zero;
					if (b) {
						ans = one;
					}
					return ans;
				}

				@Override
				public FieldElement getNeutralElement() {
					return zero;
				}

				@Override
				public Element get(Double representant) {
					boolean ans = false;
					if (representant != 0.) {
						ans = true;
					}
					return get(ans);
				}

				@Override
				public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
					if (multiplicationMatrix == null) {
						multiplicationMatrix = new ConcurrentHashMap<>();
						final Map<Vector, Map<Vector, Scalar>> linearity = new ConcurrentHashMap<>();
						final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
						coordinates.put(one, one);
						linearity.put(one, coordinates);
						final VectorSpaceHomomorphism ans = new LinearSelfMapping(this, linearity) {

							@Override
							public Vector get(Element vec) {
								return (Vector) vec;
							}

							@Override
							public Scalar[][] getGenericMatrix() {
								if (genericMatrix == null) {
									genericMatrix = new Scalar[1][1];
									genericMatrix[0][0] = one;
								}
								return genericMatrix;
							}

						};
						multiplicationMatrix.put(one, ans);
					}
					return multiplicationMatrix;
				}

				@Override
				public Field getField() {
					return this;
				}

				@Override
				public List<Vector> genericBaseToList() {
					if (base == null) {
						base = new ArrayList<>();
						base.add(one);
					}
					return base;
				}

				@Override
				public Integer getDim() {
					return 1;
				}

				@Override
				public Vector nullVec() {
					return getNeutralElement();
				}

				List<Element> elementsAsList;

				@Override
				public List<Element> getElementsAsList() {
					if (elementsAsList == null) {
						elementsAsList = new ArrayList<>();
						elementsAsList.add(zero);
						elementsAsList.add(one);
					}
					return elementsAsList;
				}

			};
		}
		return binaries;
	}

	@Override
	public PrimeField getConstructedBinaries() {
		if (constructedBinaries == null) {
			final DiscreetSemiRing binaryGroup = new FiniteRing() {

				private FiniteMonoid muliplicativeMonoid;
				Map<Element, Map<Element, Element>> operationMap;

				Map<Element, Map<Element, Element>> multiplicationMap;
				private Map<Double, Element> elements;

				@Override
				public Element getNeutralElement() {
					return get(0.0);
				}

				@Override
				public Element operation(Element first, Element second) {
					final Element neutralElement = getNeutralElement();
					if (first.equals(neutralElement)) {
						return second;
					}
					if (second.equals(neutralElement)) {
						return first;
					}
					return neutralElement;
				}

				@Override
				public Element get(Double r) {
					FieldElement element = (FieldElement) getElements().get(r);
					if (element == null) {
						element = new FieldElement() {

							Double representant = r;

							@Override
							public Double getRepresentant() {
								return representant;
							}

							@Override
							public Map<Vector, Scalar> getCoordinates() {
								return null;
							}

							@Override
							public void setCoordinates(Map<Vector, Scalar> coordinates) {

							}

							@Override
							public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {

							}

							@Override
							public void setRepresentant(Double representant) {
								this.representant = representant;
							}

							@Override
							public String toString() {
								String ans = "false";
								if (representant == 0.0) {
									ans = "true";
								}
								return "constructed binary: " + ans;
							}

						};
						getElements().put(r, element);
					}
					return element;
				}

				@Override
				public boolean isUnit(Element element) {
					return element.equals(get(0.0));
				}

				@Override
				public Element getOne() {
					return get(1.0);
				}

				@Override
				public DiscreetMonoid getMuliplicativeMonoid() {
					muliplicativeMonoid = new FiniteMonoid() {

						@Override
						public Element get(Double representant) {
							return getOne();
						}

						@Override
						public Map<Double, Element> getElements() {
							return elements;
						}

						@Override
						public Map<Element, Map<Element, Element>> getOperationMap() {
							if (multiplicationMap == null) {
								multiplicationMap = new ConcurrentHashMap<>();
								final Map<Element, Element> entry = new HashMap<>();
								entry.put(getOne(), getOne());
								multiplicationMap.put(getOne(), entry);
							}
							return multiplicationMap;
						}

						@Override
						public Element getNeutralElement() {
							return get(1.0);
						}

					};
					return muliplicativeMonoid;
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (operationMap == null) {
						operationMap = new ConcurrentHashMap<>();
						final Map<Element, Element> entry = new HashMap<>();
						final Map<Element, Element> entry2 = new HashMap<>();
						entry.put(getNeutralElement(), getNeutralElement());
						entry.put(getOne(), getOne());
						entry2.put(getNeutralElement(), getOne());
						entry2.put(getOne(), getNeutralElement());
						operationMap.put(getOne(), entry);
					}
					return operationMap;
				}

				@Override
				public Map<Double, Element> getElements() {
					if (elements == null) {
						elements = new ConcurrentHashMap<>();
					}
					return elements;
				}

				@Override
				public Element getMinusOne() {
					return getOne();
				}

				@Override
				public String toString() {
					return "the group of binaries";
				}

			};

			final DiscreetSemiRing test = binaryGroup;
			final DiscreetDomain binaryDomain = GroupGenerator.getInstance().completeToDiscreetRing(test);
			constructedBinaries = GroupGenerator.getInstance().completeToDiscreetField(binaryDomain);
		}
		return constructedBinaries;
	}

	public void setBinaries(FinitePrimeField binaries) {
		this.binaries = binaries;
	}

	@Override
	public String toXml() {
		return "<GroupGenerator />\r";
	}

}
