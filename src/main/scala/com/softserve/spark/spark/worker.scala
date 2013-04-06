package com.softserve.spark.spark

import spark.SparkContext
import SparkContext._

object Worker {
  val sc = new SparkContext("local", "wordCount")

  def wordsCount(): Long = {
    sc.parallelize(data).flatMap { _.split(" ") }.count
  }
  val data = """
    The master parameter is a string specifying a Spark or Mesos
    cluster URL to connect to, or a special “local” string to run in
    local mode, as described below. appName is a name for your
    application, which will be shown in the cluster web UI. Finally, the
    last two parameters are needed to deploy your code to a cluster if running in distributed mode, as described later.
    In the Spark shell, a special interpreter-aware SparkContext is already created for you, in the variable
    called sc. Making your own SparkContext will not work. You can set which master the
    context connects to using the MASTER environment variable. For example, to run on four cores, use
    """.lines.toIndexedSeq
}
