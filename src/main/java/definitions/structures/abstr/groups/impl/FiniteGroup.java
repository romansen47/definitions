package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.groups.DiscreteGroup;
import definitions.structures.abstr.groups.GroupElement; 

public interface FiniteGroup extends DiscreteGroup {
	
	final Map<GroupElement,Map<GroupElement,GroupElement>> operationMap=new HashMap<>();

	default Map<GroupElement, Map<GroupElement, GroupElement>> getOperationMap(){
		return operationMap;
	}

}
