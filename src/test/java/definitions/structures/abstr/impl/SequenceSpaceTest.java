package definitions.structures.abstr.impl;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

public class SequenceSpaceTest {

	final VectorSpace functionSpace=SpaceGenerator.getInstance().getTrigonometricSpace(1);
	
	final Homomorphism identity=new Identity(functionSpace) {

		@Override
		public Map<Vector, Map<Vector, Double>> getLinearity() {
			return null;
		}

		@Override
		public VectorSpace getSource() {
			return functionSpace;
		}

		@Override
		public VectorSpace getTarget() {
			return functionSpace;
		}

		@Override
		public double[][] getGenericMatrix() {
			return null;
		}
		
	};
	
	VectorSpace target=new SequenceSpace() {

		@Override
		public VectorSpace getTargetSpace() {
			return functionSpace;
		}
		
	};

	@Test
	public void testContains() {
	}

	@Test
	public void testNullVec() {
	}

	@Test
	public void testAdd() {
	}

	@Test
	public void testStretch() {
	}

}
