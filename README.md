# Gadgetothek Desktop App [![Build Status](https://travis-ci.org/lukasmartinelli/gadgetothek-desktop.svg?branch=master)](https://travis-ci.org/lukasmartinelli/gadgetothek-desktop)

This is a Java Swing Application for the desktop.
It is a mini project created after the specifications given
in the [User Interfaces 1](http://studien.hsr.ch/allModules/23868_M_UIn1.html) lecture.

## Project Setup for Eclipse

1. Install Eclipse
2. Install the [Window Builder Plugin](http://download.eclipse.org/windowbuilder/WB/integration/4.4/)
3. Install the [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
   and [add it to Eclipse](http://waynebeaton.wordpress.com/2014/03/26/add-java-8-support-to-eclipse-kepler/)
4. Install the [Maven Integration Plugin](http://www.eclipse.org/m2e/download/)

## Build Project

If you use Eclipse and setup the project as described you can build directly from Eclipse.

You can install and use Maven directly from the command line to download all dependencies
and build the project:

```bash
mvn clean install
```
