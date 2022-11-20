package definitions.structures.abstr.algebra.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class GroupGenerator implements IGroupGenerator, XmlPrintable, Unweavable {

	@Autowired
	private Naturals naturals;

	private DiscreetDomain integers;

	private PrimeField rationals;

	private FinitePrimeField binaries;

	private PrimeField constructedBinaries;

	Map<Integer, FiniteRing> map = new ConcurrentHashMap<>();

	@Override
	public DiscreetSemiRing getNaturals() {
		return this.naturals;
	}

	@Override
	public DiscreetDomain getIntegers() {
		if (this.integers == null) {
			this.integers = this.completeToDiscreetRing(this.getNaturals());
		}
		return this.integers;
	}

	/**
	 * @return the rationals
	 */
	@Override
	public PrimeField getRationals() {
		if (this.rationals == null) {
			this.rationals = this.completeToDiscreetField(this.integers);
		}
		return this.rationals;
	}

	@Override
	public FinitePrimeField getBinaries() {
		if (this.binaries == null) {
			this.binaries = new FinitePrimeField() {
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
						if (this.coordinates == null) {
							this.coordinates = new ConcurrentHashMap<>();
							if (this == one) {
								this.coordinates.put(one, one);
							} else {
								this.coordinates.put(one, zero);
							}
						}
						return this.coordinates;
					}

					@Override
					public void setCoordinates(Map<Vector, Scalar> coordinates) {
						this.coordinates = coordinates;
					}

					@Override
					public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
					}

					@Override
					public String toString() {
						return "<binary>" + this.value + "</binary>";
					}

				}

				private final DiscreetGroup multiplicativeMonoid = this.getMuliplicativeMonoid();

				@Override
				public DiscreetGroup getMuliplicativeMonoid() {
					return this.multiplicativeMonoid;
				}

				private final Binary zero = new Binary(false);
				private final Binary one = new Binary(true);
				private List<Vector> base;
				private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix;
				private Map<Element, Map<Element, Element>> operationMap;
				private Map<Double, Element> elements;

				@Override
				public Map<Double, Element> getElements() {
					if (this.elements == null) {
						this.elements = new ConcurrentHashMap<>();
						this.elements.put(0d, this.zero);
						this.elements.put(1d, this.one);
					}
					return this.elements;
				}

				@Override
				public Binary getOne() {
					return this.one;
				}

				@Override
				public Binary getZero() {
					return this.zero;
				}

				@Override
				public Element getMinusOne() {
					return this.one;
				}

				@Override
				public void setMultiplicationMatrix(Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
					this.multiplicationMatrix = multiplicationMatrix;
				}

				@Override
				public Vector getProjection(Vector vec) {
					return ((FiniteVector) vec).getProjection(this);
				}

				@Override
				public EuclideanSpace getDualSpace() {
					return GroupGenerator.this.binaries;
				}

				@Override
				public Vector stretch(Vector vec, Scalar r) {
					return this.multiplication(vec, r);
				}

				@Override
				public List<Vector> getOrthonormalBase(List<Vector> base) {
					return this.base;
				}

				@Override
				public boolean contains(Vector vec) {
					return (vec == this.one) || (vec == this.zero);
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (this.operationMap == null) {
						this.operationMap = new ConcurrentHashMap<>();
						final Map<Element, Element> zeroMap = new ConcurrentHashMap<>();
						final Map<Element, Element> oneMap = new ConcurrentHashMap<>();
						zeroMap.put(this.zero, this.zero);
						zeroMap.put(this.one, this.one);
						oneMap.put(this.zero, this.one);
						oneMap.put(this.one, this.zero);
						this.operationMap.put(this.zero, zeroMap);
						this.operationMap.put(this.one, oneMap);
					}
					return this.operationMap;
				}

				@Override
				public FiniteVector addition(Vector a, Vector b) {
					final boolean tmp = a != b;
					final Vector ans = this.get(tmp);
					return (FiniteVector) ans;
				}

				@Override
				public Vector multiplication(Element a, Element b) {
					final boolean ans = ((Binary) a).value && ((Binary) b).value;
					return this.get(ans);
				}

				private Vector get(boolean b) {
					Binary ans = this.zero;
					if (b) {
						ans = this.one;
					}
					return ans;
				}

				@Override
				public FieldElement getNeutralElement() {
					return this.zero;
				}

				@Override
				public Element get(Number representant) {
					boolean ans = false;
					if (representant.intValue() != 0) {
						ans = true;
					}
					return this.get(ans);
				}

				@Override
				public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
					if (this.multiplicationMatrix == null) {
						this.multiplicationMatrix = new ConcurrentHashMap<>();
						final Map<Vector, Map<Vector, Scalar>> linearity = new ConcurrentHashMap<>();
						final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
						coordinates.put(this.one, this.one);
						linearity.put(this.one, coordinates);
						final VectorSpaceHomomorphism ans = new LinearSelfMapping(this, linearity) {
							@Override
							public Vector get(Element vec) {
								return (Vector) vec;
							}

							@Override
							public Scalar[][] getGenericMatrix() {
								if (this.genericMatrix == null) {
									this.genericMatrix = new Scalar[1][1];
									this.genericMatrix[0][0] = one;
								}
								return this.genericMatrix;
							}

						};
						this.multiplicationMatrix.put(this.one, ans);
					}
					return this.multiplicationMatrix;
				}

				@Override
				public Field getField() {
					return this;
				}

				@Override
				public List<Vector> genericBaseToList() {
					if (this.base == null) {
						this.base = new ArrayList<>();
						this.base.add(this.one);
					}
					return this.base;
				}

				@Override
				public Integer getDim() {
					return 1;
				}

				@Override
				public Vector nullVec() {
					return this.getNeutralElement();
				}

				List<Element> elementsAsList;

				@Override
				public List<Element> getElementsAsList() {
					if (this.elementsAsList == null) {
						this.elementsAsList = new ArrayList<>();
						this.elementsAsList.add(this.zero);
						this.elementsAsList.add(this.one);
					}
					return this.elementsAsList;
				}

				@Override
				public FieldElement operation(Element first, Element second) {
					return (FieldElement) this.getOperationMap().get(first).get(second);
				}

			};
		}
		return this.binaries;
	}

	@Override
	public PrimeField getConstructedBinaries() {
		if (this.constructedBinaries == null) {
			final DiscreetSemiRing binaryGroup = new FiniteRing() {

				FiniteMonoid muliplicativeMonoid;
				Map<Element, Map<Element, Element>> operationMap;

				Map<Element, Map<Element, Element>> multiplicationMap;
				private Map<Double, Element> elements;

				@Override
				public Element getNeutralElement() {
					return this.get(0.0);
				}

				@Override
				public Element operation(Element first, Element second) {
					final Element neutralElement = this.getNeutralElement();
					if (first.equals(neutralElement)) {
						return second;
					}
					if (second.equals(neutralElement)) {
						return first;
					}
					return neutralElement;
				}

				@Override
				public Element get(Number r) {
					FieldElement element = (FieldElement) this.getElements().get(r);
					if (element == null) {
						element = new FieldElement() {
							@Override
							public Map<Vector, Scalar> getCoordinates() {
								return null;
							}

							@Override
							public void setCoordinates(Map<Vector, Scalar> coordinates) {
								// no coordinates will be used here
							}

							@Override
							public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
								// no coordinates will be used here
							}

							@Override
							public String toString() {
								return r.toString();
							}

						};
						this.getElements().put(r.doubleValue(), element);
					}
					return element;
				}

				@Override
				public boolean isUnit(Element element) {
					return !element.equals(this.get(0.0));
				}

				@Override
				public Element getOne() {
					return this.get(1.0);
				}

				@Override
				public DiscreetMonoid getMuliplicativeMonoid() {
					this.muliplicativeMonoid = new FiniteMonoid() {

						@Override
						public Element get(Number representant) {
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
								final Map<Element, Element> entry = new ConcurrentHashMap<>();
								entry.put(getOne(), getOne());
								multiplicationMap.put(getOne(), entry);
							}
							return multiplicationMap;
						}

						@Override
						public Element getNeutralElement() {
							return getOne();
						}

					};
					return this.muliplicativeMonoid;
				}

				@Override
				public Map<Element, Map<Element, Element>> getOperationMap() {
					if (this.operationMap == null) {
						this.operationMap = new ConcurrentHashMap<>();
						final Map<Element, Element> entry = new ConcurrentHashMap<>();
						final Map<Element, Element> entry2 = new ConcurrentHashMap<>();
						entry.put(this.getNeutralElement(), this.getNeutralElement());
						entry.put(this.getOne(), this.getOne());
						entry2.put(this.getNeutralElement(), this.getOne());
						entry2.put(this.getOne(), this.getNeutralElement());
						this.operationMap.put(this.getOne(), entry);
					}
					return this.operationMap;
				}

				@Override
				public Map<Double, Element> getElements() {
					if (this.elements == null) {
						this.elements = new ConcurrentHashMap<>();
					}
					return this.elements;
				}

				@Override
				public Element getMinusOne() {
					return this.getOne();
				}

				@Override
				public String toString() {
					return "the group of binaries";
				}

			};

			final DiscreetSemiRing test = binaryGroup;
			binaryGroup.getNeutralElement();
			binaryGroup.getOne();
			binaryGroup.getMuliplicativeMonoid();
			// Hier liegt das Problem! Die Vervollständigung bezieht sich hier auf die
			// Addition... Historisch bedingter Fehler: Hier sollte der Halbring der
			// Natürlichen Zahlen mit 0, welcher ... ( was?? )
			final DiscreetDomain binaryDomain = completeToDiscreetRing(test);
			binaryDomain.getNeutralElement();
			binaryDomain.getOne();
			binaryDomain.getMuliplicativeMonoid();
			this.constructedBinaries = completeToDiscreetField(binaryDomain);
			this.constructedBinaries.getNeutralElement();
			this.constructedBinaries.getOne();
			this.constructedBinaries.getMuliplicativeMonoid();
		}
		return this.constructedBinaries;
	}

	public void setBinaries(FinitePrimeField binaries) {
		this.binaries = binaries;
	}

	@Override
	public String toXml() {
		return "<GroupGenerator />\r";
	}

}
