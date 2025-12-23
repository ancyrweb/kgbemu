package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LoadOpcode_DirectMemory_Tests {
  @Test
  fun `test LD A, (nn)`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xFA.toByte(), 0x34.toByte(), 0x12.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is RegisterSource)
    Assertions.assertTrue(loadOpcode.source is MemoryAddressSource)
    Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())

    val memSource = loadOpcode.source as MemoryAddressSource
    Assertions.assertFalse(memSource.isRegisterPair())
    Assertions.assertEquals(0x1234, memSource.getDirectAddress())
  }

  @Test
  fun `test LD (nn), A`() {
    val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xEA.toByte(), 0x00.toByte(), 0xC0.toByte()), startAddress = 0x0100)
    val loadOpcode = opcodes[0] as LoadOpcode

    Assertions.assertEquals(3, loadOpcode.toByteSize())
    Assertions.assertTrue(loadOpcode.destination is MemoryAddressSource)
    Assertions.assertTrue(loadOpcode.source is RegisterSource)
    Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())

    val memDest = loadOpcode.destination as MemoryAddressSource
    Assertions.assertFalse(memDest.isRegisterPair())
    Assertions.assertEquals(0xC000, memDest.getDirectAddress())
  }
}

