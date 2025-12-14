package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.NopOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class NopTests {
  @Test
  fun `test NOP parsing`() {
    val opcodes = OpcodeTestUtils.scan(
      bytes = byteArrayOf(0x00.toByte())
    )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is NopOpcode)
  }
}
