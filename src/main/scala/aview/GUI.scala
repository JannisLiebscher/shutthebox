package aview
import swing._
import controller.ControllerInterface
import util.Observer
class Gui(controller: ControllerInterface) extends MainFrame with Observer {
  controller.add(this)

  override def update =
    sum.text = controller.getSum.toString
    dice.text = controller.getDice
    for (x <- 0 until buttonCount) {
      if (controller.isShut(x + 1)) {
        topButtons(x).shut
        baseButtons(x).shut
      } else {
        topButtons(x).notShut
        baseButtons(x).notShut
      }
    }
    this.repaint

  object sum extends TextField { columns = 5 }
  object dice extends TextField { columns = 5 }

  val buttonCount = 9
  var topButtons = Vector[guiButtonTop]()
  var baseButtons = Vector[guiButtonBase]()
  for (w <- 1 to buttonCount) {
    topButtons = topButtons :+ new guiButtonTop(w) {
      reactions += { case event.ButtonClicked(_) =>
        controller.doAndPublish(controller.shut, w)
      }
    }
    baseButtons = baseButtons :+ new guiButtonBase(w)
  }

  val next = new Button("Nächster Spieler") {
    maximumSize = new Dimension(20, 20)
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.endMove)
    }
  }

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
      for (w <- 0 to buttonCount - 1) contents += topButtons(w)
    }
    contents += new FlowPanel {
      for (w <- 0 to buttonCount - 1) contents += baseButtons(w)
    }
  }
  contents = new BorderPanel {
    add(northPanel, BorderPanel.Position.North)
    add(undo, BorderPanel.Position.West)
    add(redo, BorderPanel.Position.East)
    add(centerPanel, BorderPanel.Position.Center)
    add(next, BorderPanel.Position.South)
  }
  pack()
  centerOnScreen()
  open()
}
