package fr.ancyrweb

import fr.ancyrweb.gameboyemulator.tooling.BinaryDumper
import fr.ancyrweb.gameboyemulator.tooling.Disassembler
import java.io.File

fun main() {
//  BinaryDumper(
//    file = File("src/main/resources/tetris.gb")
//  ).dump()

  Disassembler(
    bytes = byteArrayOf(0x00, 0xC3.toByte(), 0x50, 0x01),
  ).dump()
}
