package fr.ancyrweb.gameboyemulator.assembly.opcodes

class IllegalOpcode(opCodeAddress: Int, byteValue: UByte) :
  Opcode(
    "Illegal (${OpcodeUtils.formatOpcodeByte(byteValue)})",
    opCodeAddress,
    bytesSize = 1,
  ) {
  companion object {
    /**
     * Checks if an opcode byte value is illegal (not assigned) in the Game Boy CPU.
     * According to the Game Boy CPU specification, the following opcodes are illegal:
     * 0xD3, 0xDB, 0xDD, 0xE3, 0xE4, 0xEB, 0xEC, 0xED, 0xF4, 0xFC, 0xFD
     */
    fun isIllegal(opcodeByte: Int): Boolean {
      return opcodeByte in setOf(0xD3, 0xDB, 0xDD, 0xE3, 0xE4, 0xEB, 0xEC, 0xED, 0xF4, 0xFC, 0xFD)
    }

    fun fromBytes(bytes: ByteArray, index: Int, address: Int): IllegalOpcode {
      return IllegalOpcode(address, bytes[index].toUByte())
    }
  }
}

