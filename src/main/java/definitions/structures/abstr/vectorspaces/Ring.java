package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * 
 * @author ro
 *
 *         A ring is a group R,such that R without the identity element is a
 *         monoid M.
 * 
 *         Also, the operation-mappings must collaborate in the manner of
 *         associativity. That means,
 * 
 *         for every ring element a, every ring element b and every monoid
 *         element c we have
 * 
 *         M.operation(R.operation(a, b), c) =
 *         R.operation(M.operation(a,c),M.operation(b,c))
 *
 */
public interface Ring extends Group,SemiRing {

}
