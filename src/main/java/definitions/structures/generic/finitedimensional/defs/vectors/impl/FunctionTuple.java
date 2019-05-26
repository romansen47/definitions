package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.HilbertSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public class FunctionTuple extends Tuple implements Function {

	public FunctionTuple(Map<Vector, Double> coordinates) throws Throwable {
		super(coordinates);
	}

	public FunctionTuple(double[] coordinates) throws Throwable {
		super(coordinates);
	}
	
	@Override
	public Map<Vector, Double> getCoordinates(EuclideanSpace space) throws Throwable{
		Map<Vector,Double> newCoordinates=new HashMap<>();
		for (Vector baseVec:space.getGenericBase()){
			newCoordinates.put(baseVec,space.product(this,baseVec));
		}
		return newCoordinates;
	}

	public double value(double input) throws Throwable {
		double ans = 0;
		for (Vector fun : this.getCoordinates().keySet()) {
			ans += ((Function) fun).value(input) * getCoordinates().get(fun);
		}
		return ans;
	}
}
