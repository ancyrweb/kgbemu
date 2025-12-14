package fr.ancyrweb.gameboyemulator.assembly

fun formatOpcodeByte(byteValue: UByte): String {
  val letters = byteValue.toString(16).uppercase().padStart(2, '0')
  return "${letters[0]}x${letters[1]}"
}

class UnknownOpcode(opCodeAddress: Int, byteValue: UByte) :
    Opcode(
        "??? (${formatOpcodeByte(byteValue)})",
        opCodeAddress,
        1,
    ) {
  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): UnknownOpcode {
      return UnknownOpcode(address, bytes[index].toUByte())
    }
  }
}
