package fr.ancyrweb.gameboyemulator.assembly.opcodes

class PopOpcode(opCodeAddress: Int, private val registerPair: String) :
  Opcode("POP", opCodeAddress, bytesSize = 1, highClockCycle = 12, lowClockCycle = 12) {

  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): PopOpcode? {
      val opcodeByte = bytes[index].toInt() and 0xFF
      val registerPair = when (opcodeByte) {
        0xC1 -> "BC"
        0xD1 -> "DE"
        0xE1 -> "HL"
        0xF1 -> "AF"
        else -> return null
      }

      return PopOpcode(address, registerPair)
    }
  }

  override fun disassemblySuffix(): String {
    return registerPair
  }

  fun getRegisterPair(): String {
    return registerPair
  }
}

