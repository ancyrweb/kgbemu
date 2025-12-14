package fr.ancyrweb.gameboyemulator.assembly

import fr.ancyrweb.gameboyemulator.assembly.opcodes.CallOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.HaltOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.IllegalOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.JumpOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.NopOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.Opcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.UnknownOpcode

/**
 * Scans a byte array and converts it to a list of opcodes. The scanner parses the entire byte array
 * from start to finish.
 *
 * @param bytes The byte array to scan
 * @param startAddress The memory address where the byte array starts (default: 0)
 */
class OpcodeScanner(
    private val bytes: ByteArray,
    private val startAddress: Int = 0,
) {

  private val opcodes: MutableList<Opcode> = mutableListOf()

  /**
   * Scans the byte array and converts bytes to opcodes. The scanning continues until the end of the
   * byte array.
   */
  fun scan() {
    opcodes.clear()

    var i = 0
    while (i < bytes.size) {
      val opcode = getOpcodeAt(bytes, i, startAddress + i)
      opcodes.add(opcode)
      i += opcode.toByteSize()
    }
  }

  /**
   * Returns all the opcodes that have been scanned.
   * */
  fun all(): List<Opcode> {
    return opcodes.toList()
  }

  /**
   * Returns the opcode at the given index in the byte array.
   */
  private fun getOpcodeAt(bytes: ByteArray, index: Int, address: Int): Opcode {
    val opcodeByte = bytes[index].toInt() and 0xFF

    // Check if this is an illegal opcode first
    if (IllegalOpcode.isIllegal(opcodeByte)) {
      return IllegalOpcode.fromBytes(bytes, index, address)
    }

    return when (opcodeByte) {
      0x00 -> {
        NopOpcode.fromBytes(address)
      }

      0x76 -> {
        HaltOpcode.fromBytes(address)
      }

      0xC3 -> {
        JumpOpcode.fromBytes(bytes, index, address)
      }

      0xCD -> {
        CallOpcode.fromBytes(bytes, index, address)
      }

      else -> {
        UnknownOpcode.fromBytes(bytes, index, address)
      }
    }
  }
}
