package cpu

import kotlin.test.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested

class Add_HL_Tests {
  @Nested
  inner class FunctionalTests {
    @Test
    fun `should add the value of B into A`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC050
      mmu.writeByte(address, 100u)

      cpu.load("A", 0u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertEquals(100u.toUByte(), cpu.read("A"))
    }
  }

  @Nested
  inner class ZeroFlagTests {
    @Test
    fun `set when result is zero`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC050
      mmu.writeByte(address, 0u)

      cpu.load("A", 0u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertTrue(cpu.getFlag().isZero())
    }

    @Test
    fun `cleared when result is not zero`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC050
      mmu.writeByte(address, 1u)

      cpu.load("A", 0u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isZero())
    }

    @Test
    fun `cleared when new result is not zero`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC050
      mmu.writeByte(address, 0u)

      cpu.load("A", 0u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      // At this point, the zero flag is set.
      // We want to make sure it gets cleared again.

      mmu.writeByte(address, 1u)
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isZero())
    }
  }

  @Nested
  inner class SubtractFlagTests {
    @Test
    fun `always cleared after addition`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()
      CpuTestHelper.setSubtractFlag(cpu)

      val address = 0xC050
      mmu.writeByte(address, 1u)

      cpu.load("A", 0u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isSubtract())
    }
  }

  @Nested
  inner class HalfCarryFlagTests {
    @Test
    fun `set when lower nibble overflow (over 16)`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC050
      mmu.writeByte(address, 0x01u)

      cpu.load("A", 0x0Fu)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertTrue(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `cleared when lower nibble does not overflow`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()
      CpuTestHelper.setHalfCarryFlag(cpu)

      val address = 0xC050
      mmu.writeByte(address, 0x01u)

      cpu.load("A", 0x01u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isHalfCarry())
    }

    @Test
    fun `cleared when lower nibble sum is less than 16`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()
      CpuTestHelper.setHalfCarryFlag(cpu)

      val address = 0xC050
      mmu.writeByte(address, 0x20u)

      cpu.load("A", 0x10u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isHalfCarry())
    }
  }

  @Nested
  inner class CarryFlagTests {
    @Test
    fun `cleared when result is less than or equal to 255`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC050
      mmu.writeByte(address, 0x01u)

      cpu.load("A", 0x01u)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isCarry())
    }

    @Test
    fun `cleared when result is exactly 255`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()
      CpuTestHelper.setCarryFlag(cpu)

      val address = 0xC050
      mmu.writeByte(address, 0x01u)

      cpu.load("A", 0xFEu)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertFalse(cpu.getFlag().isCarry())
    }

    @Test
    fun `set when result exceeds 255`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()
      CpuTestHelper.setCarryFlag(cpu)

      val address = 0xC050
      mmu.writeByte(address, 0x01u)

      cpu.load("A", 0xFFu)
      cpu.load("H", ((address shr 8) and 0xFF).toUByte())
      cpu.load("L", (address and 0xFF).toUByte())
      cpu.addAFromHL()

      Assertions.assertTrue(cpu.getFlag().isCarry())
    }
  }

}
