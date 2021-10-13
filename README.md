# sparta-spring-web-utils
[![Build Status](https://travis-ci.org/SpartaTech/sparta-spring-web-utils.svg?branch=Java1.8)](https://travis-ci.org/SpartaTech/sparta-spring-web-utils)
[![Coverage Status](https://coveralls.io/repos/github/SpartaTech/sparta-spring-web-utils/badge.svg?branch=Java1.8)](https://coveralls.io/github/SpartaTech/sparta-spring-web-utils?branch=Java1.8)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.spartatech/spring-web-utils-java8/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.github.spartatech/spring-web-utils-java8/)

Spring Web Utility library. Includes functionalities to enhance the use of spring-mvc framework. 

___This is the Java 8 supporting version. If you're still using java 7 see java1.7 branch.___

__Features__:

## Utilities

### Manifest Utils

The Class ManifestUtils provides easy way to access manifest.mf information. 

Usage:


* Create a Bean: 

Java-configuration-based:

```Java
@Configuration
public class Config {
   @Bean
   public ManifestUtils manifestUtils () {
      return new ManifestUtils();
   }
}
```

or, xml based configuration

```Xml
<bean id="manifestUtils" class="org.sparta.springwebutils.ManifestUtils"/>
```

* Autowire and use the methods:

   * getManifestAttributes() - Retrieves the list of all manifestAttributed found
   * getManifiestAttribute(String attributeName) - Retreive a specific attribute from manifest

</ol>

### Request Utils
Request Utils is a functionality that provides a way to list all HTTP entry points (@RequestMapping methods in a @controller class), allowing you to build interfaces to show/call your endpoints. 
The basic use of it is: 
```Java
@Configuration
public class AppConfig {
	@Bean
	public RequestUtils requestUtilsAnnotationsEnabled(){
		RequestUtils ru = new RequestUtils();
		ru.setScanEntryPointAnnotation(false);
		return ru;
	} 
}
```

```Java
public class MyClass {
   @Autowired
   private RequestUtils requestUtils;
   
   public void myMethod() {
      final List<EntryPoint> entryPoints = requestUtils.retrieveAllExternalEntryPoints();
      //do whatever you need to do with the entry points
   }
}
```

You can also use the @ExternalEntryPoint annotation to you controller if you need more flexibility in the retrieval of the entry-points. Example:

```Java
@Controller
public class MethodAnnotatedController {
	
	@ExternalEntryPoint(name="new_name", typeBlacklist=String.class, nameBlacklist="outToo")
	@RequestMapping(value="/methodAnnotatedController/testBlacklists", method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView testBlacklists(String out, Integer in, @RequestParam(defaultValue="true", required=false) Boolean inToo, Long outToo) {
		return new ModelAndView();
	}
}
```

Or annotating class:

```Java
@Controller
@ExternalEntryPoint(typeBlacklist=Object.class, nameBlacklist={"out", "out_two"})
public class ClassAnnotatedController {
	
	@RequestMapping(value="/classAnnotatedController/testTypeBlacklist", method=RequestMethod.POST)
	public ModelAndView testTypeBlacklist(String inOne, Integer inTwo, Object fileOut) {
		return new ModelAndView();
	}
	
	@RequestMapping(value="/classAnnotatedController/testNameBlacklist", method=RequestMethod.GET)
	public ModelAndView testNameBlacklist(String out, Integer in, Boolean inToo, @RequestParam("out_two") Long outToo) {
		return new ModelAndView();
	}
}
```

In this case your bean definition will be:

```Java
	@Bean
	public RequestUtils requestUtilsClearParamTypeBlackList() {
		RequestUtils ru = new RequestUtils();
		return ru;
	}
```




### SpringContext Utils

Useful class for creating libraries that has its own context. It will not share context bean from the application which is including the library. Basically the contextMerger allows you to merge objects into an context.

There are two ways of using it. First one is by context in XML file, the second one is by context on Config class.

#### Context on XML file

Usage:

You can use contextMergedBean to generate and API class which will be exposed to the users of your library. 

__YourApi.class__

```Java
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
	public YourApi(String param1, String param2) {
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


```XML
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 	http://www.springframework.org/schema/beans/spring-beans-4.1.xsd 
						http://www.springframework.org/schema/context	http://www.springframework.org/schema/context/spring-context-4.1.xsd">

	<context:component-scan base-package="com.yourcompany.yourapi"/>
</beans>
```


__YourService.class__:


```Java
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



And then the applications that want to use you application just need to create a bean for YourApi and use the methods. 

Example:

```Java
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

#### By Config Class
This usage is pretty much the same as the previous method. The only difference is that you'll be using a config class to configure your beans instead of XML file.

__YourApi.class__

```Java
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
  public YourApi(String param1, String param2) {
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

    context = SpringContextUtils.contextMergedBeans(extraParams, LibConfig.class);
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
}
```



__LibConfig__:


```Java
@ComponentScan("com.youcompany.yourproject")
public class LibConfig {
}
```


__YourService.class__:


```Java
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



And then the applications that want to use you application just need to create a bean for YourApi and use the methods. 

Example:

```Java
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


## Database

### JDBCTemplate
We provide two extension classes for the original Spring JdbcTemplate. They are __SpartaJdbcTemplate__ and __SpartaNamedParameterJdbcTemplate__. The Sparta extended classes provide some missing extra methods for JdbcTemplate using Java8 Optional when getting an Object. 

Without Sparta extensions the queryForObject returns _IncorrectResultSizeDataAccessException_ if the record was not found in the database, then your code would have to catch the exception and threat it. In Sparta extension classes we are providing methods queryForOptionalObject, these methods return an Optional object, which will contain either the value or empty if not found. 

USAGE:

```Java
SpartaJdbcTemplate jdbcTemplate = new SpartaJdbcTemplate(dataSource);

Optional<String> result = jdbcTemplate.queryForOptionalObject("select 1 from dual", String.class);
```

Same argument options for queryForObject in JdbcTemplte and NamedParameterJdbcTemplate are available for queryForOptionalObject, in SpartaJdbcTemplate and SpartaNamedParameterJdbcTemplate.

## WhereClauseBuilder
This functionality allows you to annotate a class and its fields. With the annotations information the WhereClauseBuilder will generate a where clause for the database query.

Example:


```Java
public class TestWhereBuilder {
	@QueryWhereClause("p.first_name = {this}")
	private String firstName;
	@QueryWhereClause("p.last_name = {this}")
	private String lastName;
	
	@QueryWhereClauses({
			@QueryWhereClause(value = "p.age > {this.min}", fieldTypes=Types.DOUBLE),
			@QueryWhereClause(value = "p.age < {this.max}", fieldTypes=Types.DOUBLE)
	})
	private TestRangeObj<Long> age;
   ...
}
```
```Java
public class TestWhereBuilder {
	@QueryWhereClause("p.first_name = {this}")
	private String firstName;
	@QueryWhereClause("p.last_name = {this}")
	private String lastName;
	
	@QueryWhereClauses({
			@QueryWhereClause(value = "p.age > {this.min}", fieldTypes=Types.DOUBLE),
			@QueryWhereClause(value = "p.age < {this.max}", fieldTypes=Types.DOUBLE)
	})
	private TestRangeObj<Long> age;
   ...
}
```

```Java
public class TestRangeObj<T> {
	private T min;
	private T max;
	...
}
```

```Java
	public String test() throws Exception {
		final TestWhereBuilder testObj = new TestWhereBuilder();
		testObj.setFirstName("Daniel");
		testObj.setLastName("Diehl");
		TestRangeObj<Long> range = new TestRangeObj<>();
		range.setMin(21L);
		range.setMax(60L);
		testObj.setAge(range);
		
		final MapSqlParameterSource params = new MapSqlParameterSource();
		final String where = QueryWhereClauseBuilder.buildWhereClause(testObj, params);
		
		final SpartaNamedParameterJdbcTemplate jdbc = new SpartaNamedParameterJdbcTemplate(dataSource);
		return jdbc.query("SELECT * FROM table WHERE" + where, params, new TestingRowMapper());
		
```



