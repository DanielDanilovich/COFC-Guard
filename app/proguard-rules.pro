-keep class com.cofc.guard.** { *; }
-keep class com.cofc.guard.models.** { *; }
-keep class com.cofc.guard.services.** { *; }
-keep class com.cofc.guard.utils.** { *; }
-keep class org.bouncycastle.** { *; }
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.Database
-keep class dagger.hilt.** { *; }
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
