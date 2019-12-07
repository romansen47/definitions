package definitions;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "definitions")
public class BasicFieldsConfigurer {

	final static private Logger logger = org.apache.log4j.Logger
			.getLogger(GeneratorConfiguerer.class.getCanonicalName());

	public static Logger getLogger() {
		return logger;
	}
}
