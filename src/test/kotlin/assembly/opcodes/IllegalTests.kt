package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.IllegalOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class IllegalTests {
  @ParameterizedTest(name = "opcode {0} should be illegal")
  @ValueSource(strings = ["0xD3", "0xDB", "0xDD", "0xE3", "0xE4", "0xEB", "0xEC", "0xED", "0xF4", "0xFC", "0xFD"])
  fun `test illegal opcodes parsing`(opcodeHex: String) {
    val opcodeValue = opcodeHex.removePrefix("0x").toInt(16)
    val opcodes =
        OpcodeTestUtils.scan(
            bytes =
                byteArrayOf(
                    opcodeValue.toByte(),
                )
        )

    Assertions.assertEquals(1, opcodes.size)

    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is IllegalOpcode, "Opcode $opcodeHex should be an IllegalOpcode")
  }
}
