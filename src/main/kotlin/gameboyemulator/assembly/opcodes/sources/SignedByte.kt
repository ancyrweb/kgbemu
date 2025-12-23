package fr.ancyrweb.gameboyemulator.assembly.opcodes.sources

/**
 * Represents an 8-bit signed value (-128 to 127).
 * This is a value object that encapsulates the behavior of a signed byte.
 */
class SignedByte(private val value: Byte) {
  constructor(unsignedByte: Int) : this(unsignedByte.toByte())

  /**
   * Returns the signed integer value (-128 to 127).
   */
  fun toInt(): Int {
    return value.toInt()
  }

  /**
   * Returns the unsigned byte representation (0 to 255).
   */
  fun toUnsignedInt(): Int {
    return value.toInt() and 0xFF
  }

  /**
   * Returns true if the value is negative.
   */
  fun isNegative(): Boolean {
    return value < 0
  }

  /**
   * Returns the absolute value.
   */
  fun abs(): Int {
    return if (value < 0) -value.toInt() else value.toInt()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SignedByte) return false
    return value == other.value
  }

  override fun hashCode(): Int {
    return value.hashCode()
  }

  override fun toString(): String {
    return value.toString()
  }
}

