/**
 * 
 */
package definitions.structures.abstr.groups;

/**
 * @author ro
 *
 *         A Group G is a semi group, such that for every group element a there
 *         exists an inverse element
 *
 *         b = getInverseElement(a),
 *
 *
 *         such that
 * 
 *         product(a,b) = product(b,a) = getIdentityElement().
 *
 */
public interface Group extends SemiGroup {

	GroupElement getInverseElement(GroupElement element);

}
