import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import definitions.settings.XmlPrintable;

@Aspect
public class DebugAspect {

	public final static Logger logger = Logger.getLogger(DebugAspect.class);
	private final String path = "target/";
	private final FileWriter w;
	private final BufferedWriter bw;
	private int count = 0;
	private int executionsCount = 0;
	private final Map<Integer, String> entries = new HashMap<>();

	final String pointCut = "execution(* definitions..*(..)) && !execution(java.lang.String definitions.settings.XmlPrintable.toXml()) &&"
			+ "!execution(definitions.structures.abstr.algebra.fields.impl.PrimeField definitions.structures.abstr.algebra.fields.impl.FinitePrimeField.getPrimeField()) && "
			+ "!execution(java.lang.String java.lang.Object.toString()) &&"
			+ "!execution(definitions.structures.abstr.vectorspaces.RingElement definitions.structures.abstr.vectorspaces.Ring.getOne()) &&"
			+ "!execution(* definitions.structures.abstr.algebra.fields.scalars.impl.Real.getValue()) &&"
			+ "!execution(definitions.structures.abstr.algebra.monoids.MonoidElement definitions.structures.abstr.algebra.monoids.DiscreetMonoid.get(java.lang.Integer)) &&"
			+ "!execution(* definitions.structures.euclidean.vectors.impl.Tuple.*(..)) &&"
			+ "!execution(* definitions.structures.euclidean.vectorspaces.EuclideanSpace.genericBaseToList(..)) &&"
			+ "!execution(* *.getInstance(..)) &&" + "!execution(* *.getLogger(..)) &&"
			+ "!execution(void plotter.Plotable.plot(..))";

	public DebugAspect() throws IOException {
		new File(this.path).mkdirs();
		this.w = new FileWriter(this.path + "deep-search.xml");
		this.bw = new BufferedWriter(this.w);
		this.bw.flush();
		logger.info("Created buffered file writer");
	}

//	@AfterReturning(value = pointCut, returning = "returnValue")
	public synchronized void afterLookup(final JoinPoint jp, final Object returnValue) throws Throwable {
		if (AspectsController.getInstance().getRunning() != null && AspectsController.getInstance().getRunning()) {
			this.postCreateXmlEntry(jp, returnValue);
			this.count -= 1;
			if (this.count == 0) {
				AspectsController.getInstance().setRunning(false);
			}
		}
	}

//	@Before(pointCut)
	public synchronized void beforeLookup(final JoinPoint jp) throws Throwable {
		if (AspectsController.getInstance().getRunning() != null && AspectsController.getInstance().getRunning()) {
			this.count += 1;
			this.preCreateXmlEntry(jp);
		}
	}

//	/**
//	 * @return the buffered writer
//	 */
//	public BufferedWriter getBw() {
//		return this.bw;
//	}
//
//	/**
//	 * @return the executionsCount
//	 */
//	public int getExecutionsCount() {
//		return this.executionsCount;
//	}
//
//	/**
//	 * @return the path
//	 */
//	public String getPath() {
//		return this.path;
//	}
//
//	/**
//	 * @return the writer
//	 */
//	public FileWriter getW() {
//		return this.w;
//	}

	private synchronized void postCreateXmlEntry(final JoinPoint jp, final Object o) throws Throwable {
		String ans2 = "";
		ans2 += "</executionsWithin>\r";
		if (o != null) {
			ans2 += "<return>\r";
			if (o instanceof XmlPrintable && ((XmlPrintable) o).toXml() != "") {
				ans2 += ((XmlPrintable) o).toXml();
			} else {
				ans2 += o.toString().split(Pattern.quote("@"))[0] + "\r";
			}
			ans2 += "</return>\r";
		}
		ans2 += "</" + this.entries.get(this.count) + ">";
		this.printToFile(ans2);
	}

	private synchronized void preCreateXmlEntry(final JoinPoint jp) throws Throwable {
		String name = jp.toShortString();
		name = name.split(Pattern.quote("..EnhancerBySpringCGLIB"))[0];
		try {
			name = name.split(Pattern.quote("("))[1];
		} catch (final Exception e) {
			final int i = 0;
		}
		this.entries.put(this.count, name);
		final String str = this.entries.get(this.count);
		String ans = "";
		final Object[] args = jp.getArgs();
		if (args.length > 1) {
			ans += "<arguments>\r";
			for (final Object arg : args) {
				if (arg != null) {
					ans += "<argument>\r";
					if (arg instanceof XmlPrintable) {
						ans += ((XmlPrintable) arg).toXml();
					} else {
						ans += arg.toString().split(Pattern.quote("@"))[0] + "\r";
					}
					ans += "</argument>\r";
				} else {
					ans += "null\r</argument>\r";
				}
			}
			ans += "</arguments>\r";
		}
		if (args.length == 1) {
			ans += "<argument>\r";
			for (final Object arg : args) {
				if (arg != null) {
					if (arg instanceof XmlPrintable) {
						ans += ((XmlPrintable) arg).toXml();
					} else {
						ans += arg.toString().split(Pattern.quote("@"))[0] + "\r";
					}
				} else {
					ans += "null\r";
				}
			}
			ans += "</argument>\r";
		}
		ans += "<executionsWithin>";
		ans = "<" + str + ">\r" + "<count>" + (++this.executionsCount) + "</count>\r" + ans;
		this.printToFile(ans);
	}

	private synchronized void printToFile(final String str) throws IOException {
		System.out.println(str);
		this.bw.write(str);
		this.bw.flush();
	}

//	/**
//	 * @param bw the buffered writer to set
//	 */
//	public void setBw(final BufferedWriter bw) {
//		this.bw = bw;
//	}

//	/**
//	 * @param executionsCount the executionsCount to set
//	 */
//	public void setExecutionsCount(final int executionsCount) {
//		this.executionsCount = executionsCount;
//	}

	// @After("execution(static void definitions.prototypes.AspectJTest.prepare())")
	@Before("@annotation(org.junit.Test)")
	public void setRunningTrueByAnnotation() {
		final Boolean running = AspectsController.getInstance().getRunning();
		if (running == null) {
			AspectsController.getInstance().setRunning(true);
		}
	}

//	/**
//	 * @param w the writer to set
//	 */
//	public void setW(final FileWriter w) {
//		this.w = w;
//	}

}
