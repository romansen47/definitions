package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.semigroups.impl.Naturals;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DiscreetDynamicSystem;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class DiscreetDynamicSystemTest extends AspectJTest {

	private OrderedMonoid timeSpace;
	private VectorSpace phaseSpace;
	private DynamicSystem dinamicSystem;
	private Vector startVector;
	private final int iterations = 21;

	@Before
	public void beforeTest() {
		this.timeSpace = Naturals.getInstance();
		this.phaseSpace = ComplexPlane.getInstance();
		this.startVector = ((EuclideanSpace) this.phaseSpace).genericBaseToList().get(0);
		final Function evolutionOperator = new GenericFunction() {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return (Field) ComplexPlane.getInstance();
			}

			@Override
			public String toXml() {
				return "the evolution operator of the system.";
			}

			@Override
			public Scalar value(final Scalar input) {
				final Scalar factor = RealLine.getInstance().get(-0.5);
				return (Scalar) DiscreetDynamicSystemTest.this.phaseSpace.stretch(input, factor);
			}
		};
		this.dinamicSystem = new DiscreetDynamicSystem(this.phaseSpace, evolutionOperator) {
		};
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
	public OrderedMonoid getTimespace() {
		return this.timeSpace;
	}

	/**
	 * @param dinamicSystem the dinamicSystem to set
	 */
	public void setDinamicSystem(final DynamicSystem dinamicSystem) {
		this.dinamicSystem = dinamicSystem;
	}

	@Test
	public void test() {
		Vector vec = this.startVector;
		for (int i = 0; i < this.iterations; i++) {
			final MonoidElement tmp = ((DiscreetMonoid) Naturals.getInstance()).get(i);
			final Function evolutionOp = this.dinamicSystem.getEvolutionOperator(tmp);
			vec = evolutionOp.value((Scalar) vec);
			getLogger().info(i + ": " + vec.toString());
		}
	}

}
