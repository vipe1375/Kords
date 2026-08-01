package com.vipedev.kords

class Synth {
    external fun init()
    external fun loadSf2(path: String): Int
    external fun noteOn(ch: Int, key: Int, vel: Int)
    external fun noteOff(ch: Int, key: Int)
    external fun release()

    companion object { init { System.loadLibrary("native-lib") } }
}