package fr.ancyrweb

import java.io.File

fun main() {
  BinaryDumper(
    file = File("src/main/resources/tetris.gb")
  ).dump()
}
