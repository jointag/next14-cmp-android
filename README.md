# Next14 CMP for Android

## Requirements

Minimum API level: 19 (Android 4.4)
Minimum Kotlin version: 1.5.31

The library depends on AndroidX libraries. Old support libraries are no longer supported.

## Installation

### Add the Maven repository

To download the SDK package you can use our Maven repository. To include it, add
the following lines to your app-module **build.gradle** file:

```gradle
repositories {
    maven { url "https://artifactory.jointag.com/artifactory/next14" }
}
```

### Add the library dependency

Now add the library dependency

```gradle
dependencies {

    ...

    // Add the following line
    implementation "com.next14.cmp:cmp-sdk:1.2.1"
}
```

##### Other dependencies

Additional dependencies **should automatically be downloaded** and included along
with the library through the previous gradle declaration.

## How to use

To present the consent screen to the user, simply call the `CMPActivity` method
`start` from your main activity providing your unique API KEY.

```java
public static final String API_KEY = "<YOUR API KEY>";

// Add this in your onStart or similar method
if (CMPActivity.shouldPresentCMP(this)) {
    CMPActivity.start(this, API_KEY);
}
```
