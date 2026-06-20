# প্রশ্ন এনালিসিস App

বিষয়, অধ্যায়, টপিক ও প্রশ্নসংখ্যা — Add / Edit / Delete করা যাবে। ডেটা ফোনে (Room database) স্থায়ীভাবে সেভ থাকে, app বন্ধ করলেও হারাবে না।

---

## ফোন দিয়ে GitHub-এ আপলোড করার উপায় (Android Studio ছাড়া)

### ধাপ ১: Repo বানান
1. মোবাইল ব্রাউজারে **github.com** খুলুন, লগইন করুন।
2. **New repository** → নাম দিন (যেমন `QuestionAnalysis`) → Public/Private বেছে → **Create repository**।

### ধাপ ২: ফাইল আপলোড করুন (সবচেয়ে সহজ পদ্ধতি — Termux ছাড়া)
1. এই zip ফাইলটি extract করুন (ফোনের কোনো File Manager / ZArchiver app দিয়ে)।
2. ব্রাউজারে আপনার repo খুলে URL-এর `github.com` পরিবর্তন করে `github.dev` করুন (এন্টার চাপুন)। এটি একটি ফুল কোড এডিটর খুলবে।
3. বাম পাশে Explorer-এ **Upload Files** আইকনে ট্যাপ করুন (বা ৩-ডট মেনু → Upload Files)।
4. extract করা ফোল্ডারের ভেতরের **সব ফাইল ও ফোল্ডার একসাথে সিলেক্ট করে** আপলোড করুন (folder structure ঠিক রাখার জন্য পুরো ফোল্ডার ভেতরে ঢুকে সবকিছু সিলেক্ট করবেন, পুরো ZIP না)।
5. বাম পাশে **Source Control** আইকনে ট্যাপ করুন → মেসেজ লিখুন (যেমন "Initial commit") → **Commit & Push**।
6. প্রথমবার GitHub লগইন/অনুমতি চাইতে পারে — অনুমতি দিন।

> বিকল্প পদ্ধতি (বেশি reliable): ফোনে **Termux** app ইনস্টল করে:
> ```
> pkg install git
> git clone https://github.com/<your-username>/QuestionAnalysis.git
> cd QuestionAnalysis
> # এখানে extract করা ফাইলগুলো কপি করুন (cp -r বা unzip কমান্ড দিয়ে)
> git add -A
> git commit -m "Initial commit"
> git push
> ```
> (Push করার সময় ইউজারনেম + GitHub Personal Access Token লাগবে, পাসওয়ার্ডের জায়গায় টোকেন দিতে হবে)

### ধাপ ৩: APK বানান (অটোমেটিক, Android Studio লাগবে না!)
1. ফাইল push হলে GitHub নিজেই **Actions** ট্যাবে গিয়ে build শুরু করবে (`.github/workflows/build-apk.yml` ফাইলের কারণে)।
2. Repo-র **Actions** ট্যাবে ট্যাপ করুন → চলমান workflow-এ ট্যাপ করুন → বিল্ড শেষ হলে (২-৪ মিনিট) নিচে **Artifacts** সেকশনে `app-debug-apk` পাবেন।
3. সেটাতে ট্যাপ করে ডাউনলোড করুন (zip আসবে, ভেতরে `app-debug.apk` থাকবে)।
4. APK extract করে ফোনে ইনস্টল করুন (Settings → "Unknown apps" থেকে allow করতে হতে পারে)।

### পরে কোড পরিবর্তন করতে চাইলে
- `github.dev` দিয়ে যেকোনো ফাইল এডিট করে আবার commit & push করলেই GitHub Actions আবার নতুন APK বানিয়ে দেবে।

---

## প্রজেক্ট স্ট্রাকচার
```
QuestionAnalysis/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/qanalysis/app/
│       │   ├── MainActivity.kt
│       │   ├── TopicAdapter.kt
│       │   ├── TopicViewModel.kt
│       │   └── data/ (Room: Entity, Dao, Database)
│       └── res/ (layout, values, drawable)
├── build.gradle
├── settings.gradle
└── .github/workflows/build-apk.yml   ← এটাই APK build করে
```
