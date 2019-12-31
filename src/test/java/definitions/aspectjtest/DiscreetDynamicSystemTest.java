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
		timeSpace = Naturals.getInstance();
		phaseSpace = ComplexPlane.getInstance();
		startVector = ((EuclideanSpace) phaseSpace).genericBaseToList().get(0);
		Function evolutionOperator = new GenericFunction() {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return (Field) ComplexPlane.getInstance();
			}

			@Override
			public Scalar value(Scalar input) {
				Scalar factor = RealLine.getInstance().get(-0.5);
				return (Scalar) phaseSpace.stretch(input, factor);
			}

			@Override
			public String toXml() {
				return "the evolution operator of the system.";
			}
		};
		dinamicSystem = new DiscreetDynamicSystem(phaseSpace, evolutionOperator) {
		};
	}

	@Test
	public void test() {
		Vector vec = startVector;
		for (int i = 0; i < iterations; i++) {
			MonoidElement tmp = ((DiscreetMonoid) Naturals.getInstance()).get(i);
			Function evolutionOp = dinamicSystem.getEvolutionOperator(tmp);
			vec = evolutionOp.value((Scalar) vec);
			getLogger().info(i + ": " + vec.toString());
		}
	}

	/**
	 * @return the timespace
	 */
	public OrderedMonoid getTimespace() {
		return timeSpace;
	}

	/**
	 * @return the phasespace
	 */
	public VectorSpace getPhasespace() {
		return phaseSpace;
	}

	/**
	 * @return the dinamicSystem
	 */
	public DynamicSystem getDinamicSystem() {
		return dinamicSystem;
	}

	/**
	 * @param dinamicSystem the dinamicSystem to set
	 */
	public void setDinamicSystem(DynamicSystem dinamicSystem) {
		this.dinamicSystem = dinamicSystem;
	}

}
