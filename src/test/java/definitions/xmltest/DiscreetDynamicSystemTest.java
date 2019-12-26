package definitions.xmltest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.groups.OrderedMonoid;
import definitions.structures.abstr.groups.impl.DiscreetMonoid;
import definitions.structures.abstr.groups.impl.Naturals;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class DiscreetDynamicSystemTest extends AspectJTest {

	private OrderedMonoid timeSpace;
	private VectorSpace phaseSpace;
	private DynamicSystem dinamicSystem;
	private Vector startVector;

	@Before
	public void beforeTest() {
		timeSpace = Naturals.getInstance();
		phaseSpace = ComplexPlane.getInstance();
		startVector = ((EuclideanSpace) phaseSpace).genericBaseToList().get(0);
		dinamicSystem = new DynamicSystem() {

			@Override
			public OrderedMonoid getTimeSpace() {
				return timeSpace;
			}

			@Override
			public VectorSpace getPhaseSpace() {
				return phaseSpace;
			}

			@Override
			public Function getEvolutionOperator(MonoidElement element) {
				return new GenericFunction() {
					private static final long serialVersionUID = 1L;

					@Override
					public Field getField() {
						return (Field) ComplexPlane.getInstance();
					}

					@Override
					public Scalar value(Scalar input) {
						return (Scalar) phaseSpace.stretch(input, RealLine.getInstance().get(-0.5));
					}
				};
			}
		};
	}
	
	@Test
	public void test() {
		Vector vec = startVector;
		for (int i = 0; i < 100; i++) {
			vec = dinamicSystem.getEvolutionOperator(((DiscreetMonoid) Naturals.getInstance()).get(i))
					.value((Scalar) vec);
			getLogger().info(i+": " + vec.toString());
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
