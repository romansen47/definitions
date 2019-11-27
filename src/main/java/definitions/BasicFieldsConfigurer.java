package definitions;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.fields.impl.RealLine;

@Configuration
@ComponentScan(basePackages = "definitions")
public class BasicFieldsConfigurer {

	final static private Logger logger = java.util.logging.Logger
			.getLogger(GeneratorConfiguerer.class.getCanonicalName());

	@Autowired
	private RealLine realLine;

	@Bean(name = "realLine")
	public RealLine realLine() {
		return new RealLine();
	}

	public static Logger getLogger() {
		return logger;
	}

	public RealLine getRealLine() {
		return realLine;
	}

	public void setRealLine(RealLine realLine) {
		this.realLine = realLine;
	}
}
