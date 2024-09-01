If you use GlassFish v3 or another Java EE 6 compliant application 
server, use the files in the javaee directory. 

If you use Tomcat or another servlet runner, use the files in
the tomcat directory. 

The javaee tree uses CDI (i.e. @Named). The tomcat tree uses
the @ManagedBean annotation instead. If you want to use CDI with
Tomcat or @ManagedBean with GlassFish, simply use the other tree.

An Ant script has been provided for your convenience.

You need to edit the build.properties file and set the directory of your
GlassFish or Tomcat installation. With Tomcat, first copy build.properties.tomcatN to build.properties, where N is 6 or 7.

If you use Tomcat, you also need to download
 * the JSF reference implementation
 * the EL 2.2 API and implementation
 * the bean validation reference implementation
 * optionally, the CDI reference implementation
Download URLs are in build.properties. Edit the directories for
these libraries in build.properties.

To build an application, go to the javaee or tomcat directory and run

ant -Dapp=chNN/exampleName

For example, 

ant -Dapp=ch01/login

The file exampleName.war will be copied into the deployment
directory. Point your web server to http://localhost:8080/exampleName.

