package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source that is a register.
 */
class RegisterSource (private val name: String): LoadSource() {
  override fun getName(): String {
    return name
  }
}