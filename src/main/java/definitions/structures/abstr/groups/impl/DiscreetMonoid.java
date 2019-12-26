package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.groups.Monoid;
import definitions.structures.abstr.groups.MonoidElement;

public interface DiscreetMonoid extends Monoid {

	public MonoidElement get(Integer representant);
	
}
