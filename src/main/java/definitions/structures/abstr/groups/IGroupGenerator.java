package definitions.structures.abstr.groups;

import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;

public interface IGroupGenerator {

	FiniteResidueClassRing getFiniteResidueClassRing(int order);

//	default FiniteResidueClassRing getSubRing(FiniteResidueClassRing ring, RingElement element) {
//		int characteristic = 0;
//		int index = ((FiniteResidueClassElement) element).getRepresentant();
//		int tmp = index;
//		RingElement e = (FiniteResidueClassElement) ring.get(tmp);
//		while (e != ring.getIdentityElement()) {
//			e = (FiniteResidueClassElement) ring.get(tmp + index);
//			characteristic += 1;
//		}
//		return getFiniteResidueClassRing(characteristic);
//	}

}
