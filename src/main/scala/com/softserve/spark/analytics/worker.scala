package com.softserve.spark.analytics

import spark._
import SparkContext._

object Worker {
  lazy val sc = new SparkContext("local", "wordCount")
  def stop = sc.stop

  lazy val regressionData = sc.textFile("hdfs://localhost:54310/regression.dat")
  def linearRegression(input: RDD[String]): CarsAnalysisResult = {
    // TODO I don't know what is the correct pattern in spark - one big task (merge sequental maps in one)
    // or smaller ones?
    val rawData = input.map { _.split("\\s+") }
      .filter{ _.length == 3 }
      .map { s => Point(s(1).toDouble, s(2).toDouble) }
      .cache
    // TODO Probably I would use something like this:
    // rawData.map { s => (s._1, s._2, s._1 * s._2, s._1 * s._1) }.reduce { [> scalaz mplus or hand-made tuple sum <]  }
    // anyway, code below looks better so I leave it for demo
    val xsum = rawData.map { _.x }.sum
    val ysum = rawData.map { _.y }.sum
    val xysum = rawData.map { s => s.x * s.y }.sum
    val xxsum = rawData.map { s => s.x * s.x }.sum
    val n = rawData.count

    val b = (n * xysum - xsum * ysum) / (n * xxsum - xsum * xsum)
    val a = (ysum - b * xsum) / n

    CarsAnalysisResult(rawData.collect /*With bigger data sample can be taken*/, a, b)
  }
}

case class CarsAnalysisResult(sample: Seq[Point], a: Double, b: Double)
case class Point(x: Double, y: Double)
