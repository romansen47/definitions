package definitions.structures.abstr.algebra.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.structures.abstr.algebra.fields.impl.FinitePrimeField;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
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

	Map<Integer, FiniteResidueClassRing> map = new HashMap<>();

	@Autowired
	private Integers integers;

	@Autowired
	private BinaryField binaryField;

	public BinaryField getBinaryField() {
		return this.binaryField;
	}

	@Override
	public FiniteResidueClassRing getFiniteResidueClassRing(final int order) {
		FiniteResidueClassRing ring = this.map.get(order);
		if (ring == null) {
			if (order == 2) {
				ring = this.binaryField;
			} else {
				if (Integers.getInstance().isPrimeElement(Integers.getInstance().get(order))) {
					ring = new FinitePrimeField(order);
				} else {
					ring = new FiniteResidueClassRing(order);
				}
			}
			this.map.put(order, ring);
		}
		return ring;
	}

	public Integers getIntegers() {
		return this.integers;
	}

	public void setBinaryField(final BinaryField binaryField) {
		this.binaryField = binaryField;
	}

	public void setIntegers(final Integers integers) {
		this.integers = integers;
	}

}
