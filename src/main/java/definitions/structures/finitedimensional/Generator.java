package definitions.structures.finitedimensional;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import definitions.structures.finitedimensional.mappings.IMappingGenerator;
import definitions.structures.finitedimensional.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.vectors.IVectorGenerator;
import definitions.structures.finitedimensional.vectors.impl.VectorGenerator;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.ISpaceGenerator;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

@SuppressWarnings("unused")
public class Generator implements IGenerator {

	private final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	private final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();
	private final ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	private final String PATH = "coordinateSpaces.data";
	private final String PATH2 = "functionSapces.data";

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
		obj_out.writeObject(this.spaceGenerator);
		obj_out.close();
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		final FileInputStream f_in = new FileInputStream(this.PATH);
		final ObjectInputStream obj_in = new ObjectInputStream(f_in);
		this.spaceGenerator.setCachedCoordinateSpaces((SpaceGenerator) obj_in.readObject());
		obj_in.close();
	}

	@Override
	public void saveFunctionSpaces() throws IOException {
		final FileOutputStream f_out = new FileOutputStream(this.PATH2);
		final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(this.spaceGenerator);
		obj_out.close();
	}

	@Override
	public void loadFunctionSpaces() throws IOException, ClassNotFoundException {
		final FileInputStream f_in = new FileInputStream(this.PATH2);
		final ObjectInputStream obj_in = new ObjectInputStream(f_in);
		this.spaceGenerator.setCachedCoordinateSpaces((SpaceGenerator) obj_in.readObject());
		obj_in.close();
	}
}