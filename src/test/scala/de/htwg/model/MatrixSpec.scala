package de.htwg.model
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
    "filled" should {
      val matr = new Matrix[Int](2, 0)
      "return contents from single cells using row and col" in {
        matr.cell(0, 0) should be(0)
        matr.cell(0, 1) should be(0)
        matr.cell(1, 0) should be(0)
        matr.cell(1, 1) should be(0)
      }
      "keep its size when replaceing cells" in {
        matr.size should be(2)
        matr.replace(0, 0, 1).size should be(matr.size)
        matr.replace(1, 1, 2).size should be(matr.size)
        matr.fill(-1).size should be(matr.size)
      }
      "throw an IndexOutOfBoundsException when trying to access fields outside of the matrix" in {
        an[IndexOutOfBoundsException] should be thrownBy matr.cell(-1, 1)
        an[IndexOutOfBoundsException] should be thrownBy matr.cell(1, -1)
        an[IndexOutOfBoundsException] should be thrownBy matr.cell(2, 1)
      }
      "allow to be fully filled with a single element" in {
        matr.fill(9) should be(Matrix(Vector(Vector(9, 9), Vector(9, 9))))
      }
    }
  }
}
