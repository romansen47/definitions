package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.impl.Naturals;

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
	private DiscreetSemiRing naturals;
	
	private DiscreetRing integers;

	public void setIntegers(final DiscreetRing integers) {
		this.integers = integers;
	}
	
	@Override
	public DiscreetRing getIntegers( ) {
		return integers;
	}

	public DiscreetSemiRing getNaturals() {
		if (naturals==null) {
			naturals=new Naturals();
		}
		return naturals;
	}

	public void setNaturals(DiscreetSemiRing naturals) {
		this.naturals = naturals;
	}

}
