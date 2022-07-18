# Next14 CMP for Android

## Requirements

Minimum API level: 19 (Android 4.4)

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
    implementation 'com.next14.cmp:cmp-sdk:2.0.0-alpha.2'
}
```

##### Other dependencies

Additional dependencies **should automatically be downloaded** and included along
with the library through the previous gradle declaration.

## How to use

### Initialization

The Next14 CMP SDK need to retrieves configurations at startup which contains
all the data needed to present the consent screen to the user.

The initialization is done via the `start` method.

**Java**

```java
    // Equivalent to use CMPSettings.defaultSettings()
    CMPSettings settings = new CMPSettings.Builder().build();
    CMPSdk.getInstance(this).start("<YOUR API KEY>", settings, (success, error) -> {
        if (success) {
            Log.i("CMP", "CMP initialized");
        } else {
            Log.e("CMP", "CMP failed to initialize", error);
        }
    });
```

#### Parameters

| Parameter | Description                                           | Required |
| --------- | ----------------------------------------------------- | -------- |
| apiKey    | The application unique identifier on the CMP platform | Yes      |
| settings  | Additional settings to customize the SDK behaviour    | No       |

#### CMPSettings

| Parameter                     | Description | Default |
| ----------------------------- | ----------- | ------- |
| consentPreferenceValidityDays | the number of days since the last time the user has given consent, after which the request for consent is presented again to the user | 180 |

### Display Consent Screen

After the SDK has been initialized, the application should call the
`shouldPresentCMP` method to check if the consent screen need to be displayed to
the user, and if so it should call the `present` method from an application
Activity.

The `present` method allows an optional `requestCode` parameter to be notified
when the user has expressed its consent via the Activity's `onActivityResult`
method.

If your presenter activity extends `androidx.appcompat.app.AppCompatActivity`
you can leverage the new Activity Result APIs and use the provided
`CMPActivityContract` to launch and receive the result from the Consent UI.

**Simple use**

```java
if (CMPSdk.getInstance(this).shouldPresentCMP()) {
    CMPSdk.getInstance(this).present(this);
}
```

**Receiving callback from onActivityResult**

```java
    private final int CMP_REQUEST_CODE = 100;

    @Override
    protected void onStart() {
        super.onStart();
        if (CMPSdk.getInstance(this).shouldPresentCMP()) {
            CMPSdk.getInstance(this).present(this, true, CMP_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == this.CMP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.i("CMP", "CMP Presented");
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
```

**Using CMPActivityContract**

```java
    private final ActivityResultLauncher<Boolean> cmpLauncher = registerForActivityResult(new CMPActivityContract(), result -> {
        Log.d("CMP", "CMP Presented = " + result);
    });

    @Override
    protected void onStart() {
        super.onStart();
        if (CMPSdk.getInstance(this).shouldPresentCMP()) {
            cmpLauncher.launch(true);
        }
    }
```

#### CMPSdk.present Parameters

| Parameter    | Description                                           | Required |
| ------------ | ----------------------------------------------------- | -------- |
| activity     | The presenting activity                               | Yes      |
| gdprApplies  | whether the GDPR applies to the current user or not   | No       |
| requestCode  | an optional request code to be notified when the consent screen is closed  | No       |

#### CMPActivityContract Parameters

| Parameter    | Description                                           | Required |
| ------------ | ----------------------------------------------------- | -------- |
| gdprApplies  | whether the GDPR applies to the current user or not   | No       |
