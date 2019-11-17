package definitions.structures.euclidean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.cache.MyCache;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import plotter.Plotter;
import settings.GlobalSettings;

@SuppressWarnings("unused")
@Configuration
@ComponentScan(basePackages = "definitions..*")
public class Generator implements IGenerator, Plotter {

	final private static boolean restoreFromCached = GlobalSettings.RESTORE_FROM_CACHED;

	private static final long serialVersionUID = -5553433829703982950L;

	@Autowired(required = true)
	private MappingGenerator mappingGenerator;// = MappingGenerator.getInstance();

	@Autowired(required = true)
	private SpaceGenerator spaceGenerator;// = SpaceGenerator.getInstance();

	private final String PATH = GlobalSettings.CACHEDSPACES;

	private static Generator generator;
	
	@Bean	
	public static Generator getGenerator() {
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

	@Override
	public MappingGenerator getMappinggenerator() {
		if (getGenerator().mappingGenerator==null) {
			generator.mappingGenerator=mappingGenerator();
		}
		return generator.mappingGenerator;
	}

	@Override
	public SpaceGenerator getSpacegenerator() {
		if (getGenerator().spaceGenerator==null) {
			generator.spaceGenerator=spaceGenerator();
		}
		return generator.spaceGenerator;
	}

	@Override
	public void saveCoordinateSpaces() throws IOException {
		final FileOutputStream f_out = new FileOutputStream(this.PATH);
		final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(this.spaceGenerator.getMyCache());
		obj_out.close();
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		final FileInputStream f_in = new FileInputStream(this.PATH);
		final ObjectInputStream obj_in = new ObjectInputStream(f_in);
		final MyCache ans = (MyCache) obj_in.readObject();
		this.spaceGenerator.setMyCache(ans);
		obj_in.close();
	}

	@Bean
	public SpaceGenerator spaceGenerator() {
		return new SpaceGenerator();
	}

	@Bean
	public MappingGenerator mappingGenerator() {
		return new MappingGenerator();
	}

}