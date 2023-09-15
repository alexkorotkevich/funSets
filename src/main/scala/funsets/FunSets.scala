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
    def iteration(n: Int): Boolean =
      if n > bound then true
      else if contains(s, n) && !p(n) then false
      else iteration(n + 1)

    iteration(-bound)

  def exists(s: FunSet, p: Int => Boolean): Boolean = !forall(s, n => !p(n))

  def map(s: FunSet, f: Int => Int): FunSet = (n: Int) => exists(s, (x: Int) => n == f(x))

  def toString(s: FunSet): String =
    val xs = for i <- (-bound to bound) if contains(s, i) yield i
    xs.mkString("{", ",", "}")

  def printSet(s: FunSet): Unit =
    println(s.toString)

object FunSets extends FunSets
