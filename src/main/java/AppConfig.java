
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
import aspects.GoDeep;
import aspects.Operation;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.QuaternionSpace;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

@Configuration
@ImportResource("classpath:META-INF/aspectj.xml")
public class AppConfig {

	@Autowired
	private ISpaceGenerator spaceGenerator;

	@Bean("spaceGenerator")
	public ISpaceGenerator getSpaceGenerator() {
		if (spaceGenerator == null) {
			setSpaceGenerator((SpaceGenerator) context.getBean("spaceGenerator"));
		}
		return spaceGenerator;
	}

	@Bean("spaceGenerator")
	public void setSpaceGenerator(ISpaceGenerator gen) {
		spaceGenerator = gen;
	}

	@Autowired
	private CachingAspect cachingAspect;

	@Bean("cachingAspect")
	public CachingAspect getCachingAspect() {
		if (cachingAspect == null) {
			setCachingAspect((CachingAspect) context.getBean("cachingAspect"));
		}
		return cachingAspect;
	}

	@Bean("cachingAspect")
	public void setCachingAspect(CachingAspect accessorAspect) {
		this.cachingAspect = accessorAspect;
	}

	final public Field f = (Field) QuaternionSpace.getInstance();
	List<Vector> base = new ArrayList<>();
	FunctionSpace space;

	Function alpha;
	Function beta;
	Function gamma;

	static ApplicationContext context;

	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext("classpath:META-INF/aspectj.xml");
		GoDeep.print(Thread.currentThread());
		Operation.print(Thread.currentThread());
		AppConfig application = new AppConfig();
		application.prepare();
		application.test();
		System.exit(0);
	}

	@Test
	public void testRun() throws IOException {
		context = new ClassPathXmlApplicationContext("classpath:META-INF/aspectj.xml");
		prepare();
		test();
	}

	public void prepare() {
		getCachingAspect();
		alpha = new GenericFunction() {
			private static final long serialVersionUID = 6698998256903151087L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(f.getOne(),
						new Quaternion(val, -val, 0.1 * Math.cos(val), 0.1 * Math.sin(val)));
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		beta = new GenericFunction() {
			private static final long serialVersionUID = -2624612868740391242L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(f.getOne(),
						new Quaternion(val / 2, val / 2, 0.1 * Math.sin(val), 0.1 * Math.cos(val)));
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};
		gamma = new GenericFunction() {
			private static final long serialVersionUID = -6598973940477311007L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Quaternion) input).getReal().getValue();
				final Quaternion tmp = (Quaternion) f.add(f.getOne(), new Quaternion(val, -val, val, 1 - val));
				if (Math.abs(((Quaternion) input).getReal().getValue()) < 1.e-5) {
					return (Scalar) f.stretch(input, new Real(1.e5));
				}
				return (Scalar) f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return f;
			}
		};

	}

	public void test() {
		this.space = new FiniteDimensionalFunctionSpace(f, this.base, -1, 1, true);

		this.base.add(alpha);
		this.base.add(beta);

		alpha.plot(-Math.PI, Math.PI);
//			beta.plot(-Math.PI, Math.PI);
//			gamma.plot(-Math.PI, Math.PI);
	}
}
