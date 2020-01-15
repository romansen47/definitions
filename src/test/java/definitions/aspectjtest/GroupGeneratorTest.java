package definitions.aspectjtest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.impl.Naturals;

public class GroupGeneratorTest extends AspectJTest {

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	private Group group;
	private Element one;
	private Element minusOne;
	private Element zero;
	private Element two;
	private Element three;
	private Element four1;
	private Element four2;
	private Element minusFour;

	@Before
	public void createStructures() {
		one = getIntegers().getOne();
		minusOne = getIntegers().getInverseElement(getIntegers().getOne());
		zero = getIntegers().getNeutralElement();
		two = getIntegers().addition(one, one);
		three = getIntegers().addition(one, two);
		four1 = getIntegers().multiplication(two, two);
		four2 = getIntegers().addition(two, two);
		minusFour = getIntegers().multiplication(four2, minusOne);
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Test
	public void completionTest() {
		DiscreetMonoid NaturalsWithZero = new Naturals();
	}

	@Test
	public void completionTest2() {
		DiscreetMonoid multiplicativeMonoid = GroupGenerator.getInstance().getNaturals().getMuliplicativeMonoid();
		DiscreetGroup rationals = GroupGenerator.getInstance().completeToGroup(multiplicativeMonoid);
		Element neutralElement = rationals.getNeutralElement();
		for (double i = 1; i < 10; i++) {
			Element tmp = rationals.get(i);
			Element inverse = rationals.getInverseElement(tmp);
			Element product = rationals.operation(tmp, inverse);
			boolean ans = neutralElement.equals(product);

			Assert.assertTrue(ans);
			System.out.println(
					"rational: " + tmp + ", inverse: " + inverse + ", product: " + product + "," + " check: " + ans);
		}
		Element a = rationals.get(-5.);
		Element b = rationals.get(15.);
		System.out.print(a + "" + b + "=");
		System.out.println(rationals.operation(a, b));
	}

	@Test
	public void integersTest() {
		boolean addSameAsMult = four1.equals(four2);
		boolean sumIsZero = getIntegers().addition(four1, minusFour).equals(zero);
		System.out.println(one);
		System.out.println(minusOne);
		System.out.println(zero);
		System.out.println(two);
		System.out.println(three);
		System.out.println(four1);
		System.out.println(minusFour);
		Assert.assertTrue(addSameAsMult);
		Assert.assertTrue(sumIsZero);
		System.out.println(getIntegers().get(12.));
	}

	@Test
	public void fieldsTest() {
		FieldElement newZero = (FieldElement) getRationals().getNeutralElement();
		FieldElement newOne = (FieldElement) getRationals().getMuliplicativeMonoid().getNeutralElement();
		FieldElement newMinusOne = (FieldElement) getRationals()
				.getInverseElement(getRationals().getMuliplicativeMonoid().getNeutralElement());
		FieldElement newTwo = (FieldElement) getRationals().addition(newOne, newOne);
		FieldElement newMinusTwo = (FieldElement) getRationals().multiplication(newTwo, newMinusOne);
		FieldElement newFour = (FieldElement) getRationals().multiplication(newMinusTwo, newMinusTwo);
		FieldElement half = (FieldElement) ((Group) getRationals().getMuliplicativeMonoid()).getInverseElement(newTwo);
		FieldElement quarter = (FieldElement) ((Group) getRationals().getMuliplicativeMonoid())
				.getInverseElement(newFour);
		System.out.println(newZero.toString());
		System.out.println(newOne.toString());
		System.out.println(newTwo.toString());
		System.out.println(newFour.toString());
		System.out.println(quarter.toString());
		FieldElement var = (FieldElement) getRationals().multiplication(newOne, newOne);
		for (int i = 1; i < 10; i++) {
			var = (FieldElement) getRationals().addition(var,
					getRationals().getInverseElement(getRationals().multiplication(var, half)));
			System.out.println(i + ": " + var.toString());
		}
		System.out.println(((Group) getRationals().getMuliplicativeMonoid()).getInverseElement(newZero));
	}

	@Test
	public void testBinField() {
		DiscreetSemiRing binaryField = new FiniteRing() {
 
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
				return get((first.getRepresentant() + second.getRepresentant()) % 2.);
			}

			@Override
			public Element get(Double r) {
				FieldElement element = (FieldElement) getElements().get(r);
				if (element == null) {
					element = new FieldElement() {
						@Override
						public Double getRepresentant() {
							return r;
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
						if (multiplicationMap==null) {
							multiplicationMap=new ConcurrentHashMap<>();
							Map<Element, Element> entry=new HashMap<>();
							entry.put(getOne(),getOne());
							multiplicationMap.put(getOne(),entry);
						}
						return multiplicationMap;
					}

					@Override
					public Element operation(Element first, Element second) { 
						if (first==get(0.0) || second == get(0.0)){
							return get(0.0);
						}
						return get(1.0);
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
				if (operationMap==null) {
					operationMap=new ConcurrentHashMap<>();
					Map<Element, Element> entry=new HashMap<>(); 
					Map<Element, Element> entry2=new HashMap<>(); 
					entry.put(getNeutralElement(),getNeutralElement());
					entry.put(getOne(),getOne());
					entry2.put(getNeutralElement(),getOne());
					entry2.put(getOne(),getNeutralElement());
					operationMap.put(getOne(),entry);
				}
				return operationMap;
			}

			@Override
			public Map<Double, Element> getElements() { 
				return elements;
			}

			@Override
			public Element getMinusOne() { 
				return getOne();
			}

		};
		DiscreetSemiRing test= binaryField;
		DiscreetDomain bindom=GroupGenerator.getInstance().completeToDiscreetRing(test);
		PrimeField field=GroupGenerator.getInstance().completeToDiscreetField(bindom);
		int i=0;
		Element zero=field.getZero();
		Element one=field.getOne();
		System.out.println("zero "+zero.toString()+", one "+one.toString());
		
	}

}
