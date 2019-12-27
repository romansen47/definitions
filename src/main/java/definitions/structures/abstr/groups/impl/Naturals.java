package definitions.structures.abstr.groups.impl;

import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.groups.OrderedMonoid;
import definitions.structures.abstr.groups.SemiGroup;

public class Naturals implements SemiGroup, OrderedMonoid, DiscreetMonoid {
 
	private static final long serialVersionUID = 1L;
	private static OrderedMonoid instance;

	@Override
	public Integer getOrder() {
		return null;
	}

	@Override
	public NaturalNumber operation(MonoidElement first, MonoidElement second) {
		return get(((NaturalNumber) first).getRepresentant() + ((NaturalNumber) second).getRepresentant());
	}

	@Override
	public NaturalNumber getNeutralElement() {
		return null;
	}

	@Override
	public NaturalNumber get(Integer representant) {
		return new NaturalNumber(representant);
	}

	@Override
	public boolean isHigher(MonoidElement smallerOne, MonoidElement biggerOne) {
		return ((NaturalNumber) smallerOne).getRepresentant() < ((NaturalNumber) biggerOne).getRepresentant();
	}

	public static OrderedMonoid getInstance() { 
		if (instance==null) {
			instance=new Naturals();
		}
		return instance;
	}

}
