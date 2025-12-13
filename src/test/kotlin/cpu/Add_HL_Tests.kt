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

}
