package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.rings.DiscreetDomain;
import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.Domain;
import definitions.structures.abstr.algebra.rings.FiniteRing;
import definitions.structures.impl.Naturals;

@Service
public class GroupGenerator implements IGroupGenerator, Unweavable {

	public static GroupGenerator instance;
	private DiscreetSemiRing naturals;
	private DiscreetDomain integers;
	private PrimeField rationals;
	
	public static GroupGenerator getInstance() {
		return instance;
	}
	
	public static void setInstance(final GroupGenerator groupGenerator) {
		instance = groupGenerator;
	}

	Map<Integer, FiniteRing> map = new HashMap<>(); 

	public void setIntegers(final DiscreetDomain integers) {
		this.integers = integers;
	}
	
	@Override
	public DiscreetDomain getIntegers( ) {
		if (integers==null){
			setIntegers(completeToDiscreetRing(getNaturals()));
		}
		return integers;
	}

	@Override
	public DiscreetSemiRing getNaturals() {
		if (naturals==null) {
			setNaturals(new Naturals());
		}
		return naturals;
	}

	@Override
	public void setNaturals(DiscreetSemiRing naturals) {
		this.naturals = naturals;
	}

	/**
	 * @return the rationals
	 */
	@Override
	public PrimeField getRationals() {
		if (rationals==null) {
			setRationals(completeToDiscreetField(integers));
		}
		return rationals;
	}

	/**
	 * @param rationals the rationals to set
	 */
	@Override
	public void setRationals(PrimeField rationals) {
		this.rationals = rationals;
	}

	@Override
	public void setIntegers(DiscreetRing integers) {
		this.integers=(DiscreetDomain) integers;
	}

}
