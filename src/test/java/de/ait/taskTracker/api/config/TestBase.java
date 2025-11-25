package de.ait.taskTracker.api.config;

import de.ait.taskTracker.config.AppConfigApi;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

    AppConfigApi app = new AppConfigApi();

    public static Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = app.getProperty("URI");
        RestAssured.basePath = app.getProperty("basePath");
    }

    @BeforeMethod
    public void setUp(Method method, Object[] p) {
        logger.info("logger info: " + method.getName() + " with data: " + Arrays.asList(p));
    }

    @AfterMethod(enabled = true)
    public void tearDown(ITestResult result) {
        if (result.isSuccess()) {
            logger.info("PASSED: " + result.getMethod().getMethodName());
        } else {
            logger.error("FAILED: " + result.getMethod().getMethodName());
        }
        logger.info("===============================================" );
    }
}
