package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceMapping;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DynamicSystem;

public class DiscreetDynamicSystemTest extends AspectJTest {

	private Group timeSpace;
	private VectorSpace phaseSpace;
	private DynamicSystem dinamicSystem;
	private Vector startVector;
	private final int iterations = 21;
 
	@Before
	public void beforeTest() {
		this.timeSpace = getIntegers();// new Naturals(); 
	}

	/**
	 * @return the dinamicSystem
	 */
	public DynamicSystem getDinamicSystem() {
		return this.dinamicSystem;
	}

	/**
	 * @return the phasespace
	 */
	public VectorSpace getPhasespace() {
		return this.phaseSpace;
	}

	/**
	 * @return the timespace
	 */
	public Group getTimespace() {
		return this.timeSpace;
	}

	/**
	 * @param dinamicSystem the dinamicSystem to set
	 */
	public void setDinamicSystem(final DynamicSystem dinamicSystem) {
		this.dinamicSystem = dinamicSystem;
	}

	@Test
	public void testExponentialLaw() {
		this.phaseSpace = RealLine.getInstance();
		this.startVector = ((RealLine) phaseSpace).get(1.);

		this.dinamicSystem = new DynamicSystem() {

			@Override
			public VectorSpace getPhaseSpace() {
				return phaseSpace;
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return new VectorSpaceSelfMapping() {

					@Override
					public Element get(Element vec) {
						return phaseSpace.addition((Vector) vec, (Vector) vec);
					}

					@Override
					public VectorSpace getSource() {
						return phaseSpace;
					}

				};
			}

		};
		Vector vec = this.startVector;
		Element tmp;
		Element ans;
		VectorSpaceMapping evolutionOp;
		for (double i = 0; i < this.iterations; i++) {
			tmp = ((DiscreetMonoid) this.timeSpace).get(i);
			evolutionOp = this.dinamicSystem.getEvolutionOperator(tmp);
			getLogger().info("\r" + i + ": " + ((Vector) evolutionOp.get(vec)).toXml());
		}
	}

	@Test
	public void testFibbonacciLaw() {
		this.phaseSpace = ComplexPlane.getInstance();
		this.startVector = ((ComplexPlane) phaseSpace).get(1, 1);

		this.dinamicSystem = new DynamicSystem() {

			@Override
			public VectorSpace getPhaseSpace() {
				return phaseSpace;
			}

			@Override
			public VectorSpaceSelfMapping getDefiningMapping() {
				return new VectorSpaceSelfMapping() {

					@Override
					public Element get(Element vec) {
						return ((ComplexPlane) getPhaseSpace()).get(((Complex) vec).getImag().getDoubleValue(),
								RealLine.getInstance().addition(((Complex) vec).getReal(), ((Complex) vec).getImag()).getDoubleValue());
					}

					@Override
					public VectorSpace getSource() {
						return phaseSpace;
					}

				};
			}

		};
		Vector vec = this.startVector;
		Element tmp;
		Element ans;
		VectorSpaceMapping evolutionOp;
		for (double i = 0; i < this.iterations; i++) {
			tmp = ((DiscreetMonoid) this.timeSpace).get(i);
			evolutionOp = this.dinamicSystem.getEvolutionOperator(tmp);
			Complex toComplex = (Complex) evolutionOp.get(vec);
			Real real=(Real) toComplex.getReal();
			Real imag=(Real) toComplex.getImag();
			String comp=real.toString();
			if (imag.getDoubleValue()>0) {
				comp+=" + "+imag.getDoubleValue()+"*i";
			}
			if (imag.getDoubleValue()<0) {
				comp+=" - "+Math.abs(imag.getDoubleValue())+"*i";
			}
			System.out.println(i + ": " + comp);
		}

	}

}
