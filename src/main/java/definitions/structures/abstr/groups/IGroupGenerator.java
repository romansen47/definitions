package definitions.structures.abstr.groups;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import definitions.structures.abstr.groups.impl.FiniteGroup;
import definitions.structures.abstr.groups.impl.FiniteResidueClassElement;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;
import definitions.structures.abstr.vectorspaces.RingElement;

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
