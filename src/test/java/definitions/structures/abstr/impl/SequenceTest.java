package definitions.structures.abstr.impl;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import definitions.structures.abstr.Sequence;
import definitions.structures.abstr.Vector;
import definitions.structures.field.scalar.Real;

public class SequenceTest {

	Vector sequence=new Sequence(){

		@Override
		public Vector getValue(int n) {
			return new Real(1./Math.max(0, Math.abs(n)));
		}
		
	};
	
	@Test
	public void testGetValue() {
		int input=-5000+new Random().nextInt(10000);
		Vector ans=((Sequence) sequence).getValue(input);
		Assert.assertTrue(((Real)ans).getValue()==1./input || ((Real)ans).getValue()==-1./input);
	}

}
