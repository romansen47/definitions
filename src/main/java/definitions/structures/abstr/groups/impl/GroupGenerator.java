package definitions.structures.abstr.groups.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import definitions.SpringConfiguration;
import definitions.Unweavable;
import definitions.structures.abstr.fields.impl.FinitePrimeField;
import definitions.structures.abstr.groups.IGroupGenerator;
import definitions.structures.abstr.vectorspaces.Ring;

@Configuration
//@Component
public class GroupGenerator implements IGroupGenerator, Unweavable {

	Map<Integer, FiniteResidueClassRing> map = new HashMap<>();

	public static GroupGenerator instance;

	@Autowired
	private Integers integers;

	public static GroupGenerator getInstance() {
		return instance;
	}
 
	@Override
	public FiniteResidueClassRing getFiniteResidueClassRing(int order) {
		FiniteResidueClassRing ring = map.get(order);
		if (ring == null) {
			if (order == 2) {
				ring = (FiniteResidueClassRing) SpringConfiguration.getSpringConfiguration().getApplicationContext()
						.getBean("binaryField");
			} else {
				if (Integers.getInstance().isPrimeElement(Integers.getInstance().get(order))) {
					ring = new FinitePrimeField(order);
				} else {
					ring = FiniteResidueClassRing.getFiniteCyclicRing(order);
				}
			}
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
