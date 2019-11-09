
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aspects.CachingAspect;
import aspects.DeepSearch;
import aspects.PointcutSearch;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMappingTest;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@Configuration
@ImportResource("classpath:META-INF/aspectj.xml")
public class AppConfig {

//	@Autowired
//	private ISpaceGenerator spaceGenerator;
//
//	@Bean("spaceGenerator")
//	public ISpaceGenerator getSpaceGenerator() {
//		if (spaceGenerator == null) {
//			setSpaceGenerator((SpaceGenerator) context.getBean("spaceGenerator"));
//		}
//		return spaceGenerator;
//	}
//
//	@Bean("spaceGenerator")
//	public void setSpaceGenerator(ISpaceGenerator gen) {
//		spaceGenerator = gen;
//	}
//
//	@Autowired
//	private CachingAspect cachingAspect;
//
//	@Bean("cachingAspect")
//	public CachingAspect getCachingAspect() {
//		if (cachingAspect == null) {
//			setCachingAspect((CachingAspect) context.getBean("cachingAspect"));
//		}
//		return cachingAspect;
//	}
//
//	@Bean("cachingAspect")
//	public void setCachingAspect(CachingAspect accessorAspect) {
//		this.cachingAspect = accessorAspect;
//	}
//
//	final public Field f = (Field) QuaternionSpace.getInstance();
//	List<Vector> base = new ArrayList<>();
//	FunctionSpace space;
//
//	Function alpha;
//	Function beta;
//	Function gamma;

	static ApplicationContext context;

	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext("classpath:META-INF/aspectj.xml");
		AppConfig application = new AppConfig();
		DeepSearch.print(Thread.currentThread());
		PointcutSearch.print(Thread.currentThread());
		System.exit(0);
	}

	@Test
	public void testRun() throws IOException {
		context = new ClassPathXmlApplicationContext("classpath:META-INF/aspectj.xml");
		new FiniteDimensionalLinearMappingTest().testGetGenericMatrix();
	}

}
