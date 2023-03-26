import scala.io.StdIn.readLine
import model.Game
import controller.ControllerInterface
import com.google.inject.Guice
import model.fileioComponent.FileIOXML
import model.fileioComponent.FileIOJSON
import module.*
object shutthebox {
  @main def run: Unit =
    val injector = Guice.createInjector(new ShutTheBoxModuleXML)
    val c = injector.getInstance(classOf[ControllerInterface])
    val tui = new aview.Tui(c)
    val gui = new aview.Gui(c)

    var input: String = ""
    while (true) {
      input = readLine()
      if(input == "q" | input == "quit") 
        gui.dispose() 
        System.exit(0)
      tui.getInputAndPrintLoop(input)
    }
}
