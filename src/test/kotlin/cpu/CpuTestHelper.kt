package cpu

import fr.ancyrweb.gameboyemulator.cpu.CPU
import fr.ancyrweb.gameboyemulator.ram.MMU

class CpuTestHelper {
  companion object {
    fun createCpu(): CPU {
      return CPU(MMU())
    }

    fun setSubtractFlag(cpu: CPU) {
      cpu.getFlag().setSubtract();
    }

    fun setHalfCarryFlag(cpu: CPU) {
      cpu.getFlag().setHalfCarry();
    }

    fun setCarryFlag(cpu: CPU) {
      cpu.getFlag().setCarry()
    }

    fun setZeroFlag(cpu: CPU) {
      cpu.getFlag().setZero()
    }
  }
}
