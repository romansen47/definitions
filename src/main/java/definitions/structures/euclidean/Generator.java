package definitions.structures.euclidean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import definitions.Unweavable;
import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.IFieldGenerator;
import definitions.structures.abstr.algebra.fields.impl.FieldGenerator;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.groups.IGroupGenerator;
import definitions.structures.euclidean.mappings.IMappingGenerator;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import plotter.Plotter;

@Configuration
@EnableAspectJAutoProxy
public class Generator implements IGenerator, Unweavable, Plotter, XmlPrintable {

	private static final Logger logger = LogManager.getLogger(Generator.class);

	public Logger getLogger() {
		return Generator.logger;
	}

	private static Generator instance;

	public static Generator getInstance() {
		return instance;
	}

	public static void setInstance(final Generator instance) {
		Generator.instance = instance;
	}

	@Autowired
	private IMappingGenerator mappingGenerator;

	@Autowired
	private ISpaceGenerator spaceGenerator;

	@Autowired
	private IFieldGenerator fieldGenerator;

	@Autowired
	private IGroupGenerator groupGenerator;

	@Override
	public IFieldGenerator getFieldGenerator() {
		return this.fieldGenerator;
	}

	@Override
	public IGroupGenerator getGroupGenerator() {
		return this.groupGenerator;
	}

	@Override
	public IMappingGenerator getMappingGenerator() {
		return this.mappingGenerator;
	}

	@Override
	public ISpaceGenerator getSpaceGenerator() {
		return this.spaceGenerator;
	}

	@Override
	public void setFieldGenerator(final FieldGenerator fieldGenerator) {
		this.fieldGenerator = fieldGenerator;
	}

	public void setGroupGenerator(final GroupGenerator groupGenerator) {
		this.groupGenerator = groupGenerator;
	}

	@Override
	public String toXml() {
		return "the main generator";
	}

}