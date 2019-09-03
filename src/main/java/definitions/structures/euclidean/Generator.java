package definitions.structures.euclidean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cache.ICache;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectors.IVectorGenerator;
import definitions.structures.euclidean.vectors.impl.VectorGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@SuppressWarnings("unused")
public class Generator implements IGenerator {

	private static final long serialVersionUID = -5553433829703982950L;
	private final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	private final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();
	private final ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	private final String PATH = "src/main/resources/coordinateSpaces.data";
	private final String PATH2 = "src/main/resources/functionSpaces.data";

	private static Generator generator = null;

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
		obj_out.writeObject(this.spaceGenerator.getCache());
		obj_out.close();
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		final FileInputStream f_in = new FileInputStream(this.PATH);
		final ObjectInputStream obj_in = new ObjectInputStream(f_in);
		final ICache ans = (ICache) obj_in.readObject();
		this.spaceGenerator.setCache(ans);
//		for (final Integer i : this.spaceGenerator.getCachedCoordinateSpaces().keySet()) {
//			this.spaceGenerator.getMyCache().put(new Long(i), this.spaceGenerator.getCachedCoordinateSpaces().get(i));
//		}
		obj_in.close();
	}

}