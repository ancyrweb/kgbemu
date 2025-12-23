package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.SignedByte

/**
 * Represents a load source for SP + signed offset (used by LDHL SP, n).
 */
class SPOffsetSource(private val offset: SignedByte) : OperandSource() {
  override fun getName(): String {
    return if (offset.isNegative()) {
      "SP-${String.format("$%02X", offset.abs())}"
    } else {
      "SP+${String.format("$%02X", offset.toUnsignedInt())}"
    }
  }

  fun getOffset(): Int {
    return offset.toInt()
  }

  fun getSignedByte(): SignedByte {
    return offset
  }
}

