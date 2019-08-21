/**
 * 
 */
package definitions.structures.java;

/**
 * @author ro
 *
 */
public interface IStats {

	default void increaseVectorCount() {
		setVectorCount(getVectorCount() + 1);
	}

	/**
	 * @return the vectorCount
	 */

	public int getVectorCount();

	/**
	 * @param vectorCount
	 *            the vectorCount to set
	 */

	public void setVectorCount(int vectorCount);
}
