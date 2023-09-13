package funsets

class FunSetSuite extends munit.FunSuite:

  import FunSets.*
  import munit.Clue.generate

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  trait TestSets {
    val s1: _root_.funsets.FunSets.FunSet = singletonSet(1)
    val s2: _root_.funsets.FunSets.FunSet = singletonSet(2)
    val positiveNumbersSet: _root_.funsets.FunSets.FunSet = union(singletonSet(1), singletonSet(300))
    val negativeNumbersSet: _root_.funsets.FunSets.FunSet = union(singletonSet(-10), singletonSet(-99))
    val evenNumbersSet: _root_.funsets.FunSets.FunSet = union(singletonSet(4), singletonSet(6))
  }

  test("singleton set one contains one") {

    new TestSets:
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect contains a common element in all sets") {
    new TestSets:
      val s = intersect(s2, s2)
      assert(!contains(s, 1), "intersect 1")
      assert(contains(s, 2), "intersect 2")
  }

  test("diff contains element in first, but not in second set") {
    new TestSets:
      val s = diff(s1, s2)
      assert(contains(s, 1), "diff 1")
      assert(!contains(s, 2), "diff 2")
      assert(!contains(s, 3), "diff 3")
  }

  test("filter") {
    new TestSets:
      val s = filter(s1, (x: Int) => x > 0)
      assert(contains(s, 1), "filter 1")
      assert(!contains(s, 2), "filter 2")
  }

  test("forall") {
    new TestSets:
      assert(forall(positiveNumbersSet, x => x > 0), "forall 1")
      assert(forall(negativeNumbersSet, x => x < 0), "forall 2")
  }

  test("exist") {
    new TestSets:
      assert(exists(positiveNumbersSet, x => x > 0), "exist 1")
      assert(!exists(positiveNumbersSet, x => x < 0), "exist 2")
      assert(exists(negativeNumbersSet, x => x < 0), "exist 3")
      assert(!exists(negativeNumbersSet, x => x > 0), "exist 4")
  }

  test("map") {
    new TestSets:
      var odds = map(evenNumbersSet, x => x + 1)
      assert(contains(odds, 5), "map 1")
      assert(!contains(odds, 4), "map 2")
  }

  import scala.concurrent.duration.*

  override val munitTimeout = 10.seconds
