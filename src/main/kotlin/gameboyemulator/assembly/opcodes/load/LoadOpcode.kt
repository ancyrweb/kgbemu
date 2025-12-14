package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.Opcode

class LoadOpcode(
    opCodeAddress: Int,
    private val from: LoadSource,
    private val to: LoadSource
) : Opcode("LD", opCodeAddress, bytesSize = 1, highClockCycle = 4, lowClockCycle = 4) {
  override fun disassemblySuffix(): String {
    return "${from.getName()}, ${to.getName()}"
  }
}
