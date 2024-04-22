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
  implementation 'org.bitbucket.consumerchoicemvp:repocket-android-sdk:LATEST-VERSION'
}
```

### Step 3 initialize SDK

Initialize SDK on you `Application main class`

```groovy
val repocket= Repocket.Builder().withContext(this).withApiKey("YOUR-API-KEY").build()
```


### For background running

If you want to use SDK in background you can pass boolean along with notification information at time of `initilization` use `withForegroundService` method default value is `true` which means if you not call this method SDK will use `foreground` service by default

```groovy
withForegroundService(true,"NotificationTitle","NotificationContent")
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
    when (it) {
      ConnectionEvent.Connected -> {
        // connected state
      }

      ConnectionEvent.Connecting -> {
        // connecting state
      }

      ConnectionEvent.Disconnected -> {
        // disconnected state

      }

      is ConnectionEvent.Error -> {
        // Error state
      }

      ConnectionEvent.OnRefreshTokenRequired -> {
        // Refresh Token required state
      }
    }

  }
}

```

You can disconnect SDK by calling `disconnect` function

```groovy
repocket.disconnect()
```


