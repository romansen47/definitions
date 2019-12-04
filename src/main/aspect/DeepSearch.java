
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import definitions.settings.XmlPrintable;

@Aspect
public class DeepSearch {

	public final static Map<Thread, List<String>> map = new ConcurrentHashMap<>();
	public final static Map<Thread, String> tests = new ConcurrentHashMap<>();
	final static private String PATH = "target/";
	private static FileWriter w;
	private static BufferedWriter bw;

	public static Boolean active = null;
	static int maxDepth;
	static int depth;

	@Around("execution(* definitions..*(..)) && !execution(* *.toXml(..)) && !execution(* aspects.*.*(..)) && !@annotation(org.junit.Test) && !@annotation(settings.annotations.Proceed) && !execution(* definitions.structures.euclidean.vectorspaces.ISpaceGenerator.getFiniteDimensionalVectorSpace(definitions.structures.abstr.fields.Field,int)) && !execution(* definitions.structures.abstr..*(..)) && !execution(* definitions.structures.euclidean.Generator.*(..))")
	public Object aroundLookup(ProceedingJoinPoint pjp) throws Throwable {
		if (active != null && active) {
			return this.getLookUp(pjp);
		} else {
			return pjp.proceed();
		}
	}

	@Before("@annotation(settings.annotations.Proceed)")
	public synchronized void avoidDeeperSearchBefore(JoinPoint jp) throws Throwable {
		active = false;
	}

	@After("@annotation(settings.annotations.Proceed)")
	public synchronized void avoidDeeperSearchAfter(JoinPoint jp) throws Throwable {
		active = true;
	}

	private Object getLookUp(ProceedingJoinPoint pjp) throws Throwable {
		this.createEnries(pjp);
		return this.createXmlEntry(pjp);
	}

	public String xmlString(Object o) {
		String ans="";
		if (o != null) {
			ans += "<return>\r";
			if (o instanceof XmlPrintable) {
				ans += ((XmlPrintable) o).toXml();
			} else {
				if (o instanceof Integer) {
					ans+="<integer>"+o.toString()+"</integer>";
				}
				if (o instanceof List<?>) {
					for (Object x:(List<?>)o) {
						if (x instanceof XmlPrintable) {
							ans+=((XmlPrintable)x).toXml();
						}
						else {
							ans += "<unknown>" + x.getClass().toString().split("class ")[1] + "</unknown>\r";
						}
					}
				}
				ans += "<unknown>" + o.getClass().toString().split("class ")[1] + "</unknown>\r";
			}
			ans += "</return>\r";
		} else {
			ans += "<return void/>\r";
		}
		return ans;
	}
	
	private Object createXmlEntry(ProceedingJoinPoint pjp) throws Throwable {
		List<String> list = map.getOrDefault(Thread.currentThread(), new ArrayList<>());

		String str = pjp.toShortString().split(Pattern.quote("("))[1];
		String ans = "<" + str + ">\r";
		Object[] args = pjp.getArgs();
		if (args.length > 0) {
			ans += "<arguments>\r";
			for (Object arg : args) {
				ans+=xmlString(arg);
			}
			ans += "</arguments>\r";
		}
		list.add(ans);
		ans = "";
		Object o = pjp.proceed();
		ans+=xmlString(o);
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
