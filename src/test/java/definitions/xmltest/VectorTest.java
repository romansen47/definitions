package definitions.xmltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import definitions.SpringConfiguration;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
// 
public class VectorTest{
 
	private SpringConfiguration springConfiguration = SpringConfiguration.getSpringConfiguration();
	private Generator gen=(Generator) springConfiguration.getApplicationContext().getBean("generator");
	private SpaceGenerator spaceGen=(SpaceGenerator) springConfiguration.getApplicationContext().getBean("spaceGenerator");
	
	@Test
	public void test() {
		EuclideanSpace space =(EuclideanSpace) spaceGen.getFiniteDimensionalComplexSpace(1);
		Vector vec=space.genericBaseToList().get(0);
	}

	public SpringConfiguration getSpringConfiguration() {
		return springConfiguration;
	}

	public void setSpringConfiguration(SpringConfiguration springConfiguration) {
		this.springConfiguration = springConfiguration;
	} 

}
