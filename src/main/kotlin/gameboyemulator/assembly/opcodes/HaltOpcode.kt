package fr.ancyrweb.gameboyemulator.assembly.opcodes

class HaltOpcode(opCodeAddress: Int) : Opcode(
  "HALT",
  opCodeAddress,
  highClockCycle = 4,
  lowClockCycle = 4
) {
  companion object {
    fun fromBytes(address: Int): HaltOpcode {
      return HaltOpcode(address)
    }
  }
}

