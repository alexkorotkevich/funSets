package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */


  trait TestSets {
    val s1: _root_.funsets.FunSets.FunSet = singletonSet(1)
    val s2: _root_.funsets.FunSets.FunSet = singletonSet(2)
    val s3: _root_.funsets.FunSets.FunSet = singletonSet(3)
    val s4: _root_.funsets.FunSets.FunSet = singletonSet(1)
    val s5: _root_.funsets.FunSets.FunSet = singletonSet(4)
    val positiveNumbersSet: _root_.funsets.FunSets.FunSet = union(singletonSet(1), singletonSet(300))
    val negativeNumbersSet: _root_.funsets.FunSets.FunSet = union(singletonSet(-10), singletonSet(-99))
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


  import scala.concurrent.duration.*

  override val munitTimeout = 10.seconds
