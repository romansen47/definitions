package definitions;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;

@Configurable
@Configuration 
public class GeneratorConfiguerer {

	final static private Logger logger = java.util.logging.Logger.getLogger(GeneratorConfiguerer.class.getCanonicalName());

	@Autowired(required = true)
	private Generator generator;

	@Autowired
	private RealLine realLine;
		
	@Bean(name = "realLine")
	public RealLine realLine() {
		return RealLine.getInstance();
	}

	public static Logger getLogger() {
		return logger;
	}

	public Generator getGenerator() {
		return generator;
	}

	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

	public RealLine getRealLine() {
		return realLine;
	}

	public void setRealLine(RealLine realLine) {
		this.realLine = realLine;
	} 

}
