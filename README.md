# Moshi Gson interop Retrofit converter

A [Retrofit](https://square.github.io/retrofit/) converter that converts both [Gson](https://github.com/google/gson) and [Moshi](https://github.com/square/moshi) models.

This tool can be used in the projec where both `Gson` and `Moshi` are present. 

Note that, this won't work if a particular model class is mixed with both Gson and Moshi.
This basically checks the root class of the response type and if it is a Gson model, all of its fields will also be parsed as Gson. And if it is a Moshi model, all of its fields will be parsed as Moshi 

# Installation

1. Add to _build.gradle_:
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
2. Add the dependency
```groovy
implementation 'com.github.muthuraj57:moshi-gson-interop-retrofit-converter:1.0.0'
```

---

# Usage
### Add the converter
```
Retrofit.Builder()
    ...
    .addConverterFactory(MoshiGsonInteropConverterFactory(moshi, gson))
    ...
    .create(ApiService::class.java)
```

Note: If you already have added `GsonConverterFactory` and/or `MoshiConverterFactory` in the `Retrofit` builder, you should remove those.

---

# Example

```
data class GsonModel(val name: GsonName, val age: Int)

data class GsonName(val firstName: String, val lastName: String)

@JsonClass(generateAdapter = true)
data class MoshiModel(val name: MoshiName, val age: Int)

@JsonClass(generateAdapter = true)
data class MoshiName(val firstName: String, val lastName: String)

 private interface ApiService {
        @GET("/moshiModel")
        fun getMoshiModel(): MoshiModel

        @GET("/gsonModel")
        fun getGsonModel(): GsonModel
    }
```

Here we have two APIs and the first one should be parsed with `Moshi` and the second one should be parsed with `Gson`. If `GsonConverterFactory` and/or `MoshiConverterFactory` are used independently without using `MoshiGsonInteropConverterFactory`, then all the models will be tried to parse from either `Gson` or `Moshi`(depending on the order you added the converters).

If you instead addd `MoshiGsonInteropConverterFactory` like [this to the retrofit builder](#add-the-converter), both models can be parsed with the single `Retrofit` instance.

```
apiService.getGsonModel() //This works fine.

apiService.getMoshiModel() //This also works fine.
```
