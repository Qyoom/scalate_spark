package com.qyoom.learn.analytics

import org.apache.spark._
import org.apache.spark.SparkContext._

object Worker {
  lazy val sc = new SparkContext("local", "Worker test")
  def stop = sc.stop

  def test() {
    println("Worker.test - sc: " + sc)
  }
}
