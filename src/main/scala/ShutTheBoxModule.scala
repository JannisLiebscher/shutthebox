import model.*
import controller.Controller
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import controller.ControllerInterface

class ShutTheBoxModuleModuleMock extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(Controller("mock"))
  }
}
class ShutTheBoxModuleModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(Controller())
  }
}
