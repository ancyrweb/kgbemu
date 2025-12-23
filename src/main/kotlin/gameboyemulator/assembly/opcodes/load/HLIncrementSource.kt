package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source that increments HL after the operation (HL+).
 */
class HLIncrementSource : OperandSource() {
  override fun getName(): String {
    return "(HL+)"
  }
}

