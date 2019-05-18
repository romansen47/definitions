package definitions.structures.generic.finitedimensional.defs.subspaces;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.InvertibleFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.Automorphism;
import definitions.structures.generic.finitedimensional.defs.mappings.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.IFiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;
import junit.framework.Assert;

public class FiniteDimensionalSubSpaceTest {

	static IFiniteVector e1;
	static IFiniteVector e2;
	static IFiniteVector e3;

	static double[][] matrix = new double[][] { { 1, 3 }, { 3, 2 }, { 1, 0 } };
	static List<IFiniteVector> list = new ArrayList<>();
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new FiniteVector(new double[] { 1, 0, 0 });
		e2 = new FiniteVector(new double[] { 0, 1, 0 });
		e3 = new FiniteVector(new double[] { 0, 0, 1 });

		IFiniteDimensionalLinearMapping map = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(matrix);
		IFiniteDimensionalSubSpace subSpace = new FiniteDimensionalSubSpace(map);

		@SuppressWarnings("unused")
		Set<IFiniteVector> test = subSpace.getGenericBase();

		List<IFiniteVector> base = subSpace.genericBaseToList();
		Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates = new HashMap<>();

		Map<IFiniteVector, Double> key1 = new HashMap<>();
		key1.put(base.get(0), 0.0);
		key1.put(base.get(1), 2.);

		Map<IFiniteVector, Double> key2 = new HashMap<>();
		key2.put(base.get(0), -0.5);
		key2.put(base.get(1), 0.0);

		coordinates.put(base.get(0), key1);
		coordinates.put(base.get(1), key2);

		IFiniteDimensionalLinearMapping testMap = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(subSpace, subSpace, coordinates);

		list.add(base.get(0));
		for (int i = 0; i < 4; i++) {
			list.add(testMap.get(list.get(list.size() - 1)));
		}
		System.out.println("Bye bye");
	}

	@Test
	public void first1() throws Throwable {
		boolean ans = list.get(0).equals(list.get(4));
	}

}
