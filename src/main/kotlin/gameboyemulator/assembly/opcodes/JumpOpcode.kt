package fr.ancyrweb.gameboyemulator.assembly.opcodes

import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.HLAddressSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.HLDecrementSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.HLIncrementSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.IOPortImmediateSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.IOPortRegisterSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.MemoryAddressSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.RegisterSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.load.SPOffsetSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.ImmediateValueSource
import fr.ancyrweb.gameboyemulator.assembly.opcodes.sources.OperandSource

class JumpOpcode(opCodeAddress: Int, private val address: Int) :
    Opcode("JUMP", opCodeAddress, bytesSize = 3, highClockCycle = 16, lowClockCycle = 12) {

  companion object {
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): JumpOpcode {
      val low = bytes[index + 1].toInt() and 0xFF
      val high = bytes[index + 2].toInt() and 0xFF
      val jumpAddress = (high shl 8) or low
      return JumpOpcode(address, jumpAddress)
    }
  }

  override fun disassemblySuffix(): String {
    return "0x${address.toString(16).uppercase().padStart(4, '0')}"
  }

  fun getAddress(): Int {
    return address
  }
}

class LoadOpcode(
  opCodeAddress: Int,
  val destination: OperandSource,
  val source: OperandSource,
  bytesSize: Int = 1,
  highClockCycle: Int = 4,
  lowClockCycle: Int = 4
) : Opcode("LD", opCodeAddress, bytesSize = bytesSize, highClockCycle = highClockCycle, lowClockCycle = lowClockCycle) {

  override fun disassemblySuffix(): String {
    return "${destination.getName()}, ${source.getName()}"
  }

  companion object {
    /**
     * Factory method to create load opcodes based on the opcode byte value.
     * Returns null if the opcode is not a load instruction.
     */
    fun fromBytes(bytes: ByteArray, index: Int, address: Int): LoadOpcode? {
      val opcode = bytes[index].toInt() and 0xFF

      return when (opcode) {
        // LD r, r' - Load register to register (8-bit)
        0x40 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("B"))
        0x41 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("C"))
        0x42 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("D"))
        0x43 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("E"))
        0x44 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("H"))
        0x45 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("L"))
        0x47 -> LoadOpcode(address, RegisterSource("B"), RegisterSource("A"))

        0x48 -> LoadOpcode(address, RegisterSource("C"), RegisterSource("B"))
        0x49 -> LoadOpcode(address, RegisterSource("C"), RegisterSource("C"))
        0x4A -> LoadOpcode(address, RegisterSource("C"), RegisterSource("D"))
        0x4B -> LoadOpcode(address, RegisterSource("C"), RegisterSource("E"))
        0x4C -> LoadOpcode(address, RegisterSource("C"), RegisterSource("H"))
        0x4D -> LoadOpcode(address, RegisterSource("C"), RegisterSource("L"))
        0x4F -> LoadOpcode(address, RegisterSource("C"), RegisterSource("A"))

        0x50 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("B"))
        0x51 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("C"))
        0x52 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("D"))
        0x53 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("E"))
        0x54 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("H"))
        0x55 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("L"))
        0x57 -> LoadOpcode(address, RegisterSource("D"), RegisterSource("A"))

        0x58 -> LoadOpcode(address, RegisterSource("E"), RegisterSource("B"))
        0x59 -> LoadOpcode(address, RegisterSource("E"), RegisterSource("C"))
        0x5A -> LoadOpcode(address, RegisterSource("E"), RegisterSource("D"))
        0x5B -> LoadOpcode(address, RegisterSource("E"), RegisterSource("E"))
        0x5C -> LoadOpcode(address, RegisterSource("E"), RegisterSource("H"))
        0x5D -> LoadOpcode(address, RegisterSource("E"), RegisterSource("L"))
        0x5F -> LoadOpcode(address, RegisterSource("E"), RegisterSource("A"))

        0x60 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("B"))
        0x61 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("C"))
        0x62 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("D"))
        0x63 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("E"))
        0x64 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("H"))
        0x65 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("L"))
        0x67 -> LoadOpcode(address, RegisterSource("H"), RegisterSource("A"))

        0x68 -> LoadOpcode(address, RegisterSource("L"), RegisterSource("B"))
        0x69 -> LoadOpcode(address, RegisterSource("L"), RegisterSource("C"))
        0x6A -> LoadOpcode(address, RegisterSource("L"), RegisterSource("D"))
        0x6B -> LoadOpcode(address, RegisterSource("L"), RegisterSource("E"))
        0x6C -> LoadOpcode(address, RegisterSource("L"), RegisterSource("H"))
        0x6D -> LoadOpcode(address, RegisterSource("L"), RegisterSource("L"))
        0x6F -> LoadOpcode(address, RegisterSource("L"), RegisterSource("A"))

        0x78 -> LoadOpcode(address, RegisterSource("A"), RegisterSource("B"))
        0x79 -> LoadOpcode(address, RegisterSource("A"), RegisterSource("C"))
        0x7A -> LoadOpcode(address, RegisterSource("A"), RegisterSource("D"))
        0x7B -> LoadOpcode(address, RegisterSource("A"), RegisterSource("E"))
        0x7C -> LoadOpcode(address, RegisterSource("A"), RegisterSource("H"))
        0x7D -> LoadOpcode(address, RegisterSource("A"), RegisterSource("L"))
        0x7F -> LoadOpcode(address, RegisterSource("A"), RegisterSource("A"))

        // LD r, n - Load immediate 8-bit value to register
        0x06 -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("B"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }
        0x0E -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("C"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }
        0x16 -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("D"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }
        0x1E -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("E"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }
        0x26 -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("H"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }
        0x2E -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("L"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }
        0x3E -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("A"),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 8, lowClockCycle = 8)
        }

        // LD r, (HL) - Load from memory address pointed by HL to register
        0x46 -> LoadOpcode(address, RegisterSource("B"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)
        0x4E -> LoadOpcode(address, RegisterSource("C"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)
        0x56 -> LoadOpcode(address, RegisterSource("D"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)
        0x5E -> LoadOpcode(address, RegisterSource("E"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)
        0x66 -> LoadOpcode(address, RegisterSource("H"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)
        0x6E -> LoadOpcode(address, RegisterSource("L"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)
        0x7E -> LoadOpcode(address, RegisterSource("A"), HLAddressSource(), highClockCycle = 8, lowClockCycle = 8)

        // LD (HL), r - Load from register to memory address pointed by HL
        0x70 -> LoadOpcode(address, HLAddressSource(), RegisterSource("B"), highClockCycle = 8, lowClockCycle = 8)
        0x71 -> LoadOpcode(address, HLAddressSource(), RegisterSource("C"), highClockCycle = 8, lowClockCycle = 8)
        0x72 -> LoadOpcode(address, HLAddressSource(), RegisterSource("D"), highClockCycle = 8, lowClockCycle = 8)
        0x73 -> LoadOpcode(address, HLAddressSource(), RegisterSource("E"), highClockCycle = 8, lowClockCycle = 8)
        0x74 -> LoadOpcode(address, HLAddressSource(), RegisterSource("H"), highClockCycle = 8, lowClockCycle = 8)
        0x75 -> LoadOpcode(address, HLAddressSource(), RegisterSource("L"), highClockCycle = 8, lowClockCycle = 8)
        0x77 -> LoadOpcode(address, HLAddressSource(), RegisterSource("A"), highClockCycle = 8, lowClockCycle = 8)

        // LD (HL), n - Load immediate 8-bit value to memory address pointed by HL
        0x36 -> {
          val value = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            HLAddressSource(),
            ImmediateValueSource(value, ImmediateValueSource.Size.BYTE), bytesSize = 2, highClockCycle = 12, lowClockCycle = 12)
        }

        // LD A, (BC) - Load from memory address pointed by BC to A
        0x0A -> LoadOpcode(address, RegisterSource("A"), MemoryAddressSource("BC"), highClockCycle = 8, lowClockCycle = 8)

        // LD A, (DE) - Load from memory address pointed by DE to A
        0x1A -> LoadOpcode(address, RegisterSource("A"), MemoryAddressSource("DE"), highClockCycle = 8, lowClockCycle = 8)

        // LD (BC), A - Load from A to memory address pointed by BC
        0x02 -> LoadOpcode(address, MemoryAddressSource("BC"), RegisterSource("A"), highClockCycle = 8, lowClockCycle = 8)

        // LD (DE), A - Load from A to memory address pointed by DE
        0x12 -> LoadOpcode(address, MemoryAddressSource("DE"), RegisterSource("A"), highClockCycle = 8, lowClockCycle = 8)

        // LD A, (nn) - Load from memory address nn to A
        0xFA -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val addr = (high shl 8) or low
          LoadOpcode(address,
            RegisterSource("A"),
            MemoryAddressSource(addr), bytesSize = 3, highClockCycle = 16, lowClockCycle = 16)
        }

        // LD (nn), A - Load from A to memory address nn
        0xEA -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val addr = (high shl 8) or low
          LoadOpcode(address,
            MemoryAddressSource(addr),
            RegisterSource("A"), bytesSize = 3, highClockCycle = 16, lowClockCycle = 16)
        }

        // LD A, (C) - Load from memory address 0xFF00+C to A
        0xF2 -> LoadOpcode(address, RegisterSource("A"), IOPortRegisterSource("C"), highClockCycle = 8, lowClockCycle = 8)

        // LD (C), A - Load from A to memory address 0xFF00+C
        0xE2 -> LoadOpcode(address, IOPortRegisterSource("C"), RegisterSource("A"), highClockCycle = 8, lowClockCycle = 8)

        // LDH A, (n) - Load from memory address 0xFF00+n to A
        0xF0 -> {
          val offset = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            RegisterSource("A"),
            IOPortImmediateSource(offset), bytesSize = 2, highClockCycle = 12, lowClockCycle = 12)
        }

        // LDH (n), A - Load from A to memory address 0xFF00+n
        0xE0 -> {
          val offset = bytes[index + 1].toInt() and 0xFF
          LoadOpcode(address,
            IOPortImmediateSource(offset),
            RegisterSource("A"), bytesSize = 2, highClockCycle = 12, lowClockCycle = 12)
        }

        // LD A, (HLI) / LD A, (HL+) - Load from (HL) to A and increment HL
        0x2A -> LoadOpcode(address, RegisterSource("A"), HLIncrementSource(), highClockCycle = 8, lowClockCycle = 8)

        // LD A, (HLD) / LD A, (HL-) - Load from (HL) to A and decrement HL
        0x3A -> LoadOpcode(address, RegisterSource("A"), HLDecrementSource(), highClockCycle = 8, lowClockCycle = 8)

        // LD (HLI), A / LD (HL+), A - Load from A to (HL) and increment HL
        0x22 -> LoadOpcode(address, HLIncrementSource(), RegisterSource("A"), highClockCycle = 8, lowClockCycle = 8)

        // LD (HLD), A / LD (HL-), A - Load from A to (HL) and decrement HL
        0x32 -> LoadOpcode(address, HLDecrementSource(), RegisterSource("A"), highClockCycle = 8, lowClockCycle = 8)

        // 16-bit loads
        // LD rr, nn - Load immediate 16-bit value to register pair
        0x01 -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val value = (high shl 8) or low
          LoadOpcode(address,
            RegisterSource("BC"),
            ImmediateValueSource(value, ImmediateValueSource.Size.WORD), bytesSize = 3, highClockCycle = 12, lowClockCycle = 12)
        }
        0x11 -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val value = (high shl 8) or low
          LoadOpcode(address,
            RegisterSource("DE"),
            ImmediateValueSource(value, ImmediateValueSource.Size.WORD), bytesSize = 3, highClockCycle = 12, lowClockCycle = 12)
        }
        0x21 -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val value = (high shl 8) or low
          LoadOpcode(address,
            RegisterSource("HL"),
            ImmediateValueSource(value, ImmediateValueSource.Size.WORD), bytesSize = 3, highClockCycle = 12, lowClockCycle = 12)
        }
        0x31 -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val value = (high shl 8) or low
          LoadOpcode(address,
            RegisterSource("SP"),
            ImmediateValueSource(value, ImmediateValueSource.Size.WORD), bytesSize = 3, highClockCycle = 12, lowClockCycle = 12)
        }

        // LD SP, HL - Load HL into SP
        0xF9 -> LoadOpcode(address, RegisterSource("SP"), RegisterSource("HL"), highClockCycle = 8, lowClockCycle = 8)

        // LD (nn), SP - Save SP to memory address nn
        0x08 -> {
          val low = bytes[index + 1].toInt() and 0xFF
          val high = bytes[index + 2].toInt() and 0xFF
          val addr = (high shl 8) or low
          LoadOpcode(address,
            MemoryAddressSource(addr),
            RegisterSource("SP"), bytesSize = 3, highClockCycle = 20, lowClockCycle = 20)
        }

        // LDHL SP, n - Load SP + signed byte n into HL
        0xF8 -> {
          val offset = bytes[index + 1].toInt() // Signed byte
          LoadOpcode(address,
            RegisterSource("HL"),
            SPOffsetSource(offset), bytesSize = 2, highClockCycle = 12, lowClockCycle = 12)
        }

        else -> null
      }
    }
  }
}