package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.JumpOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class JumpTests {
  @Test
  fun `test JUMP parsing`() {
    val opcodes = OpcodeTestUtils.scan(
      bytes = byteArrayOf(
        0xC3.toByte(),
        0x00.toByte(),
        0x10.toByte(), // JP 0x1000
      )
    )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is JumpOpcode)

    val jumpOpcode = opcode as JumpOpcode
    Assertions.assertEquals(0x1000, jumpOpcode.getAddress())
  }
}
