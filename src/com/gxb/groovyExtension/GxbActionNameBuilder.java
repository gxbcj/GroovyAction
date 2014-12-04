package com.gxb.groovyExtension;

import org.apache.struts2.convention.ActionNameBuilder;

import com.gxb.util.Constants;

public class GxbActionNameBuilder  implements ActionNameBuilder{
	private static final String ACTION_SUFFIX = "Action";
	
	public String build(String className) {
		String actionName = className;

        if (actionName.endsWith(ACTION_SUFFIX)) {
            actionName = actionName.substring(0, actionName.length() - ACTION_SUFFIX.length());
        }else if(actionName.endsWith(Constants.Groovy_SUFFIX)){
        	actionName = actionName.substring(0, actionName.length() - Constants.Groovy_SUFFIX.length());
        }
        if(actionName.length() > 1){
        	String first = actionName.substring(0, 1).toLowerCase();
    		actionName = first + actionName.substring(1,actionName.length());
        }else{
        	actionName = actionName.toLowerCase();
        }
		return actionName;
	}

}
