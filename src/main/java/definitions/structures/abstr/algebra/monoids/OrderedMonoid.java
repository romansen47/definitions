package definitions.structures.abstr.algebra.monoids;

public interface OrderedMonoid extends Monoid {

	boolean isHigher(MonoidElement smallerOne, MonoidElement biggerOne);

}
