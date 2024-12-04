<#-- "page" is the name of the macro which will be used in any ftl files that import this template -->
<#macro page title="SparkJavaTest">
<!doctype html>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css"> <!-- load bootstrap css -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"> <!-- load fontawesome -->
    <style>
        body        { padding-top:80px; }
    </style>
</head>
<body>
<#-- This processes the enclosed content:  -->
    <#nested>
</body>
</html>
</#macro>