package com.qyoom.learn.analytics

import org.apache.spark.{SparkConf, SparkContext}

object SparkHelper {

    def getSparkContext(
    			args: Array[String] = Array[String](), title: String = "Spark Template"
	) = {
        assert(args.length == 0 || args.length == 2 || args.length == 3)
        val cores = Runtime.getRuntime.availableProcessors()
        val parallelism = if (args.length == 3) args(2) else s"${3 * cores}"
        val master = if (args.length > 0) args(0) else s"local[$cores]"
        val JARS = if (args.length > 0) Seq(args(1)) else Seq.empty
        var conf = new SparkConf()
            .setMaster(master)
            .setAppName(title)
            .set("spark.executor.memory", "1g")
            .set("spark.default.parallelism", parallelism)
            .setJars(JARS)
        if (System.getenv("SPARK_HOME") != null) {conf = conf.setSparkHome(System.getenv("SPARK_HOME"))}
        
        println("------ example.Helper.getSparkContext----------------\n" +
            "cores: " + cores + "\n" +
            "parallelism: " + parallelism + "\n" +
            "master: " + master + "\n" +
            "JARS: " + JARS + "\n" +
            "App name: " + title + "\n" +
            "SPARK_HOME: " + System.getenv("SPARK_HOME") + "\n" +
            "---------------------------------------------------------"
        )
        
        new SparkContext(conf)
    } // end getSparkContext

}