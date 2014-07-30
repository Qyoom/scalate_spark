package com.softserve.spark.analytics

import org.apache.spark._
import org.apache.spark.SparkContext._

object Worker {
  lazy val sc = new SparkContext("local", "wordCount")
  def stop = sc.stop

  def test() {
    println("Worker.test - sc: " + sc)
  }
}
