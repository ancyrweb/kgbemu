package fr.ancyrweb.gameboyemulator.cpu

class ByteRegister {
  private var value: UByte = 0u

  fun write(newValue: UByte) {
    value = newValue
  }

  fun read(): UByte {
    return value
  }
}
