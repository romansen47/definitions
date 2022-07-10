package definitions.structures.euclidean;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.cache.MyCache;
import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.impl.FieldGenerator;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import plotter.Plotter;
import settings.GlobalSettings;

@Service
@ComponentScan(basePackages = "definitions")
public class Generator implements IGenerator, Unweavable, Plotter, XmlPrintable {

	private static final Logger logger = LogManager.getLogger(Generator.class);

	private static boolean restoreFromCached = GlobalSettings.RESTORE_FROM_CACHED;

	private static final long serialVersionUID = -5553433829703982950L;

	private static Generator instance;

	public static Generator getInstance() {
		if (Generator.instance == null) {
			Generator.instance = new Generator();
		}
		if (Generator.restoreFromCached) {
			try {
				Generator.instance.loadCoordinateSpaces();
				LogManager.getLogger(IGenerator.class).info("Cached spaces loaded");
			} catch (final Exception e) {
				LogManager.getLogger(IGenerator.class).warn("Cached spaces not loaded");
			}
			Generator.restoreFromCached = false;
		}
		return Generator.instance;
	}

	public static void setInstance(final Generator instance) {
		Generator.instance = instance;
		MappingGenerator.setInstance(instance.mappingGenerator);
		SpaceGenerator.setInstance(instance.spaceGenerator);
		FieldGenerator.setInstance(instance.fieldGenerator);
		GroupGenerator.setInstance(instance.groupGenerator);
	}

	private static final String PATH = GlobalSettings.CACHEDSPACES;

	@Autowired(required = true)
	private MappingGenerator mappingGenerator;

	@Autowired(required = true)
	private SpaceGenerator spaceGenerator;

	@Autowired
	private FieldGenerator fieldGenerator;

	@Autowired
	private GroupGenerator groupGenerator;

	@Override
	public FieldGenerator getFieldGenerator() {
		return fieldGenerator;
	}

	@Override
	public GroupGenerator getGroupGenerator() {
		return groupGenerator;
	}

	public Logger getLogger() {
		return Generator.logger;
	}

	@Override
	public MappingGenerator getMappingGenerator() {
		return mappingGenerator;
	}

	@Override
	public SpaceGenerator getSpaceGenerator() {
		return spaceGenerator;
	}

	@Override
	public void loadCoordinateSpaces() throws IOException, ClassNotFoundException {
		FileInputStream fin = null;
		ObjectInputStream objin = null;
		try {
			fin = new FileInputStream(PATH);
			objin = new ObjectInputStream(fin);
			final MyCache ans = (MyCache) objin.readObject();
			spaceGenerator.setMyCache(ans);
		} catch (final Exception e) {
			logger.info("failed to load myCache from local file");
		} finally {
			if (objin != null) {
				objin.close();
			}
			if (fin != null) {
				fin.close();
			}
		}
	}

	@Override
	public void saveCoordinateSpaces() throws IOException {
		FileOutputStream fout = null;
		ObjectOutputStream objout = null;
		try {
			fout = new FileOutputStream(PATH);
			objout = new ObjectOutputStream(fout);
			objout.writeObject(spaceGenerator.getMyCache());
			LogManager.getLogger(IGenerator.class).info("saved coordinates spaces to disk");
		} finally {
			if (objout != null) {
				objout.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
	}

	@Override
	public void setFieldGenerator(final FieldGenerator fieldGenerator) {
		this.fieldGenerator = fieldGenerator;
	}

	public void setGroupGenerator(final GroupGenerator groupGenerator) {
		GroupGenerator.setInstance(groupGenerator);
		this.groupGenerator = groupGenerator;
	}

	@Override
	public String toXml() {
		return "the main generator";
	}
}