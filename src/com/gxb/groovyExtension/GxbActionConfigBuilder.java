package com.gxb.groovyExtension;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.net.URL;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.PackageBasedActionConfigBuilder;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.ClassUtils;

import com.gxb.util.Constants;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

public class GxbActionConfigBuilder extends PackageBasedActionConfigBuilder{

	private String[] groovyActionPackages;
	
	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader(ClassUtils.getDefaultClassLoader());
	private String srcPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
	
	@Inject(value = "struts.convention.action.groovyPackages", required = false)
    public void setGrrovyActionPackages(String actionPackages) {
        if (StringUtils.isNotBlank(actionPackages)) {
            this.groovyActionPackages = actionPackages.split("\\s*[,]\\s*");
        }
    }
	
    @Inject
	public GxbActionConfigBuilder(Configuration configuration, Container container, ObjectFactory objectFactory,
            @Inject("struts.convention.redirect.to.slash") String redirectToSlash,
            @Inject("struts.convention.default.parent.package") String defaultParentPackage) {
		super(configuration, container, objectFactory, redirectToSlash, defaultParentPackage);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Set<Class> findActions()  {
		Set<Class> set = super.findActions();
		for( int index = 0; groovyActionPackages != null && index < groovyActionPackages.length; index++ ){
			try{
				String groovyPackage = srcPath+groovyActionPackages[index];
				URL url = new URL(groovyPackage);
				File folderList=new File(url.toURI()); 
				File list[] = folderList.listFiles(); 
		        for(int i=0;i <list.length;i++) {
		        	File file = list[i];
		        	if(file.toString().endsWith(Constants.GROOVY_EXTENSION) && !file.isDirectory()){
		        		String groovyPath = url.toString()+"/"+file.getName();
		        		URL gUrl = new URL(groovyPath);
		        		Class scriptClass = groovyClassLoader.parseClass(DefaultGroovyMethods.getText(gUrl, "UTF-8") );
		    			set.add(scriptClass);
		        	}
		        }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return set;
	}
}
