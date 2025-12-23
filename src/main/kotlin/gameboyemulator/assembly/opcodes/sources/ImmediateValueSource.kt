package fr.ancyrweb.gameboyemulator.assembly.opcodes.sources

/**
 * Represents a load source that is an immediate value.
 * Can be a 8-bit or 16-bit immediate value depending on the context.
 */
class ImmediateValueSource (private val value: Int, private val size: Size) : OperandSource() {
  enum class Size {
    BYTE,
    WORD
  }

  override fun getName(): String {
    return when (size) {
      Size.BYTE -> String.format("#$%02X", value and 0xFF)
      Size.WORD -> String.format("#$%04X", value and 0xFFFF)
    }
  }

  fun getValue(): Int {
    return value
  }

  fun getSize(): Size {
    return size
  }
}