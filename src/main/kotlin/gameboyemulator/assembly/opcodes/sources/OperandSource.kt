package fr.ancyrweb.gameboyemulator.assembly.opcodes.sources

abstract class OperandSource {
  open fun getName(): String {
    return ""
  }
}