package definitions.structures.abstr.groups;

import java.util.Map;

import definitions.structures.abstr.groups.impl.FiniteCyclicRing;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface IGroupGenerator {

	FiniteCyclicRing getFiniteCyclicRing(int order);
	
	default FiniteCyclicRing getSubRing(FiniteCyclicRing ring,RingElement element) {
		int characteristic=0;
		int index=((FiniteCyclicRing.Element) element).getRepresentant();
		int tmp=index;
		RingElement e=(FiniteCyclicRing.Element) ring.get(tmp);
		while(e!=ring.getIdentityElement()) {
			e=(FiniteCyclicRing.Element) ring.get(tmp+index);
			characteristic+=1;
		}
		return getFiniteCyclicRing(characteristic);
	}

}
