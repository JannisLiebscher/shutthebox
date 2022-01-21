package module
import model.*
import controller.Controller
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import controller.ControllerInterface
import model.fileioComponent.*

class ShutTheBoxModuleMock extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(Controller("mock"))
  }
}
class ShutTheBoxModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(Controller())
    bind(classOf[FileIOInterface]).toInstance(new FileIOJSON())
  }
}
