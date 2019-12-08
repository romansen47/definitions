/**
 * 
 */
package definitions.xmltest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * @author BAU12350
 *
 */
public class QuaternionFunctionSpaceTest extends AspectJTest {

	private Field f;

	private List<Vector> base = new ArrayList<>();
	private FunctionSpace space;

	private Function alpha;
	private Function beta;
	private Function gamma;

	@Before
	public void before() {

		f = (Field) QuaternionSpace.getInstance();

		alpha = new GenericFunction() {
			private static final long serialVersionUID = 6698998256903151087L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) getField().add(f.getOne(),
						new Quaternion(val, -val, 0.1 * Math.cos(val), 0.1 * Math.sin(val)));
				return (Scalar) getField().normalize(tmp);
			}

			@Override
			public Field getField() {
				return (Field) QuaternionSpace.getInstance();
			}
		};
		beta = new GenericFunction() {
			private static final long serialVersionUID = -2624612868740391242L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) getField().add(getField().getOne(),
						new Quaternion(val / 2, val / 2, 0.1 * Math.sin(val), 0.1 * Math.cos(val)));
				return (Scalar) getField().normalize(tmp);
			}

			@Override
			public Field getField() {
				return (Field) QuaternionSpace.getInstance();
			}
		};
		gamma = new GenericFunction() {
			private static final long serialVersionUID = -6598973940477311007L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) getField().add(getField().getOne(),
						new Quaternion(val, -val, val, 1 - val));
				if (Math.abs(((Quaternion) input).getReal().getValue()) < 1.e-5) {
					return (Scalar) getField().stretch(input, RealLine.getInstance().get(1.e5));
				}
				return (Scalar) getField().normalize(tmp);
			}

			@Override
			public Field getField() {
				return (Field) QuaternionSpace.getInstance();
			}
		};

		this.base.add(alpha);
		this.base.add(beta);

		if (this.getSpace() == null) {
			this.setSpace(new FiniteDimensionalFunctionSpace(f, this.base, -1, 1, true));
		}
	}

	@Test
	public void testAlpha() throws IOException {
		alpha.plot(-Math.PI, Math.PI);
		getGenerator().saveCoordinateSpaces();
	}

	@Test
	public void testBeta() throws IOException {
		beta.plot(-Math.PI, Math.PI);
		getGenerator().saveCoordinateSpaces();
	}

	@Test
	public void testGamma() throws IOException {
		gamma.plot(-Math.PI, Math.PI);
		getGenerator().saveCoordinateSpaces();
	}

	public FunctionSpace getSpace() {
		return space;
	}

	public void setSpace(FunctionSpace space) {
		this.space = space;
	}

}
