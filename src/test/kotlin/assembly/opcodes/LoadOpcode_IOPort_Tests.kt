package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LoadOpcode_IOPort_Tests {
  @Test
  fun `test LD A, (C)`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF2.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is IOPortRegisterSource)
    Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals("C", (loadOpcode.source as IOPortRegisterSource).getRegister())
  }

  @Test
  fun `test LD (C), A`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xE2.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is IOPortRegisterSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals("C", (loadOpcode.destination as IOPortRegisterSource).getRegister())
    Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())
  }

  @Test
  fun `test LDH A, (n)`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF0.toByte(), 0x44.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(2, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is IOPortImmediateSource)
    Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(0x44, (loadOpcode.source as IOPortImmediateSource).getOffset())
  }

  @Test
  fun `test LDH (n), A`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xE0.toByte(), 0x40.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(2, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is IOPortImmediateSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals(0x40, (loadOpcode.destination as IOPortImmediateSource).getOffset())
    Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())
  }
}

