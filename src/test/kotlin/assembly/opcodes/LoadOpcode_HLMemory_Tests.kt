package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LoadOpcode_HLMemory_Tests {

  companion object {
    @JvmStatic
    fun loadFromHLTestData(): Stream<Arguments> = Stream.of(
      Arguments.of("46", "B"),
      Arguments.of("4E", "C"),
      Arguments.of("56", "D"),
      Arguments.of("5E", "E"),
      Arguments.of("66", "H"),
      Arguments.of("6E", "L"),
      Arguments.of("7E", "A")
    )

    @JvmStatic
    fun loadToHLFromRegisterTestData(): Stream<Arguments> = Stream.of(
      Arguments.of("70", "B"),
      Arguments.of("71", "C"),
      Arguments.of("72", "D"),
      Arguments.of("73", "E"),
      Arguments.of("74", "H"),
      Arguments.of("75", "L"),
      Arguments.of("77", "A")
    )
  }

  @Nested
  inner class LoadFromHLAddress {
    @ParameterizedTest(name = "LD {1}, (HL) (opcode 0x{0})")
    @MethodSource("assembly.opcodes.LoadOpcode_HLMemory_Tests#loadFromHLTestData")
    fun `test load from HL address to register`(opcodeHex: String, destReg: String) {
      val opcodeByte = opcodeHex.toInt(16).toByte()
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(opcodeByte), startAddress = 0x0100)

      Assertions.assertEquals(1, opcodes.size)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is HLAddressSource)
      Assertions.assertEquals(destReg, (loadOpcode.destination as RegisterSource).getRegisterName())
    }
  }

  @Nested
  inner class LoadToHLAddress {
    @ParameterizedTest(name = "LD (HL), {1} (opcode 0x{0})")
    @MethodSource("assembly.opcodes.LoadOpcode_HLMemory_Tests#loadToHLFromRegisterTestData")
    fun `test load to HL address from register`(opcodeHex: String, sourceReg: String) {
      val opcodeByte = opcodeHex.toInt(16).toByte()
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(opcodeByte))

      Assertions.assertEquals(1, opcodes.size)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is HLAddressSource)
      Assertions.assertTrue(loadOpcode.source is RegisterSource)
      Assertions.assertEquals(sourceReg, (loadOpcode.source as RegisterSource).getRegisterName())
    }

    @Test
    fun `test LD (HL), n`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x36.toByte(), 0xAB.toByte()))
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertEquals(2, loadOpcode.toByteSize())
      Assertions.assertTrue(loadOpcode.destination is HLAddressSource)
      Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
      Assertions.assertEquals(0xAB, (loadOpcode.source as ImmediateValueSource).getValue())
    }
  }
}

