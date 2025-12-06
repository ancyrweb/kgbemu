package cpu

import kotlin.test.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested

class AddR8Tests {
  @Test
  fun `should add the value of B into A`() {
    val cpu = CpuFactory.createCpu()
    cpu.load("A", 0u)
    cpu.load("B", 100u)
    cpu.addA("B")

    Assertions.assertEquals(100u.toUByte(), cpu.read("A"))
  }

  @Nested
  inner class ZeroFlagTests {
    @Test
    fun `set when result is zero`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0u)
      cpu.load("B", 0u)
      cpu.addA("B")

      Assertions.assertTrue(cpu.getFlag().isZero())
    }

    @Test
    fun `cleared when result is not zero`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0u)
      cpu.load("B", 1u)
      cpu.addA("B")

      Assertions.assertFalse(cpu.getFlag().isZero())
    }
  }

  @Nested
  inner class SubtractFlagTests {
    @Test
    fun `always cleared after addition`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0u)
      cpu.load("B", 1u)
      cpu.addA("B")

      Assertions.assertFalse(cpu.getFlag().isSubtract())
    }
  }

  @Nested
  inner class HalfCarryFlagTests {
    @Test
    fun `set when lower nibble overflow (over 16)`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0x0Fu)
      cpu.load("B", 0x01u)
      cpu.addA("B")

      Assertions.assertTrue(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `set when both lower nibbles cause overflow`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0x08u)
      cpu.load("B", 0x08u)
      cpu.addA("B")

      Assertions.assertTrue(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `cleared when no overflow from bit 3`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0x01u)
      cpu.load("B", 0x01u)
      cpu.addA("B")

      Assertions.assertFalse(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `cleared when lower nibble sum is less than 16`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0x10u)
      cpu.load("B", 0x20u)
      cpu.addA("B")

      Assertions.assertFalse(cpu.getFlag().isHalfCarry())
    }
  }

  @Nested
  inner class CarryFlagTests {
    @Test
    fun `cleared when result is less than or equal to 255`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0x01u)
      cpu.load("B", 0x01u)
      cpu.addA("B")

      Assertions.assertFalse(cpu.getFlag().isCarry())
    }

    @Test
    fun `cleared when result is exactly 255`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0xFEu)
      cpu.load("B", 0x01u)
      cpu.addA("B")

      Assertions.assertFalse(cpu.getFlag().isCarry())
    }

    @Test
    fun `set when result exceeds 255`() {
      val cpu = CpuFactory.createCpu()
      cpu.load("A", 0xFFu)
      cpu.load("B", 0x01u)
      cpu.addA("B")

      Assertions.assertTrue(cpu.getFlag().isCarry())
    }
  }

  @Nested
  inner class ErrorTests {
    @Test
    fun `fails when source register is unknown`() {
      val cpu = CpuFactory.createCpu()
      Assertions.assertThrows(IllegalArgumentException::class.java) {
        cpu.addA("not a register")
      }
    }
  }
}
