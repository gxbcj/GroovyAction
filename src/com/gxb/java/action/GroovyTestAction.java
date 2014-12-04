package com.gxb.java.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.Dispatcher;

import com.gxb.java.module.User;
import com.gxb.util.Inject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.inject.Container;

@ParentPackage(value="user")
@Action(value="groovyTest")
@Results({
	@Result(name="list", location="/${location}")
})
public class GroovyTestAction extends ActionSupport implements Preparable{

	private static final long serialVersionUID = 1L;

	private Inject moduleInject;
	private User user;
	private String location = "test.jsp";

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String hello(){
		System.out.println("logon");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		try {
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.println("hello"+user.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String execute(){
		
		return null;
	}

	
	
	public Inject getModuleInject() {
		return moduleInject;
	}

	public void setModuleInject(Inject moduleInject) {
		this.moduleInject = moduleInject;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
