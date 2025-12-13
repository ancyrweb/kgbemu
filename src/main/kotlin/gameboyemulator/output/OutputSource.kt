package fr.ancyrweb.gameboyemulator.output

/**
 * Abstraction for output destinations. Allows dumping ROM data to various targets (console, file,
 * network, etc.)
 */
interface OutputSource {
  /** Writes a line of text to the output destination. */
  fun writeLine(text: String)

  /** Writes text without a newline to the output destination. */
  fun write(text: String)

  /** Flushes any buffered output. */
  fun flush()

  /** Closes the output source and releases resources. */
  fun close()
}
