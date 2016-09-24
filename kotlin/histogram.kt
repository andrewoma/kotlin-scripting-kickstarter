#!/usr/bin/env kotlin-script.sh
package scripts.histogram

import org.kohsuke.args4j.Option
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.CmdLineException
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStreamReader
import java.math.BigDecimal
import java.io.File
import org.jfree.chart.JFreeChart
import org.jfree.data.statistics.HistogramDataset
import org.jfree.data.statistics.HistogramType
import org.jfree.chart.ChartFactory
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.ChartUtilities

val script = File(System.getProperty("kotlin.script.file")!!)

class Config {
    @Option(name = "-output", required = true, metaVar = "<output>", usage = "Output file for the generated histogram")
    val output = ""

    @Option(name = "-input", metaVar = "<input>", usage = "Input file. If not specified stdin is assumed. "
    + "Input is expected to be a sequence of numeric values, one per line.")
    val input: String? = null

    @Option(name = "-title", metaVar = "<title>", usage = "Histogram title")
    val title: String? = null

    @Option(name = "-min", metaVar = "<percentile>", usage = "Minium percentile to include in the chart. Defaults to 5")
    val minPercentile = 5

    @Option(name = "-max", metaVar = "<percentile>", usage = "Maximum percentile to include in the chart. Defaults to 95")
    val maxPercentile = 95

    @Option(name = "-width", metaVar = "<width>", usage = "Width of the generated histogram. Defaults to 600px")
    val width = 600

    @Option(name = "-height", metaVar = "<height>", usage = "Height of the generated histogram. Defaults to 400px")
    val height = 400

    @Option(name = "-bins", metaVar = "<bins>", usage = "Number of bins in the histogram. Defaults to 50.")
    val bins = 50

    @Option(name = "-label", metaVar = "<label>", required = true, usage = "Label for the data.")
    val label = ""

    override fun toString(): String {
        return "config(input=$input, output=$output, title=$title, minPercentile=$minPercentile, " +
        "maxPercentile=$maxPercentile, width=$width, height=$height, bins=$bins, label=$label)"
    }
}
val config = Config()

fun generateHistogram() {
    validateConfig()
    val data = prepareData()
    val chart = generateChart(data)
    ChartUtilities.saveChartAsPNG(File(config.output), chart, config.width, config.height)
}

fun generateChart(data: DoubleArray): JFreeChart {
    val dataset = HistogramDataset()
    dataset.type = HistogramType.RELATIVE_FREQUENCY
    dataset.addSeries(config.label, data, config.bins)

    val chart = ChartFactory.createHistogram(config.title, null, null, dataset, PlotOrientation.VERTICAL, true,
            false, false)!!

    chart.xyPlot?.foregroundAlpha = 0.75f
    return chart
}

fun prepareData(): DoubleArray {
    val reader = BufferedReader(if (config.input == null) InputStreamReader(System.`in`) else FileReader(config.input))
    val sorted = reader.useLines { it.map { BigDecimal(it).toDouble() }.toList().sorted() }
    val start = sorted.size * config.minPercentile / 100
    val end = sorted.size * config.maxPercentile / 100
    if (end <= start) return DoubleArray(0) // Data set must be tiny

    // Some manual fun as JFreeChart seems to require an array instead of a List
    val data = DoubleArray(end - start)
    var to = 0
    for (from in start..end - 1) {
        data[to++] = sorted[from]
    }
    return data
}

fun validateConfig() {
    require(config.input == null || File(config.input).exists() && File(config.input).canRead()) { "Input file '${config.input} does not exist or can't be read" }

    require(config.minPercentile in 0..100) { "Min percentile must be between 0 and 100 inclusive" }
    require(config.maxPercentile in 0..100) { "Max percentile must be between 0 and 100 inclusive" }
    require(config.minPercentile < config.maxPercentile) { "Min percentile must be < max percentile" }
}

fun err(message: String) = System.err.println(message)

fun main(args: Array<String>) {
    val parser = CmdLineParser(config)

    try {
        parser.parseArgument(args.toList())
        generateHistogram()
    } catch (e: CmdLineException) {
        err("Error: ${e.message}\n")
        err("${script.name} generates a histogram chart for a sequence of numbers\n")
        err("Usage: ${script.name} <options>")
        parser.printUsage(System.err)
        err("\nExample: ${script.name} -input data.txt -output responses.png -label 'Response (s)' -bins 100")
        System.exit(1)
    } catch (e: IllegalArgumentException) {
        err("Error: ${e.message}\n")
        System.exit(1)
    }
}