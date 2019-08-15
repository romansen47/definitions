package definitions.structures.abstr.fields;

import java.util.List;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface FieldTechnicalProvider {
	default void show(Field field) {
		final List<Vector> base = field.genericBaseToList();
		final Scalar[][] products = new Scalar[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				products[i][j] = (Scalar) field.product(vec1, vec2);
				if (field instanceof RealLine) {
					System.out.print((products[i][j].getValue() - (products[i][j].getValue() % 0.001)) + ",");
				}
				else {
					System.out.print(products[i][j].toString()+", ");
				}
				j++;
			}
			System.out.println("");
			i++;
		}

	}
}
