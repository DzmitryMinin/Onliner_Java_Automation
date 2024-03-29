package pageObject.baseObjects;

import static driver.DriverTypes.*;
import static driver.WebDriverSetUp.createDriver;
import static driver.WebDriverSetUp.quitDriver;
import static propertyUtils.PropertyReader.getProperties;

import org.testng.annotations.*;
import testngUtils.InvokedListener;
import testngUtils.Listener;

import java.lang.reflect.InvocationTargetException;

@Listeners({Listener.class, InvokedListener.class})
public class BaseTest {
    @BeforeMethod
    protected void setUp() {
        createDriver(System.getProperties().contains("config")
                ? valueOf(getProperties().getProperty("browser").toUpperCase())
                : CHROME
        );
    }

    protected <T> T get(Class<T> page) {
        T instance;
        try {
            instance = page.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    @AfterMethod(alwaysRun = true)
    protected void tearDown() {
        quitDriver();
    }
}
