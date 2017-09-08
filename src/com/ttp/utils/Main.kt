package com.ttp.utils

import java.io.File


fun main(args: Array<String>) {
    Main.main()
}

class Main {
    fun funtest(): Boolean {

        var currentPath: String = File(".").canonicalPath

        var files : Collection<File> = collectFiles(File(currentPath))

        moveFiles(files)

        println("Current Path: $currentPath")

        return true
    }

    private fun collectFiles(path: File): Collection<File> {
        var files:ArrayList<File> = ArrayList()

        path.list { dir, name ->
            println("$dir :: $name ")
            File(name).isFile
        }.forEach { files.add(File(it)) }

        return files
    }

    private fun moveFiles(files: Collection<File>) {
        for (file in files) {
            println("FILE: ${file.name}")
        }
    }

    companion object {
        fun main() {
            Main().funtest()
        }
    }
}