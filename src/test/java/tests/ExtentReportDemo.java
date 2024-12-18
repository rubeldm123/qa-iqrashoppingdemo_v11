package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportDemo {

    public static void main(String[] args) {
        // Initialize ExtentSparkReporter
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Initialize ExtentReports and attach the reporter
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information to the report
        extent.setSystemInfo("Tester", "Your Name");
        extent.setSystemInfo("Environment", "QA");

        // Log test information
        extent.createTest("Sample Test")
                .pass("This test case passed successfully");

        // Flush the report to ensure it's written to disk
        extent.flush();
    }
}
