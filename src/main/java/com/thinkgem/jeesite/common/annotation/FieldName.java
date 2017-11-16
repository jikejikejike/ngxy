package com.thinkgem.jeesite.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
<<<<<<< HEAD
 * bean中文名注解             
=======
 * bean中文名注解  
>>>>>>> dee9ec466e63c0038229d2b3a0c8eea73a8e2632
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface FieldName {

	String value();     
	
}
