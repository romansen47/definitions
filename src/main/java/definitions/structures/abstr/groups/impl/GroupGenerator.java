package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.groups.IGroupGenerator;
import definitions.structures.abstr.groups.impl.Integers.Int;
import definitions.structures.euclidean.Generator;

@Configuration
@ComponentScan
public class GroupGenerator implements IGroupGenerator {

	Map<Integer, FiniteCyclicRing> map = new HashMap<>();
	
	public static GroupGenerator getInstance() {
		return Generator.getInstance().getGroupGenerator();
	}
	
	@Override
	public FiniteCyclicRing getFiniteCyclicRing(int order) {
		FiniteCyclicRing ring = map.get(order);
		if (ring == null) {
			ring = finiteCyclicRing((Int) Integers.getInstance().get(order));
			map.put(order, ring);
		}
		return ring;
	}

	@Bean
	public FiniteCyclicRing finiteCyclicRing(Integers.Int i) {
		return new FiniteCyclicRing(i.getValue());
	}

}
