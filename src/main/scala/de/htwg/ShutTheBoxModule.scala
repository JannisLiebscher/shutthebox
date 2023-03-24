package main.scala.de.htwg

import main.scala.de.htwg.controller.Controller

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
