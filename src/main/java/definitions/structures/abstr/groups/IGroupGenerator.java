package definitions.structures.abstr.groups;

import definitions.structures.abstr.groups.impl.CyclicRingElement;
import definitions.structures.abstr.groups.impl.FiniteCyclicRing;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface IGroupGenerator {

	FiniteCyclicRing getFiniteCyclicRing(int order);
	
	default FiniteCyclicRing getSubRing(FiniteCyclicRing ring,RingElement element) {
		int characteristic=0;
		int index=((CyclicRingElement) element).getRepresentant();
		int tmp=index;
		RingElement e=(CyclicRingElement) ring.get(tmp);
		while(e!=ring.getIdentityElement()) {
			e=(CyclicRingElement) ring.get(tmp+index);
			characteristic+=1;
		}
		return getFiniteCyclicRing(characteristic);
	}

}
