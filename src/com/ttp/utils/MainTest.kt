package com.ttp.utils

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.util.*

class MainTest {
    @Test
    fun renameBasic() {
        assertRename(expected = "res/drawable-mdpi/a.png", source = "", name = "a.png")
        assertRename("abc/res/drawable-mdpi/a.png", "abc", "a.png")
        assertRename("/abc/res/drawable-mdpi/a.png", "/abc", "a.png")
    }

    private fun assertRename(expected: String, source: String, name: String) {
        assertEquals(File(expected), rename(File(source), File(name)))
    }

    @Test
    fun rename2x() {
        assertRename("res/drawable-xhdpi/a.png", "", "a@2x.png")
        assertRename("sdfd/res/drawable-xhdpi/b.png", "sdfd", "b@2x.png")
        assertRename("cccd/res/drawable-xhdpi/dfsf.png", "cccd", "dfsf@2x.png")
    }

    @Test
    fun rename3x() {
        assertRename("res/drawable-xxxhdpi/a.png", "", "a@3x.png")
        assertRename("bbad/res/drawable-xxxhdpi/b.png", "bbad", "b@3x.png")
        assertRename("dfdfi/dfjk/res/drawable-xxxhdpi/dfsf.png", "dfdfi/dfjk", "dfsf@3x.png")
    }

    private fun rename(source: File, file: File): File {
        val replaceTable = Hashtable<String, String>()
        replaceTable.put("@2x", "xhdpi")
        replaceTable.put("@3x", "xxxhdpi")

        for (key in replaceTable.keys) {
            if (key in file.name) {
                val targetFolder = source.resolve("res/drawable-${replaceTable.get(key)}")
                val newFile = file.name.replace(key, "")

                return targetFolder.resolve(newFile)
            }
        }

        return source.resolve("res/drawable-mdpi").resolve(file)
    }

}
