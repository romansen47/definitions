package definitions.aspectjtest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.rings.impl.Integers;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class IntegersTest extends AspectJTest {

	private Domain integers;
	private RingElement zero;
	private RingElement one;
	private RingElement three;
	private RingElement minusOne;
	RingElement five;
	RingElement six;
	private int maxInt = 1000;

	Function f;

	List<Integer> primes = new ArrayList<>();

	@Before
	public void before() {

		this.integers = Integers.getInstance();
		this.zero = (RingElement) this.integers.getNeutralElement();
		this.one = ((Integers) this.integers).get(1);
		this.three = ((Integers) this.integers).get(3);
		this.minusOne = ((Integers) this.integers).get(-1);
		this.five = ((Integers) this.integers).get(5);
		this.six = ((Integers) this.integers).get(6);
		this.f = new Function() {
			private static final long serialVersionUID = 1L;

			@Override
			public Map<Vector, Scalar> getCoordinates() {
				return null;
			}

			@Override
			public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
				return null;
			}

			@Override
			public void setCoordinates(final Map<Vector, Scalar> coordinates) {
			}

			@Override
			public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
			}

			@Override
			public Scalar value(final Scalar input) {
				int ans = 1;
				for (int i = 0; i < input.getValue(); i++) {
					if (IntegersTest.this.integers
							.isPrimeElement((RingElement) ((DiscreetGroup) IntegersTest.this.integers).get(i))) {
						ans++;
					}
				}
				return RealLine.getInstance().get(ans);
			}

		};

	}

	protected RingElement getFive() {
		return this.five;
	}

	protected Ring getIntegers() {
		return this.integers;
	}

	protected int getMaxInt() {
		return this.maxInt;
	}

	protected RingElement getMinusOne() {
		return this.minusOne;
	}

	protected RingElement getOne() {
		return this.one;
	}

	protected RingElement getSix() {
		return this.six;
	}

	protected RingElement getThree() {
		return this.three;
	}

	protected RingElement getZero() {
		return this.zero;
	}

	protected void setFive(final RingElement five) {
		this.five = five;
	}

	protected void setIntegers(final Ring integers) {
		this.integers = (Domain) integers;
	}

	protected void setMaxInt(final int maxInt) {
		this.maxInt = maxInt;
	}

	protected void setMinusOne(final RingElement minusOne) {
		this.minusOne = minusOne;
	}

	protected void setOne(final RingElement one) {
		this.one = one;
	}

	protected void setSix(final RingElement six) {
		this.six = six;
	}

	protected void setThree(final RingElement three) {
		this.three = three;
	}

	protected void setZero(final RingElement zero) {
		this.zero = zero;
	}

	@Test
	public void showPrimeCountingFunction() {
		this.f.plot(0, this.maxInt);
	}

	@Test
	public void testDivides() {
		Assert.assertTrue(!this.integers.divides(this.three, this.five));
		Assert.assertTrue(this.integers.divides(this.three, this.six));
	}

	@Test
	public void testGetIdentityElement() {
		Assert.assertTrue(this.integers.getNeutralElement().equals(this.zero));
	}

	@Test
	public void testGetInverseElement() {
		Assert.assertTrue(this.integers.getInverseElement(this.zero).equals(this.zero));
		Assert.assertTrue(this.integers.getInverseElement(this.one).equals(this.minusOne));
	}

	@Test
	public void testGetOrder() {
		Assert.assertTrue(this.integers.getOrder() == null);
	}

	@Test
	public void testIsPrimeElement() {
		for (int i = 0; i < this.maxInt; i++) {
			getLogger().info(((Integers) this.integers).get(i) + " is prime: "
					+ this.integers.isPrimeElement(((Integers) this.integers).get(i)));
		}
	}

	@Test
	public void testIsUnit() {
		Assert.assertTrue(!this.integers.isUnit(this.zero));
		Assert.assertTrue(this.integers.isUnit(this.one));
		Assert.assertTrue(this.integers.isUnit(this.minusOne));
		Assert.assertTrue(!this.integers.isUnit(this.five));
	}

}
