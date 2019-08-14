/**
 * 
 */
package definitions.structures.java;

/**
 * @author ro
 *
 */
public class Stats implements IStats {

	private int vectorCount;

	private static IStats instance = null;

	public static IStats getInstance() {
		if (instance == null) {
			instance = new Stats();
			instance.setVectorCount(0);
		}
		return instance;
	}

	@Override
	public int getVectorCount() {
		return this.vectorCount;
	}

	@Override
	public void setVectorCount(int vectorCount) {
		this.vectorCount = vectorCount;
	}

}
