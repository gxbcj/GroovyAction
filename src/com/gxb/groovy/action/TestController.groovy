package com.gxb.groovy.action;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Action;
import com.gxb.util.Inject;
import com.gxb.java.module.User;
import com.opensymphony.xwork2.Preparable;
import org.springframework.jdbc.core.JdbcTemplate;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.CreateIfNull;

@ParentPackage(value="user")
@Action(value="test")
@Results([
	@Result(name="list", location="/index.jsp"),
	@Result(name="view", location="/view.jsp")
])
public class TestController extends ActionSupport implements Preparable{

	def User user = new User();
	
	def Inject moduleInject;
	
	@Override
	public void prepare() throws Exception {
		println 'prepare';
	}
	
	def String execute(){
		println user.getName()+'  '+user.getAge();
		return "view";
	}

	def String search(){
		def list = ['1','2','3','4','5'];
		list.each{ item ->
			println item;
		}
		println "search1";
		return "list";
	}

	def String edit(){
		println "edit_hello";
		return "list";
	}
	
}
