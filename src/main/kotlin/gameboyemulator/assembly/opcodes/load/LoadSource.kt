package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

abstract class LoadSource {
  open fun getName(): String {
    return ""
  }
}
