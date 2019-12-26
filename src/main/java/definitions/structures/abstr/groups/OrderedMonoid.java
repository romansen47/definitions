package definitions.structures.abstr.groups;

public interface OrderedMonoid extends Monoid{
	
	boolean isHigher(MonoidElement smallerOne,MonoidElement biggerOne);

}
