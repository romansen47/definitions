/**
 * 
 */
package definitions.structures.euclidean.functionspaces.impl;

import static org.junit.Assert.*;

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
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpaceTest;

/**
 * @author BAU12350
 *
 */
public class QuaternionFunctionSpaceTest {// extends FiniteDimensionalFunctionSpaceTest {

	final public Field f = (Field) QuaternionSpace.getInstance();
	List<Vector> base = new ArrayList<>();
	FunctionSpace space;

	@Test
	public void test() {

		final Function alpha = new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				double val = ((Quaternion) input).getReal().getValue();
				Quaternion tmp = (Quaternion) f.add(f.getOne(), new Quaternion(val, -val, 0, 0));
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		final Function beta = new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				double val = ((Quaternion) input).getReal().getValue();
				Quaternion tmp = (Quaternion) f.add(f.getOne(), new Quaternion(val, val, 0, 0));
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		final Function gamma = new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				double val = ((Quaternion) input).getReal().getValue();
				Quaternion tmp = (Quaternion) f.add(f.getOne(), new Quaternion(val, -val, val,1- val));
				if (Math.abs(((Quaternion) input).getReal().getValue())<1.e-5) {
					return (Scalar) f.stretch(input, new Real(1.e5));
				}
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		space = new FiniteDimensionalFunctionSpace(f, base, -1, 1, true);

		base.add(alpha);
		base.add(beta);
//		alpha.plot(-Math.PI, Math.PI);
//		beta.plot(-Math.PI, Math.PI);
		gamma.plot(-Math.PI, Math.PI);
	}

}
