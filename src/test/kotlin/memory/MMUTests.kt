package memory

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MMUTests {
  @Test
  fun testReadByte() {
    val mmu = MMUTestHelper.createMMU()
    val toWrite: UByte = 0x42u

    mmu.writeByte(0xC000, toWrite)

    val value = mmu.readByte(0xC000)
    Assertions.assertEquals(toWrite, value)
  }
}
