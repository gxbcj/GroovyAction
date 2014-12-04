package com.gxb.groovyExtension;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import groovy.lang.GroovyClassLoader;

import javax.servlet.ServletContext;

import com.gxb.util.Constants;
import com.opensymphony.xwork2.inject.Inject;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.spring.StrutsSpringObjectFactory;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.ClassUtils;

import com.opensymphony.xwork2.inject.Container;

public class GxbObjectFactory extends StrutsSpringObjectFactory{

	private static final long serialVersionUID = 1L;
	private final Map<String, Object> groovyClasses = new HashMap<String, Object>();

	
	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader(ClassUtils.getDefaultClassLoader());

	private String srcPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
	
	private Boolean groovyCache = true;
	
	@Inject
    public GxbObjectFactory(
            @Inject(value=StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE,required=false) String autoWire,
            @Inject(value=StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE_ALWAYS_RESPECT,required=false) String alwaysAutoWire,
            @Inject(value=StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_USE_CLASS_CACHE,required=false) String useClassCacheStr,
            @Inject ServletContext servletContext,
            @Inject(StrutsConstants.STRUTS_DEVMODE) String devMode,
            @Inject Container container) {
		super(autoWire, alwaysAutoWire, useClassCacheStr, servletContext, devMode,
				container);
	}

	@Inject(value = "struts.convention.action.groovyCache", required = false)
	public void setGroovyCache(String groovyCache) {
        this.groovyCache = "true".equals(groovyCache);
    }
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassInstance(String className) throws ClassNotFoundException{
		if (!className.endsWith(Constants.Groovy_SUFFIX)) {
			return super.getClassInstance(className);
		} else {
			Class clz = null;
//			className = className.substring(0, className.length() - GROOVY_EXTENSION.length());
			if( groovyCache ){
				synchronized(groovyClasses) {
					clz = (Class) groovyClasses.get(className);
				}
			}
			if( clz == null){
				try {
					clz = getScript(className);
					if (groovyCache) {
		                synchronized(groovyClasses) {
		                	groovyClasses.put(className, clz);
		                }
		            }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return clz;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Class getScript(String className) throws Exception {
		String newClassName = className.replace(".", "/");
		String groovyPath = srcPath+newClassName+".groovy";
		URL url = new URL(groovyPath);
		Class scriptClass = groovyClassLoader.parseClass(DefaultGroovyMethods.getText(url, "UTF-8") );
		return scriptClass;
	}
	

}
