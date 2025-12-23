package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.PopOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PopOpcodeTests {
  companion object {
    @JvmStatic
    fun popOpcodeTestData(): Stream<Arguments> = Stream.of(
      Arguments.of("C1", "BC"),
      Arguments.of("D1", "DE"),
      Arguments.of("E1", "HL"),
      Arguments.of("F1", "AF")
    )
  }

  @ParameterizedTest(name = "POP {1} (opcode 0x{0})")
  @MethodSource("popOpcodeTestData")
  fun `test POP opcodes`(opcodeHex: String, registerPair: String) {
    val opcodeByte = opcodeHex.toInt(16).toByte()
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(opcodeByte))

    Assertions.assertEquals(1, opcodes.size)
    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is PopOpcode)

    val popOpcode = opcode as PopOpcode
    Assertions.assertEquals(1, popOpcode.toByteSize())
    Assertions.assertEquals(registerPair, popOpcode.getRegisterPair())
  }
}

