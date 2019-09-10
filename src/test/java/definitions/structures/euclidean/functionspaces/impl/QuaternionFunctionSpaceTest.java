/**
 * 
 */
package definitions.structures.euclidean.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * @author BAU12350
 *
 */
public class QuaternionFunctionSpaceTest {

	static final public Field f = (Field) QuaternionSpace.getInstance();
	List<Vector> base = new ArrayList<>();
	FunctionSpace space;

	static Function alpha;
	static Function beta;
	static Function gamma;
	
	public static void main(String[] args) {
		prepare();
		new QuaternionFunctionSpaceTest().test();
	}

	@BeforeClass
	public static void prepare() {
		alpha = new GenericFunction() {
			private static final long serialVersionUID = 6698998256903151087L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(
						f.getOne(),
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
				final Quaternion tmp = (Quaternion) f.add(
						f.getOne(),
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
				final Quaternion tmp = (Quaternion) f
						.add(f.getOne(), new Quaternion(val, -val, val, 1 - val));
				if (Math.abs(((Quaternion) input).getReal().getValue()) < 1.e-5) {
					return (Scalar) f.stretch(input, new Real(1.e5));
				}
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};

	}
	
	@com.aop.lib.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true)
	@Test
	public void test() {
		this.space = new FiniteDimensionalFunctionSpace(f, this.base, -1, 1, true);

		this.base.add(alpha);
		this.base.add(beta);

		alpha.plot(-Math.PI, Math.PI);
//		beta.plot(-Math.PI, Math.PI);
//		gamma.plot(-Math.PI, Math.PI);
	}

}
