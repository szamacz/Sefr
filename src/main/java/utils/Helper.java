package utils;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * Created by Adam on 2017-01-06.
 */
public class Helper {

    protected static String getCurrentDate() {
        return new SimpleDateFormat(Constant.DATE_PATTERN).format(Calendar.getInstance().getTime());
    }

    protected static String getElementLocator(WebElement element) {
        String info = element.toString();
        info = info.substring(0, info.length() - 1);
        return " located by " + info.replaceFirst("\\[\\[.*\\)\\]", "").replaceFirst("Proxy.*By.", "");
    }

    /**
     * Gets name of method according to given depth
     *
     * @return
     */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Reads properties from test.properties file
     *
     * @return properties
     */
    public static Properties readProperties() {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(Constant.PROPERTIES_FILENAME);
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * Highlights element on the page
     *
     * @param element webelement to highlight
     */
    public static void highlight(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) Base.getInstance();
        jse.executeScript("arguments[0].style.border='5px solid red'", element);
    }

    /**
     * Decrypts Base64hash string
     *
     * @param code string to decode
     * @return
     */
    public String decrypt(String code) {
        byte[] valueDecoded = Base64.decodeBase64(code.getBytes());
        return new String(valueDecoded);
    }
}
