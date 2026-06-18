# Study Tracker

This branch contains a simple Android Study Tracker app using Kotlin, Jetpack Compose and Room, following a clean architecture approach.

Structure:
- data/model: Room entity
- data/local: DAO and Room database
- data/repository: repository wrapping DAO
- ui: ViewModel (StateFlow) + Compose UI

Notes:
- This is a focused sample; Gradle build files and themes are intentionally omitted from this commit. You can integrate these sources into an Android project or I can add full build files and themes on request.

How to use:
1. Create a new Android project with Jetpack Compose and Kotlin.
2. Copy the `app/src/main/java/com/example/studytracker` directory into your project's source tree.
3. Add dependencies: Room (runtime, ktx, compiler), Jetpack Compose, Material3, Kotlin coroutines.
4. Initialize the AppDatabase via AppDatabase.getInstance(context) and run the app.

If you want, I can add Gradle files, theme resources, and a sample UI theme (Material 3) and push a complete buildable project.
