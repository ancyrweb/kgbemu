package cpu

import kotlin.test.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested

class AddCarryA_Scalar_Tests {
  @Nested
  inner class FunctionalTests {
    @Test
    fun `should not add anything when the carry flag is cleared`() {
      val cpu = CpuTestHelper.createCpu()
      cpu.load("A", 0u)
      cpu.addCarryA(100u)

      Assertions.assertEquals(100u.toUByte(), cpu.read("A"))
    }

    @Test
    fun `should add 1 when the carry flag is set`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setCarryFlag(cpu)

      cpu.addCarryA(1u)

      Assertions.assertEquals(2u.toUByte(), cpu.read("A"))
    }
  }

  @Nested
  inner class ZeroFlagTests {
    @Test
    fun `set when result is zero`() {
      val cpu = CpuTestHelper.createCpu()
      cpu.load("A", 0u)
      cpu.addCarryA(0u)

      Assertions.assertTrue(cpu.getFlag().isZero())
    }

    @Test
    fun `cleared when result is not zero`() {
      val cpu = CpuTestHelper.createCpu()
      cpu.load("A", 0u)
      cpu.addCarryA(1u)

      Assertions.assertFalse(cpu.getFlag().isZero())
    }

    @Test
    fun `cleared when new result is not zero`() {
      val cpu = CpuTestHelper.createCpu()
      cpu.load("A", 0u)
      cpu.addCarryA(0u)

      // At this point, the zero flag is set.
      // We want to make sure it gets cleared again.

      cpu.addCarryA(1u)

      Assertions.assertFalse(cpu.getFlag().isZero())
    }
  }

  @Nested
  inner class SubtractFlagTests {
    @Test
    fun `always cleared after addition`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setSubtractFlag(cpu)

      cpu.load("A", 0u)
      cpu.addCarryA(1u)

      Assertions.assertFalse(cpu.getFlag().isSubtract())
    }
  }

  @Nested
  inner class HalfCarryFlagTests {
    @Test
    fun `set when lower nibble overflow (over 16)`() {
      val cpu = CpuTestHelper.createCpu()
      cpu.load("A", 0x0Fu)
      cpu.addCarryA(0x01u)

      Assertions.assertTrue(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `cleared when lower nibble does not overflow`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setHalfCarryFlag(cpu)

      cpu.load("A", 0x01u)
      cpu.addCarryA(0x01u)

      Assertions.assertFalse(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `cleared when lower nibble sum is less than 16`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setHalfCarryFlag(cpu)

      cpu.load("A", 0x10u)
      cpu.addCarryA(0x20u)

      Assertions.assertFalse(cpu.getFlag().isHalfCarry())
    }
  }

  @Nested
  inner class CarryFlagTests {
    @Test
    fun `cleared when result is less than or equal to 255`() {
      val cpu = CpuTestHelper.createCpu()

      cpu.load("A", 0x01u)
      cpu.addCarryA(0x01u)

      Assertions.assertFalse(cpu.getFlag().isCarry())
    }

    @Test
    fun `cleared when result is exactly 255`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setCarryFlag(cpu)

      cpu.load("A", 0xFDu)
      cpu.addCarryA(0x01u)

      Assertions.assertFalse(cpu.getFlag().isCarry())
    }

    @Test
    fun `set when result exceeds 255`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setCarryFlag(cpu)

      cpu.load("A", 0xFFu)
      cpu.addCarryA(0x01u)

      Assertions.assertTrue(cpu.getFlag().isCarry())
    }
  }
}
