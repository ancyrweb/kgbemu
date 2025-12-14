package assembly.opcodes

object OpcodeTestUtils {
  fun scan(bytes: ByteArray): List<fr.ancyrweb.gameboyemulator.assembly.opcodes.Opcode> {
    val scanner = fr.ancyrweb.gameboyemulator.assembly.OpcodeScanner(
      bytes = bytes
    )

    scanner.scan()
    return scanner.all()
  }
}
