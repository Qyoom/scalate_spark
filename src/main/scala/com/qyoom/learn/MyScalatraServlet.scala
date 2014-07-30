package com.qyoom.learn

import org.scalatra._
import org.scalatra.json._
import scalate.ScalateSupport
import org.json4s.{DefaultFormats, Formats}
import analytics._
import analytics.SparkPi._
import analytics.SparkKMeans._
import analytics.DecisionTreeRunner._

class MyScalatraServlet extends SparkscalatraAppStack {

	protected implicit val jsonFormats: Formats = DefaultFormats

    before(""".*\.json""".r) {
    		contentType = formats("json")
  	}

  	get("/") {
  		<html>
  			<body>
  				<h1>Hello, world!</h1>
        			Say <a href="hello-scalate">hello to Scalate</a>.
	  		</body>
	  	</html>
  	}
  
	//******** TODO: Need to see if there is any risk of causing multiple SparkContexts here
  	//******** I think they may be getting stopped as soon as the "job" finishes.
  	//******** This is just an un-pretty experiment at this stage...

  	get("/pi") {
	  calcPi()
  	}
  
  	get("/kmeans") {
	  kMeansDemo()
  	}
  
  	get("/dectree") {
	  	decTreeDemo()
	}
}
