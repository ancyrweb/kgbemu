package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source for I/O port operations using register C (0xFF00 + C).
 */
class IOPortRegisterSource(private val register: String) : LoadSource() {
  override fun getName(): String {
    return "(\$$register)"
  }

  fun getRegister(): String {
    return register
  }
}

