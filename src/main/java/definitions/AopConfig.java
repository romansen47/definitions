package definitions;

import java.util.logging.Logger;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.aspects.CachingAspect;
import definitions.aspects.DeepSearch;
import definitions.aspects.DistributionCollector;
import definitions.aspects.PointcutSearch;
import definitions.aspects.TestAspect;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@Configuration
@ComponentScan(basePackages = "definitions")
public class AopConfig implements AspectJConfig {
	
	final static private Logger logger = java.util.logging.Logger.getLogger(AopConfig.class.getCanonicalName());

	@Autowired(required = true)
	private CachingAspect cachingAspect; 

	@Autowired(required = true)
	private DeepSearch deepSearch; 

	@Autowired(required = true)
	private DistributionCollector distributionCollector; 

	@Autowired(required = true)
	private PointcutSearch pointcutSearch; 

	@Autowired(required = true)
	private TestAspect testAspect; 

	@Autowired(required = true)
	private Generator generator; 

	public static Logger getLogger() {
		return logger;
	}

	public CachingAspect getCachingAspect() {
		return cachingAspect;
	}

	public void setCachingAspect(CachingAspect cachingAspect) {
		this.cachingAspect = cachingAspect;
	}

	public DeepSearch getDeepSearch() {
		return deepSearch;
	}

	public void setDeepSearch(DeepSearch deepSearch) {
		this.deepSearch = deepSearch;
	}

	public DistributionCollector getDistributionCollector() {
		return distributionCollector;
	}

	public void setDistributionCollector(DistributionCollector distributionCollector) {
		this.distributionCollector = distributionCollector;
	}

	public PointcutSearch getPointcutSearch() {
		return pointcutSearch;
	}

	public void setPointcutSearch(PointcutSearch pointcutSearch) {
		this.pointcutSearch = pointcutSearch;
	}

	public TestAspect getTestAspect() {
		return testAspect;
	}

	public void setTestAspect(TestAspect testAspect) {
		this.testAspect = testAspect;
	}
	
	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
}
