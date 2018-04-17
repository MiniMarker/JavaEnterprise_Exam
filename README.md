# JavaEnterprise_Exam

[![Build Status](https://travis-ci.com/MiniMarker/JavaEnterprise_Exam.svg?token=63V1sLhHsu3poeGMykFw&branch=master)](https://travis-ci.com/MiniMarker/JavaEnterprise_Exam)

* Student: Christian Marker
* Subject: PG5100-1 18V Enterpriseprogrammering 1

This repo contains my solution for our 48-hour home exam.

**Project Structure**

The project contains 3 submodules
* backend
* frontend
* reports

## How to run code

1. Clone this repo
2. Run from project root folder: 
<br/> ```mvn install``` will install **with** running tests
<br/> ```mvn install -DskipTests``` will install **without** running tests
3. Run **LocalApplicationRunner** located in ~/frontend/src/test/java/no/cmarker/frontend/LocalApplicationRunner.java
4. Open ```localhost:8080/index.jsf``` in your browser