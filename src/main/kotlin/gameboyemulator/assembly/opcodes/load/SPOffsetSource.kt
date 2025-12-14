package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source for SP + signed offset (used by LDHL SP, n).
 */
class SPOffsetSource(private val offset: Int) : LoadSource() {
  override fun getName(): String {
    return if (offset >= 0) {
      "SP+${String.format("$%02X", offset and 0xFF)}"
    } else {
      "SP-${String.format("$%02X", (-offset) and 0xFF)}"
    }
  }

  fun getOffset(): Int {
    return offset
  }
}

