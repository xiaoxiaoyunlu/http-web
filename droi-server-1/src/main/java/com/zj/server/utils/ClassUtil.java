package com.zj.server.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;

public class ClassUtil {
	public static Method[] getAllMethodOf(Class<?> courseClass)
	  {
	    Method[] methods = null;
	    
	    Class<?> itr = courseClass;
	    while (!itr.equals(Object.class))
	    {
	      methods = (Method[])ArrayUtils.addAll(itr.getDeclaredMethods(), methods);
	      itr = itr.getSuperclass();
	    }
	    return methods;
	  }
	
	  public static String getClassLowerName(Class<?> clz){
		  return getLowerFirstChar(getSimpleName(clz));
	  }
	  
	  public static String getSimpleName(Class<?> c)
	  {
	    return null != c ? c.getSimpleName() : null;
	  }
	  
	  public static String getLowerFirstChar(String str){
		  char[] charArray = str.toCharArray();
		  charArray[0]+=32;
		  return String.valueOf(charArray);
	  }
}
