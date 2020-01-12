package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.impl.Naturals;

public class GroupGeneratorTest extends AspectJTest {

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	private Group group;
	private DiscreetSemiRing naturals = new Naturals();
	private DiscreetDomain integers = GroupGenerator.getInstance().completeToDiscreetRing(naturals);
	private PrimeField rationals = GroupGenerator.getInstance().completeToDiscreetField(integers);
	private Element one = integers.getOne();
	private Element minusOne = integers.getInverseElement(integers.getOne());
	private Element zero = integers.getNeutralElement();
	private Element two = integers.addition(one, one);
	private Element three = integers.addition(one, two);
	private Element four1 = integers.multiplication(two, two);
	private Element four2 = integers.addition(two, two);
	private Element minusFour = integers.multiplication(four2, minusOne);
	
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
		boolean sumIsZero = integers.addition(four1, minusFour).equals(zero);
		System.out.println(one);
		System.out.println(minusOne);
		System.out.println(zero);
		System.out.println(two);
		System.out.println(three);
		System.out.println(four1);
		System.out.println(minusFour);
		Assert.assertTrue(addSameAsMult);
		Assert.assertTrue(sumIsZero);
		System.out.println(integers.get(12.));
	}
	
	@Test
	public void fieldsTest() {
		FieldElement newZero=(FieldElement) rationals.getNeutralElement();
		FieldElement newOne=(FieldElement) rationals.getMuliplicativeMonoid().getNeutralElement();
		FieldElement newMinusOne=(FieldElement) rationals.getInverseElement(rationals.getMuliplicativeMonoid().getNeutralElement());
		FieldElement newTwo=(FieldElement) rationals.addition(newOne,newOne);
		FieldElement newMinusTwo=(FieldElement) rationals.multiplication(newTwo,newMinusOne);
		FieldElement newFour=(FieldElement) rationals.multiplication(newMinusTwo,newMinusTwo);
		FieldElement half=(FieldElement) ((Group)rationals.getMuliplicativeMonoid()).getInverseElement(newTwo);
		FieldElement quarter=(FieldElement) ((Group)rationals.getMuliplicativeMonoid()).getInverseElement(newFour);
		System.out.println(newZero.toString());
		System.out.println(newOne.toString());
		System.out.println(newTwo.toString());
		System.out.println(newFour.toString());
		System.out.println(quarter.toString()); 
		FieldElement var=(FieldElement) rationals.multiplication(newOne,newOne);
		for (int i=1;i<10;i++) {
			var=(FieldElement) rationals.addition(var,rationals.getInverseElement(rationals.multiplication(var,half)));
			System.out.println(i+": "+var.toString());
		}
	}

}
