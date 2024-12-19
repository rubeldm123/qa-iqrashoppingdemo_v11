package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = "test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setReportName("Automation Test Results");
            sparkReporter.config().setDocumentTitle("Extent Report");
            sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Your Name");
        }
        return extent;
    }
}
