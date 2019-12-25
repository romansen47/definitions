/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;
 
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class TrigonometricSpaceTest {

	final static Field field = RealLine.getInstance();
	final static EuclideanSpace trigSpace = SpaceGenerator.getInstance().getTrigonometricSpace(field, 10);
 
	public EuclideanFunctionSpace getLinearSpace() {
		return (EuclideanFunctionSpace) trigSpace;
	}

}
