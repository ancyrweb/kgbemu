package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source that refers to the memory address pointed to by the HL register.
 */
class HLAddressSource : LoadSource() {
  override fun getName(): String {
    return "(HL)"
  }
}
