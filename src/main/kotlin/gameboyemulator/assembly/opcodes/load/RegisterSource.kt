package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source that is a register.
 */
class RegisterSource (private val name: String): OperandSource() {
  override fun getName(): String {
    return name
  }

  fun getRegisterName(): String {
    return name
  }
}