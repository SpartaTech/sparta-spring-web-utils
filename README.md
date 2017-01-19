# sparta-spring-web-utils
Spring Web Utility library. Includes functionalities to enhance the use of spring-mvc framework. 

___This is the Java 8 supporting version. If you're still using java 7 see java1.7 branch.___

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

##Database

###JDBCTemplate
We provide two extension classes for the original Spring JdbcTemplate. They are __SpartaJdbcTemplate__ and __SpartaNamedParameterJdbcTemplate__. The Sparta extended classes provide some missing extra methods for JdbcTemplate for Using Java8 Optional when getting an Object. 

Without Sparta extensions the queryForObject returns _IncorrectResultSizeDataAccessException_ if the record was not found in the database, then you code would have to catch the exception and threat it. Instead with Sparta extension classes we are providing methods queryForOptionalObject, this methods returns an Optional object, which will return the value or empty if not found. 

USAGE:

```Java
SpartaJdbcTemplate jdbcTemplate = new SpartaJdbcTemplate(dataSource);

Optional<String> result = jdbcTemplate.queryForOptionalObject("select 1 from dual", String.class);
```

Same argument options for queryForObject are available for queryForOptionalObject, in SpartaJdbcTemplate and SpartaNamedParameterJdbcTemplate.

##WhereClauseBuilder
This functionality allows you to annotate a class and its fields, with this information the WhereClauseBuilder will generate a where clause for the database.

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



