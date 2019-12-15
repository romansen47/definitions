package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import definitions.SpringConfiguration;
import definitions.Unweavable;
import definitions.structures.abstr.groups.IGroupGenerator;

@Configuration
//@Component
public class GroupGenerator implements IGroupGenerator, Unweavable {

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
			if (order == 2) {
				ring = (FiniteCyclicRing) SpringConfiguration.getSpringConfiguration().getApplicationContext()
						.getBean("binaryField");
			}
			ring = FiniteCyclicRing.getFiniteCyclicRing(order);
			map.put(order, ring);
		}
		return ring;
	}

	public static void setInstance(GroupGenerator groupGenerator) {
		instance = groupGenerator;
	}

	public Integers getIntegers() {
		return integers;
	}

	public void setIntegers(Integers integers) {
		this.integers = integers;
	}

}
