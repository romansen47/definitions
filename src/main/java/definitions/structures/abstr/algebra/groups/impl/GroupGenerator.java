package definitions.structures.abstr.algebra.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.rings.impl.Integers;
import definitions.structures.abstr.vectorspaces.Ring;

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

	@Override
	public Ring completeToRing(SemiRing semiRing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Field completeToField(Domain domain) {
		// TODO Auto-generated method stub
		return null;
	}

}
