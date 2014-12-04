GroovyAction
==============
这个项目还是2010年，考研复习放松的时候突发奇想开始的，想用groovy替代SSH中的action层(其实service，module什么的都可以)
主要也是那段时间动态语言风头比较劲，还有就是groovy的语法糖，不像java那么死板，之前也没想着分享，现在挂上来了(感觉可以替代Grails的)。<br>
其实具体的工作量并不大，主要就是支持脚本语言groovy编写的action，扩充了ObjectFactory，增加了groovy的classloader，同时扩展了配置项，分为生产环境和运行环境，在生产环境中，不把class进行缓存，可以动态展现对action类的修改，运行环境就是把解析的class进行缓存，提高性能。<br>
最关键的就是com.gxb.groovyExtension包下的三个扩展类GxbActionConfigBuilder,GxbActionNameBuilder和GxbObjectFactory。<br>
现在分别介绍这三个类的作用。


#GxbActionConfigBuilder
首先就是读取配置的groovy action所在的package<br>
```java
@Inject(value = "struts.convention.action.groovyPackages", required = false)
public void setGrrovyActionPackages(String actionPackages) {
    if (StringUtils.isNotBlank(actionPackages)) {
        this.groovyActionPackages = actionPackages.split("\\s*[,]\\s*");
    }
}
```
指定groovy action所在package，如果有多个，可以用','逗号分割
```xml
<constant name="struts.convention.action.groovyPackages" value="com/gxb/groovy/action"/>
```
java编写的action在这里配置
```xml
<constant name="struts.convention.action.packages" value="com.gxb.java.action"/>
```
然后就是重写findActions方法<br>
```java
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
```
代码
```java
Set<Class> set = super.findActions();
```
就是兼容java写的action。
我们需要在struts.xml中配置，从而覆盖默认配置
```xml
<bean type="org.apache.struts2.convention.ActionConfigBuilder" name="actionConfigBuilderGxb" class="com.gxb.groovyExtension.GxbActionConfigBuilder"/>
<constant name="struts.convention.actionConfigBuilder" value="actionConfigBuilderGxb"/>
```



#GxbActionNameBuilder
这个只是重定义了命名规则
如果没有明确指定action的名称，比如没有添加@Action(value="xxx")，则根据类名来定义action
这里对于groovy写的action，我们用Controller为后缀，作为和java写的action区分。
```java
if (actionName.endsWith(ACTION_SUFFIX)) {
    actionName = actionName.substring(0, actionName.length() - ACTION_SUFFIX.length());
}else if(actionName.endsWith(Constants.Groovy_SUFFIX)){
	actionName = actionName.substring(0, actionName.length() - Constants.Groovy_SUFFIX.length());
}
```
同样我们需要在struts.xml中配置覆盖默认配置
```xml
<bean type="org.apache.struts2.convention.ActionNameBuilder" name="actionNameBuilderGxb" class="com.gxb.groovyExtension.GxbActionNameBuilder"/>
<constant name="struts.convention.actionNameBuilder" value="actionNameBuilderGxb"/>
```

#GxbObjectFactory
这个是改进的key，原来struts2和spring整合，我们使用的是'StrutsSpringObjectFactory'这个类
现在我们需要对这个类进行扩展，首先就是先继承这个类，重写getClassInstance()这个方法，添加对groovy的支持
```java
@Override
@SuppressWarnings("unchecked")
public Class getClassInstance(String className) throws ClassNotFoundException{
	if (!className.endsWith(Constants.Groovy_SUFFIX)) {
		return super.getClassInstance(className);
	} else {
		Class clz = null;
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
```
同时也加入了对class的缓存,值为false代表开发环境，每次调用action都会重新加载groovy script，设置为true则用于生产环境。
struts.xml中的配置为
```xml
<constant name="struts.convention.action.groovyCache" value="false"/>
```
读取配置项
```java
@Inject(value = "struts.convention.action.groovyCache", required = false)
public void setGroovyCache(String groovyCache) {
    this.groovyCache = "true".equals(groovyCache);
}
```

#Spring中配置groovy bean
spring提供了对动态语言的支持，
在applicationContext-groovy.xml中
```xml
<lang:groovy id="moduleInject" script-source="classpath:com/gxb/groovy/util/ModuleInject.groovy">  
	<lang:property  name="title" value="Hello World, it'me" />
</lang:groovy>
```
我们配置了一个moduleInject的bean，使用的groovy脚本路径在script-source属性中配置。用法和平时没区别，在action中注入。

#开发区别
使用groovy action开发，不需要显示的对成员变量实现get，set方法。这个方便很多
但是有个比较大的区别就是属性封装变量必须'new'，
在java action中，我们只需要
```java
private User user;
```
而在groovy action中，我们需要
```groovy
def User user = new User();
```