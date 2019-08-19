package solver;
//package definitions.solver;
//
//import definitions.structures.abstr.fields.Field;
//import definitions.structures.abstr.fields.impl.RealLine;
//import definitions.structures.abstr.fields.scalars.Scalar;
//import definitions.structures.abstr.fields.scalars.impl.Real;
//import definitions.structures.abstr.vectorspaces.vectors.Function;
//import definitions.structures.euclidean.vectors.impl.GenericFunction;
//
//public class ExpliciteEulerSolver implements Solver {
//
//	final Field f = RealLine.getInstance();
//	public Function fun;
//	double initialData;
//	final double eps;
//
//	public ExpliciteEulerSolver(final Function fun, final double initialValue, final double eps) {
//		this.fun = fun;
//		this.initialData = initialValue;
//		this.eps = eps;
//	}
//
//	@Override
//	public Function solve() {
//		return new GenericFunction() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 3792649640196647131L;
//
//			@Override
//			public Scalar value(final Scalar input) {
//				double ans = ExpliciteEulerSolver.this.initialData;
//				double xval = -Math.PI;
//				while (xval < input.getValue()) {
//					ans += ExpliciteEulerSolver.this.eps
//							* ExpliciteEulerSolver.this.fun.value(new Real(xval)).getValue();
//					xval += ExpliciteEulerSolver.this.eps;
//				}
//				return new Real(ans);
//			}
//
//			@Override
//			public Field getField() {
//				return ExpliciteEulerSolver.this.f;
//			}
//		};
//	}
//}
