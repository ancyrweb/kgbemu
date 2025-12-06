package fr.ancyrweb.gameboyemulator.cpu

class FlagRegister {
  /**
   * Bit 7 - Z - Zero Flag
   * Bit 6 - N - Subtract Flag
   * Bit 5 - H - Half Carry Flag
   * Bit 4 - C - Carry Flag
   * Bits 3-0 - Not used, always 0
   */
  private var value: UByte = 0u

  fun clear() {
    value = 0u
  }

  fun isZero(): Boolean {
    return (value.toInt() and 0b10000000) != 0
  }

  fun setZero() {
    value = (value.toInt() or 0b10000000).toUByte()
  }

  fun isSubtract(): Boolean {
    return (value.toInt() and 0b01000000) != 0
  }

  fun setSubtract() {
    value = (value.toInt() or 0b01000000).toUByte()
  }

  fun isHalfCarry(): Boolean {
    return (value.toInt() and 0b00100000) != 0
  }

  fun setHalfCarry() {
    value = (value.toInt() or 0b00100000).toUByte()
  }

  fun isCarry(): Boolean {
    return (value.toInt() and 0b00010000) != 0
  }

  fun setCarry() {
    value = (value.toInt() or 0b00010000).toUByte()
  }
}