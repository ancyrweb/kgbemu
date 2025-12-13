package fr.ancyrweb.gameboyemulator.tooling

import fr.ancyrweb.gameboyemulator.output.ConsoleOutputSource
import fr.ancyrweb.gameboyemulator.output.OutputSource
import java.io.File

class Disassembler (
  private val bytes: ByteArray,
  private val outputSource: OutputSource = ConsoleOutputSource(),
) {
  fun dump() {
    var i = 0
    while (i < bytes.size) {
      val opcode = bytes[i].toInt() and 0xFF
      if (opcode == 0x00) {
        outputSource.writeLine("0x${i.toString(16).uppercase().padStart(4, '0')}: NOP")
        i++
      } else if (opcode == 0xC3) {
        if (i + 2 < bytes.size) {
          val low = bytes[i + 1].toInt() and 0xFF
          val high = bytes[i + 2].toInt() and 0xFF
          val address = (high shl 8) or low
          outputSource.writeLine("0x${i.toString(16).uppercase().padStart(4, '0')}: JP 0x${address.toString(16).uppercase().padStart(4, '0')}")
          i += 3
        } else {
          outputSource.writeLine("0x${i.toString(16).uppercase().padStart(4, '0')}: JP <incomplete>")
          i++
        }
      } else {
        outputSource.writeLine("0x${i.toString(16).uppercase().padStart(4, '0')}: DB 0x${opcode.toString(16).uppercase().padStart(2, '0')}")
        i++
      }
    }
  }
}