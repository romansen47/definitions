package definitions.structures.abstr.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Endomorphism;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.mappings.impl.LinearMapping;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface Field extends EuclideanAlgebra,FieldTechnicalProvider {

	default Vector inverse(Vector factor) {
		VectorSpace multLinMaps= new LinearMappingsSpace(this,this);
		FiniteDimensionalHomomorphism hom=new FiniteDimensionalLinearMapping(this,this) {
			@Override
			public Vector get(Vector vec) {
				return getTarget().nullVec();
			}
			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				Map<Vector,Map<Vector,Scalar>> coord=new HashMap<>();
				for (Vector vec:((EuclideanSpace) getSource()).genericBaseToList()) {
					coord.put(vec, target.nullVec().getCoordinates());
				}
				return coord;
			}
			@Override
			public Scalar[][] getGenericMatrix() {
				Scalar[][] mat=new Scalar[target.getDim()][source.getDim()];
				for (Scalar[] entry:mat) {
					for (Scalar scalar:entry){
						scalar=RealLine.getInstance().getZero();
					}
				}
				return mat;
			}
		};
		for (Vector vec:genericBaseToList()) {
			Vector tmp=this.getMultiplicationMatrix().get(vec);
			hom=(FiniteDimensionalHomomorphism) multLinMaps.add(hom, multLinMaps.stretch(tmp, factor.getCoordinates().get(vec)));
		}
		return hom.solve((FiniteVector) getOne());
//		return ((Scalar) factor).getInverse();
	}

	@Override
	Vector getOne();

	default Vector getZero() {
		return nullVec();
	}
	
}
