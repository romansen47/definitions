package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import definitions.SpringConfiguration;
import definitions.Unweavable;
import definitions.structures.abstr.fields.impl.FinitePrimeField;
import definitions.structures.abstr.groups.IGroupGenerator;

@Configuration 
public class GroupGenerator implements IGroupGenerator, Unweavable {

	public static GroupGenerator instance;

	public static GroupGenerator getInstance() {
		return instance;
	}

	public static void setInstance(final GroupGenerator groupGenerator) {
		instance = groupGenerator;
	}

	Map<Integer, FiniteResidueClassRing> map = new HashMap<>();

	@Autowired
	private Integers integers;
	
	@Autowired
	private BinaryField binaryField;

	@Override
	public FiniteResidueClassRing getFiniteResidueClassRing(final int order) {
		FiniteResidueClassRing ring = this.map.get(order);
		if (ring == null) {
			if (order == 2) { 
				ring=binaryField;
			} else {
				if (Integers.getInstance().isPrimeElement(Integers.getInstance().get(order))) {
					ring = new FinitePrimeField(order);
				} else {
					ring = FiniteResidueClassRing.getFiniteCyclicRing(order);
				}
			}
			this.map.put(order, ring);
		}
		return ring;
	}

	public Integers getIntegers() {
		return this.integers;
	}

	public void setIntegers(final Integers integers) {
		this.integers = integers;
	}

	public BinaryField getBinaryField() {
		return binaryField;
	}

	public void setBinaryField(BinaryField binaryField) {
		this.binaryField = binaryField;
	}

}
