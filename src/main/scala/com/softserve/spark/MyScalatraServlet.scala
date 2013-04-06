package com.softserve.spark

import org.scalatra._
import scalate.ScalateSupport

class MyScalatraServlet extends SparkscalatraAppStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
        Result is {spark.Worker.wordsCount}
      </body>
    </html>
  }
}
