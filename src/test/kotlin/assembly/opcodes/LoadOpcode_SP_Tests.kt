package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LoadOpcode_SP_Tests {
  @Test
  fun `test LD SP, nn`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x31.toByte(), 0xFE.toByte(), 0xFF.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
    Assertions.assertEquals("SP", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(0xFFFE, (loadOpcode.source as ImmediateValueSource).getValue())
    Assertions.assertEquals(ImmediateValueSource.Size.WORD, loadOpcode.source.getSize())
  }

  @Test
  fun `test LD SP, HL`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF9.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals("SP", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals("HL", (loadOpcode.source as RegisterSource).getRegisterName())
  }

  @Test
  fun `test LD (nn), SP`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x08.toByte(), 0x00.toByte(), 0xC0.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is MemoryAddressSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals("SP", (loadOpcode.source as RegisterSource).getRegisterName())

    val memDest = loadOpcode.destination as MemoryAddressSource
    Assertions.assertFalse(memDest.isRegisterPair())
    Assertions.assertEquals(0xC000, memDest.getDirectAddress())
  }

  @Test
  fun `test LDHL SP, n with positive offset`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF8.toByte(), 0x10.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(2, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is SPOffsetSource)
    Assertions.assertEquals("HL", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(16, (loadOpcode.source as SPOffsetSource).getOffset())
  }

  @Test
  fun `test LDHL SP, n with negative offset`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF8.toByte(), 0xF0.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(2, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is SPOffsetSource)
    Assertions.assertEquals("HL", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(-16, (loadOpcode.source as SPOffsetSource).getOffset())
  }
}

