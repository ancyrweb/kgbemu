package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.LoadOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LoadOpcode_RegisterToRegister_Tests {
  companion object {
    @JvmStatic
    fun registerToRegisterTestData(): Stream<Arguments> = Stream.of(
      Arguments.of("40", "B", "B"),
      Arguments.of("41", "B", "C"),
      Arguments.of("42", "B", "D"),
      Arguments.of("43", "B", "E"),
      Arguments.of("44", "B", "H"),
      Arguments.of("45", "B", "L"),
      Arguments.of("47", "B", "A"),
      Arguments.of("48", "C", "B"),
      Arguments.of("49", "C", "C"),
      Arguments.of("4A", "C", "D"),
      Arguments.of("4B", "C", "E"),
      Arguments.of("4C", "C", "H"),
      Arguments.of("4D", "C", "L"),
      Arguments.of("4F", "C", "A"),
      Arguments.of("50", "D", "B"),
      Arguments.of("51", "D", "C"),
      Arguments.of("52", "D", "D"),
      Arguments.of("53", "D", "E"),
      Arguments.of("54", "D", "H"),
      Arguments.of("55", "D", "L"),
      Arguments.of("57", "D", "A"),
      Arguments.of("58", "E", "B"),
      Arguments.of("59", "E", "C"),
      Arguments.of("5A", "E", "D"),
      Arguments.of("5B", "E", "E"),
      Arguments.of("5C", "E", "H"),
      Arguments.of("5D", "E", "L"),
      Arguments.of("5F", "E", "A"),
      Arguments.of("60", "H", "B"),
      Arguments.of("61", "H", "C"),
      Arguments.of("62", "H", "D"),
      Arguments.of("63", "H", "E"),
      Arguments.of("64", "H", "H"),
      Arguments.of("65", "H", "L"),
      Arguments.of("67", "H", "A"),
      Arguments.of("68", "L", "B"),
      Arguments.of("69", "L", "C"),
      Arguments.of("6A", "L", "D"),
      Arguments.of("6B", "L", "E"),
      Arguments.of("6C", "L", "H"),
      Arguments.of("6D", "L", "L"),
      Arguments.of("6F", "L", "A"),
      Arguments.of("78", "A", "B"),
      Arguments.of("79", "A", "C"),
      Arguments.of("7A", "A", "D"),
      Arguments.of("7B", "A", "E"),
      Arguments.of("7C", "A", "H"),
      Arguments.of("7D", "A", "L"),
      Arguments.of("7F", "A", "A")
    )
  }

  @ParameterizedTest(name = "LD {1}, {2} (opcode 0x{0})")
  @MethodSource("assembly.opcodes.LoadOpcode_RegisterToRegister_Tests#registerToRegisterTestData")
  fun `test register to register load`(opcodeHex: String, destReg: String, sourceReg: String) {
    val opcodeByte = opcodeHex.toInt(16).toByte()
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(opcodeByte))

    Assertions.assertEquals(1, opcodes.size)
    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is LoadOpcode)

    val loadOpcode = opcode as LoadOpcode
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals(destReg, (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(sourceReg, (loadOpcode.source as RegisterSource).getRegisterName())
  }
}

