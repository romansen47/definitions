package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;

public interface IGroupGenerator {

	class ProductElement extends Pair<MonoidElement, MonoidElement> implements MonoidElement {
		private static final long serialVersionUID = 1L;

		private final MonoidElement k;
		private final MonoidElement v;

		public ProductElement(final MonoidElement k, final MonoidElement v) {
			super(k, v);
			this.k = k;
			this.v = v;
		}

		@Override
		public String toString() {
			if (this.k instanceof FiniteGroupElement && this.v instanceof FiniteGroupElement) {
				final FiniteGroupElement u = (FiniteGroupElement) this.k;
				final FiniteGroupElement w = (FiniteGroupElement) this.v;
				return " ( " + u.getRepresentant() + " , " + w.getRepresentant() + " ) ";
			}
			return " ( " + this.k.toString() + " , " + this.v.toString() + " ) ";
		}
	}

	FiniteResidueClassRing getFiniteResidueClassRing(int order);

	default Monoid outerProduct(final Monoid a, final Monoid b) {
		Monoid ans = null;
		if (a.getOrder() != null && b.getOrder() != null) {
			ans = new FiniteMonoid() {
				private long serialVersionUID = 1L;
				private Map<MonoidElement, Map<MonoidElement, MonoidElement>> operationMap = null;
				private int order = a.getOrder() * b.getOrder();

				@Override
				public MonoidElement get(final Integer representant) {
					return FiniteMonoid.elements.get(representant);
				}

				@Override
				public MonoidElement getNeutralElement() {
					return new ProductElement(a.getNeutralElement(), b.getNeutralElement());
				}

				@Override
				public Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap() {
					if (this.operationMap == null) {
						for (int i = 0; i < a.getOrder(); i++) {
							for (int j = 0; j < b.getOrder(); j++) {
								final ProductElement tmp = new ProductElement(((FiniteMonoid) a).get(i),
										((FiniteMonoid) b).get(j));
								FiniteMonoid.elements.put(i * b.getOrder() + j, tmp);
							}
						}
						this.operationMap = new HashMap<>();
						for (final Integer key1 : FiniteMonoid.elements.keySet()) {
							for (final Integer key2 : FiniteMonoid.elements.keySet()) {
								this.operation(FiniteMonoid.elements.get(key1), FiniteMonoid.elements.get(key2));
							}
						}
					}
					return this.operationMap;
				}

				@Override
				public Integer getOrder() {
					return this.order;
				}

				@Override
				public MonoidElement operation(final MonoidElement first, final MonoidElement second) {
					MonoidElement ans = FiniteMonoid.super.operation(first, second);
					if (ans != null) {
						return ans;
					}
					ans = new ProductElement(
							a.operation(((ProductElement) first).getFirst(), ((ProductElement) second).getFirst()),
							b.operation(((ProductElement) first).getSecond(), ((ProductElement) second).getSecond()));
					Map<MonoidElement, MonoidElement> tmpMap = this.operationMap.get(first);
					if (tmpMap == null) {
						tmpMap = new HashMap<>();
					}
					tmpMap.put(second, ans);
					this.operationMap.put(first, tmpMap);
					return ans;
				}

			};
		} else {
			ans = new Monoid() {
				private long serialVersionUID = 1L;

				@Override
				public MonoidElement getNeutralElement() {
					return new ProductElement(a.getNeutralElement(), b.getNeutralElement());
				}

				@Override
				public Integer getOrder() {
					return null;
				}

				@Override
				public MonoidElement operation(final MonoidElement first, final MonoidElement second) {
					return new ProductElement(
							a.operation(((ProductElement) first).getFirst(), ((ProductElement) first).getSecond()),
							b.operation(((ProductElement) second).getFirst(), ((ProductElement) second).getSecond()));
				}
			};
		}
		return ans;
	}

}
