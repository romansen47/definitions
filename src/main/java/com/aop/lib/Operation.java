package com.aop.lib;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class Operation{
	
	private FileWriter w;
	
	private BufferedWriter bw;
	
	private static int i = 0;
	
	boolean tracing = false;

	@Around("@annotation( wrapAnnotation ) && execution(* *(..))")
	public Object processSystemRequest(final ProceedingJoinPoint pjp, Trace wrapAnnotation) throws Throwable {
//		System.out.println(i++);
		if (wrapAnnotation.initial()) {
			tracing = true;
		}
		if (!tracing || !wrapAnnotation.trace()) {
			return pjp.proceed();
		}
		if (w == null) {
			w = new FileWriter("C:/tmp/TestRun.xml");
			bw = new BufferedWriter(w);
		}
		String ans = pjp.toShortString();
		int k = ans.length();
		ans = (ans.substring(10, k).split("@")[0]);
		k = ans.length();
		if (!wrapAnnotation.initial()) {
			ans = ans.substring(0, k - 5);
		} else {
			ans = ans.substring(0, k - 3);
		}
		bw.write("<" + ans + ">\r");
		bw.flush();
		String tmp1 = "";
		if (pjp.getThis() != null) {
			tmp1 += "<this>\r";
			tmp1 += (pjp.getThis().toString().split("@")[0]) + "\r";
			tmp1 += "</this>";
			bw.write(tmp1 + "\r");
		}
		Object o = pjp.proceed();
		bw.write("</" + ans + ">\r");
		bw.flush();
		return o;
	}

}
