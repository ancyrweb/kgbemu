package fr.ancyrweb.gameboyemulator.assembly.opcodes

class UnknownOpcode(opCodeAddress: Int, byteValue: UByte) :
    Opcode(
        "??? (${OpcodeUtils.formatOpcodeByte(byteValue)})",
        opCodeAddress,
    ) {
  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): UnknownOpcode {
      return UnknownOpcode(address, bytes[index].toUByte())
    }
  }
}
