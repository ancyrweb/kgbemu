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
    val srcRegister = registers[src] ?: throw IllegalArgumentException("Unknown register")
    addA(srcRegister.read())
  }

  fun addA(value: UByte) {
    flag.clear()

    val destRegister = registers["A"] ?: throw IllegalArgumentException("Unknown register")

    val destValue = destRegister.read()
    val result = destValue + value

    destRegister.write(result.toUByte())

    checkFlags(
      value,
      destValue,
      result
    )
  }

  fun addCarryA(src: String) {
    val srcRegister = registers[src] ?: throw IllegalArgumentException("Unknown register")
    addCarryA(srcRegister.read())
  }

  fun addCarryA(value: UByte) {
    val destRegister = registers["A"] ?: throw IllegalArgumentException("Unknown register")

    val destValue = destRegister.read()
    val result = destValue + value + if (flag.isCarry()) 1u else 0u

    destRegister.write(result.toUByte())

    checkFlags(
      value,
      destValue,
      result
    )
  }


  fun getFlag(): FlagRegister {
    return flag
  }

  private fun checkFlags(
    srcValue: UByte,
    destValue: UByte,
    result: UInt
  ) {
    // Check Zero Flag
    if (result.toUByte() == 0u.toUByte()) {
      flag.setZero()
    } else {
      flag.clearZero()
    }

    flag.clearSubtract()

    // Check Half Carry
    // Half carry is set when there's a carry from bit 3 (overflow from lower nibble)
    val lowNibbleSum = (destValue.toInt() and 0x0F) + (srcValue.toInt() and 0x0F)
    if (lowNibbleSum > 0x0F) {
      flag.setHalfCarry()
    } else {
      flag.clearHalfCarry()
    }

    // Check Carry Flag
    // Carry is set when result exceeds 255 (8-bit overflow)
    if (result > 0xFFu) {
      flag.setCarry()
    } else {
      flag.clearCarry()
    }
  }
}
