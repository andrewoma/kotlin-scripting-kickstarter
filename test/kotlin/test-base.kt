package scripts.base

import java.io.File

open class TestBase {
    val home = findProjectHome()

    fun file(name: String) = File(home, name)

    private fun findProjectHome(): File {
        val path = this.javaClass.getProtectionDomain()?.getCodeSource()?.getLocation()?.getPath()
        return File(path!!.substring(0, path.indexOf("/build/classes/")))
    }
}