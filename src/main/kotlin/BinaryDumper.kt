package fr.ancyrweb

import fr.ancyrweb.gameboyemulator.output.ConsoleOutputSource
import fr.ancyrweb.gameboyemulator.output.OutputSource
import java.io.File

/**
 * Dumps the contents of a ROM file in hexadecimal format. Supports various output destinations
 * through the OutputSource abstraction.
 */
class BinaryDumper(
    private val file: File,
    private val outputSource: OutputSource = ConsoleOutputSource(),
) {

  /**
   * Dumps the ROM file contents in hex format with ASCII representation. Displays 16 bytes per line
   * with memory address offset.
   */
  fun dump() {
    if (!file.exists()) {
      outputSource.writeLine("Error: File not found at ${file.absolutePath}")
      return
    }

    outputSource.writeLine("Dumping file: ${file.absolutePath}")
    outputSource.writeLine("File size: ${file.length()} bytes")
    outputSource.writeLine("=".repeat(80))

    val bytes = file.readBytes()
    val bytesPerLine = 16

    for (i in bytes.indices step bytesPerLine) {
      val line = buildLine(bytes, i, bytesPerLine)
      outputSource.writeLine(line)
    }

    outputSource.writeLine("=".repeat(80))
    outputSource.writeLine("Total bytes dumped: ${bytes.size}")
    outputSource.flush()
  }

  /** Builds a single line of hex dump output. */
  private fun buildLine(bytes: ByteArray, offset: Int, bytesPerLine: Int): String {
    val line = StringBuilder()

    // Address offset
    line.append("%08X: ".format(offset))

    // Hex values
    for (j in 0 until bytesPerLine) {
      val index = offset + j
      if (index < bytes.size) {
        line.append("%02X ".format(bytes[index]))
      } else {
        line.append("   ")
      }

      // Extra space in the middle for readability
      if (j % 8 == 0 && j > 0) line.append(" ")
    }

    line.append(" | ")

    // ASCII representation
    for (j in 0 until bytesPerLine) {
      val index = offset + j
      if (index < bytes.size) {
        val byte = bytes[index].toInt() and 0xFF
        val char = if (byte in 32..126) byte.toChar() else '.'
        line.append(char)
      }
    }

    return line.toString()
  }

  /** Closes the output source and releases resources. */
  fun close() {
    outputSource.close()
  }
}
