package definitions.aspectjtest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.impl.GroupGenerator;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroup;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.impl.Naturals;

public class GroupGeneratorTest extends AspectJTest {

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	private Group group;

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
		DiscreetMonoid multiplicatioveMonoid = new DiscreetMonoid() {

			@Override
			public Element getNeutralElement() {
				return get(1);
			}

			@Override
			public Element operation(Element first, Element second) {
				return get(first.getRepresentant() * second.getRepresentant());
			}

			@Override
			public Element get(Integer representant) {
				return new Element() {
					@Override
					public Integer getRepresentant() {
						return (Integer) Math.abs(representant);
					}

					@Override
					public boolean equals(Object other) {
						if (other instanceof Element) {
							return representant == ((Element) other).getRepresentant();
						}
						return false;
					}
				};
			}

		};
		DiscreetGroup rationals = GroupGenerator.getInstance().completion(multiplicatioveMonoid);
		Element neutralElement = rationals.getNeutralElement();
		for (int i = 1; i < 10; i++) {
			Element tmp = rationals.get(i);
			Element inverse = rationals.getInverseElement(tmp);
			Element product = rationals.operation(tmp, inverse);
			boolean ans = rationals.getNeutralElement().equals(product);
			System.out.println(
					"rational: " + tmp + ", inverse: " + inverse + ", product: " + product + "," + " check: " + ans);
		}
		Element a = rationals.get(-5);
		Element b = rationals.get(15);
		System.out.println(rationals.operation(a, b));
	}

}
