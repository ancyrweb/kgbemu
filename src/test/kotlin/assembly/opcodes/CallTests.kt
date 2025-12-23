package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.CallOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CallTests {
  @Test
  fun `test CALL parsing`() {
    val opcodes = OpcodeTestUtils.scan(
      bytes = byteArrayOf(
        0xCD.toByte(),
        0x00.toByte(),
        0x10.toByte(), // CALL 0x1000
      )
    )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is CallOpcode)

    val callOpcode = opcode as CallOpcode
    Assertions.assertEquals(0x1000, callOpcode.getAddress())
  }
}
