//package definitions.structures.abstr.groups.impl;
//
//import org.springframework.stereotype.Component;
//
//import definitions.structures.abstr.groups.CyclicGroup;
//import definitions.structures.abstr.groups.GroupElement;
//import definitions.structures.abstr.groups.MonoidElement;
//
//@Component(value="binaryGroup")
//public class BinaryGroup extends FiniteCyclicRing {
//
//	private static final long serialVersionUID = 1L;
//
//	public BinaryGroup() {
//		super(2);
//	}
//
//	@Override
//	public Integer getOrder() {
//		return 2;
//	}
//
//	@Override
//	public MonoidElement operation(GroupElement first, GroupElement second) {
//		if (first==getIdentityElement()) {
//			return second;
//		}
//		else {
//			if (second==getGenerator()){
//				return getIdentityElement();
//			}
//		}
//		return getGenerator();
//	}
//
//	@Override
//	public GroupElement getInverseElement(GroupElement element) {
//		return element;
//	}
//
//}
