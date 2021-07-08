# CoinMarketCap

In this File I'm going to explain <b>How to configure </b> and <b>Run this Framework</b>

The tech stack used for developing this framework are:
1. **JAVA** as the programming language for writing test code
2. **Selenium** as the Front-end Automation tool
3. **Rest Assured** as the Back-end Automation tool
4. **Cucumber** as the BDD tool for developing the framework
5. **TestNg** as the unit test framework
6. **Maven** as the build tool
7. **Eclipse** as the preferred IDE for writing java code.
8. **Extent Report** as the reporting tool.

#### Getting Started
Setup your machine.
1. Install JDK 1.8 & set "JAVA_HOME" in environment variable
2. Install Eclipse
3. Download and Set "Maven_Home" as a environment variable
4. Install Cucumber Eclipse plugin in Eclipse

#### Running tests
``Note:`` Sometime JRE System Library might be referring the wrong Library, please do select the JDK path in Build Path ```
1. You can run the tests directly from the Eclipse, by right-clicking POM.xml and **maven test**.
2. After the execution Execution logs and HTML report will be generated with the time stamp under "reports" folder, right click on the MiroExecutionResult.html to see the detailed results.
3. Framework will executes on Both Mac & Win [Make sure the above mentioned tools are installed and configured].
4. For Mac:
		-> Open the terminal
		-> Navigate to Project [Miro] Folder{root} directory 
		-> Execute the below command 
		```mvn clean install```
5. For Windows: 
		-> Open the command prompt
		-> Navigate to Project [Miro] Folder{root} directory 
		-> Execute the below command
		```mvn clean install```

---

#### Reason to choose BDD framework approach 
1. BDD is meant to be collaborative. Behaviour Driven Development (BDD) framework helps to attain all the prospects of a technical or business team.
2. Universal language which is very easy to describe
3. Business Value
4. Partnership between Business Analysts, Stake-holders, QA Team and developers
5. Separation of Test Cases from Test Code
6. Inherent Re-usability
7. Aspect-Oriented Controls
8. Easier Reviews
9. Clarity, Steam-lining, Shift left
10. Maintainable 

## BDD Framework Execution flow

### Steps to run /Execution flow

1. Run -mvn clean install command 
2. POM.xml starts downloading the dependencies and calls TestNG.xml
3. TestNG.xml calls a class "ScenarioRunner.java" [which is under src/test/java/runner package
4. In ScenarioRunner.java, CucumberOptions refers the feature files [which is under src/test/resources/feature/] folder, tags adds the scenarios to run, glue code refers the step definition package [src/test/java/com.coinmarketcap.steps], plugin uses extent report for report generation. 
5. Once after referring all the required dependencies, Framework will start running the scenarios one by one, below is the one sample example of how a single scenario runs[Which is same for all the scenarios]
				
				5.1. Executes before class - to add the system info to extent report
				
				5.2. Control goes to "TestContext.java" class to initialize the driver objects based on the browser name selected in the "config.properties"[src/test/resources/dataset/] - It will launch the browser and enters the URL.
				
				5.3. Control goes to "BaseTest.java" class and run the before hook function [Which executes before every scenario].
				
				5.4. Scenario statement will be called to execute and corresponding step implementation will be called from the "com.coinmarketcaps.steps".
				
				5.5. In step definition, with the help of Pico Container [Dependency Injection] library framework gets the objects of the page object classes.
				
				5.6. Corresponding methods from Page Classes will perform the actions, Assertion will validate the actions and after each step execution a screenshot will be taken and attached to the report.
				
				5.7. Once all the scenario statements execution completes, after hook function from BaseTest.java will executes.
				
				5.8. In Base test, Framework will check if the scenario is failed or not, if the scenario is failed, a screenshot and the exception message will be added to the extent report. And browser will be closed regardless of scenario fail or pass.
				
				5.9. New test will be called and above flow will continue till the last scenario executes.
				
				5.10. A HTML report and a log will be created under the "report" folder

6. Execution Completes
7. We can examine the Framework execution by referring to HTML report and Log.