package scripts.histogram

import org.junit.Test
import java.io.File
import kotlin.test.assertTrue
import scripts.base.TestBase

class HistogramTest : TestBase() {
    @Test fun generateHistogram() {
        System.setProperty("kotlin.script.file", file("/kotlin/histogram.kt").path)

        val output = File.createTempFile("histogram", ".png")
        output.deleteOnExit()

        main(arrayOf("-input", file("/test/resources/histogram-sample.txt").path,
                "-label", "Response (s)", "-title", "Web Server Response Times", "-output", output.path))
        assertTrue(output.exists())
    }
}