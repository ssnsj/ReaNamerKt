package com.ttp.utils

import java.io.File
import java.util.*


fun main(args: Array<String>) {
    Main.main()
}

class Main {

    data class Parameters(val runDry: Boolean, val paths: Paths)

    var parameters = Parameters(true, Paths(File("./zeplin"), File("")))

    fun Main() {

//        var currentPath: String = File(".").canonicalPath
//
//        var files : Collection<File> = collectFiles(File(currentPath))
//
//        moveFiles(files)
//
//        println("Current Path: $currentPath")

    }

    fun collectFiles(path: File): Collection<String> {
        var files: ArrayList<String> = ArrayList()

        path.list { dir, name ->
            //            println("$dir :: $name ")
            dir.resolve(name).isFile
        }.forEach { files.add(it) }

        return files
    }

    fun moveFiles(files: Collection<File>) {
        for (file in files) {
            println("FILE: ${file.name}")
        }
    }

    fun getCurrentPath() = File("./").canonicalFile

    fun getPaths(source: File, target: File): Paths {
        return when {
            source.name.isEmpty() -> Paths(getCurrentPath(), getCurrentPath())
            target.name.isEmpty() -> Paths(source, source)
            !source.equals(target) -> Paths(source, target)
            else -> throw RuntimeException("None of above")
        }
    }

    fun rename(target: File, file: File): File {
        val replaceTable = Hashtable<String, String>()
        replaceTable.put("@2x", "xhdpi")
        replaceTable.put("@3x", "xxxhdpi")

        var file = File(file.name.toLowerCase().replace("-", "_"))


        for (key in replaceTable.keys) {
            if (key in file.name) {
                val targetFolder = target.resolve("res/drawable-${replaceTable.get(key)}")
                val newFile = file.name.replace(key, "")

                return targetFolder.resolve(newFile)
            }
        }

        return target.resolve("res/drawable-mdpi").resolve(file)
    }

    companion object {
        fun main() {
        }
    }

    fun processFiles(files: Collection<String>) {
        for (name in files) {

            val source = parameters.paths.source.resolve(name)
            val target = rename(parameters.paths.target, File(name))

            if (parameters.runDry) {
                println("mv ${source.canonicalPath} ${target.canonicalPath}")
            }
        }
    }


}