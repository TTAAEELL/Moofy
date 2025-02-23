applicationVariants.all {
    val variant = this
    val isFdroid = variant.productFlavors.any { it.name == "fdroid" }
    if (isFdroid) {
        val versionCodes =
            mapOf("armeabi-v7a" to 2, "arm64-v8a" to 1, "x86" to 4, "x86_64" to 3, "universal" to 0)

        variant.outputs
            .map { it as com.android.build.gradle.internal.api.ApkVariantOutputImpl }
            .forEach { output ->
                val abi = output.getFilter("ABI") ?: "universal"
                output.outputFileName = "NikaNG_${variant.versionName}-fdroid_${abi}.apk"
                if (versionCodes.containsKey(abi)) {
                    output.versionCodeOverride =
                        (100 * variant.versionCode + versionCodes[abi]!!).plus(5000000)
                } else {
                    return@forEach
                }
            }
    } else {
        val versionCodes =
            mapOf("armeabi-v7a" to 4, "arm64-v8a" to 4, "x86" to 4, "x86_64" to 4, "universal" to 4)

        variant.outputs
            .map { it as com.android.build.gradle.internal.api.ApkVariantOutputImpl }
            .forEach { output ->
                val abi = if (output.getFilter("ABI") != null)
                    output.getFilter("ABI")
                else
                    "universal"

                output.outputFileName = "Moofy_${variant.versionName}_${abi}.apk"
                if (versionCodes.containsKey(abi)) {
                    output.versionCodeOverride = (1000000 * versionCodes[abi]!!).plus(variant.versionCode)
                } else {
                    return@forEach
                }
            }  // ✅ Fixed: Closing brace for `forEach`
    }  // ✅ Closing brace for `if`
}  // ✅ Closing brace for `applicationVariants.all`
