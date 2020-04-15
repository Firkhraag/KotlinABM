import application.app.MyApp

class MyApplicationToRun {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MyApp().main(arrayOf())
        }
    }
}