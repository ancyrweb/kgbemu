package assembly.opcodes.sources

import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.SignedByte
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested

class SignedByteTests {

  @Nested
  inner class PositiveValues {
    @Test
    fun `test positive value conversion`() {
      val signedByte = SignedByte(16)

      Assertions.assertEquals(16, signedByte.toInt())
      Assertions.assertEquals(16, signedByte.toUnsignedInt())
      Assertions.assertFalse(signedByte.isNegative())
      Assertions.assertEquals(16, signedByte.abs())
    }

    @Test
    fun `test zero value`() {
      val signedByte = SignedByte(0)

      Assertions.assertEquals(0, signedByte.toInt())
      Assertions.assertEquals(0, signedByte.toUnsignedInt())
      Assertions.assertFalse(signedByte.isNegative())
      Assertions.assertEquals(0, signedByte.abs())
    }

    @Test
    fun `test maximum positive value`() {
      val signedByte = SignedByte(127)

      Assertions.assertEquals(127, signedByte.toInt())
      Assertions.assertEquals(127, signedByte.toUnsignedInt())
      Assertions.assertFalse(signedByte.isNegative())
      Assertions.assertEquals(127, signedByte.abs())
    }
  }

  @Nested
  inner class NegativeValues {
    @Test
    fun `test negative value conversion`() {
      val signedByte = SignedByte(0xF0) // -16 in two's complement

      Assertions.assertEquals(-16, signedByte.toInt())
      Assertions.assertEquals(0xF0, signedByte.toUnsignedInt())
      Assertions.assertTrue(signedByte.isNegative())
      Assertions.assertEquals(16, signedByte.abs())
    }

    @Test
    fun `test small negative value`() {
      val signedByte = SignedByte(0xFF) // -1 in two's complement

      Assertions.assertEquals(-1, signedByte.toInt())
      Assertions.assertEquals(0xFF, signedByte.toUnsignedInt())
      Assertions.assertTrue(signedByte.isNegative())
      Assertions.assertEquals(1, signedByte.abs())
    }

    @Test
    fun `test minimum negative value`() {
      val signedByte = SignedByte(0x80) // -128 in two's complement

      Assertions.assertEquals(-128, signedByte.toInt())
      Assertions.assertEquals(0x80, signedByte.toUnsignedInt())
      Assertions.assertTrue(signedByte.isNegative())
      Assertions.assertEquals(128, signedByte.abs())
    }
  }

  @Nested
  inner class EqualityTests {
    @Test
    fun `test equality of same values`() {
      val byte1 = SignedByte(16)
      val byte2 = SignedByte(16)

      Assertions.assertEquals(byte1, byte2)
      Assertions.assertEquals(byte1.hashCode(), byte2.hashCode())
    }

    @Test
    fun `test inequality of different values`() {
      val byte1 = SignedByte(16)
      val byte2 = SignedByte(-16)

      Assertions.assertNotEquals(byte1, byte2)
    }

    @Test
    fun `test same instance equality`() {
      val signedByte = SignedByte(42)

      Assertions.assertEquals(signedByte, signedByte)
    }
  }

  @Nested
  inner class ToStringTests {
    @Test
    fun `test toString for positive value`() {
      val signedByte = SignedByte(42)
      Assertions.assertEquals("42", signedByte.toString())
    }

    @Test
    fun `test toString for negative value`() {
      val signedByte = SignedByte(0xF0) // -16
      Assertions.assertEquals("-16", signedByte.toString())
    }
  }
}

