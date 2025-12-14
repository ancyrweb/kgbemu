package fr.ancyrweb.gameboyemulator.output

/** Console output implementation. Writes output to standard output (System.out). */
class ConsoleOutputSource : OutputSource {
  override fun writeLine(text: String) {
    println(text)
  }

  override fun write(text: String) {
    print(text)
  }

  override fun flush() {
    System.out.flush()
  }


}
