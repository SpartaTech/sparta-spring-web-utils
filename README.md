# sparta-spring-web-utils
Spring Web Utility library. Includes functionalities to enhance the use of spring-mvc framework. 

__Features__:

##Utilities

####Manifest Utils

The Class ManifestUtils provides easy way to access manifest.mf information. 

Usage:


* Create a Bean: 

Java-configuration-based:

```
@Configuration
public class Config {
   @Bean
   public ManifestUtils manifestUtils () {
      return new ManifestUtils();
   }
}
```

or, xml based configuration

```
<bean id="manifestUtils" class="org.sparta.springwebutils.ManifestUtils"/>
```

* Autowire and use the methods:

   * getManifestAttributes() - Retrieves the list of all manifestAttributed found
   * getManifiestAttribute(String attributeName) - Retreive a specific attribute from manifest

</ol>

####Request Utils


####SpringContext Utils

Useful class for creating libraries that has its own context. It will not share context bean from the application which is including the library. Basically the contextMerger allows you to merge objects into an context 


Usage:

You can use contextMergedBean to generate and API class which will be exposed to the users of your library. 

__YourApi.class__

```
public class YourApi {
	private static Logger LOGGER = LoggerFactory.getLogger(YourApi.class);
	
	private ApplicationContext context;

	private YourService service;
	
	/**
	 * Load context 
	 * 
	 * @param param1 Example param
	 * @param param2 Example param
	 */
	public BsoSoapClientApi(String param1, String param2) {
		initContext(param1, param2);
	}
	
	/**
	 * Initializes context with provided data
	 * 
	 * @param param1 Example param
	 * @param param2 Example param
	 */
	private void initContext(String param1, String param2) {
		LOGGER.info("Initializing your-api context");
		
		Map<String, Object> extraParams = new HashMap<String, Object>(); 
		extraParams.put("param1", param1);
		extraParams.put("param2", param2);
		
		context = SpringContextUtils.contextMergedBeans("META-INF/spring/your-api-beans.xml", extraParams);
		service = context.getBean(YourService.class);
		
		LOGGER.info("your-api context initialized");
	}
	
	/**
	 * Calls Example Method
	 * 
	 * @return String Example return
	 */
	public String helloWorld() {
		return service.helloWorld();
	}
```



__META-INF/spring/your-api-beans.xml__:


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 	http://www.springframework.org/schema/beans/spring-beans-4.1.xsd 
						http://www.springframework.org/schema/context	http://www.springframework.org/schema/context/spring-context-4.1.xsd">

	<context:component-scan base-package="com.yourcompany.yourapi"/>
</beans
```


__YourService.class__:


```
@Service
public class YourService {
  /**
   * Example method in the service.
   *
   * @return String helloworld
   */
  public String helloWorld() {
     return "Hello World";
  }
}
```



And then the applications that wants to use you application just needs to create a bean for YourApi and use the Methods. 

Example:

```
@Configuration
public class Config {
  @Bean
  public YourApi yourApi() {
     return new YourApi("value1", "value2");
  }
}

@Component
public class Test () {
   @Autowired
   private YourApi yourApi;

   public void test() {
      System.out.println(yourApi.helloWorld());
   }
}
```





