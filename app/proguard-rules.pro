# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

#Move all clases to single folder
-repackageclasses

#Optimization N iterations. You can chance count iterations
-optimizationpasses 3

#Remove all ours Log.x() files
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** d(...);
    public static *** e(...);
}

-dontwarn com.google.errorprone.annotations.Immutable

-dontusemixedcaseclassnames
-verbose

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep class java.lang.invoke.** { *; }

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-renamesourcefileattribute SourceFile
-repackageclasses

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-dontusemixedcaseclassnames
-dontwarn retrofit2.KotlinExtensions$*
-dontwarn retrofit2.KotlinExtensions

-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-keepattributes Exceptions
-dontwarn java.lang.invoke.**


-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

-dontwarn javax.annotation.**
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

-dontwarn java.lang.invoke.StringConcatFactory

# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
   static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
   static **$* *;
}
-keepclassmembers class <2>$<3> {
   kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
   public static ** INSTANCE;
}
-keepclassmembers class <1> {
   public static <1> INSTANCE;
   kotlinx.serialization.KSerializer serializer(...);
}
