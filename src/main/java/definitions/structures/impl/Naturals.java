package definitions.structures.impl;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.impl.semigroups.DiscreetSemiGroupImpl; 

@SuppressWarnings("serial")
public class Naturals extends DiscreetSemiGroupImpl implements SemiRing, DiscreetMonoid, OrderedMonoid{

	class NaturalNumber implements Element{
		
		private final int representant;
		
		public NaturalNumber(int i) {
			this.representant=i;
		}
		
		@Override
		public Integer getRepresentant() {
			return representant;
		}
	}
	
	DiscreetMonoid multiplicativeMonoid;
	
	@Override
	public Element getNeutralElement() {
		return get(0);
	}

	@Override
	public Element operation(Element first, Element second) {
		return get(first.getRepresentant() + second.getRepresentant());
	}

	@Override
	public Element get(Integer representant) {
		return new NaturalNumber(representant) {
			@Override
			public Integer getRepresentant() {
				return (Integer) Math.abs(representant);
			}
			@Override
			public boolean equals(Object other) {
				if (other instanceof NaturalNumber) {
					return representant == ((NaturalNumber) other).getRepresentant();
				}
				return false;
			}
		};
	}

	@Override
	public DiscreetMonoid getMuliplicativeMonoid() {
		if (multiplicativeMonoid==null) {
			multiplicativeMonoid=new DiscreetMonoid() {

				@Override
				public Element getNeutralElement() {
					return Naturals.this.get(1);
				}

				@Override
				public Element operation(Element first, Element second) {
					return Naturals.this.get(first.getRepresentant()*second.getRepresentant());
				}

				@Override
				public Element get(Integer representant) { 
					return Naturals.this.get(representant);
				}
				
			};
		}
		return multiplicativeMonoid;
	}

	@Override
	public boolean isUnit(Element element) { 
		return element.equals(getOne());
	}

	@Override
	public Element getOne() { 
		return getMuliplicativeMonoid().getNeutralElement();
	}

	@Override
	public boolean isHigher(Element smallerOne, Element biggerOne) { 
		return biggerOne.getRepresentant()>smallerOne.getRepresentant();
	} 

}
