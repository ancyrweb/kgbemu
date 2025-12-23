package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source that refers to the memory address pointed to by the HL register.
 */
class HLAddressSource : OperandSource() {
  override fun getName(): String {
    return "(HL)"
  }
}
