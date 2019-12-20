package definitions.structures.abstr.groups.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class IntegersTest extends AspectJTest {

	private Ring integers;
	private RingElement zero;
	private RingElement one;
	private RingElement three;
	private RingElement minusOne;
	RingElement five;
	RingElement six;
	private int maxInt = 300;

	Function f;
	
	List<Integer> primes=new ArrayList<>();
	
	@Before
	public void before() {
		
		integers = Integers.getInstance();
		zero = (RingElement) integers.getIdentityElement();
		one = (RingElement) ((Integers) integers).get(1);
		three = (RingElement) ((Integers) integers).get(3);
		minusOne = (RingElement) ((Integers) integers).get(-1);
		five = (RingElement) ((Integers) integers).get(5);
		six = (RingElement) ((Integers) integers).get(6);
		f = new Function() { 
			private static final long serialVersionUID = 1L;
			
			@Override
			public Scalar value(Scalar input) { 
				int ans = 1;
				for (int i=0;i<input.getValue();i++) {
					if (integers.isPrimeElement((RingElement) ((DiscreteGroup)integers).get(i))) {
						ans++;
					}
				}
				return RealLine.getInstance().get(ans);
			}
			
			@Override
			public Map<Vector, Scalar> getCoordinates() { 
				return null;
			}
 
			@Override
			public void setCoordinates(Map<Vector, Scalar> coordinates) { 
			}

			@Override
			public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() { 
				return null;
			}

		};
		
	}

	@Test
	public void testGetOrder() {
		Assert.assertTrue(integers.getOrder() == null);
	}

	@Test
	public void testGetIdentityElement() {
		Assert.assertTrue(integers.getIdentityElement().equals(zero));
	}

	@Test
	public void testGetInverseElement() {
		Assert.assertTrue(integers.getInverseElement(zero).equals(zero));
		Assert.assertTrue(integers.getInverseElement(one).equals(minusOne));
	}

	@Test
	public void testIsPrimeElement() {
		for (int i = 0; i < maxInt; i++) {
			getLogger().info(((Integers) integers).get(i) + " is prime: "
					+ integers.isPrimeElement((RingElement) ((Integers) integers).get(i)));
		}
	}

	@Test
	public void testIsUnit() {
		Assert.assertTrue(!integers.isUnit(zero));
		Assert.assertTrue(integers.isUnit(one));
		Assert.assertTrue(integers.isUnit(minusOne));
		Assert.assertTrue(!integers.isUnit(five));
	}

	@Test
	public void testDivides() {
		Assert.assertTrue(!integers.divides(three, five));
		Assert.assertTrue(integers.divides(three, six));
	}

	
	@Test
	public void showPrimeCountingFunction() {
		f.plot(0, maxInt);
	}

	protected Ring getIntegers() {
		return integers;
	}

	protected void setIntegers(Ring integers) {
		this.integers = integers;
	}

	protected RingElement getZero() {
		return zero;
	}

	protected void setZero(RingElement zero) {
		this.zero = zero;
	}

	protected RingElement getOne() {
		return one;
	}

	protected void setOne(RingElement one) {
		this.one = one;
	}

	protected RingElement getThree() {
		return three;
	}

	protected void setThree(RingElement three) {
		this.three = three;
	}

	protected RingElement getMinusOne() {
		return minusOne;
	}

	protected void setMinusOne(RingElement minusOne) {
		this.minusOne = minusOne;
	}

	protected RingElement getFive() {
		return five;
	}

	protected void setFive(RingElement five) {
		this.five = five;
	}

	protected RingElement getSix() {
		return six;
	}

	protected void setSix(RingElement six) {
		this.six = six;
	}

	protected int getMaxInt() {
		return maxInt;
	}

	protected void setMaxInt(int maxInt) {
		this.maxInt = maxInt;
	}

}
