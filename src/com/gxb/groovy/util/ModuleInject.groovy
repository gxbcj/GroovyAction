package com.gxb.groovy.util

import com.gxb.util.Inject
public class ModuleInject implements Inject{

	def String title;
	
	def String sayLittle(){
		return title;
	}
}
