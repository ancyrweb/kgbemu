package fr.ancyrweb.gameboyemulator.assembly.opcodes

class NopOpcode(opCodeAddress: Int) : Opcode(
  "NOP",
  opCodeAddress,
  highClockCycle = 4,
  lowClockCycle = 4
) {
  companion object {
    fun fromBytes(address: Int): NopOpcode {
      return NopOpcode(address)
    }
  }
}