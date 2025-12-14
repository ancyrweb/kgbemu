package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.IllegalOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IllegalTests {
  @Test
  fun `test illegal opcodes parsing`() {
    val opcodes =
        OpcodeTestUtils.scan(
            bytes =
                byteArrayOf(
                    0xD3.toByte(),
                )
        )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is IllegalOpcode)
  }
}
