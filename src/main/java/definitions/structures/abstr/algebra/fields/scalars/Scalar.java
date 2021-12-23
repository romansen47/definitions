package definitions.structures.abstr.algebra.fields.scalars;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface Scalar extends Element, Vector, FiniteVectorMethods, XmlPrintable {

	/**
	 * This has practical reasons. Gives double value, if possible.
	 *
	 * @return the double value
	 */
	//	double getDoubleValue();

	/**
	 * {@inheritDoc}
	 */
	//	@Override
	//	default String toXml() {
	//		return "<" + getClass().toString().split("class ")[1] + " value=\"" + this.getDoubleValue() + "\" />";
	//	}

}
