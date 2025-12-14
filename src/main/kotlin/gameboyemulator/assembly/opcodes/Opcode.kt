package fr.ancyrweb.gameboyemulator.assembly.opcodes

abstract class Opcode (private val name: String, private val opCodeAddress: Int, private val bytesSize: Int) {
  fun toDisassemblyString(): String {
     return "0x${opCodeAddress.toString(16).uppercase().padStart(4, '0')}: $name ${disassemblySuffix()}"
   }

  fun toByteSize(): Int {
    return bytesSize
  }

  protected open fun disassemblySuffix(): String {
    return ""
  }
}
