//package definitions;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import definitions.structures.abstr.fields.impl.RealLine;
//import definitions.structures.euclidean.Generator;
// 
//@Configuration 
//public class GeneratorConfiguerer {
//
//	final static private Logger logger = LogManager.getLogger(GeneratorConfiguerer.class.getCanonicalName());
//
//	@Autowired(required = true)
//	private Generator generator;
//
//	@Autowired
//	private RealLine realLine;
//		
////	@Bean(name = "realLine")
////	public RealLine realLine() {
////		return RealLine.getInstance();
////	}
//
//	public static Logger getLogger() {
//		return logger;
//	}
//
//	public Generator getGenerator() {
//		return generator;
//	}
//
//	public void setGenerator(Generator generator) {
//		this.generator = generator;
//	}
//
//	public RealLine getRealLine() {
//		return realLine;
//	}
//
//	public void setRealLine(RealLine realLine) {
//		this.realLine = realLine;
//	} 
//
//}
