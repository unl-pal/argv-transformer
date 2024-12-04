# spring-hotconf
The project's goal is to provide a library for Spring allowing to change internal web app configuration through a SSH connection.

## Setup ##

### Maven configuration ###
    <dependency>
      <groupId>com.github.gmousset</groupId>
      <artifactId>spring-hotconf</artifactId>
      <version>1.0.0</version>
    </dependency>

### Spring configuration ###
The configuration is very simple. 

In your `@Configuration` file:

- Add `@EnableHotConfiguration` to activate spring-hotconf
- Create a `HotConfBootstrap` bean
	- Set a username and a password for the SSH access
	- Set a listening port for SSH server


That's all.

Example:

	@Configuration
	@ComponentScan(basePackages = "com.github.gm.hotconf.test.web.service")
	@EnableHotConfiguration
	public class AppConfig {

		@Bean
		public HotConfBootstrap getCrashShell() {
			final HotConfBootstrap swb = new HotConfBootstrap();
			swb.setUsername("admin");
			swb.setPassword("password");
			swb.setSshPort("2222");
			return swb;
		}	
	}

### Usage ###


#### Configurable Properties ####

You can make a property configurable by adding `@HotConfigurableProperty` annotation.

Example:

	@Service
	public class MyService implement Service {
		
		@HotConfigurableProperty("prop.email")
		private String email;
		
		/*
		 * ...
		 */
	}

The parameter is optional, it allows to specify a name for the property.
If it is not set, the name will be *springBeanName.propertyName* (eg: *myService.email*).

If the property is initialized with an `@Value` annotation, the name will be the same without the __${}__.

Example:

	@Service
	public class MyService implement Service {
		
		@HotConfigurableProperty
		@Value("${prop.email}")
		private String email;
		
		/*
		 * ...
		 */
	}

##### Concurrency #####

__Configurable properties are not thread-safe if the property types are not.__
Consider the use of thread-safe types like __AtomicInteger__ for integer or __AtomicBoolean__ for boolean.
See [Atomic package](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/package-summary.html).

#### Hooks ####

##### Before/After hooks #####

A hook method can be called before or after a property value modification.
Any method without parameter can be a hook if you annotate it with `@HotConfigurationHookBefore` or `@HotConfigurationHookAfter`.

Example:

	@HotConfigurationHookBefore(value = "prop.email")
	public void hookBeforeForPropEmail() {
		makeSomeTreatementsBeforeUpdate();
	}
	
	@HotConfigurationHookAfter(value = "prop.email")
	public void hookBeforeForPropEmail() {
		makeSomeTreatementsAfterUpdate();
	}

##### Priority #####

Many hooks methods can be executed on the same property update.
To control the execution order, you can set a priority.
The highest priorized hook is executed firstly.

	@HotConfigurationHookAfter(value = "prop.email", priority = 3)
	public void hookBeforeForPropEmailProp3() {
		// ...
	}

	@HotConfigurationHookAfter(value = "prop.email", priority = 2)
	public void hookBeforeForPropEmailProp2() {
		// ...
	}

	@HotConfigurationHookAfter(value = "prop.email", priority = 1)
	public void hookBeforeForPropEmailProp1() {
		// ...
	}
	

#### Hot configuration ####

Launch your web app and start a ssh connection. You see the CraSH prompt. For more information, visit [The CRaSH website](http://www.crashub.org/).

	MacbookPro-Gwendal-Mousset:~ gwendalmousset$ ssh localhost -p 2222 -l admin
	Password authentication
	Password: 

	   _____     ________                 _______    ____ ____
	 .'     `.  |        `.             .'       `. |    |    | 1.3.1
	|    |    | |    |    |  .-------.  |    |    | |    |    |
	|    |____| |    `   .' |   _|    |  .    '~_ ` |         |
	|    |    | |    .   `.  .~'      | | `~_    `| |         |
	|    |    | |    |    | |    |    | |    |    | |    |    |
	 `._____.'  |____|____| `.________|  `._____.'  |____|____|
	
	Follow and support the project on http://www.crashub.org
	Welcome to MacbookPro-Gwendal-Mousset.local + !
	It is Thu Apr 09 15:39:51 CEST 2015 now
	% 

When you type the `help` command, CRaSH lists all available commands:

	% help
	Try one of these commands with the -h or --help switch:                                                                                                                                    
                                                                                                                                                                                           
	NAME      DESCRIPTION                                                                                                                                                                      
	dashboard a monitoring dashboard                                                                                                                                                           
	egrep     search file(s) for lines that match a pattern                                                                                                                                    
	env       display the term env                                                                                                                                                             
	filter    a filter for a stream of map                                                                                                                                                     
	hotconf   change internal app configuration                                                                                                                                                
	java      various java language commands                                                                                                                                                   
	jdbc      JDBC connection                                                                                                                                                                  
	jmx       Java Management Extensions                                                                                                                                                       
	jndi      Java Naming and Directory Interface                                                                                                                                              
	jpa       Java persistance API                                                                                                                                                             
	jul       java.util.logging commands                                                                                                                                                       
	jvm       JVM informations                                                                                                                                                                 
	less      opposite of more                                                                                                                                                                 
	man       format and display the on-line manual pages                                                                                                                                      
	shell     shell related command                                                                                                                                                            
	sleep     sleep for some time                                                                                                                                                              
	sort      sort a map                                                                                                                                                                       
	system    vm system properties commands                                                                                                                                                    
	thread    JVM thread commands                                                                                                                                                              
	help      provides basic help                                                                                                                                                              
	repl      list the repl or change the current repl                                                                                                                                         
	
	%

Spring-hotconf is available through the `hotconf` command:

	% hotconf help
	usage: hotconf [-h | --help] COMMAND [ARGS]

	The most commonly used hotconf commands are:
   	list             list all configurable properties
   	get              print property value
   	set              change property value

You can list all configurable properties with `list`:

	% hotconf list
	prop.email

Get the current property value:

	% hotconf get prop.email
	publisher@mycompany.com
	
And set a new value:

	% hotconf set prop.email "customer-support@mycompany.com"
	customer-support@mycompany.com
