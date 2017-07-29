#include <jni.h>
#include <android/log.h>

#define TAG "JNI_CZH" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型




// 访问并修改Java的实例成员变量
JNIEXPORT void JNICALL Java_com_laochen_jni_Person_modifyFieldFromJNI(JNIEnv *env, jobject thiz, jint age) {
    jclass clazz = (*env)->GetObjectClass(env, thiz);
    jfieldID fieldID = (*env)->GetFieldID(env, clazz, "age", "I"); // 与age的访问修饰符无关
    jint oldAge = (*env)->GetIntField(env, thiz, fieldID);
    LOGE("old age:%d", oldAge);
    (*env)->SetIntField(env, thiz, fieldID, age);
}


// 调用Java的实例方法
JNIEXPORT void JNICALL Java_com_laochen_jni_Person_callInstanceMethod(JNIEnv *env, jobject thiz) {
    jclass clazz = (*env)->GetObjectClass(env, thiz);
    jmethodID methodID = (*env)->GetMethodID(env, clazz, "setAge", "(I)V");
    (*env)->CallVoidMethod(env, thiz, methodID, 30);
}


// 访问Java的静态成员变量


// 调用Java的静态方法