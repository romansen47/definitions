package definitions.structures.abstr.algebra.fields;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface FieldMethods extends XmlPrintable, Serializable {

	public static final Logger logger = LogManager.getLogger(FieldMethods.class);

	default Scalar get(final double value) {
		return null;
	}

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
