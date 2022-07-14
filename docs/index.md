# Moshi Gson interop Retrofit converter

A [Retrofit](https://square.github.io/retrofit/) converter that converts both [Gson](https://github.com/google/gson) and [Moshi](https://github.com/square/moshi) models.

This tool can be used in the projec where both `Gson` and `Moshi` are present. 

Note that, this won't work if a particular model class is mixed with both Gson and Moshi.
This basically checks the root class of the response type and if it is a Gson model, all of its fields will also be parsed as Gson. And if it is a Moshi model, all of its fields will be parsed as Moshi 

# Installation

Add to _build.gradle_:
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```groovy
implementation 'com.github.muthuraj57:moshi-gson-interop-retrofit-converter:1.0.0'
```
