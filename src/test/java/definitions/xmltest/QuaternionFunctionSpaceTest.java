/**
 * 
 */
package definitions.xmltest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import definitions.SpringConfiguration;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.BinaryField;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author BAU12350
 *
 */
public class QuaternionFunctionSpaceTest {

	private SpringConfiguration springConfiguration;

	private Field f;

	List<Vector> base = new ArrayList<>();
	FunctionSpace space;

	static Function alpha;
	static Function beta;
	static Function gamma;

	@Before
	public void prepare() {
		springConfiguration = new SpringConfiguration();
		f = (Field) QuaternionSpace.getInstance();
//		springConfiguration.getApplicationContext();//
//				.getBean("definitions.structures.abstr.fields.impl.QuaternionSpace");// (Field)
																						// 

		alpha = new GenericFunction() {
			private static final long serialVersionUID = 6698998256903151087L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(f.getOne(),
						new Quaternion(val, -val, 0.1 * Math.cos(val), 0.1 * Math.sin(val)));
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		beta = new GenericFunction() {
			private static final long serialVersionUID = -2624612868740391242L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(f.getOne(),
						new Quaternion(val / 2, val / 2, 0.1 * Math.sin(val), 0.1 * Math.cos(val)));
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		gamma = new GenericFunction() {
			private static final long serialVersionUID = -6598973940477311007L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(f.getOne(), new Quaternion(val, -val, val, 1 - val));
				if (Math.abs(((Quaternion) input).getReal().getValue()) < 1.e-5) {
					return (Scalar) f.stretch(input, RealLine.getInstance().get(1.e5));
				}
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};

	}

	@Test
	public void test() throws IOException {

		this.space = new FiniteDimensionalFunctionSpace(f, this.base, -1, 1, true);

		this.base.add(alpha);
		this.base.add(beta);

		alpha.plot(-Math.PI, Math.PI);
//		beta.plot(-Math.PI, Math.PI);
//		gamma.plot(-Math.PI, Math.PI);
		Generator.getInstance().saveCoordinateSpaces();
	}
 

}
