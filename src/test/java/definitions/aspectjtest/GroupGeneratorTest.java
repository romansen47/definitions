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

	/**
	 * We test if we can create the
	 */
	@Test
	public void completionTest() {
		final DiscreetGroup rationals = GroupGenerator.getInstance().completeToGroup(GroupGenerator.getInstance().getNaturals());
		final Element neutralElement = rationals.getNeutralElement();
		for (double i = 1; i < 10; i++) {
			final Element tmp = rationals.get(i);
			final Element inverse = rationals.getInverseElement(tmp);
			final Element addition = rationals.operation(tmp, inverse);
			final boolean ans = neutralElement.equals(addition);
			Assert.assertTrue(ans);
			System.out.println(
					"rational: " + tmp.toString() + ", inverse: " + inverse + ", addition: " + addition + "," + " check: " + ans);
		}
		final Element a = rationals.get(-5);
		final Element b = rationals.get(15);
		System.out.print(a + "+" + b + "= 10:");
		Element ans=rationals.operation(a, b);
		System.out.println(ans.equals(rationals.get(10)));
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
		for (int i = 1; i < 5; i++) {
			debugTmp = (FieldElement) AspectJTest.getRationals().multiplication(var, half);
			tmp = AspectJTest.getRationals().getInverseElement(debugTmp);
			var = (FieldElement) AspectJTest.getRationals().addition(var, tmp);
			logger.info(i + ": " + var.toString()); 
		}
		logger.info("Inverse of new zero is "+AspectJTest.getRationals().getMuliplicativeMonoid().getInverseElement(newZero));
		logger.info("for i==5 we get an error. to be fixed, e.g. by replacing (224,208) by (16,0) somewhere...");
	}

	@Test
	public void testBinField() {

		final PrimeField field = GroupGenerator.getInstance().getConstructedBinaries();
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
