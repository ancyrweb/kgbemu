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
}
