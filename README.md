> **English** | [繁體中文](README.zh-TW.md)

# Vocab Master

> **An Android vocabulary trainer that bridges the gap between *recognition* and *recall*.**
> Build your own word lists, study them as flashcards, then prove you really know them — by matching or by spelling.

[![ci](https://github.com/wong060404/vocab-master/actions/workflows/ci.yml/badge.svg)](https://github.com/wong060404/vocab-master/actions/workflows/ci.yml)
![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Java-007396?logo=openjdk&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-34-blue)
![Compile SDK](https://img.shields.io/badge/compileSdk-35-blue)
![Database](https://img.shields.io/badge/Persistence-Room%20(SQLite)-4CAF50)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Screenshots

<p align="center">
  <img src="docs/screenshots/01-home.png" width="200" alt="Home menu: Tap to Match, Spelling, Customize vocabularies">
  <img src="docs/screenshots/02-study-list.png" width="200" alt="Study list showing each word with its part of speech and Chinese meaning">
  <img src="docs/screenshots/03-tap-to-match.png" width="200" alt="Tap to Match: pairing English words with their Chinese meanings">
</p>
<p align="center">
  <img src="docs/screenshots/04-spelling.png" width="200" alt="Spelling quiz: a Chinese prompt and a free-text answer field">
  <img src="docs/screenshots/05-customize.png" width="200" alt="Customize Vocabularies: group management and per-word deletion">
</p>

---

## Table of Contents

- [Screenshots](#screenshots)
- [About the Project](#about-the-project)
- [Features](#features)
- [Screens & Navigation Flow](#screens--navigation-flow)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Built-in Vocabulary](#built-in-vocabulary)
- [Getting Started](#getting-started)
- [How to Use the App](#how-to-use-the-app)
- [Design Decisions](#design-decisions)
- [Testing](#testing)
- [Known Limitations & Future Work](#known-limitations--future-work)
- [Academic Context](#academic-context)
- [Acknowledgements](#acknowledgements)
- [License](#license)

---

## About the Project

**Vocab Master** is a single-developer Android application for learning English vocabulary with Chinese
meanings. It was created out of a very practical observation made while working as a part-time tutor:

> Standard language apps fail because their content is **fixed**. They cannot adapt to the specific
> "weak spots" of a student, nor to the exact vocabulary list a school quiz will cover next week.

Vocab Master flips that around. Instead of shipping one rigid word list, the app hands the power to the
**teacher / learner**:

| Principle | What it means in the app |
| --- | --- |
| **Efficiency Through Customization** | A tutor can enter a week's worth of vocabulary in minutes inside **Customize Mode**. |
| **Recognition → Recall** | Two distinct modes: *Tap to Match* (association) and *Spelling* (active production). |
| **Study before Test** | The user journey is deliberately `Select → Study → Test`, encouraging short-term memory encoding before retrieval practice. |
| **Persistent Personal Dictionary** | A local **Room (SQLite)** database keeps every built-in and user-created word permanently on the device. |
| **Multi-Sensory Engagement** | Visual cues, colour-coded feedback, background music and tactile input (tap & type). Offline-first — no account, no network. |

### Target Users

- **Educators & Tutors** — need to distribute curated vocabulary lists that match a school curriculum or exam.
- **Language Students** — primary/secondary students who want an engaging, less stressful way to master English words and their Chinese meanings.
- **Independent Learners** — people building specialised lists (business English, travel phrases, exam prep) who want a structured way to test their progress.

---

## Features

### Two Learning Modes

**Tap to Match (Recognition)**
- English words are shuffled into the left column, Chinese meanings into the right.
- Tap an English word, then tap its meaning — the matched pair is locked together with a **randomly generated colour**.
- Re-selecting a word clears its old pairing, so mistakes are always recoverable.
- Press **Finish** to validate: correct pairs flash **green**, wrong pairs flash **red**, and the board resets for another attempt.
- A perfect run navigates to the celebration screen.

**Spelling Quiz (Active Recall)**
- Shows the Chinese meaning and the part of speech as a hint.
- The user must type the exact English spelling.
- Progress indicator (`Word 3 / 10`) plus instant *Correct! / Wrong! Try again.* toast feedback.
- Completing every word leads to the success screen.

### Customize Mode (the heart of the app)

- **Built-in groups** — `Basic`, `Enhanced`, `Elite` (hard-coded, cannot be deleted).
- **Create unlimited custom groups** — e.g. *Food*, *Business*, *Travel*, *Week 5 Quiz*.
- **Dynamic UI generation** — group filter buttons are built at runtime from the unique difficulty values found in the database. The UI literally grows as the tutor adds content.
- **Add vocabulary** through a clean dialog: English + Part of Speech + Chinese.
- **Delete a single word**, or delete an entire group, both protected by `AlertDialog` confirmations.
- Every list is shown sorted A→Z in a `RecyclerView` with a per-row delete button.

### UI / UX

- **Card-based Study Mode** — centred `MaterialCardView`-style rows mimic physical flashcards; horizontal weights keep English, POS and Chinese neatly aligned.
- **Auto-sizing Material Buttons** everywhere, so long academic words never clip or wrap on small phones or tablets.
- **Consistent theming** — shared `default_bg` background, custom logo, circular gradient buttons.
- **Rainbow gradient shader** on the *About the APP* button as a personal creative touch.

### Multimedia

- Four looping background tracks: home page, study, quiz, and difficulty selection.
- Clapping sound on the success screen.
- **Mute toggle** pinned to the top-right of every major screen — silences the app *without* touching system volume.
- Music is automatically paused in `onPause()` and resumed in `onResume()`.
- Loading screen animated with **two simultaneous Lottie** vector animations.

---

## Screens & Navigation Flow

```
                        ┌──────────────────┐
                        │  LoadingActivity │  ← LAUNCHER (2 Lottie animations)
                        └────────┬─────────┘
                                 │ 3 s delay
              ┌──────────────────┴───────────────────┐
       no DIFFICULTY extra                    DIFFICULTY extra
              │                                      │
              ▼                                      ▼
     ┌─────────────────┐                     ┌────────────────┐
     │  MainActivity   │                     │ StudyActivity  │
     │   (home menu)   │                     │ (list preview) │
     └───┬───┬───┬─────┘                     └───────┬────────┘
         │   │   │                                     │
  Tap to │   │   │ Customize              ┌────────────┴─────────────┐
  Match  │   │   └──────────────┐   MODE=SPELLING             MODE=MATCHING
         │   │ Spelling         ▼         │                          │
         │   │            ┌───────────────┴──────┐          ┌────────┴────────┐
         │   │            │ CustomizeActivity    │          │  QuizActivity   │
         │   │            │ (add / delete words) │          │ (Tap to Match)  │
         │   │            └──────────────────────┘          └────────┬────────┘
         ▼   ▼                                                      │ all correct
 ┌───────────────────────┐   ┌──────────────────────────┐           │
 │ TapMatchChooseActivity│   │ SpellingChooseActivity   │           │
 │  Basic / Enhanced /   │   │  Basic / Enhanced /      │           │
 │  Elite / Custom ▾     │   │  Elite / Custom ▾        │           │
 └───────────┬───────────┘   └────────────┬─────────────┘           │
             │                            │                         │
             └────────► LoadingActivity ◄─┘                         │
                            │                                       │
                            ▼                                       │
                    ┌────────────────┐                              │
                    │ StudyActivity  │                              │
                    └───────┬────────┘                              │
                            │ Start quiz / Start spelling           │
                            ▼                                       │
                 ┌────────────────────────┐                         │
                 │ SpellingQuizActivity   │                         │
                 └───────────┬────────────┘                         │
                             │ all words done                       │
                             ▼                                     ▼
                        ┌──────────────────────────────┐
                        │      SuccessActivity         │
                        │  (clapping + back to home)   │
                        └──────────────────────────────┘

  MainActivity ──► AboutThisAppActivity   (About / How-to-use, scrollable)
```

`LoadingActivity` doubles as the **splash screen** and as a **routing hub**: if it receives a
`DIFFICULTY` extra it forwards straight to `StudyActivity`, otherwise it opens the home menu.

---

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | **Java 11** (source/target compatibility 1.11) |
| IDE / Build | Android Studio · **Gradle 9.1.0** · Android Gradle Plugin **9.0.1** (Kotlin DSL + version catalog) |
| Min / Target / Compile SDK | **34 / 35 / 35** |
| Application ID | `com.example.vocab_master_mad_project` |
| UI | XML layouts, `AppCompatActivity`, **Material Components 1.13.0**, `RecyclerView`, `AlertDialog`, `Spinner` |
| Persistence | **Room 2.6.1** (`room-runtime` + `room-compiler` via `annotationProcessor`) over SQLite |
| Animation | **Lottie 6.7.1** (`com.airbnb.android:lottie`) |
| Media | `android.media.MediaPlayer` with raw MP3 resources |
| Testing | JUnit 4.13.2, AndroidX Test Ext JUnit 1.3.0, Espresso 3.7.0 |
| AndroidX | `appcompat` 1.7.1, `core`/`activity` via AGP defaults |

### Dependencies (`gradle/libs.versions.toml`)

```toml
appcompat      = "1.7.1"
material       = "1.13.0"
lottie         = "6.7.1"
room           = "2.6.1"
junit          = "4.13.2"
junitVersion   = "1.3.0"   # androidx.test.ext:junit
espressoCore   = "3.7.0"
agp            = "9.0.1"
```

---

## Architecture

The project follows a **Repository-inspired, layered pattern** with a clear separation of concerns,
so that UI code never talks to SQLite directly.

```
┌───────────────────────────────────────────────────────────────┐
│  PRESENTATION LAYER  (Activities + XML layouts + Adapters)    │
│                                                               │
│  LoadingActivity   MainActivity   StudyActivity               │
│  TapMatchChooseActivity   SpellingChooseActivity              │
│  QuizActivity   SpellingQuizActivity   CustomizeActivity      │
│  SuccessActivity   AboutThisAppActivity                       │
│                                                               │
│  VocabAdapter (manage list)   GroupAdapter (group chooser)     │
└───────────────────────────┬───────────────────────────────────┘
                            │  Java method calls (no SQL here)
┌───────────────────────────▼───────────────────────────────────┐
│  DOMAIN / REPOSITORY LAYER                                    │
│                                                               │
│  VocabManager   — singleton façade                            │
│    • getAllVocabs() / getAllGroups()                          │
│    • getRandomVocabs(difficulty[, count])                     │
│    • getSortedVocabs(difficulty)                              │
│    • addVocab() / deleteVocab() / deleteGroup()               │
│    • seedDatabaseIfEmpty()                                    │
└───────────────────────────┬───────────────────────────────────┘
                            │
┌───────────────────────────▼───────────────────────────────────┐
│  DATA LAYER                                                   │
│                                                               │
│  VocabDao (Room @Dao interface)                               │
│  AppDatabase (Room @Database singleton, DB "vocab_database")  │
│  Vocab (Room @Entity, table "vocabs", Serializable)           │
└───────────────────────────────────────────────────────────────┘
```

**Key points**

- `AppDatabase` is a thread-safe singleton (`getInstance(Context)`), built with
  `fallbackToDestructiveMigration()` and `allowMainThreadQueries()` — the latter is a deliberate
  simplification for this coursework-sized app; a production build would move queries to a background
  executor (see [Future Work](#known-limitations--future-work)).
- `VocabManager` is also a singleton and is initialised **first thing** by `LoadingActivity`, so the
  schema exists and the seed data is ready before any other screen opens.
- `Vocab implements Serializable`, which lets a fully shuffled quiz list be handed from
  `StudyActivity` to `QuizActivity` / `SpellingQuizActivity` as an `Intent` extra (`QUIZ_LIST`).
- Quiz sizes are decided by `VocabManager.getRandomVocabs()`:
  `Basic → 5`, `Enhanced → 10`, `Elite → 15`, and **custom groups get every word, shuffled**.
  The Spelling mode always uses a fixed pool of **10** words per round.

---

## Project Structure

```
Vocab_Master_V1.0/
├── build.gradle.kts                 # root build script
├── settings.gradle.kts              # rootProject.name = "Vocab_Master_mad_project"
├── gradle.properties
├── gradlew / gradlew.bat
├── README.md                        # this file
├── docs/                            # course deliverables (addendum)
│   ├── MAD_Vocab_Master.pptx        # presentation slides
│   └── VocabMaster.apk              # compiled, installable build
├── gradle/
│   ├── libs.versions.toml           # version catalog (AGP, Room, Lottie, Material…)
│   └── wrapper/gradle-wrapper.properties   # Gradle 9.1.0
└── app/
    ├── build.gradle.kts             # app module: compileSdk 35, minSdk 34, Java 11
    ├── proguard-rules.pro
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml          # LoadingActivity is the LAUNCHER
        │   ├── ic_launcher-playstore.png
        │   ├── java/com/example/vocab_master_mad_project/
        │   │   ├── AppDatabase.java          # Room database singleton
        │   │   ├── VocabDao.java             # Room DAO (CRUD + filters)
        │   │   ├── Vocab.java                # @Entity "vocabs"
        │   │   ├── VocabManager.java         # repository + 800-word seeder
        │   │   ├── VocabAdapter.java         # RecyclerView adapter (manage list)
        │   │   ├── GroupAdapter.java         # RecyclerView adapter (group chooser)
        │   │   ├── LoadingActivity.java      # splash + routing
        │   │   ├── MainActivity.java         # home menu, BGM, rainbow shader
        │   │   ├── StudyActivity.java        # flashcard list preview
        │   │   ├── QuizActivity.java         # Tap to Match game
        │   │   ├── SpellingChooseActivity.java
        │   │   ├── SpellingQuizActivity.java # typing quiz
        │   │   ├── TapMatchChooseActivity.java
        │   │   ├── CustomizeActivity.java    # full CRUD UI
        │   │   ├── SuccessActivity.java      # celebration + clapping SFX
        │   │   └── AboutThisAppActivity.java
        │   └── res/
        │       ├── drawable/     # default_bg, button_default, logo, me, launcher vectors
        │       ├── layout/       # 16 XML layouts (activities, dialogs, list items)
        │       ├── mipmap-*/     # adaptive launcher icons (all densities)
        │       ├── raw/          # loading.json, loading2.json, 5 × .mp3 audio
        │       ├── values/       # colors.xml, strings.xml, themes.xml (+ values-night)
        │       └── xml/          # backup_rules.xml, data_extraction_rules.xml
        ├── test/java/.../ExampleUnitTest.java
        └── androidTest/java/.../ExampleInstrumentedTest.java
```

**Layout files of interest**

| File | Purpose |
| --- | --- |
| `activity_loading.xml` | Two `LottieAnimationView`s (centre 300dp, bottom-right 150dp) |
| `activity_main.xml` | Logo, mode buttons, Customize button, About button, mute toggle |
| `activity_study.xml` + `item_vocab_study.xml` | Flashcard list |
| `activity_quiz.xml` + `item_quiz_box.xml` | Two-column matching board |
| `activity_spelling_quiz.xml` | Hint texts + input field + submit |
| `activity_customize.xml` | Group button row, `RecyclerView`, add/create/delete buttons |
| `dialog_add_vocab.xml` | English / POS / Chinese input dialog |
| `dialog_group_chooser.xml` + `item_group_select.xml` | Custom-group picker dialog |
| `about_this_app.xml` | Scrollable About + How-to-use screen |

---

## Database Schema

**Database name:** `vocab_database` · **Version:** `1` · **Table:** `vocabs`

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | generated by Room |
| `english` | `TEXT` | `NOT NULL` (`@NonNull`) | the word itself |
| `pos` | `TEXT` | nullable | part of speech — `n.`, `v.`, `adj.`, `adv.`, `prep.`, `num.`, `int.` |
| `chinese` | `TEXT` | nullable | Chinese meaning / translation |
| `difficulty` | `TEXT` | nullable | the **group name**: `Basic`, `Enhanced`, `Elite`, or any user-created name |

**`VocabDao` operations**

```java
List<Vocab>  getAllVocabs();                        // ORDER BY english ASC
List<Vocab>  getVocabsByDifficulty(String group);   // ORDER BY english ASC
List<String> getAllGroups();                        // SELECT DISTINCT difficulty
void insert(Vocab v);
void insertAll(List<Vocab> v);
void update(Vocab v);
void delete(Vocab v);
void deleteById(int id);
void deleteGroupByDifficulty(String group);
```

### The Dynamic Group System

There is **no separate `groups` table**. A group *is* the set of rows sharing the same `difficulty`
value, and `SELECT DISTINCT difficulty` is what populates the UI:

1. `CustomizeActivity` calls `VocabManager.getAllGroups()`.
2. Any name that is not `Basic` / `Enhanced` / `Elite` gets a dynamically created `MaterialButton`.
3. Tapping that button simply filters by that string — so built-in and user-created groups are treated
   **identically** by the study and quiz engines.
4. Deleting a group is a single `DELETE FROM vocabs WHERE difficulty = :name`.

The three built-in names are protected in the UI layer (`confirmDeleteGroup()` refuses them), which is
why they are described as *hard-coded defaults that cannot be deleted in the app*.

---

## Built-in Vocabulary

On first launch `VocabManager.seedDatabaseIfEmpty()` populates the database with **800 words**
(only when the table is empty, so user edits are never overwritten):

| Group | Words | Quiz size per round |
| --- | --- | --- |
| **Basic** | 250 | 5 |
| **Enhanced** | 270 | 10 |
| **Elite** | 280 | 15 (all words for custom groups) |

Words are thematically batched and roughly alphabetical within each batch — e.g.
*Body & Health, Clothing, Food & Drink, Animals & Insects, Nature & Places, Household Items, Common
Actions, Adjectives, Time/Numbers/People* for Basic; *Business & Professional, Workplace & Tasks,
Science & Technology, Nature & Environment, Society & Mind, Abstract & Measurement* for Enhanced;
and higher-register lexis such as *ubiquitous, meticulous, exacerbate, quintessential* style entries
for Elite.

---

## Getting Started

### Prerequisites

- **Android Studio** (Ladybug or newer recommended — AGP 9.0.1 / Gradle 9.1.0)
- **JDK 11** or newer
- An **Android 14 (API 34)** or newer device / emulator — `minSdk = 34`

### Build & Run

```bash
# 1. Clone
git clone https://github.com/<your-username>/Vocab_Master_V1.0.git
cd Vocab_Master_V1.0

# 2. Build a debug APK (Linux / macOS)
./gradlew assembleDebug

#    …or on Windows
gradlew.bat assembleDebug

# 3. Install on a connected device / running emulator
./gradlew installDebug
```

Then open the project in Android Studio and press **Run ▶**, or install
`app/build/outputs/apk/debug/app-debug.apk` manually.

> **Note** — `local.properties` (containing your SDK path) is intentionally **not** committed; Android
> Studio regenerates it on first open. No API keys, accounts or internet access are required: the app
> is fully offline.

### Release build

```bash
./gradlew assembleRelease
```

Minification is currently **disabled** (`isMinifyEnabled = false`), and the release build is unsigned —
configure your own signing config before publishing to Google Play.

---

## How to Use the App

### 1. Create your own vocabulary groups

1. From the home screen tap **Customize vocabularies**.
2. Tap **+ New Group** and enter a name — by topic (`Food`, `Travel`, `Science`) or by schedule
   (`Week 5 Quiz`).
3. The new group button appears immediately in the filter row.

### 2. Add your words

1. Select the group you want to fill.
2. Tap **Add** and complete the dialog: **English**, **Part of Speech**, **Chinese**.
3. The word is inserted instantly and the sorted list refreshes.

### 3. Study

1. Home → **Tap to Match** or **Spelling**.
2. Choose a difficulty: **Basic**, **Enhanced**, **Elite**, or **Custom ▾** for your own groups.
3. A short animated loading screen appears while the sample is drawn at random.
4. Read through the flashcard list. Tap **Start quiz** / **Start spelling** when ready.

### 4. Test yourself

- **Tap to Match** — tap an English word (it turns blue), then tap the matching Chinese meaning. The
  pair locks in a shared colour. Match everything, then press **Finish**; green = correct, red = wrong.
  Any mistake resets the board after a moment so you can try again.
- **Spelling** — type the English word for the shown Chinese meaning. Correct answers advance
  automatically; wrong answers let you retry (`Text is re-selected for a quick retype`).

### 5. Celebrate & repeat

Finishing everything perfectly opens the **Success** screen with the clapping sound. Return home and
come back daily — reviewing the same group repeatedly is the intended way to move words from
short-term into long-term memory.

### 6. Managing your data

- **Delete one word:** trash icon on its row → confirm.
- **Delete a whole group:** select it, tap the delete-group button → confirm.
  *(Built-in groups are protected and cannot be deleted.)*

---

## Design Decisions

| Decision | Rationale |
| --- | --- |
| **Two distinct quiz modes** | Matching exercises *recognition*; typing exercises *production*. Knowing a word means being able to write it, not only to spot it. |
| **Forced Study phase before every quiz** | `Select → Study → Test` encourages encoding before retrieval, which measurably improves retention. |
| **Local Room database, not in-memory lists** | A personalised dictionary must survive app restarts; Room also gives compile-time-checked queries and automatic migrations. |
| **`difficulty` string reused as the group name** | Avoids a second table and a join; the "Dynamic Group System" then treats built-in and custom groups identically. |
| **Randomised pair colours in Tap to Match** | Colour becomes an extra memory anchor and makes correct/wrong feedback immediately legible. |
| **AlertDialog confirmations** | Group deletion is destructive; a confirmation step prevents accidental loss of an entire curated list. |
| **Auto-sizing Material Buttons** | Long academic vocabulary must never clip or wrap — this keeps the layout professional from small phones to tablets. |
| **Mute toggle on every screen** | Silences the app for easily distracted users without altering the device's system volume. |
| **Singleton `VocabManager` initialised in `LoadingActivity`** | Guarantees the database exists and seed data is present before any screen that reads it. |

---

## Testing

The project ships the standard instrumented/unit test scaffolding:

- `app/src/test/java/.../ExampleUnitTest.java` — plain JUnit 4.
- `app/src/androidTest/java/.../ExampleInstrumentedTest.java` — Espresso / AndroidJUnitRunner.

```bash
./gradlew test              # local unit tests
./gradlew connectedAndroidTest   # instrumented tests (device/emulator required)
```

**Manual testing performed** focused on the persistence layer and CRUD flows: seeding on a clean
install, adding/deleting individual words, creating and deleting custom groups, and verifying that
custom groups appear correctly in both quiz modes. Development and testing were carried out on
Android Studio with a **virtual device running Android 14 (API 34)** and `compileSdk = 35`.

---

## Known Limitations & Future Work

These are honest, deliberate boundaries of the current version:

- **`allowMainThreadQueries()` is enabled.** Simple for coursework, but it blocks the UI thread on large
  lists. *Next step:* move Room access onto an `ExecutorService` and expose `LiveData`/`Flow`.
- **No spaced-repetition scheduling.** Words are drawn uniformly at random. A Leitner-box / SRS model
  would target weak words far more efficiently.
- **Spelling answers are compared with `equalsIgnoreCase`.** No fuzzy matching, so a single typo counts
  as wrong; and the spelling pool is capped at 10 words per round.
- **English is matched by string, not by row id**, in the Tap-to-Match board — duplicate English words
  within one group could highlight together.
- **`fallbackToDestructiveMigration()`** wipes data on a schema change. A real migration strategy would
  be required before shipping an update.
- **No import/export.** A tutor cannot yet share a curated group with another device — CSV or JSON
  import/export is the highest-value next feature.
- **Only the two UI-test stubs exist.** Unit tests covering `VocabManager`'s quiz-size and shuffling
  rules would be a cheap, high-value addition.
- **Audio and images are packaged in the APK.** Moving them to a network or downloadable asset pack
  would reduce install size.

---

## Academic Context

Vocab Master was developed as the **Final Project** for the course:

| | |
| --- | --- |
| **Course** | Mobile Application Development (MAD) |
| **Course code / term** | CCIT 4059, 2025–2026 Semester 2 |
| **Project type** | Individual course project (Android application) |
| **Author** | Wong Chun Cheung |
| **Development platform** | Android Studio, Java |

Deliverables for the course also included a project documentation report, presentation slides, a video
demonstration, and the compiled APK.

### AI Tool Usage Declaration

In accordance with the course's declaration-of-use requirement, the following is stated openly:

- **Tool used:** Google **Gemini**.
- **Areas of use:** used as an assistant and teacher while developing the app — for example,
  **debugging** and learning **how to manage a database** (Room) inside the app.
- **AI-generated content:** the **default (seed) vocabulary lists** were generated with the assistance
  of AI, then reviewed before inclusion.

The application logic, feature design and UI implementation remain the author's own work.

---

## Acknowledgements

- [AndroidX](https://developer.android.com/jetpack/androidx) & [Material Components for Android](https://github.com/material-components/material-components-android)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Airbnb Lottie for Android](https://github.com/airbnb/lottie-android) — loading animations
- Course teaching team for the MAD project brief and guidance

---

## License

Released under the **MIT License** — see the [`LICENSE`](LICENSE) file for details.

```
Copyright (c) 2026 Wong Chun Cheung
```

> If you reuse this project, please keep the attribution and note that the bundled vocabulary and audio
> assets were produced for academic coursework.

---

<p align="center"><i>Start your learning journey today and become a Vocab Master! 🎓</i></p>
