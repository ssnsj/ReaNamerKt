package com.ttp.utils

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import java.util.*
import kotlin.test.assertEquals

class MainTest {
    lateinit var main: Main

    @Before
    fun initialize() {
        main = Main()
    }

    @Test
    fun empty_target_should_be_same_with_source() {
        val result = main.getPaths(File("aaa"), File(""))
        assertEquals(result.source, result.target)
    }

    @Test
    fun target과source가_다른_값으로_들어오면_각각을_사용한다() {
        val source = File("aaa");
        val target = File("bbb");

        val result = main.getPaths(source, target)

        assertEquals(source, result.source)
        assertEquals(target, result.target)
    }

    @Test
    fun source가_없으면_현재폴더를_source로_사용한다() {
        val source = File("");
        val target = File("");

        val result = main.getPaths(source, target)

        assertEquals(main.getCurrentPath(), result.source)
        assertEquals(result.source, result.target)
    }

    @Test
    fun renameBasic() {
        assertRename(expected = "res/drawable-mdpi/a.png", target = "", name = "a.png")
        assertRename("abc/res/drawable-mdpi/a.png", "abc", "a.png")
        assertRename("/abc/res/drawable-mdpi/a.png", "/abc", "a.png")
    }

    private fun assertRename(expected: String, target: String, name: String) {
        assertEquals(File(expected), main.rename(File(target), File(name)))
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

    @Test
    fun source폴더의_모든_파일들을_수집한다_하위폴더는_제외() {

        var currentPath = File("./zeplin")
        println("current:" + currentPath.canonicalPath)

        val paths = main.getPaths(File("./zeplin"), File(""))

        val files = main.collectFiles(paths.source)

        main.processFiles(files)
    }

}
