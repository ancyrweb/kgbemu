package fr.ancyrweb.gameboyemulator.assembly.opcodes

class PushOpcode(opCodeAddress: Int, private val registerPair: String) :
  Opcode("PUSH", opCodeAddress, bytesSize = 1, highClockCycle = 16, lowClockCycle = 16) {

  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): PushOpcode? {
      val opcodeByte = bytes[index].toInt() and 0xFF
      val registerPair = when (opcodeByte) {
        0xC5 -> "BC"
        0xD5 -> "DE"
        0xE5 -> "HL"
        0xF5 -> "AF"
        else -> return null
      }

      return PushOpcode(address, registerPair)
    }
  }

  override fun disassemblySuffix(): String {
    return registerPair
  }

  fun getRegisterPair(): String {
    return registerPair
  }
}

