import scala.io.StdIn.readLine
import model.Game
import controller.ControllerInterface
import com.google.inject.Guice
object shutthebox {
  @main def run: Unit =
    val injector = Guice.createInjector(new ShutTheBoxModuleModule)
    //val c: ControllerInterface =
    // new controller.Controller(new Game())
    val c = injector.getInstance(classOf[ControllerInterface])
    val tui = new aview.Tui(c)
    val gui = new aview.Gui(c)

    var input: String = ""
    while (input != "q") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
}
