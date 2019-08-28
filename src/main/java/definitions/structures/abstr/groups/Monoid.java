package definitions.structures.abstr.groups;

import java.io.Serializable;

/**
 * 
 * @author ro
 *
 *         A monoid is a set of things, which can be 'multiplied'.
 * 
 *         In detail, we have a method
 *         (MonoidElement,MonoidElement,)->MonoidElement.
 */
public interface Monoid extends Serializable {

	Integer getOrder();

	MonoidElement operation(GroupElement first, GroupElement second);

}
