# Budget Tracker App

[![Kotlin](https://img.shields.io/badge/Kotlin-100%25-orange)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-blue)](https://developer.android.com/jetpack/compose)
[![MVVM](https://img.shields.io/badge/Architecture-MVVM-brightgreen)](https://developer.android.com/jetpack/architecture)
[![Room](https://img.shields.io/badge/Room-Database-lightgrey)](https://developer.android.com/training/data-storage/room)
[![Hilt](https://img.shields.io/badge/Hilt-DI-purple)](https://developer.android.com/training/dependency-injection/hilt)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

---

## Overview

Budget Tracker is an Android app to track daily **income** and **expenses**.  
It features a clean and reactive UI with **Jetpack Compose**, persistent storage using **Room**, and follows **MVVM** architecture with **Hilt** for dependency injection.

---

## Features

- Add, delete, and view income and expense transactions  
- Automatically calculate current balance  
- Data persisted locally via Room  
- Reactive UI with StateFlow  
- Unit tests for ViewModel and Repository
- Pie chart visualization of Income vs Expenses  

---

## Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose  
- **Architecture:** MVVM  
- **Dependency Injection:** Hilt  
- **Database:** Room  
- **Asynchronous:** Kotlin Coroutines & Flow  
- **Testing:** JUnit, kotlinx-coroutines-test  

---

## Project Structure

## Project Structure

- `data/`  
  - `local/` → Room entities, DAO, Database  
  - `repository/` → Repository implementation  

- `domain/`  
  - `model/` → TransactionRecord, TransactionType  
  - `repository/` → Repository interface  

- `ui/`  
  - `BudgetTrackerScreen/` → HomeScreen, TransactionsList, ViewModel  
  - `components/` → Reusable composables (TransactionListItem, BudgetTopBar, etc.)  
  - `utils/` → Extensions (currency, date formatting)  

- `di/` → Hilt modules

---

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/githussein/BudgetTrackerChallenge.git

