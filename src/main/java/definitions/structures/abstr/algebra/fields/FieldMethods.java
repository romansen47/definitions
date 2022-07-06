package definitions.structures.abstr.algebra.fields;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * Interface that provides convenience methods for fields
 * 
 * @author roman
 *
 */
public interface FieldMethods extends XmlPrintable, Serializable {

	public static final Logger logger = LogManager.getLogger(FieldMethods.class);

	/**
	 * method to obtain an element. not really consistent since this makes no sence
	 * for finite fields that are no prime fields
	 * 
	 * @param value the input
	 * @return the field element
	 */
	default Scalar get(final double value) {
		return null;
	}

	/**
	 * Method to show the product matrix of base vectors of the field
	 * 
	 * @param field the field
	 */
	default void show(final Field field) {
		final List<Vector> base = field.genericBaseToList();
		final Scalar[][] products = new Scalar[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			String s = "";
			for (final Vector vec2 : base) {
				products[i][j] = (Scalar) field.product(vec1, vec2);
				s += products[i][j] + " ";
				j++;
			}
			logger.info(s);
			i++;
		}
	}
}
