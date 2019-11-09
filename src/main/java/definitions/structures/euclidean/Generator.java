package definitions.structures.euclidean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cache.ICache;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import plotter.Plotter;
import settings.GlobalSettings;

@SuppressWarnings("unused")
@Configuration
public class Generator implements IGenerator,Plotter {

	@Autowired
	final private static boolean restoreFromCached = GlobalSettings.RESTORE_FROM_CACHED;

	private static final long serialVersionUID = -5553433829703982950L;
//	private final IVectorGenerator vectorGenerator = VectorGenerator.getInstance();
	
	@Autowired
	private final IMappingGenerator mappingGenerator = MappingGenerator.getInstance();
	
	@Autowired
	private final ISpaceGenerator spaceGenerator = SpaceGenerator.getInstance();
	
	@Autowired
	private final String PATH = GlobalSettings.CACHEDSPACES;

	@Autowired
	private static Generator generator;

	@Bean
	public static IGenerator getGenerator() {
		if (generator == null) {
			generator = new Generator();
			if (restoreFromCached) {
				try {
					generator.loadCoordinateSpaces();
					System.out.println("Cached spaces loaded");
				} catch (final Exception e) {
					System.out.println("Cached spaces not loaded");
				}
			}
		}
		return generator;
	}

	private Generator() {
	}

//	@Override
//	public IVectorGenerator getVectorgenerator() {
//		return generator.vectorGenerator;
//	}

	@Override
	@Bean
	public IMappingGenerator getMappinggenerator() {
		return generator.mappingGenerator;
	}

	@Override
	@Bean
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
		obj_in.close();
	}

}