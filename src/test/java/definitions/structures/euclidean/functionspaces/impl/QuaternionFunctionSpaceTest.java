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
import definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpaceTest;

/**
 * @author BAU12350
 *
 */
public class QuaternionFunctionSpaceTest extends FiniteDimensionalFunctionSpaceTest {

	final Field field = (Field) QuaternionSpace.getInstance();

	final Function alpha=new GenericFunction() {
		@Override
		public Scalar value(Scalar input) {
			Real n=new Real(input.getValue());
			Quaternion a=new Quaternion(n,n,n,n);
			Quaternion b=(Quaternion) field.product(a, a);
			return b;
		}
	};

	final Function beta=new GenericFunction() {
		@Override
		public Scalar value(Scalar input) {
			Real n=new Real(input.getValue());
			return new Quaternion(n,n,n,n);
		}
	};
	
	List<Vector> base=new ArrayList<>();
	FunctionSpace space;
	
	@Test
	public void test() {
		base.add(alpha);
		base.add(beta);
		space=new FiniteDimensionalFunctionSpace(field,base, -1, 1, true);
		beta.plot(-1, 1);
	}

}
