package de.htwg.se.stb.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ObserverSpec extends AnyWordSpec {
  class ObservableTest extends Observable {}
  class ObserverTest extends Observer {
    var updated = false
    def isUpdated: Boolean = updated
    override def update = updated = true
  }

  "An Observer" should {
    "be notified of changes withing his observable" in {
      val observable = new ObservableTest()
      val observer1, observer2 = new ObserverTest
      observable.add(observer1)
      observable.subscribers should contain(observer1)
      observable.add(observer2)
      observable.subscribers should contain(observer2)

      observer1.isUpdated should be(false)
      observer2.isUpdated should be(false)

      observable.remove(observer1)
      observable.subscribers should not contain (observer1)

      observable.notifyObservers

      observer1.updated should be(false)
      observer2.updated should be(true)
    }
  }
}
