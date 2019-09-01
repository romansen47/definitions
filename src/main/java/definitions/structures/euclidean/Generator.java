package definitions.structures.euclidean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectors.IVectorGenerator;
import definitions.structures.euclidean.vectors.impl.VectorGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@SuppressWarnings("unused")
public class Generator implements IGenerator {

	private final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	private final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();
	private final ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	private final String PATH = "src/main/resources/coordinateSpaces.data";
	private final String PATH2 = "src/main/resources/functionSpaces.data";

	private static Generator generator = null;

	@Override
	public Map<Integer, EuclideanSpace> getCachedSpaces() {
		return this.getSpacegenerator().getCachedCoordinateSpaces();
	}

	public static IGenerator getGenerator() {
		if (generator == null) {
			generator = new Generator();
		}
		return generator;
	}

	private Generator() {
	}

	@Override
	public IVectorGenerator getVectorgenerator() {
		return generator.vectorGenerator;
	}

	@Override
	public IMappingGenerator getMappinggenerator() {
		return generator.mappingGenerator;
	}

	@Override
	public ISpaceGenerator getSpacegenerator() {
		return generator.spaceGenerator;
	}

	@Override
	public void saveCoordinateSpaces() throws IOException {
		final FileOutputStream f_out = new FileOutputStream(this.PATH);
		final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(this.spaceGenerator.getCachedCoordinateSpaces());
		obj_out.close();
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		final FileInputStream f_in = new FileInputStream(this.PATH);
		final ObjectInputStream obj_in = new ObjectInputStream(f_in);
		final Map<Integer, EuclideanSpace> ans = (Map<Integer, EuclideanSpace>) obj_in.readObject();
		this.spaceGenerator.setCachedCoordinateSpaces(ans);
		for (final Integer i : this.spaceGenerator.getCachedCoordinateSpaces().keySet()) {
			this.spaceGenerator.getMyCache().put(new Long(i), this.spaceGenerator.getCachedCoordinateSpaces().get(i));
		}
		obj_in.close();
	}

	@Override
	public void saveFunctionSpaces() throws IOException {
		final FileOutputStream f_out = new FileOutputStream(this.PATH2);
		final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(this.spaceGenerator.getCachedFunctionSpaces());
		obj_out.close();
	}

	@Override
	public void loadFunctionSpaces() throws IOException, ClassNotFoundException {
		final FileInputStream f_in = new FileInputStream(this.PATH2);
		final ObjectInputStream obj_in = new ObjectInputStream(f_in);
		this.spaceGenerator.setCachedFunctionSpaces((Map<Integer, EuclideanFunctionSpace>) obj_in.readObject());
		obj_in.close();
	}
}