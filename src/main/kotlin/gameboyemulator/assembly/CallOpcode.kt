package fr.ancyrweb.gameboyemulator.assembly

class CallOpcode(opCodeAddress: Int, val address: Int) : Opcode("CALL", opCodeAddress, 3) {
  companion object {
    fun fromBytes(bytes: ByteArray, index: Int): CallOpcode {
      val low = bytes[index + 1].toInt() and 0xFF
      val high = bytes[index + 2].toInt() and 0xFF
      val address = (high shl 8) or low
      return CallOpcode(index, address)
    }
  }

  override fun disassemblySuffix(): String {
    return "0x${address.toString(16).uppercase().padStart(4, '0')}"
  }
}
