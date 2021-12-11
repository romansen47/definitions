package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

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
	private Element four1;
	private Element four2;
	private Element minusFour;

	@Before
	public void createStructures() {
		one = AspectJTest.getIntegers().getOne();
		minusOne = AspectJTest.getIntegers().getInverseElement(AspectJTest.getIntegers().getOne());
		zero = AspectJTest.getIntegers().getNeutralElement();
		two = AspectJTest.getIntegers().addition(one, one);
		four1 = AspectJTest.getIntegers().multiplication(two, two);
		four2 = AspectJTest.getIntegers().addition(two, two);
		minusFour = AspectJTest.getIntegers().multiplication(four2, minusOne);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Test
	/*
	 * @TODO! This is still not working.
	 *
	 * This completes with respect to (+). I think this originally this should
	 * complete with respect to (*), since the multiplicatice monoid is used and
	 * completion is called rationals.
	 *
	 * The problem is the use of (+) for Double-representation in
	 * getRepresentant-method.
	 *
	 * getRepresentant should disappear soon.
	 */
	public void completionTest() {
		final DiscreetMonoid multiplicativeMonoid = GroupGenerator.getInstance().getNaturals().getMuliplicativeMonoid();
		final DiscreetGroup rationals = GroupGenerator.getInstance().completeToGroup(multiplicativeMonoid);
		final Element neutralElement = rationals.getNeutralElement();
		for (double i = 1; i < 10; i++) {
			final Element tmp = rationals.get(i);
			final Element inverse = rationals.getInverseElement(tmp);
			final Element product = rationals.operation(tmp, inverse);
			final boolean ans = neutralElement.equals(product);

			Assert.assertTrue(ans);
			System.out.println(
					"rational: " + tmp + ", inverse: " + inverse + ", product: " + product + "," + " check: " + ans);
		}
		final Element a = rationals.get(-5.);
		final Element b = rationals.get(15.);
		System.out.print(a + "+" + b + "=");
		System.out.println(rationals.operation(a, b));
	}

	@Test
	public void integersTest() {
		final boolean addSameAsMult = four1.equals(four2);
		final boolean sumIsZero = AspectJTest.getIntegers().addition(four1, minusFour).equals(zero);
		Assert.assertTrue(addSameAsMult);
		Assert.assertTrue(sumIsZero);
	}

	@Test
	public void fieldsTest() {
		final FieldElement newZero = AspectJTest.getRationals().getNeutralElement();
		final FieldElement newOne = (FieldElement) AspectJTest.getRationals().getMuliplicativeMonoid().getNeutralElement();
		final FieldElement newTwo = (FieldElement) AspectJTest.getRationals().addition(newOne, newOne);
		final FieldElement half = (FieldElement) AspectJTest.getRationals().getMuliplicativeMonoid().getInverseElement(newTwo);
		FieldElement var = (FieldElement) AspectJTest.getRationals().multiplication(newOne, newOne);
		FieldElement tmp;
		FieldElement debugTmp;
		for (int i = 1; i < 10; i++) {
			debugTmp = (FieldElement) AspectJTest.getRationals().multiplication(var, half);
			tmp = AspectJTest.getRationals().getInverseElement(debugTmp);
			var = (FieldElement) AspectJTest.getRationals().addition(var, tmp);
			System.out.println(i + ": " + var.toString());
		}
		System.out.println(AspectJTest.getRationals().getMuliplicativeMonoid().getInverseElement(newZero));
	}

	@Test
	public void testBinField() {

		final PrimeField field = GroupGenerator.getInstance().getBinaries();
		System.out.println(field.toXml());

		final Element zero = field.getZero();
		final Element one = field.getOne();
		System.out.println("zero " + zero.toString() + ", one " + one.toString());

		System.out.println("zero + zero: " + field.addition(zero, zero));
		System.out.println("zero + one: " + field.addition(zero, one));
		System.out.println("one + zero: " + field.addition(one, zero));
		System.out.println("one + one: " + field.addition(one, one) + "\r");

		System.out.println("zero * zero: " + field.multiplication(zero, zero));
		System.out.println("zero * one: " + field.multiplication(zero, one));
		System.out.println("one * zero: " + field.multiplication(one, zero));
		System.out.println("one * one: " + field.multiplication(one, one));

	}

}
