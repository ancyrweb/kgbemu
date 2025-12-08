package cpu

import fr.ancyrweb.gameboyemulator.cpu.CPU
import kotlin.test.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested

class LoadTests {
  @Nested
  inner class NumberTests {
    @Test
    fun `load from a number`() {
      val cpu = CPU()
      cpu.load("A", 100u)

      Assertions.assertEquals(100u.toUByte(), cpu.read("A"))
    }

    @Test
    fun `fails when register is unknown`() {
      val cpu = CPU()
      Assertions.assertThrows(IllegalArgumentException::class.java) { cpu.load("not a register", 100u) }
    }
  }

  @Nested
  inner class RegisterTests {
    @Test
    fun `load from a register`() {
      val cpu = CpuTestHelper.createCpu()
      cpu.load("A", 100u)
      cpu.load("B", "A")

      Assertions.assertEquals(100u.toUByte(), cpu.read("B"))
    }

    @Test
    fun `fails when source register is unknown`() {
      val cpu = CpuTestHelper.createCpu()
      Assertions.assertThrows(IllegalArgumentException::class.java) {
        cpu.load("not a register", "B")
      }
    }

    @Test
    fun `fails when destination register is unknown`() {
      val cpu = CpuTestHelper.createCpu()
      Assertions.assertThrows(IllegalArgumentException::class.java) {
        cpu.load("B", "not a register")
      }
    }
  }
}
