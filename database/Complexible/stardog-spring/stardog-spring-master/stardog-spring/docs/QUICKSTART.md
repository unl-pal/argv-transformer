# Stardog Spring Integration

## 1. Overview

The Stardog Spring integration provides a set of capabilities for 
rapidly building Stardog backed applications with the Spring Framework.
As with many other parts of the Spring Framework, Stardog's Spring integration
uses the template design pattern for abstracting standard boilerplate away
from application developers.  

At the lowest level, this includes:
- `DataSource` and `DataSourceFactoryBean` for managing Stardog connections
- `SnarlTemplate` for transaction and connection pool safe Stardog programming
- `DataImporter` for easy bootstrapping of input data into Stardog
  
Future iterations of the Stardog Spring subproject will address other common
enterprise capabilities provided by Spring, such as Spring Batch, Spring Data, etc.


## 2. Installation

If installing from the source project, the Gradle build can be run to install
the stardog-spring jar with `gradlew install`.

If installing the jar manually into Maven, you can use `mvn install`:

```
mvn install:install-file
      -DgroupId=com.stardog.stardog
      -DartifactId=stardog-spring
      -Dversion=1.0.0
      -Dfile=stardog-spring.jar
      -Dpackaging=jar
      -DpomFile=pom.xml
```
      
In addition to the standard Stardog dependencies, stardog-spring pulls in 
spring-core and spring-beans version 3.0.5.RELEASE.

**Note**: the Spring batch dependencies are only needed if you use the beans in the batch sub-package.

## 3. Usage

There are three principal beans to add to a Spring application context:

1. `DataSourceFactoryBean`
2. `SnarlTemplate`
3. `DataImporter`
  
`DataSourceFactoryBean` is a Spring `FactoryBean` that configures and produces a `DataSource`. 
All of the Stardog `ConnectionConfiguration` and `ConnectionPoolConfig` methods are also property
names of the `DataSourceFactoryBean` (e.g. `to`, `url`, `createIfNotPresent`).  

`DataSource` is a new class, similar to `javax.sql.DataSource` for retrieving a `Connection` from the
`ConnectionPool`.  This additional abstraction serves as place to weave in Spring specific capabilities
(e.g. spring-tx support in the future) without directly requiring Spring in Stardog.

`SnarlTemplate` provides a template abstraction over much of the SNARL API, and follows the same
look and feel of other popular Spring templates such as `JdbcTemplate`, `JmsTemplate`, etc.  The key 
methods on `SnarlTemplate` are:

- `List<T> query(String sparqlQuery, Map<String,String> args, RowMapper<T>)`
    - Executes the `SELECT` query with provided argument list, and invokes the mapper for result row.
- `T doWithAdder(AdderCallback<T>)`
    - Transaction and connection pool safe adder call
- `T doWithGetter(String subject, String predicate, GetterCallback<T>)`
    - Connection pool boiler plate for `Getter` interface, including the subject/predicate filter
- `T doWithRemover(RemoverCallback<T>)`
    - Transaction and connection pool safe remover call
- `T execute(ConnectionCallback<T>)`
    - Connection and transaction safe callback to working with the connection directly
- `List<T> construct(String constructSparql, Map<String,String> args, GraphMapper<T>)`
    - Executes the `CONSTRUCT` query with provided argument list, and invokes the `GraphMapper` for result rows
    
`DataImporter` is a new class that automates the loading of RDF files into Stardog at initialization time.
This class also uses the Spring Resource API, so files can be loaded anywhere that is resolvable by the
Resource API (e.g. classpath:, file:, url:, etc).  The class has a single load method for further run-time
loading.  It can also be configured with a list of files to load at initialization time.  The list assumes
a uniform set of file formats, so if there are many different types of files to load with different RDF formats,
there would be different `DataImporter` beans configured in Spring 

A sample `ApplicationContext` follows:

```
<bean name="dataSource" class="com.stardog.ext.spring.DataSourceFactoryBean">
    <property name="to" value="testdb"/>
    <property name="createIfNotPresent" value="true"/>
    <property name="username" value="admin"/>
    <property name="password" value="admin"/>
    <property name="embedded" value="true"/> <!-- starts the Stardog server in embedded mode -->
</bean>

<bean name="template" class="com.stardog.ext.spring.SnarlTemplate">
    <property name="dataSource" ref="dataSource"/>
</bean>

<bean name="importer" class="com.stardog.ext.spring.DataImporter">
    <property name="snarlTemplate" ref="template"/>
    <property name="format" value="N3"/>
    <property name="inputFiles">
        <list>
            <value>classpath:sp2b_10k.n3</value>
        </list>
    </property>
</bean>
```


## 4. Basic `SnarlTemplate` Examples

#### 4.1 query() with SELECT queries

```
String sparql = "SELECT ?a ?b WHERE { ?a  <urn:test:b> ?b } LIMIT 5";

List<Map<String,String>> results = snarlTemplate.query(sparql, new RowMapper<Map<String,String>>() {

    @Override
    public Map<String,String> mapRow(BindingSet bindingSet) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("a", bindingSet.getValue("a").stringValue());
        map.put("b", bindingSet.getValue("b").stringValue());
        return map;
    } 
});
```

#### 4.2 doWithGetter()

```
List<String> results = snarlTemplate.doWithGetter(null, "urn:test:n", new GetterCallback<String>() {
    @Override
    public String processStatement(Statement statement) {
        return statement.getObject().stringValue();
    } 
});
```
    
#### 4.3 doWithAdder()

