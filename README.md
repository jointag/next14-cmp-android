# Next14 CMP for Android

## Requirements

Minimum API level: 26 (Android 8.0)

## Installation

### Add the Maven repository

To download the SDK package you can use our Maven repository. To include it, add
the following lines to your app-module **build.gradle** file:

```gradle
repositories {
    maven { url "https://artifactory.jointag.com/artifactory/next14" }
}
```
### Set the JAVA compatibility version

In the `android` block of your app-module **build.gradle** file add the following:

```gradle
android {

  ...
  // Add the following lines
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}
```

### Add the library dependency

Now add the library dependency

```gradle
dependencies {

    ...

    // Add the following line
    implementation 'com.next14.cmp:cmp-sdk:1.0.0'
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
