package fr.ancyrweb.gameboyemulator.tooling

import fr.ancyrweb.gameboyemulator.assembly.CallOpcode
import fr.ancyrweb.gameboyemulator.assembly.JumpOpcode
import fr.ancyrweb.gameboyemulator.assembly.NopOpcode
import fr.ancyrweb.gameboyemulator.assembly.Opcode
import fr.ancyrweb.gameboyemulator.assembly.UnknownOpcode

/**
 * Scans a byte array and converts it to a list of opcodes.
 * The scanner parses the entire byte array from start to finish.
 */
class OpcodeScanner(
    private val bytes: ByteArray,
) {

  private val opcodes: MutableList<Opcode> = mutableListOf()

  /**
   * Scans the byte array and converts bytes to opcodes.
   * The scanning continues until the end of the byte array.
   */
  fun scan() {
    opcodes.clear()

    var i = 0
    while (i < bytes.size) {
      val opcode = getOpcodeAt(bytes, i)
      opcodes.add(opcode)
      i += opcode.toByteSize()
    }
  }

  /**
   * Returns all the opcodes that have been scanned.
   */
  fun all(): List<Opcode> {
    return opcodes.toList()
  }

  private fun getOpcodeAt(bytes: ByteArray, index: Int): Opcode {
    return when (bytes[index].toInt() and 0xFF) {
      0x00 -> {
        NopOpcode.fromBytes(bytes, index)
      }

      0xC3 -> {
        JumpOpcode.fromBytes(bytes, index)
      }

      0xCD -> {
        CallOpcode.fromBytes(bytes, index)
      }

      else -> {
        UnknownOpcode.fromBytes(bytes, index)
      }
    }
  }
}

