# UITestsJava

#Logging
Done using Apache Log4J (Configuration can be seen in "log4j.properties")

#taking screenshot on failed tests;
Done using Extent reports

##Extent Reports (Human Readable Reports)
Will be generated and can be viewed in "test-output/ExtentReport.html" after execution
Failed Screenshots will be stored in the FailedTestsScreenshots folder as well.
If the Test case failed then the Screenshot will be captured and It will be attached in the Report

##Logs
Logs can be viewed in "log" Folder in HTML and Log File Formal also in Condole While Execution

#generating random values for insignificant test data, for example, for new user;
Created generaterandomname() Method for this purpose

#WebDriver factory;
Implemented Page Object Model, where page objects can be stored separately.

#encapsulation layers like test data, logic of tests, actions on web pages and so on;
Impletmented 	@Test(priority =1) signInTest() where methods are created in other class
Utils and config.properties created to make use of reusability

# reading test data from file, for example, the name of dress, size and color in the checkout test.
Added excelutils.java function to read data from excel
excelutil.getvalue1() canbe used for getting data

#Tests are configured to run in different browsers chrome,firefox on windows, linux and mac operation systems
##Execution Import the project in Eclipse IDE 
Run the below files as TestNG Test from Eclipse or IntelliJ IDE:
RunTestsonChromeBrowser.xml //Runtests only on Chrome
RunTestsonCrossbrowsers.xml //Runtests in chrome and firefox 
RunCrossbrowserTestsinParallel.xml //Runtests in chrome and firefox in parallel mode

##Run Tests from Command Prompt
mvn clean test -DsuiteXmlFile=testng.xml
