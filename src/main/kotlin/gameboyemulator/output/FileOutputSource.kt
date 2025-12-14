package fr.ancyrweb.gameboyemulator.output

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/** File output implementation. Writes output to a file on disk. */
class FileOutputSource(file: File) : OutputSource {
  private val writer: BufferedWriter = BufferedWriter(FileWriter(file))

  override fun writeLine(text: String) {
    writer.write(text)
    writer.newLine()
  }

  override fun write(text: String) {
    writer.write(text)
  }

  override fun flush() {
    writer.flush()
  }

  fun close() {
    writer.close()
  }
}
