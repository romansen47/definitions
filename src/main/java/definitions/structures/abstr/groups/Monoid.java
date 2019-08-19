package definitions.structures.abstr.groups;

import java.io.Serializable;

public interface Monoid extends Serializable {

	Integer getOrder();

	MonoidElement operation(GroupElement first, GroupElement second);

}
