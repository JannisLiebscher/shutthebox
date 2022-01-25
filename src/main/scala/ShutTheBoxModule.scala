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
    bind(classOf[ControllerInterface]).toInstance(
      new Controller(model.Game("mock"), new FileIOJSON)
    )
  }
}
class ShutTheBoxModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(
      Controller("json")
    )
  }
}
class ShutTheBoxModuleXML extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(
      Controller("xml")
    )
  }
}
