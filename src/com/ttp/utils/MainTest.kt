package com.ttp.utils

import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.util.*

class MainTest {
    data class Paths(val source: File, val target: File)

    @Test
    fun empty_target_should_be_same_with_source() {
        val result = getPaths(File("aaa"), File(""))
        assertEquals(result.source, result.target)
    }

    @Test
    fun target과source가_다른_값으로_들어오면_각각을_사용한다() {
        val source = File("aaa");
        val target = File("bbb");

        val result = getPaths(source, target)

        assertEquals(source, result.source)
        assertEquals(target, result.target)
    }

    @Test
    fun source가_없으면_현재폴더를_source로_사용한다() {
        val source = File("");
        val target = File("");

        val result = getPaths(source, target)

        assertEquals(getCurrentPath(), result.source)
        assertEquals(result.source, result.target)
    }

    private fun getCurrentPath() = File("./").canonicalFile

    private fun getPaths(source: File, target: File): Paths {
        return when {
            source.name.isEmpty() -> Paths(getCurrentPath(), getCurrentPath())
            target.name.isEmpty() -> Paths(source, source)
            !source.equals(target) -> Paths(source, target)
            else -> throw RuntimeException("None of above")
        }
    }


    @Test
    fun renameBasic() {
        assertRename(expected = "res/drawable-mdpi/a.png", target = "", name = "a.png")
        assertRename("abc/res/drawable-mdpi/a.png", "abc", "a.png")
        assertRename("/abc/res/drawable-mdpi/a.png", "/abc", "a.png")
    }

    private fun assertRename(expected: String, target: String, name: String) {
        assertEquals(File(expected), rename(File(target), File(name)))
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

    @Test
    fun renameForAndroidResourceNameCapital() {
        assertRename("res/drawable-xxxhdpi/a.png", "", "A@3x.png")
    }

    @Test
    fun renameForAndroidResourceNameHyphen() {
        assertRename("res/drawable-xxxhdpi/a_.png", "", "a-@3x.png")
    }

    private fun rename(target: File, file: File): File {
        val replaceTable = Hashtable<String, String>()
        replaceTable.put("@2x", "xhdpi")
        replaceTable.put("@3x", "xxxhdpi")

        var file = File(file.name.toLowerCase())

        for (key in replaceTable.keys) {
            if (key in file.name) {
                val targetFolder = target.resolve("res/drawable-${replaceTable.get(key)}")
                val newFile = file.name.replace(key, "")

                return targetFolder.resolve(newFile)
            }
        }

        return target.resolve("res/drawable-mdpi").resolve(file)
    }

}
