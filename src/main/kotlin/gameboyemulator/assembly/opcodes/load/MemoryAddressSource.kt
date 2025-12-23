package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

/**
 * Represents a load source that is a memory address (16-bit).
 * Can be a register pair (BC, DE) or a direct address.
 */
class MemoryAddressSource : OperandSource {
  private val address: String
  private val isRegisterPair: Boolean
  private val directAddress: Int?

  constructor(registerPair: String) {
    this.address = registerPair
    this.isRegisterPair = true
    this.directAddress = null
  }

  constructor(address: Int) {
    this.address = String.format("$%04X", address and 0xFFFF)
    this.isRegisterPair = false
    this.directAddress = address
  }

  override fun getName(): String {
    return if (isRegisterPair) {
      "($address)"
    } else {
      "($address)"
    }
  }

  fun isRegisterPair(): Boolean {
    return isRegisterPair
  }

  fun getRegisterPair(): String? {
    return if (isRegisterPair) address else null
  }

  fun getDirectAddress(): Int? {
    return directAddress
  }
}

