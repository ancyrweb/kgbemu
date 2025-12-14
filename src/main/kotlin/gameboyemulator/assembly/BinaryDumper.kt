package fr.ancyrweb.gameboyemulator.assembly

import fr.ancyrweb.gameboyemulator.output.ConsoleOutputSource
import fr.ancyrweb.gameboyemulator.output.OutputSource
import java.io.File

/**
 * Dumps the contents of a ROM file in hexadecimal format. Supports various output destinations
 * through the OutputSource abstraction.
 */
class BinaryDumper(
  private val file: File,
  private val outputSource: OutputSource = ConsoleOutputSource(),
) {
  fun dump() {
    if (!file.exists()) {
      outputSource.writeLine("Error: File not found at ${file.absolutePath}")
      return
    }

    val bytes = file.readBytes()

    if (bytes.size < 0x0150) {
      outputSource.writeLine("Error: ROM file is too small (${bytes.size} bytes). Expected at least 336 bytes.")
      return
    }

    outputSource.writeLine("=== Gameboy ROM Header ===")
    outputSource.writeLine("")

    // Entry point (0x0100 - 0x0103)
    outputSource.writeLine("Entry Point (0x0100-0x0103):")
    outputSource.writeLine("  ${formatHexBytes(bytes, 0x0100, 0x0103)}")
    outputSource.writeLine("")

    // Nintendo Logo (0x0104 - 0x0133)
    outputSource.writeLine("Nintendo Logo (0x0104-0x0133):")
    outputSource.writeLine("  ${formatHexBytes(bytes, 0x0104, 0x0133)}")
    outputSource.writeLine("")

    // Title (0x0134 - 0x0142)
    outputSource.writeLine("Title (0x0134-0x0142):")
    val title = extractString(bytes, 0x0134, 0x0142)
    outputSource.writeLine("  Hex: ${formatHexBytes(bytes, 0x0134, 0x0142)}")
    outputSource.writeLine("  ASCII: \"$title\"")
    outputSource.writeLine("")

    // CGB Flag (0x0143)
    outputSource.writeLine("CGB Flag (0x0143):")
    val cgbFlag = bytes[0x0143].toInt() and 0xFF
    outputSource.writeLine("  0x${cgbFlag.toString(16).uppercase().padStart(2, '0')} (${getCGBDescription(cgbFlag)})")
    outputSource.writeLine("")

    // License Code (0x0144 - 0x0145)
    outputSource.writeLine("License Code (0x0144-0x0145):")
    outputSource.writeLine("  ${formatHexBytes(bytes, 0x0144, 0x0145)}")
    outputSource.writeLine("")

    // SGB Flag (0x0146)
    outputSource.writeLine("SGB Flag (0x0146):")
    val sgbFlag = bytes[0x0146].toInt() and 0xFF
    outputSource.writeLine("  0x${sgbFlag.toString(16).uppercase().padStart(2, '0')} (${getSGBDescription(sgbFlag)})")
    outputSource.writeLine("")

    // Cartridge Type (0x0147)
    outputSource.writeLine("Cartridge Type (0x0147):")
    val cartridgeType = bytes[0x0147].toInt() and 0xFF
    outputSource.writeLine("  0x${cartridgeType.toString(16).uppercase().padStart(2, '0')} (${getCartridgeTypeDescription(cartridgeType)})")
    outputSource.writeLine("")

    // ROM Size (0x0148)
    outputSource.writeLine("ROM Size (0x0148):")
    val romSize = bytes[0x0148].toInt() and 0xFF
    outputSource.writeLine("  0x${romSize.toString(16).uppercase().padStart(2, '0')} (${getRomSizeDescription(romSize)})")
    outputSource.writeLine("")

    // RAM Size (0x0149)
    outputSource.writeLine("RAM Size (0x0149):")
    val ramSize = bytes[0x0149].toInt() and 0xFF
    outputSource.writeLine("  0x${ramSize.toString(16).uppercase().padStart(2, '0')} (${getRamSizeDescription(ramSize)})")
    outputSource.writeLine("")

    // Destination Code (0x014A)
    outputSource.writeLine("Destination Code (0x014A):")
    val destCode = bytes[0x014A].toInt() and 0xFF
    outputSource.writeLine("  0x${destCode.toString(16).uppercase().padStart(2, '0')} (${getDestinationDescription(destCode)})")
    outputSource.writeLine("")

    // Old License Code (0x014B)
    outputSource.writeLine("Old License Code (0x014B):")
    val oldLicense = bytes[0x014B].toInt() and 0xFF
    outputSource.writeLine("  0x${oldLicense.toString(16).uppercase().padStart(2, '0')}")
    outputSource.writeLine("")

    // ROM Version (0x014C)
    outputSource.writeLine("ROM Version (0x014C):")
    val romVersion = bytes[0x014C].toInt() and 0xFF
    outputSource.writeLine("  0x${romVersion.toString(16).uppercase().padStart(2, '0')}")
    outputSource.writeLine("")

    // Header Checksum (0x014D)
    outputSource.writeLine("Header Checksum (0x014D):")
    val headerChecksum = bytes[0x014D].toInt() and 0xFF
    outputSource.writeLine("  0x${headerChecksum.toString(16).uppercase().padStart(2, '0')}")
    outputSource.writeLine("")

    // Global Checksum (0x014E - 0x014F)
    outputSource.writeLine("Global Checksum (0x014E-0x014F):")
    outputSource.writeLine("  ${formatHexBytes(bytes, 0x014E, 0x014F)}")
    outputSource.writeLine("")
  }

  private fun formatHexBytes(bytes: ByteArray, start: Int, end: Int): String {
    return (start..end).joinToString(" ") { index ->
      (bytes[index].toInt() and 0xFF).toString(16).uppercase().padStart(2, '0')
    }
  }

  private fun extractString(bytes: ByteArray, start: Int, end: Int): String {
    return (start..end)
        .map { bytes[it] }
        .takeWhile { it.toInt() != 0 }
        .map { if (it in 32..126) it.toInt().toChar() else '.' }
        .joinToString("")
  }

  private fun getCGBDescription(value: Int): String {
    return when (value) {
      0x80 -> "CGB compatible"
      0xC0 -> "CGB only"
      else -> "No CGB support"
    }
  }

  private fun getSGBDescription(value: Int): String {
    return when (value) {
      0x03 -> "SGB functions"
      else -> "No SGB functions"
    }
  }

  private fun getCartridgeTypeDescription(value: Int): String {
    return when (value) {
      0x00 -> "ROM ONLY"
      0x01 -> "MBC1"
      0x02 -> "MBC1+RAM"
      0x03 -> "MBC1+RAM+BATTERY"
      0x05 -> "MBC2"
      0x06 -> "MBC2+BATTERY"
      0x08 -> "ROM+RAM"
      0x09 -> "ROM+RAM+BATTERY"
      0x0B -> "MMM01"
      0x0C -> "MMM01+RAM"
      0x0D -> "MMM01+RAM+BATTERY"
      0x0F -> "MBC3+TIMER+BATTERY"
      0x10 -> "MBC3+TIMER+RAM+BATTERY"
      0x11 -> "MBC3"
      0x12 -> "MBC3+RAM"
      0x13 -> "MBC3+RAM+BATTERY"
      0x19 -> "MBC5"
      0x1A -> "MBC5+RAM"
      0x1B -> "MBC5+RAM+BATTERY"
      0x1C -> "MBC5+RUMBLE"
      0x1D -> "MBC5+RUMBLE+RAM"
      0x1E -> "MBC5+RUMBLE+RAM+BATTERY"
      0x20 -> "MBC6"
      0x22 -> "MBC7+SENSOR+RUMBLE+RAM+BATTERY"
      0xFC -> "POCKET CAMERA"
      0xFD -> "BANDAI TAMA5"
      0xFE -> "HuC3"
      0xFF -> "HuC1+RAM+BATTERY"
      else -> "Unknown"
    }
  }

  private fun getRomSizeDescription(value: Int): String {
    return when (value) {
      0x00 -> "32 KiB (2 banks)"
      0x01 -> "64 KiB (4 banks)"
      0x02 -> "128 KiB (8 banks)"
      0x03 -> "256 KiB (16 banks)"
      0x04 -> "512 KiB (32 banks)"
      0x05 -> "1 MiB (64 banks)"
      0x06 -> "2 MiB (128 banks)"
      0x07 -> "4 MiB (256 banks)"
      0x08 -> "8 MiB (512 banks)"
      0x52 -> "1.1 MiB (72 banks)"
      0x53 -> "1.2 MiB (80 banks)"
      0x54 -> "1.5 MiB (96 banks)"
      else -> "Unknown"
    }
  }

  private fun getRamSizeDescription(value: Int): String {
    return when (value) {
      0x00 -> "None"
      0x01 -> "Unused"
      0x02 -> "8 KiB (1 bank)"
      0x03 -> "32 KiB (4 banks)"
      0x04 -> "128 KiB (16 banks)"
      0x05 -> "64 KiB (8 banks)"
      else -> "Unknown"
    }
  }

  private fun getDestinationDescription(value: Int): String {
    return when (value) {
      0x00 -> "Japanese"
      0x01 -> "Non-Japanese"
      else -> "Unknown"
    }
  }
}