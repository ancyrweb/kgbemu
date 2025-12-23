package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.PushOpcode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PushOpcodeTests {
  companion object {
    @JvmStatic
    fun pushOpcodeTestData(): Stream<Arguments> = Stream.of(
      Arguments.of("C5", "BC"),
      Arguments.of("D5", "DE"),
      Arguments.of("E5", "HL"),
      Arguments.of("F5", "AF")
    )
  }

  @ParameterizedTest(name = "PUSH {1} (opcode 0x{0})")
  @MethodSource("pushOpcodeTestData")
  fun `test PUSH opcodes`(opcodeHex: String, registerPair: String) {
    val opcodeByte = opcodeHex.toInt(16).toByte()
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(opcodeByte))

    Assertions.assertEquals(1, opcodes.size)
    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is PushOpcode)

    val pushOpcode = opcode as PushOpcode
    Assertions.assertEquals(1, pushOpcode.toByteSize())
    Assertions.assertEquals(registerPair, pushOpcode.getRegisterPair())
  }
}

