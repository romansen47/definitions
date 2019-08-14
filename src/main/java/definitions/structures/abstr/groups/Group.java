/**
 * 
 */
package definitions.structures.abstr.groups;

/**
 * @author ro
 *
 */
public interface Group {

	Integer getOrder();

	GroupElement operation(GroupElement first, GroupElement second);

}
