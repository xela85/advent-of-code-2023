package fr.xela.adventofcode.day1

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.io.file.*

import scala.annotation.tailrec
@main
def exercice1() =
  Files[IO].readUtf8Lines(Path("files/day1.txt"))
    .filter(_.nonEmpty)
    .map { line =>
      val digits = line.filter(Character.isDigit)
      (digits.head.toString + digits.last).toInt
    }
    .compile.foldMonoid
    .flatTap(IO.println)
    .unsafeRunSync()




val digitMap = Map(
   "one" -> 1,
  "two" -> 2,
  "three" -> 3,
  "four" -> 4,
  "five" -> 5,
  "six" -> 6,
  "seven" -> 7,
  "eight" -> 8,
  "nine" -> 9,
) ++ (1 to 9).map(digit => digit.toString -> digit)

@tailrec
def allIndexesOf(str: String, contained: String, startIndex: Int = 0, acc: List[Int] = Nil): List[Int] =
  str.indexOf(contained, startIndex) match
    case -1 => acc
    case n => allIndexesOf(str, contained, startIndex + 1, n :: acc)

@main
def exercice2() =
  Files[IO].readUtf8Lines(Path("files/day1.txt"))
    .filter(_.nonEmpty)
    .map { line =>
      val allDigits: List[Int] = digitMap.toList.flatMap {
        case (digit, value) =>
          allIndexesOf(line, digit).map(_ -> value)
      }.sortBy(_._1).map(_._2)
      allDigits.head * 10 + allDigits.last
    }
    .compile.foldMonoid
    .flatTap(IO.println)
    .unsafeRunSync()


