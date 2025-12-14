package fr.ancyrweb.gameboyemulator.tooling

import fr.ancyrweb.gameboyemulator.assembly.Opcode
import fr.ancyrweb.gameboyemulator.output.ConsoleOutputSource
import fr.ancyrweb.gameboyemulator.output.OutputSource
import java.io.File

class Disassembler(
    private val file: File,
) {

  private val opcodes: MutableList<Opcode> = mutableListOf()

  fun scan() {
    if (!file.exists()) {
      throw IllegalStateException("File not found at ${file.absolutePath}")
    }

    val bytes = file.readBytes()

    if (bytes.size < 0x0150) {
      throw IllegalStateException(
          "ROM file is too small (${bytes.size} bytes). Expected at least 336 bytes."
      )
    }

    opcodes.clear()

    // Scan the entry point section (0x0100 to 0x0103)
    val entryPointBytes = bytes.copyOfRange(0x0100, 0x0104)
    val entryPointScanner = OpcodeScanner(entryPointBytes)
    entryPointScanner.scan()
    opcodes.addAll(entryPointScanner.all())

    // Scan the code section (0x0150 to end)
    val codeBytes = bytes.copyOfRange(0x0150, bytes.size)
    val codeScanner = OpcodeScanner(codeBytes)
    codeScanner.scan()
    opcodes.addAll(codeScanner.all())
  }

  fun dump(outputSource: OutputSource = ConsoleOutputSource()) {
    outputSource.writeLine("=== Disassembly ===")
    outputSource.writeLine("")
    for (opcode in opcodes) {
      outputSource.writeLine(opcode.toDisassemblyString())
    }

    outputSource.flush()
  }
}
