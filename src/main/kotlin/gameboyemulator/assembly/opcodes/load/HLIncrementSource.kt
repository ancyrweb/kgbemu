package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source that increments HL after the operation (HL+).
 */
class HLIncrementSource : LoadSource() {
  override fun getName(): String {
    return "(HL+)"
  }
}

