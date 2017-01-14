package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.IOException;

import static com.relevantcodes.extentreports.LogStatus.PASS;
import static com.relevantcodes.extentreports.LogStatus.SKIP;
import static com.relevantcodes.extentreports.LogStatus.UNKNOWN;

/**
 * Created by Adam on 2016-12-19.
 */
public class TestReport {

    private Screenshot screenshot = new Screenshot();
    private static ExtentReports extentReports;
    private static ExtentTest test;

    /**
     * Class constructor
     * Creates new test instance
     *
     * @param testName
     */
    protected TestReport(String testName) {
        test = extentReports.startTest(testName);
    }

    /**
     * Placeholder for instance of test
     * Needed for adding new log lines in test report
     *
     * @return
     */
    protected static ExtentTest getTest() {
        return test;
    }

    /**
     * Setups new test report and adds system info to it
     *
     * @param replaceExisting
     */
    protected static void setupTestReport(boolean replaceExisting) {
        try {
            extentReports = new ExtentReports(getExtentReportFile(), replaceExisting);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            addSystemInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addSystemInfo() throws IOException {
        extentReports.addSystemInfo("Browser", Helper.readProperties().getProperty("browser"));
    }

    private static String getExtentReportFile() throws IOException {
        return Helper.readProperties().getProperty("report") + "TestReport_" + Helper.getCurrentDate() + ".html";
    }

    /**
     * Ends current test in test report
     */
    protected void endTest() {
        // close report.
        extentReports.endTest(test);
    }

    /**
     * Ends test report and append all tests to it
     */
    protected static void endTestReport() {
        // writing everything to document.
        extentReports.flush();
    }

    /**
     * Adds log to test report with description
     *
     * @param status  log status
     * @param details description
     */
    public static void addLog(LogStatus status, String details) {
        getTest().log(status, details);
    }

    /**
     * Adds log to test report with exception
     *
     * @param status log status
     * @param t      exception
     */
    public static void addLog(LogStatus status, Throwable t) {
        getTest().log(status, t);
    }

    /**
     * Adds log with custom HTML
     *
     * @param status log status
     * @param html   custom html
     */
    public static void addLogWithHtml(LogStatus status, String html) {
        getTest().log(status, "HTML", html);
    }

    /**
     * Gets status of current test run
     *
     * @return
     */
    protected LogStatus getRunStatus() {
        return getTest().getRunStatus();
    }

    /**
     * Sets run status for each test in test report
     */
    protected void setRunStatus() {
        switch (getRunStatus()) {
            case PASS:
                addLogWithHtml(PASS, "Test passed");
                break;
            case FAIL:
                addLog(PASS, "Test failed");
                break;
            case SKIP:
                addLog(SKIP, "Test skipped");
                break;
            default:
                addLog(UNKNOWN, "Test status unknown");
                break;
        }
    }

    /**
     * Adds screenshot to test report
     */
    protected void addScreenshotToReport() {
        addLog(LogStatus.INFO, "Snapshot from last step: " + getTest().addScreenCapture(screenshot.captureScreenShot()));
    }

}