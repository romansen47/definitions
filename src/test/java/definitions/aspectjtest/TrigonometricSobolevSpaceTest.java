package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class TrigonometricSobolevSpaceTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree;

	public int getSobolevDegree() {
		return this.sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		this.sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		this.setTrigonometricDegree(50);
		this.setSobolevDegree(3);

		this.setField(getRealLine());
		super.setUp();
		this.setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(),
				this.getTrigonometricDegree(), this.getSobolevDegree()));

	}

	@Test
	public void test1() {
		final Function staircaseFunction1Projection = this.getStaircaseFunction()
				.getProjection(this.getTrigonometricSpace());
		this.getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}

	@Test
	public void testOnContinuousFunction() throws Exception {
		Function h = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3842946945322219375L;

			@Override
			public Scalar value(Scalar input) {
				Double inputValue = input.getRepresentant();
				double abs = Math.abs(Math.sin(inputValue) * Math.cos(inputValue)-0.25);
				return RealLine.getInstance().get(abs);
			}
		};
		Function hProjection;
		for (int i = 0; i < 5; i++) {
			this.setSobolevDegree(i);
			this.setTrigonometricSpace(getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(),
					this.getTrigonometricDegree(), this.getSobolevDegree()));
			hProjection = h.getProjection(this.getTrigonometricSpace());
			h.plotCompare(-Math.PI, Math.PI, hProjection);
		}
	}

}
