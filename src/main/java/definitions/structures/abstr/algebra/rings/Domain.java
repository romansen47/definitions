package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.vectorspaces.Ring;

/**
 *
 * @author ro
 *
 *         Domain is a (possibly non-commutative) ring, where
 *
 *         a*x = b*x for non-zero x always implies a=b. This means that the
 *         mapping
 *
 *         a |- a*x is injective for every non-zero x.
 *
 */
public interface Domain extends Ring, SemiDomain {

}
