package tooling

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

object HexUtils {
  fun sizeOfRange(start: Int, end: Int): Int {
    return (end - start + 1)
  }
}

class HexUtilsTests {
  @Test
  fun `empty range`() {
    Assertions.assertEquals(HexUtils.sizeOfRange(0xFFFF, 0xFFFF), 1)
  }

  @Test
  fun `one digit bit range`() {
    Assertions.assertEquals(HexUtils.sizeOfRange(0xFFF0, 0xFFFF), 16)
  }

  @Test
  fun `sandbox`() {
    Assertions.assertEquals(HexUtils.sizeOfRange(0x8000, 0x97FF), 6144)
  }
}
