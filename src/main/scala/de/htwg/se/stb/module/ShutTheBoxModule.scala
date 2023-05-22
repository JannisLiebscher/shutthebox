package de.htwg.se.stb.module

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.stb.controller.{Controller, ControllerInterface}
import de.htwg.se.stb.model.*
import de.htwg.se.stb.model.fileioComponent.*
import net.codingwell.scalaguice.ScalaModule

class ShutTheBoxModuleMock extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(
      new Controller(Game("mock"), new FileIOJSON)
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
class ShutTheBoxModuleSQL extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(
      Controller("sql")
    )
  }
}
class ShutTheBoxModuleMongo extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[ControllerInterface]).toInstance(
      Controller("mongo")
    )
  }
}
