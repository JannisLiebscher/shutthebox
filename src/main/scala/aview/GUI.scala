package aview
import swing._
import controller.ControllerInterface
import util.Observer
import org.w3c.dom.Text
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
    score.text = controller.getPlayers
    if (controller.getScore(2) > 45 && controller.getScore(2) <= 45)
      score.text = "Player 1 wins!"

  object sum extends TextField {
    columns = 5
    editable = false
  }
  object dice extends TextField {
    columns = 5
    editable = false
  }
  object score extends TextField {
    columns = 30
    editable = false
  }

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
  val load = new Button("Laden") {
    maximumSize = new Dimension(20, 20)
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.load)
    }
  }
  val save = new Button("Speichern") {
    maximumSize = new Dimension(20, 20)
    reactions += { case event.ButtonClicked(_) =>
      controller.save
    }
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
    contents += load
    contents += save
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
  val southPanel = new FlowPanel {
    contents += score
    contents += next
  }
  contents = new BorderPanel {
    add(northPanel, BorderPanel.Position.North)
    add(undo, BorderPanel.Position.West)
    add(redo, BorderPanel.Position.East)
    add(centerPanel, BorderPanel.Position.Center)
    add(southPanel, BorderPanel.Position.South)
  }
  pack()
  centerOnScreen()
  open()
}
