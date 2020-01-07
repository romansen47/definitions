package definitions.structures.abstr.algebra.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.algebra.rings.impl.Integers;

@Service
public class GroupGenerator implements IGroupGenerator, Unweavable {

	public static GroupGenerator instance;

	public static GroupGenerator getInstance() {
		return instance;
	}

	public static void setInstance(final GroupGenerator groupGenerator) {
		instance = groupGenerator;
	}

	Map<Integer, FiniteRing> map = new HashMap<>();

	@Autowired
	private Integers integers;

	public void setIntegers(final Integers integers) {
		this.integers = integers;
	}

}
