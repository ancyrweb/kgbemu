package fr.ancyrweb.gameboyemulator.assembly

class JumpOpcode(opCodeAddress: Int, val address: Int) : Opcode("JUMP", opCodeAddress, 3) {
  companion object {
    fun fromBytes(bytes: ByteArray, index: Int): JumpOpcode {
      val low = bytes[index + 1].toInt() and 0xFF
      val high = bytes[index + 2].toInt() and 0xFF
      val address = (high shl 8) or low
      return JumpOpcode(index, address)
    }
  }

  override fun disassemblySuffix(): String {
    return "0x${address.toString(16).uppercase().padStart(4, '0')}"
  }
}