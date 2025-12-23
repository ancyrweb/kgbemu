package assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class LoadOpcodeTests {


  @Nested
  inner class RegisterPairMemoryTests {
    @Test
    fun `test LD A, (BC)`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x0A.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is MemoryAddressSource)
      Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())

      val memSource = loadOpcode.source as MemoryAddressSource
      Assertions.assertTrue(memSource.isRegisterPair())
      Assertions.assertEquals("BC", memSource.getRegisterPair())
    }

    @Test
    fun `test LD A, (DE)`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x1A.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is MemoryAddressSource)
      Assertions.assertEquals("A", (loadOpcode.destination as RegisterSource).getRegisterName())

      val memSource = loadOpcode.source as MemoryAddressSource
      Assertions.assertTrue(memSource.isRegisterPair())
      Assertions.assertEquals("DE", memSource.getRegisterPair())
    }

    @Test
    fun `test LD (BC), A`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x02.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is MemoryAddressSource)
      Assertions.assertTrue(loadOpcode.source is RegisterSource)
      Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())

      val memDest = loadOpcode.destination as MemoryAddressSource
      Assertions.assertTrue(memDest.isRegisterPair())
      Assertions.assertEquals("BC", memDest.getRegisterPair())
    }

    @Test
    fun `test LD (DE), A`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x12.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is MemoryAddressSource)
      Assertions.assertTrue(loadOpcode.source is RegisterSource)
      Assertions.assertEquals("A", (loadOpcode.source as RegisterSource).getRegisterName())

      val memDest = loadOpcode.destination as MemoryAddressSource
      Assertions.assertTrue(memDest.isRegisterPair())
      Assertions.assertEquals("DE", memDest.getRegisterPair())
    }
  }

  @Nested
  inner class DirectMemoryTests {
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

  @Nested
  inner class IOPortTests {
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

  @Nested
  inner class HLIncrementDecrementTests {
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

  @Nested
  inner class SixteenBitLoadTests {
    @Test
    fun `test LD BC, nn`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x01.toByte(), 0x34.toByte(), 0x12.toByte()), startAddress = 0x0100)
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
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x11.toByte(), 0xEF.toByte(), 0xBE.toByte()), startAddress = 0x0100)
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
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x21.toByte(), 0x00.toByte(), 0x80.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertEquals(3, loadOpcode.toByteSize())
      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is ImmediateValueSource)
      Assertions.assertEquals("HL", (loadOpcode.destination as RegisterSource).getRegisterName())
      Assertions.assertEquals(0x8000, (loadOpcode.source as ImmediateValueSource).getValue())
      Assertions.assertEquals(ImmediateValueSource.Size.WORD, loadOpcode.source.getSize())
    }

    @Test
    fun `test LD SP, nn`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x31.toByte(), 0xFE.toByte(), 0xFF.toByte()), startAddress = 0x0100)
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
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF9.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is RegisterSource)
      Assertions.assertEquals("SP", (loadOpcode.destination as RegisterSource).getRegisterName())
      Assertions.assertEquals("HL", (loadOpcode.source as RegisterSource).getRegisterName())
    }

    @Test
    fun `test LD (nn), SP`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0x08.toByte(), 0x00.toByte(), 0xC0.toByte()), startAddress = 0x0100)
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
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF8.toByte(), 0x10.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertEquals(2, loadOpcode.toByteSize())
      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is SPOffsetSource)
      Assertions.assertEquals("HL", (loadOpcode.destination as RegisterSource).getRegisterName())
      Assertions.assertEquals(16, (loadOpcode.source as SPOffsetSource).getOffset())
    }

    @Test
    fun `test LDHL SP, n with negative offset`() {
      val opcodes = OpcodeTestUtils.scan(byteArrayOf(0xF8.toByte(), 0xF0.toByte()), startAddress = 0x0100)
      val loadOpcode = opcodes[0] as LoadOpcode

      Assertions.assertEquals(2, loadOpcode.toByteSize())
      Assertions.assertTrue(loadOpcode.destination is RegisterSource)
      Assertions.assertTrue(loadOpcode.source is SPOffsetSource)
      Assertions.assertEquals("HL", (loadOpcode.destination as RegisterSource).getRegisterName())
      Assertions.assertEquals(-16, (loadOpcode.source as SPOffsetSource).getOffset())
    }
  }
}

