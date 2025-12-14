package fr.ancyrweb.gameboyemulator.assembly.opcodes.load

/**
 * Represents a load source that is an immediate value.
 * Can be a 8-bit or 16-bit immediate value depending on the context.
 */
class ImmediateValueSource (val value: Int) : LoadSource() {}