package definitions.structures.abstr.groups.impl;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.groups.CyclicGroup;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.MonoidElement;

@Component(value="binaryGroup")
public interface BinaryGroup extends CyclicGroup {

//	final GroupElement identityElement = new GroupElement() {
// 
//		private static final long serialVersionUID = 1L;
//
//		@Override
//		public String toXml() {
//			return "<zero/>";
//		}
//	};
//
//	final GroupElement generator = new GroupElement() {
// 
//		private static final long serialVersionUID = 1L;
//
//		@Override
//		public String toXml() {
//			return "<generator/>";
//		}
//	};

	@Override
	default  public Integer getOrder() { 
		return 2;
	}

	@Override
	default public MonoidElement operation(GroupElement first, GroupElement second) { 
		if (first==getIdentityElement()) {
			return second;
		}
		else {
			if (second==getGenerator()){
				return getIdentityElement();
			}
		}
		return getGenerator();
	}

	@Override
	default public GroupElement getInverseElement(GroupElement element) {
		return element;
	}

}
