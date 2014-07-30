package com.qyoom.learn.analytics

import org.apache.spark._
import org.apache.spark.SparkContext._

import com.qyoom.learn.analytics.SparkHelper._

import scala.math.random

/** Computes an approximation to pi */
object SparkPi {

	//val conf = new SparkConf().setAppName("Spark Pi").setMaster("localhost")
	val args = Array[String]()
    val sc = SparkHelper.getSparkContext(args, "SparkPi")
    //lazy val sc = new SparkContext("local", "Calc Pi")
  	def stop = sc.stop

	def calcPi(slices: Int = 2) {
    
	    println("SparkPi.calcPi - sc: " + sc)
	    //val slices = if (args) args(0).toInt else 2
	    
	    val n = 100000 * slices
	
	    val count = sc.parallelize(1 to n, slices).map { i =>
	      val x = random * 2 - 1
	      val y = random * 2 - 1
	      if (x * x + y * y < 1) 1 else 0
	    }.reduce(_ + _)
	
	    println("Pi is roughly " + 4.0 * count / n)
	        
    }
}