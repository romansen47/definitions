package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class FiniteDimensionalSobolevSpace extends FiniteDimensionalFunctionSpace {

	public FiniteDimensionalSobolevSpace(List<Vector> genericBase, double left, double right) throws Throwable {
		super(genericBase, left, right);
	}
	
	public FiniteDimensionalSobolevSpace(IFiniteDimensionalFunctionSpace space) throws Throwable {
		super(space.genericBaseToList(), space.getIntervall()[0],space.getIntervall()[1]);
		List<Vector> newBaseTmp=getOrthonormalBase(base);
		List<Vector> newBase=new ArrayList<>();
		List<Vector> newCoordinates=SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(dim).genericBaseToList();
		int dim=space.genericBaseToList().size();
		for (int i=0;i<dim;i++) {
			final int j=i;
			final Function baseFunction=new FunctionTuple(newCoordinates.get(i).getGenericCoordinates()) {
				@Override
				public double value(double input) throws Throwable {
					return ((Function)newBaseTmp.get(j)).value(input);
				}
			};
			newBase.add(baseFunction);
		}
		for (Vector vec:newBase) {
			final Map<Vector,Double> coordinates=new ConcurrentHashMap<>();
			coordinates.put(vec,1.);
			for (Vector vec2:newBase) {
				if (!vec.equals(vec2)) {
					coordinates.put(vec2,0.);
				}
			}
			vec.setCoordinates(coordinates);
		}
		setBase(newBase);
	}
	
	public Function getDerivative(Vector fun) throws Throwable {
		return new GenericFunction() {
			double eps=1.e-5;
			@Override
			public double value(double input) throws Throwable {
				return (((Function)fun).value(input+eps)-((Function)fun).value(input))/eps; 
			}
		};
	}
	
	@Override
	public double product(Vector vec1,Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function ) {
			return super.product(vec1, vec2)+super.product(getDerivative(vec1), getDerivative(vec2));
		}
		return super.product(vec1, vec2);
	}
	
	@Override
	public Vector projection(Vector w, Vector v) throws Throwable {
		return stretch(v, product(w, v));
	}

}
