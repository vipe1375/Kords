#include <jni.h>
#include <fluidsynth.h>
#include <android/log.h>

static fluid_settings_t *settings;
static fluid_synth_t *synth;
static fluid_audio_driver_t *driver;

JNIEXPORT void JNICALL
Java_com_vipedev_kords_Synth_init(JNIEnv *e, jobject o) {
    settings = new_fluid_settings();
    fluid_settings_setstr(settings, "audio.driver", "oboe");   // ← "opensles" remplacé par "oboe"
    synth = new_fluid_synth(settings);
    driver = new_fluid_audio_driver(settings, synth);
}

JNIEXPORT jint JNICALL
Java_com_vipedev_kords_Synth_loadSf2(JNIEnv *e, jobject o, jstring path) {
    const char *p = (*e)->GetStringUTFChars(e, path, 0);
    int id = fluid_synth_sfload(synth, p, 1);
    (*e)->ReleaseStringUTFChars(e, path, p);

    if (id >= 0) {
        int r = fluid_synth_program_select(synth, 0, id, 11, 24);
        __android_log_print(ANDROID_LOG_DEBUG, "Synth", "select ret=%d id=%d", r, id);
    }
    return id;   // -1 = échec
}

JNIEXPORT void JNICALL
Java_com_vipedev_kords_Synth_noteOn(JNIEnv *e, jobject o, jint ch, jint key, jint vel) {
    fluid_synth_noteon(synth, ch, key, vel);
}

JNIEXPORT void JNICALL
Java_com_vipedev_kords_Synth_noteOff(JNIEnv *e, jobject o, jint ch, jint key) {
    fluid_synth_noteoff(synth, ch, key);
}

JNIEXPORT void JNICALL
Java_com_vipedev_kords_Synth_release(JNIEnv *e, jobject o) {
    if (driver) { delete_fluid_audio_driver(driver); driver = NULL; }
    if (synth)  { delete_fluid_synth(synth);         synth  = NULL; }
    if (settings){ delete_fluid_settings(settings);  settings = NULL; }
}