package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public static int RESPONSE_STATUS_CODE_200 = 200;
    public static int RESPONSE_STATUS_CODE_201 = 201;
    public static int RESPONSE_STATUS_CODE_403 = 403;
    public static int RESPONSE_STATUS_CODE_404 = 404;
    public static int RESPONSE_STATUS_CODE_500 = 500;

    public Properties prop;

    public TestBase () {
        try {
            prop = new Properties();
            FileInputStream ipStream = new FileInputStream(System.getProperty("user.dir")
                    + "/src/main/java/com/qa/config/config.properties");
            prop.load(ipStream);
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
