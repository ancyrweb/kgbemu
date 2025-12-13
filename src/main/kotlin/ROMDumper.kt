package fr.ancyrweb

import java.io.File

class ROMDumper (val file: File) {

  fun dump() {
    if (!file.exists()) {
      println("Error: File not found at ${file.absolutePath}")
      return
    }

    println("Dumping file: ${file.absolutePath}")
    println("File size: ${file.length()} bytes")
    println("=" .repeat(80))

    val bytes = file.readBytes()
    val bytesPerLine = 16

    for (i in bytes.indices step bytesPerLine) {
      // Print address offset
      print("%08X: ".format(i))

      // Print hex values
      for (j in 0 until bytesPerLine) {
        val index = i + j
        if (index < bytes.size) {
          print("%02X ".format(bytes[index]))
        } else {
          print("   ")
        }

        // Add extra space in the middle for readability
        if (j % 8 == 0 && j > 0) print(" ")
      }

      print(" | ")

      // Print ASCII representation
      for (j in 0 until bytesPerLine) {
        val index = i + j
        if (index < bytes.size) {
          val byte = bytes[index].toInt() and 0xFF
          val char = if (byte in 32..126) byte.toChar() else '.'
          print(char)
        }
      }

      println()
    }

    println("=" .repeat(80))
    println("Total bytes dumped: ${bytes.size}")
  }
}
