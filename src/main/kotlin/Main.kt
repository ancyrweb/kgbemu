package fr.ancyrweb

import fr.ancyrweb.gameboyemulator.assembly.Disassembler
import java.io.File

fun main() {
  //  BinaryDumper(
  //    file = File("src/main/resources/tetris.gb")
  //  ).dump()

  val disassembler = Disassembler(file = File("src/main/resources/tetris.gb"))

  disassembler.scan()
  disassembler.dump()
}
