package aview
import swing._
import controller.Controller
import util.Observer
class Gui(controller: Controller) extends MainFrame with Observer {
  controller.add(this)
  override def update =
    sum.text = controller.getSum.toString
    dice.text = controller.getDice
    if (controller.isShut(1) == true) button1Up.text = "#"
    else button1Up.text = "1"
    if (controller.isShut(2) == true) button2Up.text = "#"
    else button2Up.text = "2"
    if (controller.isShut(3) == true) button3Up.text = "#"
    else button3Up.text = "3"
    if (controller.isShut(4) == true) button4Up.text = "#"
    else button4Up.text = "4"
    if (controller.isShut(5) == true) button5Up.text = "#"
    else button5Up.text = "5"
    if (controller.isShut(6) == true) button6Up.text = "#"
    else button6Up.text = "6"
    if (controller.isShut(7) == true) button7Up.text = "#"
    else button7Up.text = "7"
    if (controller.isShut(8) == true) button8Up.text = "#"
    else button8Up.text = "8"
    if (controller.isShut(9) == true) button9Up.text = "#"
    else button9Up.text = "9"
    if (controller.isShut(1) == true) button1Down.text = "1"
    if (controller.isShut(2) == true) button2Down.text = "2"
    if (controller.isShut(3) == true) button3Down.text = "3"
    if (controller.isShut(4) == true) button4Down.text = "4"
    if (controller.isShut(5) == true) button5Down.text = "5"
    if (controller.isShut(6) == true) button6Down.text = "6"
    if (controller.isShut(7) == true) button7Down.text = "7"
    if (controller.isShut(8) == true) button8Down.text = "8"
    if (controller.isShut(9) == true) button9Down.text = "9"
    this.repaint

  object sum extends TextField { columns = 5 }
  object dice extends TextField { columns = 5 }
  object shutText extends TextField { columns = 5 }
  val undo = new Button("Undo") {
    maximumSize = new Dimension(20, 20)
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.undo)
    }
  }
  val redo = new Button("Redo") {
    maximumSize = new Dimension(20, 20)
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.redo)
    }
  }
  val wuerfel = new Button("Würfeln") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.wuerfeln)
    }
  }
  val button1Up = new Button("1") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 1)
    }
  }
  val button2Up = new Button("2") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 2)
    }
  }
  val button3Up = new Button("3") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 3)
    }
  }
  val button4Up = new Button("4") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 4)
    }
  }
  val button5Up = new Button("5") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 5)
    }
  }
  val button6Up = new Button("6") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 6)
    }
  }
  val button7Up = new Button("7") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 7)
    }
  }
  val button8Up = new Button("8") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 8)
    }
  }
  val button9Up = new Button("9") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.shut, 9)
    }
  }
  val button1Down = new Button("#")
  val button2Down = new Button("#")
  val button3Down = new Button("#")
  val button4Down = new Button("#")
  val button5Down = new Button("#")
  val button6Down = new Button("#")
  val button7Down = new Button("#")
  val button8Down = new Button("#")
  val button9Down = new Button("#")

  this.preferredSize = new Dimension(580, 200)
  title = "ShutTheBox"
  val northPanel = new FlowPanel {
    contents += wuerfel
    contents += new Label(" Gewürfelt: ")
    contents += dice
    contents += new Label(" Summe: ")
    contents += sum
  }
  val centerPanel = new FlowPanel {
    contents += new FlowPanel {
      contents += button1Up
      contents += button2Up
      contents += button3Up
      contents += button4Up
      contents += button5Up
      contents += button6Up
      contents += button7Up
      contents += button8Up
      contents += button9Up
    }
    contents += new FlowPanel {
      contents += button1Down
      contents += button2Down
      contents += button3Down
      contents += button4Down
      contents += button5Down
      contents += button6Down
      contents += button7Down
      contents += button8Down
      contents += button9Down
    }
  }
  contents = new BorderPanel {
    add(northPanel, BorderPanel.Position.North)
    add(undo, BorderPanel.Position.West)
    add(redo, BorderPanel.Position.East)
    add(centerPanel, BorderPanel.Position.Center)
  }

  pack()
  centerOnScreen()
  open()
}
