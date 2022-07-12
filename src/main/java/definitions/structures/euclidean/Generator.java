package definitions.structures.euclidean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import definitions.Unweavable;
import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.impl.FieldGenerator;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import plotter.Plotter;

@Service
@ComponentScan(basePackages = "definitions")
public class Generator implements IGenerator, Unweavable, Plotter, XmlPrintable {

	private static final Logger logger = LogManager.getLogger(Generator.class);

	private static Generator instance;

	public static Generator getInstance() {
		if (Generator.instance == null) {
			Generator.instance = new Generator();
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