```
snarlTemplate.doWithAdder(new AdderCallback<Boolean>() {
    @Override
    public Boolean add(Adder adder) throws StardogException {
        String uriA = "urn:test:j";
        String uriB = "urn:test:k";
        String litA = "hello world";
        String litB = "goodbye";
        
        adder.statement(new URIImpl(uriA), new URIImpl(uriB), new LiteralImpl(litA));
        adder.statement(new URIImpl(uriA), new URIImpl(uriB), new LiteralImpl(litB));
        return true;
    } 		
});
```
		
#### 4.4 doWithRemover()
		
```
snarlTemplate.doWithRemover(new RemoverCallback<Boolean>() {
    @Override
    public Boolean remove(Remover remover) throws StardogException {
        remover.statements(new URIImpl("urn:test:m"), new URIImpl("urn:test:n"), null);
        return true;
    } 
});
```

#### 4.5 construct()

```
String sparql = "CONSTRUCT { ?a <urn:test:new> ?b } WHERE { ?a <urn:test:p> ?b }";
List<Map<String,String>>  results = snarlTemplate.construct(sparql, new GraphMapper<Map<String,String>>() {
    @Override
    public Map<String, String> mapRow(Statement next) {
        Map<String,String> map = new HashMap<String,String>();	
        map.put(next.getSubject().stringValue(), next.getObject().stringValue());
        return map;
    } 
});
```

## 5. Spring Batch Support

Spring Batch provides a robust infrastructure for building complex batch processing applications. 
Given that structured and unstructured data may not be in RDF form, Stardog Spring supports Spring Batch for
building readers and writers that can participate in a larger batch set of transactions.  The `DataSource` and
`SnarlTemplate` are leveraged in implementations of the `ItemReader` and `ItemWriter` interfaces, and the callbacks
commonly used with `SnarlTemplate` can then be used in the batch transaction.  One new callback is added for using
a list of templated objects with the Snarl Adder interface.

#### 5.1 Sample Batch Configuration File

The following is a sample batch configuration file that uses a `SnarlReader` and `SnarlWriter` to read triples from
an embedded Stardog and then write them back with new predicates.  While this only shows the very basics of Spring
Batch, these building blocks can be used with any of the Spring Batch capabilities.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:batch="http://www.springframework.org/schema/batch" 
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd http://www.springframework.org/schema/batch http://www.springframework.org/schema/batch/spring-batch-2.1.xsd">

    <!--  
	//////////////////////   Basic Stardog-Spring configuration //////////////////////
	-->
    
	<bean name="dataSource" class="com.stardog.ext.spring.DataSourceFactoryBean">
		<property name="to" value="testdb"/>
		<property name="createIfNotPresent" value="true"/>
	</bean>

	<bean name="template" class="com.stardog.ext.spring.SnarlTemplate">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	
	<!--  
	//////////////////////   Basic Spring Batch configuration //////////////////////
	
		- Synchronous task executor for simple JUnit Runners, can be easily swapped for AsyncTaskExecutor
		- in memory batch management
		- StepScope beans (SnarlReader and SnarlWriter) are created and destroyed with each batch run
	-->
	
	<bean name="transactionManager" class="org.springframework.batch.support.transaction.ResourcelessTransactionManager">
	</bean>

	  <bean id="jobRepository" class="org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean">
		<property name="transactionManager" ref="transactionManager"/>
	  </bean>


	<bean id="syncTaskExecutor" class="org.springframework.core.task.SyncTaskExecutor"/>

	<bean id="jobLauncher"
	      class="org.springframework.batch.core.launch.support.SimpleJobLauncher">
	    <property name="jobRepository" ref="jobRepository" />
	    <property name="taskExecutor" ref="syncTaskExecutor"/>
	</bean>

	<bean class="org.springframework.batch.core.scope.StepScope" />
	
	<bean id="testRowMapper" class="com.stardog.ext.spring.batch.TestRowMapper"/>
	
	<bean id="testBatchCallback" class="com.stardog.ext.spring.batch.TestBatchCallback"/>
	
	<!--  
	//////////////////////   Batch job configuration for Stardog //////////////////////
	-->
	
	<bean id="snarlReader" class="com.stardog.ext.spring.batch.SnarlItemReader" scope="step">
		<property name="dataSource" ref="dataSource"/>
		<property name="query" value="SELECT ?a ?b WHERE { ?a &lt;urn:test:predicate> ?b }"/>
		<property name="rowMapper" ref="testRowMapper"/>
	</bean>
	
	<bean id="snarlWriter" class="com.stardog.ext.spring.batch.SnarlItemWriter" scope="step">
		<property name="dataSource" ref="dataSource"/>
		<property name="callback" ref="testBatchCallback"/>
	</bean>
	
	<batch:job id="simpleJob" >
		<batch:step id="simpleStep">
			<batch:tasklet task-executor="syncTaskExecutor" throttle-limit="5">
				<batch:chunk reader="snarlReader" writer="snarlWriter" commit-interval="5"/>
			</batch:tasklet>
		</batch:step>
	</batch:job>
	
</beans>
```

#### 5.2  Callbacks

The new callback is the `BatchAdderCallback` which uses a list of `<T>` instead of just one:

```
public void write(Adder adder, List<? extends TestRecord> items)
        throws StardogException {
    
    for (TestRecord item : items) {
        adder.statement(new URIImpl(item.getName()), new URIImpl("urn:test:propertyUpdate"), new LiteralImpl((String) item.getValue() + "update"));
    }
    
}
```





