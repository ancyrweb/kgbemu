package fr.ancyrweb

import fr.ancyrweb.gameboyemulator.tooling.BinaryDumper
import fr.ancyrweb.gameboyemulator.tooling.Disassembler
import java.io.File

fun main() {
//  BinaryDumper(
//    file = File("src/main/resources/tetris.gb")
//  ).dump()

  Disassembler(
    file = File("src/main/resources/tetris.gb")
  ).dump()
}
