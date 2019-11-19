package definitions;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;

import definitions.aspects.AspectJ;
 
@AspectJ
public class SpringConfiguration implements ApplicationContextAware{
 
	private static SpringConfiguration springConfiguration;
	
	@Bean(name="springConfiguration")
	public static SpringConfiguration getSpringConfiguration() {
		if(springConfiguration==null) {
			springConfiguration=new SpringConfiguration();
		}
		return springConfiguration;
	}
	
	@Autowired
	private ApplicationContext applicationContext=annotationConfigApplicationContext();
	
	public SpringConfiguration() {
		this.setApplicationContext(applicationContext);
		((AnnotationConfigApplicationContext) this.applicationContext).scan("definitions..*");
		((AbstractApplicationContext) this.applicationContext).refresh();
	}
	
	@Bean(name = "annotationConfigApplicationContext")
	public ApplicationContext annotationConfigApplicationContext() { 
		applicationContext = new AnnotationConfigApplicationContext(); 
		return applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		applicationContext = new AnnotationConfigApplicationContext();
	}
}
