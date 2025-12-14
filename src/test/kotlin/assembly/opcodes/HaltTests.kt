package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.HaltOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HaltTests {
  @Test
  fun `test halt opcode parsing`() {
    val opcodes =
        OpcodeTestUtils.scan(
            bytes =
                byteArrayOf(
                    0x76.toByte(),
                )
        )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is HaltOpcode)
  }
}
