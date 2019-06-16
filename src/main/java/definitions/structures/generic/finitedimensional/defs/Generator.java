package definitions.structures.generic.finitedimensional.defs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.mappings.IMappingGenerator;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.ISpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IVectorGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.VectorGenerator;

@SuppressWarnings("unused")
public class Generator implements IGenerator {

	private final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	private final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();
	private ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	private final String PATH="coordinateSpaces.data";
	private final String PATH2="functionSapces.data";

	private static Generator generator = null;

	@Override
	public Map<Integer, EuclideanSpace> getCachedSpaces() {
		return getSpacegenerator().getCachedCoordinateSpaces();
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
		FileOutputStream f_out = new FileOutputStream(PATH);
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(spaceGenerator);
		obj_out.close();
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		FileInputStream f_in = new FileInputStream(PATH);
		ObjectInputStream obj_in = new ObjectInputStream(f_in);
		spaceGenerator.setCachedCoordinateSpaces((SpaceGenerator) obj_in.readObject());
		obj_in.close();
	}
	@Override
	public void saveFunctionSpaces() throws IOException {
		FileOutputStream f_out = new FileOutputStream(PATH2);
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(spaceGenerator);
		obj_out.close();
	}

	@Override
	public void loadFunctionSpaces() throws IOException, ClassNotFoundException {
		FileInputStream f_in = new FileInputStream(PATH2);
		ObjectInputStream obj_in = new ObjectInputStream(f_in);
		spaceGenerator.setCachedCoordinateSpaces((SpaceGenerator) obj_in.readObject());
		obj_in.close();
	}
}