package com.softserve.spark

import org.scalatra._
import scalate.ScalateSupport
import org.scalatra.json._
import org.json4s.{DefaultFormats, Formats}

class MyScalatraServlet extends SparkscalatraAppStack {
  import analytics.Worker._
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

  get("/test") {
    test()
  }
}
