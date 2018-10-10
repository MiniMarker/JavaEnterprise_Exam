# JavaEnterprise_Exam

[![Build Status](https://travis-ci.org/MiniMarker/JavaEnterprise_Exam.svg?branch=master)](https://travis-ci.org/MiniMarker/JavaEnterprise_Exam)

* Student: Christian Marker
* Subject: PG5100-1 18V Enterpriseprogrammering 1

This repo contains my solution for our 48-hour home exam.

**EXTRA**
* I added an extra class th the project called "BookPost" from the start, this is a List of books for sale and extends 
the book class an an OneToMany relationship. A book can have several BookPosts created by users (a user can only publish 
one bookpost pr book at the time) (TESTED)
* Ability to add book if the book you are selling is not on the list (TESTED)
* Ability to send independent chat messages to a user (TESTED)
* Ability to mark a message as read (when you reply to a message, this message will be marked as read) (TESTED)
* The project is deployed on Travis CI

**Project Structure**

The project contains 3 submodules
* backend
    * Entities
    * Services
    * ServiceTests
* frontend
    * Controllers
    * WebSecurityConfig
    * Selenium Tests
    * Application Runner
* reports
    * JaCoCo coverage
    * OWASP check

## How to run code

1. Clone this repo
2. Run from project root folder: 
<br/> ```mvn install``` will install and generate reports **with** running tests
<br/> ```mvn install -DskipTests``` will install and generate reports **without** running tests
3. Run **LocalApplicationRunner** located in ~/frontend/src/test/java/no/cmarker/frontend/LocalApplicationRunner.java
4. Open ```localhost:8080/``` in your browser