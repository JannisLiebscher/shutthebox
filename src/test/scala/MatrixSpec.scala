package model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MatrixSpec extends AnyWordSpec {

  "A Matrix" when {
    "empty" should {
      "be created using an Int as a dimension and a generic filling" in {
        val matr = new Matrix[Int](3, 0)
        matr.size should be(3)
        matr.rows(0).forall(p => p == 0) should be(true)
        matr.rows(1).forall(p => p == 0) should be(true)
        matr.rows(2).forall(p => p == 0) should be(true)
      }
      "be instantiated with a full Matrix given as a Vector of Vectors" in {
        val matr = Matrix[Int](Vector(Vector(1, 2)))
        matr.size should be(1)
        matr.cell(0, 0) should be(1)
        matr.cell(0, 1) should be(2)
      }
      "return an empty vector if receiving negative or zero values as size" in {
        val matr = new Matrix[Int](0, 1)
        matr.rows should be(Vector())
        an[IndexOutOfBoundsException] should be thrownBy matr.cell(0, 0)
      }
    }
  }
}
