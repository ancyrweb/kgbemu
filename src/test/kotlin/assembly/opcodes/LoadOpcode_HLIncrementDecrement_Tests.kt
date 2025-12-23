package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.LoadOpcode
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LoadOpcode_HLIncrementDecrement_Tests {
  @Test
  fun `test LD A, (HL+)`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x2A.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is HLIncrementSource)
    Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())
  }

  @Test
  fun `test LD A, (HL-)`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x3A.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is HLDecrementSource)
    Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())
  }

  @Test
  fun `test LD (HL+), A`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x22.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is HLIncrementSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())
  }

  @Test
  fun `test LD (HL-), A`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x32.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is HLDecrementSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())
  }
}

