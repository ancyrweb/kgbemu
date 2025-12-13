package fr.ancyrweb.gameboyemulator.ram

class MMU {
  val ram = ByteArray(0x10000)

  fun readByte(address: Int): UByte {
    if (address < 0 || address > 0xFFFF) {
      throw IllegalArgumentException("Address out of bounds: $address")
    }

    return ram[address].toUByte()
  }

  fun writeByte(address: Int, value: UByte) {
    ram[address] = value.toByte()
  }
}
