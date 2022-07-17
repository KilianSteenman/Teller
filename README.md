# Teller

![CI](https://github.com/KilianSteenman/Teller/workflows/Android%20CI/badge.svg?branch=master)
[![JitPack](https://jitpack.io/v/KilianSteenman/Teller.svg)](https://jitpack.io/#KilianSteenman/Teller)

Android analytics wrapper

Wrap your analytics calls in a single object.

## Implementation

Add the Teller dependency to your project

```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```

Latest
version: [![JitPack](https://jitpack.io/v/KilianSteenman/Teller.svg)](https://jitpack.io/#KilianSteenman/Teller)

```groovy
dependencies {
    implementation "com.github.KilianSteenman.Teller:teller:<latest version>"
}
```

### Adding your analytics Framework

To add an Analytics Framework extend the `Framework` interface. A `Measurement` will be passed to
the count method, use this data to call your analytics Framework.

Example:

```kotlin
internal class FirebaseFramework : Framework {

    override val name: String = NAME

    override fun count(measurement: Measurement) {
        Firebase.analytics.logEvent(event.name) {
            addParams(event.params)
        }
    }

    companion object {
        const val NAME = "Firebase"
    }
}
```

### Registering your Framework to Teller

Setup Teller in your Applications `onCreate` by registering your analytics `Framework`
implementations.

```kotlin
Teller.instance.addMeasurementChain(
    MeasurementChain.Builder()
        // Optionally add your interceptors here    
        .build(FirebaseFramework())
)
```

### Interceptors

In some cases you might want to process your data before sending it to your Framework.
`MeasurementInterceptors` can be used for this.

### Sending a measurement

To send a measurement call `Teller.count`. This methods takes a `Measurement`, which can be
constructed by using the `Measurement.Builder`.

Example:

```kotlin
Teller.instance.count(
    Measurement.Builder(FirebaseFramework.NAME, "ScreenView")
        .setName("click")
        .addParam("button_press" to "login")
        .build()
)
```

## Provided Frameworks

Some common Analytics Frameworks are already available for use.

### Firebase

// TODO: Figure out how these will be uploaded

```groovy
dependencies {
    implementation "com.github.KilianSteenman.Teller:firebase:<latest version>"
}
```

## Provided interceptors

Some useful interceptors that are part of this repository.

### TellerLogger

The TellerLogger interceptor adds a [Chucker](https://github.com/ChuckerTeam/chucker) styled
notification and UI for your analytics.

#### Usage

Add the TellerLogger dependency to your project

Latest
version: [![JitPack](https://jitpack.io/v/KilianSteenman/Teller.svg)](https://jitpack.io/#KilianSteenman/Teller)

```groovy
dependencies {
    debugImplementation "com.github.KilianSteenman.Teller:tellerlogger:<latest version>"
    releaseImplementation "com.github.KilianSteenman.Teller:tellerlogger-noop:<latest version>"
}
```

Add the `LoggingMeasurementInterceptor` to your `MeasurementChain`.

Example usage:

```kotlin
addMeasurementChain(
    MeasurementChain.Builder()
        .addInterceptor(LoggingMeasurementInterceptor(applicationContext))
        .build(/* FrameWork */)
)
```
