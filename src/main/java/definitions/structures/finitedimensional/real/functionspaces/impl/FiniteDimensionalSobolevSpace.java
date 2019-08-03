package definitions.structures.finitedimensional.real.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.mappings.impl.DerivativeOperator;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

/**
 * Concrete implementation of a finite dimensional sobolev function space.
 * 
 * @author ro
 *
 */
public class FiniteDimensionalSobolevSpace extends FiniteDimensionalFunctionSpace {

	private DerivativeOperator derivativeBuilder;
	/**
	 * The sobolev degree.
	 */
	private Integer degree;

	/**
	 * Constructor.
	 * 
	 * @param genericBase the base
	 * @param left        the inf of the interval.
	 * @param right       the sup of the intervall.
	 * @param degree      the sobolev degree.
	 */
	public FiniteDimensionalSobolevSpace(final List<Vector> genericBase, final double left, final double right,
			int degree) {
		super(genericBase, left, right,true);
		this.degree = degree;
		getDerivativeBuilder();
	}

	/**
	 * Constructor. Converts function space to sobolev space.
	 * 
	 * @param space  the function space.
	 * @param degree the sobolev degree of the converted space.
	 */
	public FiniteDimensionalSobolevSpace(final EuclideanFunctionSpace space, int degree,boolean ortho) {
		super(space.genericBaseToList(), space.getInterval()[0], space.getInterval()[1],false);
		this.degree=degree;
		if (ortho) {
			setBase(getOrthonormalBase(this.base));
		}
//		final List<Vector> newBaseTmp = this.base;// this.getOrthonormalBase(this.base);
//		final List<Vector> newBase = new ArrayList<>();
//		final List<Vector> newCoordinates = ((EuclideanSpace) Generator.getGenerator().getSpacegenerator()
//				.getFiniteDimensionalVectorSpace(this.dim)).genericBaseToList();
//		final int dim = space.genericBaseToList().size();
//		for (int i = 0; i < dim; i++) {
//			final int j = i;
//			final Function baseFunction = 
//					new FunctionTuple(newCoordinates.get(i).getGenericCoordinates()) {
//				@Override
//				public Scalar value(final Scalar input) {
//					return ((Function) newBaseTmp.get(j)).value(input);
//				}
//			};
//			newBase.add(baseFunction);
//		}
//		this.setBase(space.genericBaseToList());
//		this.setBase(this.getOrthonormalBase(this.base));
//		final Map<Vector,Map<Vector,Scalar>> tmpCoord=new ConcurrentHashMap<>();
//		for (final Vector vec : base) {
//			final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
//			for (final Vector vec2 : base) {
//				if (!((Function)vec).equals((Function)vec2)) {
//					coordinates.put(vec2, RealLine.getInstance().getZero());
//				}
//				coordinates.put(vec, RealLine.getInstance().getOne());
//			}
//			tmpCoord.put(vec,coordinates);
//		}
//		for (final Vector vec : base) {
//			vec.setCoordinates(tmpCoord.get(vec));
//		}
//		this.setBase(newBase);
//		if (ortho) {
//			orthoNormalizeBase()
//			this.setBase(this.getOrthonormalBase(this.base));
//		}
//		this.plotBase();
	}

	/**
	 * Constructor.
	 * 
	 * @param degree the sobolev degree.
	 */
	protected FiniteDimensionalSobolevSpace(int degree) {
		this.degree = degree;
	}

	@Override
	public Scalar innerProduct(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((vec1 instanceof FunctionTuple) && (vec2 instanceof FunctionTuple)
					&& (((FunctionTuple) vec1).getGenericBase() == ((FunctionTuple) vec2).getGenericBase())) {
				return super.innerProduct(vec1, vec2);
			} else {
				double product = 0;
				Vector tmp1 = vec1;
//				new GenericFunction() {
//					@Override
//					public Scalar value(Scalar input) {
//						return ((Function) vec1).value(input);
//					}
//				};
				Vector tmp2 = vec2;
//				new GenericFunction() {
//					@Override
//					public Scalar value(Scalar input) {
//						return ((Function) vec2).value(input);
//					}
//				};
//				try { 
//					tmp1=((Function) tmp1).getProjection(this);
//					tmp2=((Function) tmp2).getProjection(this);
//				}
//				catch (Exception e) {
//					System.out.println("Base not created yet.");
//				}
				product += super.innerProduct((Function)tmp1, (Function)tmp2).getValue();
				for (int i = 0; i < this.getDegree(); i++) {
					if (!(tmp1 instanceof FunctionTuple) || derivativeBuilder==null) {
						tmp1 = ((Function) tmp1).getDerivative();
						tmp1 = ((Function) tmp2).getDerivative();
					}
					else {
						tmp1 = derivativeBuilder.get(tmp1);
						tmp1 = derivativeBuilder.get(tmp2);
					}
					product += super.innerProduct(tmp1, tmp2).getValue();
				}
				return new Real(product);
			}
		}
		return super.innerProduct(vec1, vec2);
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		return this.stretch(v, this.innerProduct(w, v));
	}

	/**
	 * Getter for the sobolev degree.
	 * 
	 * @return the sobolev degree
	 */
	public final Integer getDegree() {
		if (degree==null) {
			degree=base.size();
		}
		return this.degree;
	}

	public DerivativeOperator getDerivativeBuilder() {
		if (derivativeBuilder==null) {
			setDerivativeBuilder(new DerivativeOperator(this,this));
		}
		return derivativeBuilder;
	}

	public void setDerivativeBuilder(DerivativeOperator derivativeBuilder) {
		this.derivativeBuilder = derivativeBuilder;
	}

}