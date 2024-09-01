
8 March 2011 KL 
 Modified the project to be a JEE6 web application using JSF2.  Used the following steps:
1.	Create a new web app with JEE6 and JSF2
2.	Copy all the source packages from old to new project
3.	Copy unit tests from old to new project
4.	In web.xml, change the start page from index.faces
5.	Replace index.html with the following:
6.  Run unit test suite to make sure are getting expected results (see below)

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html">
    <head>
        <title>EBazaar Home</title>
    </head>
    <body>
            <h1><h:outputText value="EBazaar Home" /></h1>
            <h:form>
                <h:commandButton value="Browse Catalogs" action="#{bsControllerPCB.viewCatalogs}"/>
            </h:form>
    </body>
</html>


2 March 2008

This is a starting skeleton for a JSF lab in CS545, March 2008.  The skeleton
is mostly operational for the browse and select use case.  It uses stub data
from a mock product subsystem.  The browse and select use case controller
is implemented for the happy day use case flow.  The rest of the skeleton runs 
with hard-wired data.

There are a set of unit tests included.  Most of the tests are working, but there
are 3 tests that fail and 2 with errors.