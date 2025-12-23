package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source that decrements HL after the operation (HL-).
 */
class HLDecrementSource : OperandSource() {
  override fun getName(): String {
    return "(HL-)"
  }
}

