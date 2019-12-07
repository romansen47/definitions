package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.groups.IGroupGenerator;

@Configuration
//@Component
public class GroupGenerator implements IGroupGenerator {

	Map<Integer, FiniteCyclicRing> map = new HashMap<>();

	public static GroupGenerator instance;

	@Autowired
	private Integers integers;

	public static GroupGenerator getInstance() {
		return instance;
	}

	@Override
	public FiniteCyclicRing getFiniteCyclicRing(int order) {
		FiniteCyclicRing ring = map.get(order);
		if (ring == null) {
			ring = FiniteCyclicRing.getFiniteCyclicRing(order);
			map.put(order, ring);
		}
		return ring;
	}

	public static void setInstance(GroupGenerator groupGenerator) {
		instance = groupGenerator;
	}

	public Integers getIntegers() {
		if (Integers.getInstance() == null) {
			Integers.setInstance(integers);
		}
		return integers;
	}

}
