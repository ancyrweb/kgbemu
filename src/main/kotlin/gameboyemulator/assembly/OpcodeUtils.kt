package fr.ancyrweb.gameboyemulator.assembly

object OpcodeUtils {
  fun formatOpcodeByte(byteValue: UByte): String {
    val letters = byteValue.toString(16).uppercase().padStart(2, '0')
    return "${letters[0]}x${letters[1]}"
  }
}
