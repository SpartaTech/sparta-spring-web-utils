# sparta-spring-web-utils
[![Build Status](https://app.travis-ci.com/SpartaTech/sparta-spring-web-utils.svg?branch=master)](https://app.travis-ci.com/SpartaTech/sparta-spring-web-utils)
[![Coverage Status](https://coveralls.io/repos/github/SpartaTech/sparta-spring-web-utils/badge.svg?branch=master)](https://coveralls.io/github/SpartaTech/sparta-spring-web-utils?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.spartatech/spring-web-utils/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.github.spartatech/spring-web-utils/)

Spring Web Utility library. Includes functionalities to enhance the use of spring-mvc framework. 

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



