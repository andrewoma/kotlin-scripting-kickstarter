package scripts.base

import java.io.File

open class TestBase {
    val home = findProjectHome()

    fun file(name: String) = File(home, name)

    private fun findProjectHome(): File {
        val path = this.javaClass.protectionDomain?.codeSource?.location?.path
        return File(path!!.substring(0, path.indexOf("/build/classes/")))
    }
}