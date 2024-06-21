# Repocket Android SDK Demo

## Getting Started

### Step 1: Configure dependency URL

#### Add snippet below to settings.gradle file:

```groovy
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    maven {
      url = uri("https://jitpack.io")
      credentials {
        username = "YOUR-AUTH-TOKEN"
      }
    }
  }
}
```

### Step 2: Embed dependencies

Add dependency in you Android project app level  `build.gradle`.

```groovy
dependencies {
  implementation 'org.bitbucket.consumerchoicemvp:repocket-android-client-sdk:LATEST-VERSION'
}
```

if your app targeting below API 23 (Android 6.0) you need to add this in your app level `build.gradle` file

```groovy
compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
```

add this dependency under `dependency` section

```groovy

coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

```

[Use Java 8 language features and APIs](https://developer.android.com/studio/write/java8-support.html)

### Step 3 initialize SDK

Initialize SDK on you `Application main class`

```groovy
val repocket= Repocket.Builder().withContext(this).withApiKey("YOUR-API-KEY").build()
```

### For background running

If you want to use SDK in background you can pass boolean along with notification information at time of `initilization`
use `withForegroundService` method default value is `true` which means if you not call this method SDK will
use `foreground` service by default

```groovy
withForegroundService(true,notificationId,notificationObject")
```

### Step 4 Connect SDK

You can connect SDK by just calling `connnect` function

```groovy
repocket.connect()
```

### Step 5 (Optional)

You can listen connection related events by collecting `connection flow`

```groovy
lifecycleScope.launch {
            repocket.connectionStatus.collect {
                when(it){
                    ConnectionEvent.Connected -> {
                        //"Connected"
                    }
                    ConnectionEvent.Connecting -> {
                        //"Connecting"
                    }
                    ConnectionEvent.Disconnected -> {
                        //"disconnected"
                    }
                    is ConnectionEvent.Error -> {
                        // "Error"
                    }
                    ConnectionEvent.OnRefreshTokenRequired -> {
                        //"Token Error"
                    }
                    ConnectionEvent.CheckingLatestVersion -> {
                        //"Checking update"
                    }
                    ConnectionEvent.ErrorOnUpdateSdk -> {
                        //"Error on update"
                    }
                    ConnectionEvent.UpdateCompleted -> {
                         //"Update completed"
                    }
                    ConnectionEvent.UpdatingSdk -> {
                        //"Updating"
                    }
                }

            }
        }

```

You can disconnect SDK by calling `disconnect` function

```groovy
repocket.disconnect()
```




