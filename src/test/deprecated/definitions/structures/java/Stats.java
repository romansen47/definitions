/**
 * 
 */
package definitions.structures.java;

/**
 * @author ro
 *
 */
public class Stats implements IStats {

	private static IStats instance = null;

	public static IStats getInstance() {
		if (instance == null) {
			instance = new Stats();
			instance.setVectorCount(0);
		}
		return instance;
	}

	private int vectorCount;

	@Override
	public int getVectorCount() {
		return this.vectorCount;
	}

	@Override
	public void setVectorCount(final int vectorCount) {
		this.vectorCount = vectorCount;
	}

}
