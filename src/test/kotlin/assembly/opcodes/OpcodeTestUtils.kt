package assembly.opcodes

object OpcodeTestUtils {
  fun scan(bytes: ByteArray, startAddress: Int = 0): List<fr.ancyrweb.gameboyemulator.assembly.opcodes.Opcode> {
    val scanner = fr.ancyrweb.gameboyemulator.assembly.OpcodeScanner(
      bytes = bytes,
      startAddress = startAddress
    )

    scanner.scan()
    return scanner.all()
  }
}
