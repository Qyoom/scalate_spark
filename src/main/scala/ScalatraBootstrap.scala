import com.qyoom.learn._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new MyScalatraServlet, "/*")
  }
  override def destroy(context: ServletContext) {
    analytics.SparkPi.stop
    analytics.SparkKMeans.stop
  }
}
