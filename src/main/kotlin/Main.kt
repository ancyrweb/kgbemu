package fr.ancyrweb

import java.io.File

fun main() {
  ROMDumper(
    file = File("src/main/resources/tetris.gb")
  ).dump()
}