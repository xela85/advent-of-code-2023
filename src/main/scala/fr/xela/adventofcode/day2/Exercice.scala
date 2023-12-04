package fr.xela.adventofcode.day2

import cats.effect.IO
import fs2.Chunk
import cats.effect.unsafe.implicits.global
import fs2.io.file.{Files, Path}
import monocle.syntax.all.*

case class ColorTriplet(red: Int, blue: Int, green: Int) {
  def possibleWith(bag: ColorTriplet): Boolean = red <= bag.red && blue <= bag.blue && green <= bag.green
  def max(another: ColorTriplet): ColorTriplet = ColorTriplet(red.max(another.red), blue.max(another.blue), green.max(another.green))

  def power: Int = red * blue * green

}


val redRegex = raw"(\d+) red".r
val blueRegex = raw"(\d+) blue".r
val greenRegex = raw"(\d+) green".r
def parseGame(str: String) = {
  val parts = str.split(",").map(_.trim)
  parts.foldLeft(ColorTriplet(0, 0, 0)) { (game, part) =>
     part match {
       case redRegex(number) => game.focus(_.red).modify(_ + number.toInt)
       case blueRegex(number) => game.focus(_.blue).modify(_ + number.toInt)
       case greenRegex(number) => game.focus(_.green).modify(_ + number.toInt)
     }
  }
}

val input = ColorTriplet(red = 12, blue = 14, green = 13)

@main
def exercice1() =
  Files[IO].readUtf8Lines(Path("files/day2.txt"))
    .filter(_.nonEmpty)
    .map(_.replaceAll(raw"Game \d+:", "").split(";").toList.map(parseGame))
    .zipWithIndex
    .map { (game, index) =>
      if(game.forall(_.possibleWith(input))) index + 1
      else 0
    }
    .compile.foldMonoid
    .flatTap(IO.println)
    .unsafeRunSync()

@main
def exercice2() =
  Files[IO].readUtf8Lines(Path("files/day2.txt"))
    .filter(_.nonEmpty)
    .map(_.replaceAll(raw"Game \d+:", "").split(";").toList.map(parseGame))
    .map { game =>
      game.reduce(_ max _).power
    }
    .compile.foldMonoid
    .flatTap(IO.println)
    .unsafeRunSync()
