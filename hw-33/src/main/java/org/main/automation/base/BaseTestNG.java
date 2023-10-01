package org.main.automation.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class BaseTestNG
{
    final protected Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, Object[] testArgs) {
        logger.debug("---------------------------------------------------------------------------");
        logger.debug("-- Run test: " + method.getAnnotation(Test.class).testName());
        logger.debug("---------------------------------------------------------------------------");
        int i = 1;
        for (Object obj : testArgs) {
            logger.debug("Argument " + i ++ + ": " + obj);
        }

    }
}
