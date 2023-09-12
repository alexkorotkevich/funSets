package funsets

import scala.annotation.tailrec

trait FunSets extends FunSetsInterface:

  override type FunSet = Int => Boolean

  def contains(s: FunSet, elem: Int): Boolean = s(elem)

  def singletonSet(elem: Int): FunSet =
    (n: Int) => elem == n: Boolean

  def union(s: FunSet, t: FunSet): FunSet = (n: Int) => contains(s, n) || contains(t, n)

  def intersect(s: FunSet, t: FunSet): FunSet = (n: Int) => contains(s, n) && contains(t, n)

  def diff(s: FunSet, t: FunSet): FunSet = (n: Int) => contains(s, n) && !contains(t, n)

  def filter(s: FunSet, p: Int => Boolean): FunSet = (n: Int) => contains(s, n) && p(n)

  val bound = 1000

  def forall(s: FunSet, p: Int => Boolean): Boolean =
    @tailrec
    def iteration(a: Int): Boolean =
      if a > bound then true
      else if contains(s, a) && !p(a) then false
      else iteration(a + 1)

    iteration(-bound)

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: FunSet, p: Int => Boolean): Boolean = !forall(s, x => !p(x))

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: FunSet, f: Int => Int): FunSet = ???

  /**
   * Displays the contents of a set
   */
  def toString(s: FunSet): String =
    val xs = for i <- (-bound to bound) if contains(s, i) yield i
    xs.mkString("{", ",", "}")

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: FunSet): Unit =
    println(s.toString)

//  @main def test = singletonSet(2)

object FunSets extends FunSets
