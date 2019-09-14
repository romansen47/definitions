//package definitions.structures.java;
//
//import java.io.FileOutputStream;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Marshaller;
//
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import definitions.structures.abstr.fields.scalars.impl.Real;
//import definitions.structures.euclidean.Generator;
//import definitions.structures.euclidean.vectors.impl.Tuple;
//import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
//
//public class XmlTest {
//
//	final static int dim = 1;
//
//	final static EuclideanSpace space = Generator.getGenerator().getSpacegenerator()
//			.getFiniteDimensionalVectorSpace(dim);
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		try {
//			// creating the JAXB context
//			JAXBContext jContext = JAXBContext.newInstance(Tuple.class);
//			// creating the marshaller object
//			Marshaller marshallObj = jContext.createMarshaller();
//			// setting the property to show xml format output
//			marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			// setting the values in POJO class
//			// calling the marshall method
//			marshallObj.marshal(new Real(Math.PI), new FileOutputStream("src/main/resources/PI.xml"));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
//	public final void test() {
//		Assert.assertTrue(true);
//	}
//
//}
