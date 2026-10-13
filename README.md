# Pay Calculator

An Android app that calculates regular pay, overtime pay, total pay, and tax.

## Features

- Enter hours worked, hourly rate, and tax rate as a percentage.
- Calculate overtime at 1.5 times the hourly rate for hours above 40.
- View all amounts rounded to two decimal places.
- Reject missing or invalid inputs.
- Open an About screen showing the student name and ID.
- Return from About using the title bar back arrow, preserving calculator values.

## Run the app

1. Clone or download this repository.
2. Open the project folder in Android Studio.
3. Allow Gradle sync to finish. Install Android SDK 37 if prompted.
4. Select an Android emulator or connected phone running Android 7.0 or newer.
5. Select the `app` configuration and click Run.

Use Android Studio's bundled JDK. The project includes the Gradle wrapper.

To build a debug APK on Windows:

```bat
gradlew.bat assembleDebug
```

On macOS or Linux:

```sh
sh gradlew assembleDebug
```

The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Calculations

For up to 40 hours, regular pay is hours multiplied by hourly rate. Above 40 hours, regular pay is 40 multiplied by hourly rate, and overtime pay is the extra hours multiplied by hourly rate and 1.5.

Total pay is regular pay plus overtime pay. Tax is calculated on regular pay. Enter `10` for a 10% tax rate.

For example, 45 hours at $20 per hour with 10% tax gives:

| Result | Amount |
| --- | ---: |
| Regular pay | $800.00 |
| Overtime pay | $150.00 |
| Total pay | $950.00 |
| Tax | $80.00 |

## Project structure

- `MainActivity.java`: input fields, validation, results, and About navigation.
- `AboutActivity.java`: student details and return navigation.
- `PayCalculator.java`: pay and tax calculations.
- `ScreenInsets.java`: screen spacing around system bars and the title bar.

The app uses Java and Android's native UI components.
