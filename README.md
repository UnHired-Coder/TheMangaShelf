# 📚 TheMangaShelf

**TheMangaShelf** is a beautifully designed Android application that helps manga enthusiasts explore, sort, and keep track of their favorite manga titles. It supports offline caching, sorting/filtering, and a seamless reading experience — all wrapped in a clean, responsive UI.

---



| | | |
|--|--|--|
| ![screenshot_1742751902759-min-portrait](https://github.com/user-attachments/assets/3d419f0b-6f0a-46cc-9b95-03c1129b3156) | ![screenshot_1742751965122-min-portrait](https://github.com/user-attachments/assets/b6759e58-c0d0-4fe1-b4d2-034286fdbf04) | ![screenshot_1742751918705-min-portrait](https://github.com/user-attachments/assets/511f42de-d049-4b5e-9a97-80ab86c4a006)| 


## 📸 App Functions & Features 

> 📌 Features of the app!

| | | |
|--|--|--|
| Manga List Page ![screenshot_1742751918705-min-portrait](https://github.com/user-attachments/assets/87de6e42-ff25-43f6-ae70-efd53cd104bb) | Display Manga Page & Mark Read ![screenshot_1742751957423-min-portrait](https://github.com/user-attachments/assets/c34c3f8c-58de-4cad-8b86-b561822c0bb3) | Sort Manga by [Score, Popularity]  ![screenshot_1742751938371-min-portrait](https://github.com/user-attachments/assets/e9ba521c-e2ac-4a6a-b77e-334fb12fa91f) |
| Favourite Manga ![screenshot_1742751970872-min-portrait](https://github.com/user-attachments/assets/177270fb-8898-4203-afd8-caab55162bd7) | Browse by Date ![screenshot_1742751928722-min-portrait](https://github.com/user-attachments/assets/1095ba20-9e28-4a98-afb9-e49c6273178a) |  Error Handling, Persistent Storege ![capture_1742752113213-min-portrait](https://github.com/user-attachments/assets/e906df8b-dea6-4fe3-ba49-4803342d740d) |




---
## 🚀 Features


### 🏠 Manga List Page (Home Screen)

- Fetches manga data from remote API: `https://jsonkeeper.com/b/KEJO`
- Displays:
  - Title
  - Cover Image
  - Score
  - Popularity
  - Year of Publication
- Offline caching using Room DB
- Grouped by publication year with horizontally scrollable year tabs
- Auto-scroll and auto-tab-switch based on scroll position
- Sort by:
  - 🔼/🔽 Score
  - 🔼/🔽 Popularity
- Dynamic UI updates without year tabs when sorted
- Mark/unmark manga as Favorite
- Responsive layouts for all screen sizes and orientations
- Error handling:
  - Network failure
  - Empty data

### 📄 Manga Detail Page

- Displays full manga details:
  - Title
  - Cover Image
  - Score
  - Popularity
  - Published Chapter Date (formatted)
  - Category
- Mark as Favorite / Unfavorite
- Mark as Read
- Real-time sync of favorite status with list page

---

## 🧱 Architecture & Libraries Used

The app follows modular architecture with best practices and performance optimizations:

### 📦 Core Libraries

| Library | Purpose |
|--------|---------|
| **Koin** | Dependency Injection |
| **Glide** | Image Loading |
| **Lottie** | Delightful Animation |
| **Room** | Local Data Caching |
| **Retrofit** | Networking |
| **Kotlinx Serialization** | JSON Parsing |
| **Coroutines** | Asynchronous Programming |
| **Jetpack Compose** | Declarative UI |
| **Navigation Compose** | Navigation Management |

---

## 📲 Installation Instructions

1. **Clone the Repository**

```bash
git clone https://github.com/UnHired-Coder/TheMangaShelf.git
cd TheMangaShelf
```

2. **Open the Project**

Open the project in **Android Studio**.

3. **Build the Project**

Let Gradle sync and build the project. Make sure you are connected to the internet for the first run.

4. **Run the App**

Choose an emulator or physical device and click ▶️ Run.

---

## ✅ Scope & Quality

- Clean, modular architecture [MVVM]
- Offline support
- Pagination-ready structure
- Beautiful and responsive UI
- Real-time updates on favorite/read status
- Error handling and retry mechanisms
- Smooth transitions and animations

---

## 📜 License

This project is for demonstration and educational purposes.
