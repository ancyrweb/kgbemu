package cpu

import fr.ancyrweb.gameboyemulator.cpu.CPU

class CpuFactory {
  companion object {
    fun createCpu(): CPU {
      return CPU()
    }
  }
}
