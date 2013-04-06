package com.softserve.spark

import org.scalatra._
import scalate.ScalateSupport

class MyScalatraServlet extends SparkscalatraAppStack {
import spark.Worker._

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
        Result is {regression(linearData)}
      </body>
    </html>
  }
}
