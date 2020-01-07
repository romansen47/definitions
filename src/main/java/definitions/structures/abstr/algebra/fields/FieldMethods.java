package definitions.structures.abstr.algebra.fields;

import java.io.Serializable;
import java.util.List;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface FieldMethods extends XmlPrintable, Serializable {

	default Scalar get(final double value) {
		return null;
	}

	default void show(final Field field) {
		final List<Vector> base = field.genericBaseToList();
		final Scalar[][] products = new Scalar[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				products[i][j] = (Scalar) field.product(vec1, vec2);
				if (field instanceof RealLine) {
					System.out.print((products[i][j].getDoubleValue() - (products[i][j].getDoubleValue() % 0.001)) + ",");
				} else {
					System.out.print(products[i][j].toString() + ", ");
				}
				j++;
			}
			System.out.println("");
			i++;
		}
	}
}
