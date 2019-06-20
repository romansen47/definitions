package definitions.structures.abstr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public abstract class LinearMapping implements Homomorphism {

	protected final VectorSpace source;
	protected final VectorSpace target;
	protected Map<Vector, Map<Vector, Double>> linearity;
	protected double[][] genericMatrix;

	public LinearMapping(final VectorSpace source, final VectorSpace target) throws Throwable {
		this.source = source;
		this.target = target;
	}

	@Override
	public Map<Vector,Map<Vector,Double>> getLinearity() throws Throwable{
		if (linearity==null) {
			linearity = new ConcurrentHashMap<>();
			for (Vector vec1:((EuclideanSpace)source).genericBaseToList()) {
				Vector tmp=get(vec1);
				Map<Vector,Double> tmpCoord=new ConcurrentHashMap<>();
				for (Vector vec2:((EuclideanSpace)target).genericBaseToList()){
					tmpCoord.put(vec2,((EuclideanSpace)target).product(vec2,tmp));
				}
				linearity.put(vec1,tmpCoord);
			}
		}
		return linearity;
	}
	
	public double[][] getGenericMatrix() throws Throwable {
		if (!(source instanceof EuclideanSpace && target instanceof EuclideanSpace)) {
			return null;
		}
		if (genericMatrix == null) {
			this.genericMatrix = new double[((EuclideanSpace) this.getTarget())
					.dim()][((EuclideanSpace) this.getSource()).dim()];
			int i = 0;
			for (final Vector vec1 : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
				int j = 0;
				for (final Vector vec2 : ((EuclideanSpace) this.getTarget()).genericBaseToList()) {
					this.genericMatrix[j][i] = this.getLinearity(vec1).get(vec2);
					j++;
				}
				i++;
			}
		}
		return genericMatrix;
	}
	
	@Override
	public VectorSpace getSource() {
		return source;
	}

	@Override
	public VectorSpace getTarget() {
		return target;
	}

	@Override
	public Map<Vector, Double> getLinearity(Vector vec1) throws Throwable {
		return linearity.get(vec1);
	}
}
