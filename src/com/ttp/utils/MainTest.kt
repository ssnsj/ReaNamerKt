package com.ttp.utils

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.util.*

class MainTest {
    @Test
    fun renameBasic() {
        assertEquals(File("res/drawable-mdpi/a.png"), rename(File(""), File("a.png")))
        assertEquals(File("abc/res/drawable-mdpi/a.png"), rename(File("abc"), File("a.png")))
        assertEquals(File("/abc/res/drawable-mdpi/a.png"), rename(File("/abc"), File("a.png")))
    }

    @Test
    fun rename2x() {
        assertEquals(File("res/drawable-xhdpi/a.png"), rename(File(""), File("a@2x.png")))
        assertEquals(File("sdfd/res/drawable-xhdpi/b.png"), rename(File("sdfd"), File("b@2x.png")))
        assertEquals(File("cccd/res/drawable-xhdpi/dfsf.png"), rename(File("cccd"), File("dfsf@2x.png")))
    }

    @Test
    fun rename3x() {
        assertEquals(File("res/drawable-xxxhdpi/a.png"), rename(File(""), File("a@3x.png")))
        assertEquals(File("bbad/res/drawable-xxxhdpi/b.png"), rename(File("bbad"), File("b@3x.png")))
        assertEquals(File("dfdfi/dfjk/res/drawable-xxxhdpi/dfsf.png"), rename(File("dfdfi/dfjk"), File("dfsf@3x.png")))
    }

    private fun rename(source: File, file: File): File {
        var convertTable = Hashtable<String, String>()
        convertTable.put("@2x", "xhdpi")
        convertTable.put("@3x", "xxxhdpi")

        for (item in convertTable.keys) {
            if (item in file.name) {
                val targetFolder = source.resolve("res/drawable-${convertTable.get(item)}")
                val newFile = file.name.replace(item, "")

                return targetFolder.resolve(newFile)
            }
        }

        return source.resolve("res/drawable-mdpi/").resolve(file)
    }

}
