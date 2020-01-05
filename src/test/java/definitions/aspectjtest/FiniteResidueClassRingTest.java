package definitions.aspectjtest;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.impl.GroupGenerator;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class FiniteResidueClassRingTest extends AspectJTest {

	final static int index = 5;

	static Map<Integer, Ring> finiteResidueClassRingList = new HashMap<>();

	static Map<Integer, Long> times = new HashMap<>();

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	@Test
	public synchronized void test() {
		long time;
		for (int i = 1; i < index; i++) {
			time = System.currentTimeMillis();
			final Ring r = getGenerator().getGroupGenerator().getFiniteResidueClassRing(i);
			time = System.currentTimeMillis() - time;
			times.put(i, time);
			getLogger().info(r.toString());
			System.out.println("ring is a field: " + (r instanceof Field));
			((FiniteResidueClassRing) r).print();
		}
		for (int i = 0; i < index - 1; i++) {
			System.out.println(i + ": " + times.get(i) + " milli seconds");
		}
		final Function fun = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8051636753712354251L;

			@Override
			public Field getField() {
				return RealLine.getInstance();
			}

			@Override
			public Scalar value(final Scalar input) {
				final int i = ((int) (input.getValue() - (input.getValue() % 1.0)));
				return RealLine.getInstance().get(times.get(i));
			}
		};
		fun.plot(1, index - 1);
	}

}
