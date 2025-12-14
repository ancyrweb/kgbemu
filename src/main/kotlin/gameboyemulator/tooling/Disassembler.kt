package fr.ancyrweb.gameboyemulator.tooling

import fr.ancyrweb.gameboyemulator.assembly.CallOpcode
import fr.ancyrweb.gameboyemulator.assembly.JumpOpcode
import fr.ancyrweb.gameboyemulator.assembly.NopOpcode
import fr.ancyrweb.gameboyemulator.assembly.Opcode
import fr.ancyrweb.gameboyemulator.assembly.UnknownOpcode
import fr.ancyrweb.gameboyemulator.output.ConsoleOutputSource
import fr.ancyrweb.gameboyemulator.output.OutputSource
import java.io.File

class Disassembler(
    private val file: File,
    private val outputSource: OutputSource = ConsoleOutputSource(),
) {

  fun dump() {
    if (!file.exists()) {
      outputSource.writeLine("Error: File not found at ${file.absolutePath}")
      return
    }

    val bytes = file.readBytes()

    if (bytes.size < 0x0150) {
      outputSource.writeLine(
          "Error: ROM file is too small (${bytes.size} bytes). Expected at least 336 bytes."
      )
      return
    }

    val opcodes: MutableList<Opcode> = mutableListOf()

    var i = 0x0100
    while (i < bytes.size) {
      val opcode = getOpcodeAt(bytes, i)
      opcodes.add(opcode)
      i += opcode.toByteSize()

      // Only the entry point is disassembled for now
      // Then we jump straight to the beginning of the code section
      if (i == 0x0104) {
        i = 0x0150
      }
    }

    outputSource.writeLine("=== Disassembly ===")
    outputSource.writeLine("")
    for (opcode in opcodes) {
      outputSource.writeLine(opcode.toDisassemblyString())
    }

    outputSource.flush()
  }

  private fun getOpcodeAt(bytes: ByteArray, index: Int): Opcode {
    return when (bytes[index].toInt() and 0xFF) {
      0x00 -> {
        NopOpcode.fromBytes(bytes, index)
      }

      0xC3 -> {
        JumpOpcode.fromBytes(bytes, index)
      }

      0xCD -> {
        CallOpcode.fromBytes(bytes, index)
      }

      else -> {
        UnknownOpcode.fromBytes(bytes, index)
      }
    }
  }
}
