package fr.ancyrweb.gameboyemulator.assembly.opcodes

class IllegalOpcode(opCodeAddress: Int, byteValue: UByte) :
  Opcode(
    "Illegal (${OpcodeUtils.formatOpcodeByte(byteValue)})",
    opCodeAddress,
    1,
  ) {
  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): UnknownOpcode {
      return UnknownOpcode(address, bytes[index].toUByte())
    }
  }
}

