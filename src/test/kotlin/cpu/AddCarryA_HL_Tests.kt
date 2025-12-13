package cpu

import kotlin.test.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested

class AddCarryA_HL_Tests {
  @Nested
  inner class FunctionalTests {
    @Test
    fun `should not add anything when the carry flag is cleared`() {
      val cpu = CpuTestHelper.createCpu()
      val mmu = cpu.getMMU()

      val address = 0xC000
      mmu.writeByte(address, 100u)

      cpu.load("A", 0u)
      CpuTestHelper.loadHL(cpu, address)

      cpu.addCarryAFromHL()

      Assertions.assertEquals(100u.toUByte(), cpu.read("A"))
    }

    @Test
    fun `should add 1 when the carry flag is set`() {
      val cpu = CpuTestHelper.createCpu()
      CpuTestHelper.setCarryFlag(cpu)
      val mmu = cpu.getMMU()

      val address = 0xC000
      mmu.writeByte(address, 100u)

      cpu.load("A", 0u)
      CpuTestHelper.loadHL(cpu, address)

      cpu.addCarryAFromHL()

      Assertions.assertEquals(101u.toUByte(), cpu.read("A"))
    }
  }
}
