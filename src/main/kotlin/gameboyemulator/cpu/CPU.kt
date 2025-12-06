package fr.ancyrweb.gameboyemulator.cpu

class CPU {
  private val registers = mapOf(
    "A" to ByteRegister(),
    "B" to ByteRegister(),
    "C" to ByteRegister(),
    "D" to ByteRegister(),
    "E" to ByteRegister(),
    "H" to ByteRegister(),
    "L" to ByteRegister()
  )

  private val flag = FlagRegister()

  fun load(string: String, i: UByte) {
    val register = registers[string] ?: throw IllegalArgumentException("Unknown register")
    register.write(i)
  }

  fun load(dest: String, src: String) {
    val srcRegister = registers[src] ?: throw IllegalArgumentException("Unknown register")
    val destRegister = registers[dest] ?: throw IllegalArgumentException("Unknown register")
    destRegister.write(srcRegister.read())
  }

  fun read(string: String): UByte {
    val register = registers[string] ?: throw IllegalArgumentException("Unknown register")
    return register.read()
  }

  fun addA(src: String) {
    flag.clear()

    val srcRegister = registers[src] ?: throw IllegalArgumentException("Unknown register")
    val aRegister = registers["A"] ?: throw IllegalArgumentException("Unknown register")

    val aValue = aRegister.read()
    val srcValue = srcRegister.read()
    val result = aValue + srcValue

    aRegister.write(result.toUByte())

    // Check Zero Flag
    if (result.toUByte() == 0u.toUByte()) {
      flag.setZero()
    }

    // Check Half Carry
    // Half carry is set when there's a carry from bit 3 (overflow from lower nibble)
    val lowNibbleSum = (aValue.toInt() and 0x0F) + (srcValue.toInt() and 0x0F)
    if (lowNibbleSum > 0x0F) {
      flag.setHalfCarry()
    }
  }

  fun getFlag(): FlagRegister {
    return flag
  }
}
