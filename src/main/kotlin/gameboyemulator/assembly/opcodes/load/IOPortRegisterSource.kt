package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source for I/O port operations using register C (0xFF00 + C).
 */
class IOPortRegisterSource(private val register: String) : OperandSource() {
  override fun getName(): String {
    return "(\$$register)"
  }

  fun getRegister(): String {
    return register
  }
}

