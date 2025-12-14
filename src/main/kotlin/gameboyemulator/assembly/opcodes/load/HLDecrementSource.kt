package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source that decrements HL after the operation (HL-).
 */
class HLDecrementSource : LoadSource() {
  override fun getName(): String {
    return "(HL-)"
  }
}

