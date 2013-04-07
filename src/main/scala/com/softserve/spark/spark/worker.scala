package com.softserve.spark.spark

import spark._
import SparkContext._

object Worker {
  lazy val sc = new SparkContext("local", "wordCount")
  def stop = sc.stop

  lazy val linearData = sc.textFile("hdfs://localhost:54310/regression.dat")
  def regression(input: RDD[String]): (Double, Double) = {
    // TODO I don't know what is the correct pattern in spark - one big task (merge sequental maps in one)
    // or smaller ones?
    val rawData = input.map { _.split(" ") }.map { s => (s(0).toDouble, s(1).toDouble) }.cache
    // TODO Probably I would use something like this:
    // rawData.map { s => (s._1, s._2, s._1 * s._2, s._1 * s._1) }.reduce { /* scalaz mplus or hand-made tuple sum */  }
    // anyway, code below looks better so I leave it for demo
    val xsum = rawData.map { _._1 }.sum
    val ysum = rawData.map { _._2 }.sum
    val xysum = rawData.map { s => s._1 * s._2 }.sum
    val xxsum = rawData.map { s => s._1 * s._1 }.sum
    val n = rawData.count

    val b = (n * xysum - xsum * ysum) / (n * xxsum - xsum * xsum)
    val a = (ysum - b * xsum) / n

    (a, b)
  }
}
