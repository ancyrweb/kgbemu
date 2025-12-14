package fr.ancyrweb.gameboyemulator.assembly

class NopOpcode(opCodeAddress: Int) : Opcode("NOP", opCodeAddress, 1) {
  companion object {
    fun fromBytes(address: Int): NopOpcode {
      return NopOpcode(address)
    }
  }
}