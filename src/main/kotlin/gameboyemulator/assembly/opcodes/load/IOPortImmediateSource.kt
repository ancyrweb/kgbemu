package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source for I/O port operations using immediate offset (0xFF00 + n).
 */
class IOPortImmediateSource(private val offset: Int) : OperandSource() {
  override fun getName(): String {
    return String.format("($%02X)", offset and 0xFF)
  }

  fun getOffset(): Int {
    return offset
  }
}

