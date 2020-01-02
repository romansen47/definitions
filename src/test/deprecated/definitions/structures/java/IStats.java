/**
 * 
 */
package definitions.structures.java;

/**
 * @author ro
 *
 */
public interface IStats {

	/**
	 * @return the vectorCount
	 */

	int getVectorCount();

	default void increaseVectorCount() {
		this.setVectorCount(this.getVectorCount() + 1);
	}

	/**
	 * @param vectorCount the vectorCount to set
	 */

	void setVectorCount(int vectorCount);
}
