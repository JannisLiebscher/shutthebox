package aview
import swing._
import controller.*
import util.*
class Gui(controller: Controller) extends MainFrame with Observer {
  controller.add(this)
  object sum extends TextField { columns = 5 }
  object dice extends TextField { columns = 5 }
  object shutText extends TextField { columns = 5 }
  object board extends TextPane {
    font = new Font("Monaco", 0, 19)
    preferredSize = new Dimension(600, 300)
  }
  val wuerfel = new Button("Würfeln") {
    reactions += { case event.ButtonClicked(_) =>
      controller.doAndPublish(controller.wuerfeln)
    }
  }
  val shutButton = new Button("Shut") {
    reactions += { case event.ButtonClicked(_) =>
      val tmp = Integer.parseInt(shutText.text)
      shutText.text = ""
      controller.doAndPublish(controller.shut, tmp)
    }
  }

  override def update =
    sum.text = controller.getSum.toString
    dice.text = controller.getDice
    board.text = controller.getBoard
    this.repaint

  this.preferredSize = new Dimension(300, 200)
  title = "ShutTheBox"
  contents = new BoxPanel(Orientation.Vertical) {
    contents += new FlowPanel {
      contents += wuerfel
      contents += new Label(" Shut: ")
      contents += shutButton
      contents += shutText
    }
    contents += board
    contents += new FlowPanel {
      contents += new Label(" Gewürfelt: ")
      contents += dice
      contents += new Label(" Summe: ")
      contents += sum
    }

    border = Swing.EmptyBorder(10, 10, 10, 10)
  }
  pack()
  centerOnScreen()
  open()

}
