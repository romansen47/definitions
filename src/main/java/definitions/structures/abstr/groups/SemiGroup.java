/**
 * 
 */
package definitions.structures.abstr.groups;

/**
 * @author RoManski
 *
 *         A semi group G is a special monoid with an Element called identity
 *         element. The mappings
 *
 *         alpha: Vector vec -> product(Vector vec,identityElement) beta: Vector
 *         vec -> product(identityElement,Vector vec)
 *
 *         are identically equal to the identity mapping on G.
 */
public interface SemiGroup extends Monoid {

	/**
	 * Getter for the identity element
	 * 
	 * @return the identity element of the semi group
	 */
	MonoidElement getIdentityElement();

}
