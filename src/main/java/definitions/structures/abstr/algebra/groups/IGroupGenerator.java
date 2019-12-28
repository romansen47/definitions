package definitions.structures.abstr.algebra.groups;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing; 

public interface IGroupGenerator {

	FiniteResidueClassRing getFiniteResidueClassRing(int order);

	class ProductElement extends Pair<MonoidElement, MonoidElement> implements MonoidElement {
		private static final long serialVersionUID = 1L;

		private MonoidElement k;
		private MonoidElement v;
		public ProductElement(MonoidElement k, MonoidElement v) {
			super(k, v);
			this.k=k;
			this.v=v;
		}
		
		public String toString() {
			if (k instanceof FiniteGroupElement && v instanceof FiniteGroupElement) {
				FiniteGroupElement u=(FiniteGroupElement)k;
				FiniteGroupElement w=(FiniteGroupElement)v;
				return " ( "+u.getRepresentant()+" , "+w.getRepresentant()+" ) ";
			}
			return " ( "+k.toString()+" , "+v.toString()+" ) ";
		}
	}
	
	default Monoid outerProduct(Monoid a, Monoid b) {
		Monoid ans = null;
		if (a.getOrder() != null && b.getOrder() != null) {
			ans = new FiniteMonoid() {
				private static final long serialVersionUID = 1L;
				private Map<MonoidElement, Map<MonoidElement, MonoidElement>> operationMap = null;
				protected Map<Integer, MonoidElement> elements = new HashMap<>();
				private int order = a.getOrder() * b.getOrder();

				@Override
				public MonoidElement get(Integer representant) {
					return elements.get(representant);
				}

				@Override
				public MonoidElement getNeutralElement() {
					return new ProductElement(a.getNeutralElement(), b.getNeutralElement());
				}

				@Override
				public Integer getOrder() {
					return order;
				}

				@Override
				public Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap() {
					if (operationMap == null) {
						for (int i = 0; i < a.getOrder(); i++) {
							for (int j = 0; j < b.getOrder(); j++) {
								ProductElement tmp = new ProductElement(((FiniteMonoid) a).get(i),
										((FiniteMonoid) b).get(j));
								elements.put(i * b.getOrder() + j, tmp);
							}
						}
						operationMap = new HashMap<>();
						for (Integer key1 : elements.keySet()) {
							for (Integer key2 : elements.keySet()) {
								operation(elements.get(key1), elements.get(key2));
							}
						}
					}
					return operationMap;
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
					Map<MonoidElement, MonoidElement> tmpMap = operationMap.get(first);
					if (tmpMap == null) {
						tmpMap = new HashMap<>();
					}
					tmpMap.put(second, ans);
					operationMap.put(first, tmpMap);
					return ans;
				}

				@Override
				public boolean isAbelian() {
					// TODO Auto-generated method stub
					return false;
				}
			};
		} else {
			ans = new Monoid() {
				private static final long serialVersionUID = 1L;

				@Override
				public Integer getOrder() {
					return null;
				}

				@Override
				public MonoidElement operation(MonoidElement first, MonoidElement second) {
					return new ProductElement(
							a.operation(((ProductElement) first).getFirst(), ((ProductElement) first).getSecond()),
							b.operation(((ProductElement) second).getFirst(), ((ProductElement) second).getSecond()));
				}

				@Override
				public MonoidElement getNeutralElement() {
					return new ProductElement(a.getNeutralElement(), b.getNeutralElement());
				}

				@Override
				public boolean isAbelian() {
					return a.isAbelian() && b.isAbelian();
				}
			};
		}
		return ans;
	}

}
