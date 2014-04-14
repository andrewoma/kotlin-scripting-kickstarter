package scripts.histogram

import org.junit.Test as test
import java.io.File
import kotlin.test.assertTrue
import scripts.base.TestBase
import kotlin.test.fail

class HistogramTest : TestBase() {
    test fun generateHistogram() {
        System.setProperty("kotlin.script.file", file("/kotlin/histogram.kt").path)

        val output = File.createTempFile("histogram", ".png")
        output.deleteOnExit()

        main(array("-input", file("/test/resources/histogram-sample.txt").path,
                "-label", "Response (s)", "-title", "Web Server Response Times", "-output", output.path))
        assertTrue(output.exists())
    }
}