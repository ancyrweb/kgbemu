package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LoadOpcode_ImmediateToRegister_Tests {
  companion object {
    @JvmStatic
    fun immediateToRegisterTestData(): Stream<Arguments> = Stream.of(
      Arguments.of("06", "B", 0xFF),
      Arguments.of("0E", "C", 0x42),
      Arguments.of("16", "D", 0x12),
      Arguments.of("1E", "E", 0x34),
      Arguments.of("26", "H", 0xAB),
      Arguments.of("2E", "L", 0x10),
      Arguments.of("3E", "A", 0x42)
    )
  }

  @ParameterizedTest(name = "LD {1}, #${2} (opcode 0x{0})")
  @MethodSource("immediateToRegisterTestData")
  fun `test immediate to register load`(opcodeHex: String, destReg: String, immediateValue: Int) {
    val opcodeByte = opcodeHex.toInt(16).toByte()
    val valueByte = immediateValue.toByte()
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(opcodeByte, valueByte), startAddress = 0x0100)

    Assertions.assertEquals(1, opcodes.size)
    val opcode = opcodes[0]
    Assertions.assertTrue(opcode is LoadOpcode)

    val loadOpcode = opcode as LoadOpcode
    Assertions.assertEquals(2, loadOpcode.toByteSize())

    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
    Assertions.assertEquals(destReg, (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(immediateValue, (loadOpcode.source as ImmediateValueSource).getValue())
    Assertions.assertEquals(ImmediateValueSource.Size.BYTE, loadOpcode.source.getSize())
  }
}

