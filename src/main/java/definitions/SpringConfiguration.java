package definitions;

import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;

import definitions.aspects.AspectJ;

@AspectJ
public class SpringConfiguration {

	private static final Logger logger = Logger.getLogger(SpringConfiguration.class);
 
	private AnnotationConfigApplicationContext annotationConfigApplicationContext=annotationConfigApplicationContext();
	
	public SpringConfiguration() {
		this.annotationConfigApplicationContext.scan("definitions..*");
		((AbstractApplicationContext) this.annotationConfigApplicationContext).refresh();
	}
	
	@Bean(name = "annotationConfigApplicationContext")
	public AnnotationConfigApplicationContext annotationConfigApplicationContext() {
		annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		return annotationConfigApplicationContext;
	}
	
	public ApplicationContext getAnnotationConfigApplicationContext() {
		return this.annotationConfigApplicationContext;
	}
}
