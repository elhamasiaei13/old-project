These artifacts must be installed manually:

mvn install:install-file -Dfile=AAA.jar -DpomFile=AAA.pom

Agilefant uses a custom-built flexjson package that seems to be a variant of
1.8, but there's no source code. A stock 1.8 version does not work, so before
doing major refactoring we need to keep using this specific JAR file.

Struts2jsonresult doesn't have public maven artifacts, so we have to include
it manually. Using Struts 2 native JSON support would be better, but using it
would require major refactoring.
