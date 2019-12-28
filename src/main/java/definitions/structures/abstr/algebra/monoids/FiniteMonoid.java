package definitions.structures.abstr.algebra.monoids;

import java.util.Map;

import definitions.structures.euclidean.Generator;

public interface FiniteMonoid extends DiscreetMonoid {

	Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap();

	@Override
	default MonoidElement operation(final MonoidElement first, final MonoidElement second) {
		Map <MonoidElement,MonoidElement> tmpMap = this.getOperationMap().get(first);
		if (tmpMap == null) {
			return null;
		}
		return tmpMap.get(second);
	}

	default void print() {
		Generator.getInstance().getLogger().info("Operation matrix:\r");
		String ans="  operation    ";
		for (int i=0;i<getOperationMap().keySet().size();i++) {
			MonoidElement element1=get(i);
			ans+=element1+"  ";
		}
		System.out.println(ans+"\r");
		for (int i=0;i<getOperationMap().keySet().size();i++) {
			MonoidElement element1=get(i);
			ans=element1+"   ";
			for (int j=0;j<getOperationMap().keySet().size();j++) {
				MonoidElement element2=get(j);
				ans +=" "+getOperationMap().get(element1).get(element2)+" ";
			}
			System.out.println(ans);
		}
	}
}
