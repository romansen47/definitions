package definitions.structures.euclidean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.cache.MyCache;
import definitions.structures.abstr.fields.impl.FieldGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import plotter.Plotter;
import settings.GlobalSettings;

@SuppressWarnings("unused")
@Configurable
@Configuration
@ComponentScan(basePackages = "definitions..*")
public class Generator implements IGenerator, Plotter {

	private static boolean restoreFromCached = GlobalSettings.RESTORE_FROM_CACHED;

	private static final long serialVersionUID = -5553433829703982950L;
	private final String PATH = GlobalSettings.CACHEDSPACES;

	private static Generator instance;
 
	@Autowired(required = true)
	private MappingGenerator mappingGenerator;

	@Autowired(required = true)
	private SpaceGenerator spaceGenerator;
	
	@Autowired
	private FieldGenerator fieldGenerator;

	public static synchronized Generator getInstance() {
		if (instance==null) {
			instance = new Generator();
		}
		if (restoreFromCached) {
			try {
				instance.loadCoordinateSpaces();
				System.out.println("Cached spaces loaded");
			} catch (final Exception e) {
				System.out.println("Cached spaces not loaded");
			}
			restoreFromCached=false;
		}
		return instance;
	}

	@Override
	public MappingGenerator getMappinggenerator() {
		return mappingGenerator;
	}

	@Override
	public SpaceGenerator getSpacegenerator() {
		return spaceGenerator;
	}

	private FieldGenerator getFieldGenerator() {
		return fieldGenerator;
	}

	private void setFieldGenerator(FieldGenerator fieldGenerator) {
		this.fieldGenerator = fieldGenerator;
	}

	@Override
	public void saveCoordinateSpaces() throws IOException {
		final FileOutputStream f_out = new FileOutputStream(this.PATH);
		final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(spaceGenerator.getMyCache());
		obj_out.close();
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		try {
			final FileInputStream f_in = new FileInputStream(this.PATH);
			final ObjectInputStream obj_in = new ObjectInputStream(f_in);
			final MyCache ans = (MyCache) obj_in.readObject();
			this.spaceGenerator.setMyCache(ans);
			obj_in.close();
		} catch (Exception e) {
			e.addSuppressed(new Exception("failed to load myCache from local file"));
			e.printStackTrace();
		}
	}
 
	public static void setInstance(Generator instance) {
		Generator.instance=instance;
		MappingGenerator.setInstance(instance.mappingGenerator);
		SpaceGenerator.setInstance(instance.spaceGenerator);
		FieldGenerator.setInstance(instance.fieldGenerator);
	}

}