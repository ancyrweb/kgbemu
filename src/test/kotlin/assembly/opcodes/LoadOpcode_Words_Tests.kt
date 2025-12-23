package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LoadOpcode_Words_Tests {
  @Test
  fun `test LD BC, nn`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x01.toByte(), 0x34.toByte(), 0x12.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
    Assertions.assertEquals("BC", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(0x1234, (loadOpcode.source as ImmediateValueSource).getValue())
    Assertions.assertEquals(ImmediateValueSource.Size.WORD, loadOpcode.source.getSize())
  }

  @Test
  fun `test LD DE, nn`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x11.toByte(), 0xEF.toByte(), 0xBE.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
    Assertions.assertEquals("DE", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(0xBEEF, (loadOpcode.source as ImmediateValueSource).getValue())
    Assertions.assertEquals(ImmediateValueSource.Size.WORD, loadOpcode.source.getSize())
  }

  @Test
  fun `test LD HL, nn`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x21.toByte(), 0x00.toByte(), 0x80.toByte()))
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
    Assertions.assertEquals("HL", (loadOpcode.destination as RegisterSource).getRegisterName())
    Assertions.assertEquals(0x8000, (loadOpcode.source as ImmediateValueSource).getValue())
    Assertions.assertEquals(ImmediateValueSource.Size.WORD, loadOpcode.source.getSize())
  }
}

