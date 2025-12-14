package fr.ancyrweb.gameboyemulator.assembly.opcodes

class JumpOpcode(opCodeAddress: Int, val address: Int) : Opcode("JUMP", opCodeAddress, 3) {
  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): JumpOpcode {
      val low = bytes[index + 1].toInt() and 0xFF
      val high = bytes[index + 2].toInt() and 0xFF
      val jumpAddress = (high shl 8) or low
      return JumpOpcode(address, jumpAddress)
    }
  }

  override fun disassemblySuffix(): String {
    return "0x${address.toString(16).uppercase().padStart(4, '0')}"
  }
}