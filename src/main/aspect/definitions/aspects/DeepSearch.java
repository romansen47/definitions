package definitions.aspects;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import definitions.settings.XmlPrintable;

@Aspect
@Component
public class DeepSearch implements CustomAspect{
 
	public final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;

	public static Boolean active = null;
	static int maxDepth;
	static int depth;
	 
	@Around("execution(!static * definitions.structures..*(..)) && !execution(* *.toXml(..)) && !execution(* aspects.*.*(..)) && !@annotation(org.junit.Test) && !@annotation(settings.annotations.Proceed) && !execution(* definitions.structures.euclidean.vectorspaces.ISpaceGenerator.getFiniteDimensionalVectorSpace(definitions.structures.abstr.fields.Field,int)) && !execution(* definitions.structures.abstr..*(..)) && !execution(* definitions.structures.euclidean.Generator.*(..))")
	public Object aroundLookup(ProceedingJoinPoint pjp) throws Throwable {
		if (active != null && active) {
			return this.getLookUp(pjp);
		} else {
			return pjp.proceed();
		}
	}
	
//	@Before("@annotation(anno)")
//	public synchronized void avoidDeeperSearchBefore(JoinPoint jp,settings.annotations.Proceed anno) throws Throwable {
//		active=false;
//	}
//	
//	@After("@annotation(anno)")
//	public synchronized void avoidDeeperSearchAfter(JoinPoint jp,settings.annotations.Proceed anno) throws Throwable {
//		active=true;
//	}

	private Object getLookUp(ProceedingJoinPoint pjp) throws Throwable {
		this.createEnries(pjp);
		return this.createXmlEntry(pjp);
	}

	private Object createXmlEntry(ProceedingJoinPoint pjp) throws Throwable {
		List<String> list = map.getOrDefault(Thread.currentThread(), new ArrayList<>());

		String str = pjp.toShortString().split(Pattern.quote("("))[1];
		String ans = "<" + str + ">\r"; 
		Object[] args = pjp.getArgs();
		if (args.length > 0) {
			ans += "<arguments>\r";
			for (Object arg : args) {
				if (arg instanceof XmlPrintable) {
					ans += ((XmlPrintable) arg).toXml();
				} else {
					ans += "<unknownNonXmlPrintableElement " + arg.toString().
							split(Pattern.quote("$"))[0]+ "/>\r";
				}
			}
			ans += "</arguments>\r";
		}
		list.add(ans);
		ans="";
		Object o = pjp.proceed();
		if (o != null) {
			ans += "<return>\r";
			if (o instanceof XmlPrintable) {
				ans += ((XmlPrintable) o).toXml();
			} else {
				ans += "<unknownXmlObject " + o.getClass().toString().split("class ")[1] + "/>\r";
			}
			ans += "</return>\r";
		}
		else {
			ans += "<return void/>\r";
		}
		ans += "</" + str + ">\r";
		list.add(ans);
		return o;
	}

//	private Object createXmlEntry(ProceedingJoinPoint pjp) throws Throwable {
//		
//		List<String> list = map.getOrDefault(Thread.currentThread(),new ArrayList<>());
//		
//		String str = pjp.toShortString().split(Pattern.quote("("))[1];
//		
//		list.add("<" + str + ">\r");
//		XmlPrintable[] args = (XmlPrintable[]) pjp.getArgs();
//		if (args.length > 0) {
//			list.add("<arguments>");
//			for (Object arg : args) {
//				list.add(args.toString());
//			}
//			list.add("</arguments>");
//		}
//		Object o = pjp.proceed();
//		if (o != null) {
//			list.add("<returnValue>" + o.toString().split(Pattern.quote("@"))[0] + "</returnValue>\r");
//		}
//		list.add("</" + str + ">\r");
//		return o;
//	}

	private void createEnries(ProceedingJoinPoint pjp) {
		List<String> list = map.getOrDefault(Thread.currentThread(), new ArrayList<>());
		if (list == null) {
			list = new ArrayList<>();
			map.put(Thread.currentThread(), list);
		}
		String invocator = tests.get(Thread.currentThread());
		if (invocator == null) {
			invocator = pjp.getSignature().toShortString().split(Pattern.quote("@"))[0];
			invocator = invocator.replace("execution(", Pattern.quote("(")).split(Pattern.quote("("))[1];
			tests.put(Thread.currentThread(), invocator);
		}
	}

	public static void print(Thread thread) throws IOException {
		String testcase = tests.get(thread);
		String path = PATH + testcase.replace(Pattern.quote("."), "/") + "/" + "deep-search.xml";
		new File(PATH + testcase).mkdirs();
		w = new FileWriter(path);
		bw = new BufferedWriter(w);
		bw.write("<" + testcase + ">\r");
		bw.flush();
		List<String> list = map.get(thread);
		if (list == null) {
			java.util.logging.Logger.getLogger("DeepSearch").info("list empty");
		} else {
			for (String str : list) {
				bw.write(str);
				bw.flush();
			}
		}
		bw.write("</" + testcase + ">");
		bw.flush();
		bw.close();
		w.close();
	}
}
