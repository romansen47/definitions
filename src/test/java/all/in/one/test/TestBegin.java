/**
 * 
 */
package all.in.one.test;

import org.junit.Test;

import definitions.IWatcher;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.Watcher;

/**
 * @author RoManski
 *
 */
public abstract class TestBegin {

	final static IWatcher watcher = Watcher.getInstance();
	ISpaceGenerator spGen;

	@Test
	public void testGenerators() {
		spGen = Generator.getGenerator().getSpacegenerator();
	}

}
