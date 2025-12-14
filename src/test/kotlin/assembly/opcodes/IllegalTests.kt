package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.IllegalOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IllegalTests {
  @ParameterizedTest(name = "opcode 0x{0} should be illegal")
  @ValueSource(ints = [0xD3, 0xDB, 0xDD, 0xE3, 0xE4, 0xEB, 0xEC, 0xED, 0xF4, 0xFC, 0xFD])
  fun `test illegal opcodes parsing`(opcodeValue: Int) {
    val opcodes =
        OpcodeTestUtils.scan(
            bytes =
                byteArrayOf(
                    opcodeValue.toByte(),
                )
        )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is IllegalOpcode, "Opcode 0x${opcodeValue.toString(16).uppercase()} should be an IllegalOpcode")
  }
}
