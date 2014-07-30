package com.qyoom.learn.analytics

import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import breeze.linalg.{Vector, DenseVector, squaredDistance}

/**
 * K-means clustering.
 */
object SparkKMeans {
  val args = Array[String]()
  val sc = SparkHelper.getSparkContext(args, "SparkKMeans")
  def stop = sc.stop
  
  val R = 1000

  def parseVector(line: String): Vector[Double] = {
    DenseVector(line.split(' ').map(_.toDouble))
  }

  def closestPoint(p: Vector[Double], centers: Array[Vector[Double]]): Int = {
    var index = 0
    var bestIndex = 0
    var closest = Double.PositiveInfinity

    for (i <- 0 until centers.length) {
      val tempDist = squaredDistance(p, centers(i))
      if (tempDist < closest) {
        closest = tempDist
        bestIndex = i
      }
    }

    bestIndex
  }

  def kMeansDemo() {
    
    val lines = sc.textFile("./test-data/kmeans_test_data.txt")
    val data = lines.map(parseVector _).cache()
    val K = 3 //args(1).toInt
    val convergeDist = 0.001 //args(2).toDouble

    val kPoints = data.takeSample(withReplacement = false, K, 42).toArray
    var tempDist = 1.0

    while (tempDist > convergeDist) {
      val closest = data.map(p => (closestPoint(p, kPoints), (p, 1)))

      val pointStats = closest.reduceByKey { case ((x1, y1), (x2, y2)) => (x1 + x2, y1 + y2)}

      val newPoints = pointStats.map { pair =>
        (pair._1, pair._2._1 * (1.0 / pair._2._2))
      }.collectAsMap()

      tempDist = 0.0
      for (i <- 0 until K) {
        tempDist += squaredDistance(kPoints(i), newPoints(i))
      }

      for (newP <- newPoints) {
        kPoints(newP._1) = newP._2
      }
      println("Finished iteration (delta = " + tempDist + ")")
    }

    println("Final centers:")
    kPoints.foreach(println)
    sc.stop()
  }
}
