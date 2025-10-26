# NeuroDose Android Application - Development Roadmap

## Technology Stack Decision

### Core Technologies
- **Language**: Kotlin (100%)
- **UI Framework**: Jetpack Compose (Modern declarative UI)
- **Architecture**: MVVM (Model-View-ViewModel) with Repository Pattern
- **Dependency Injection**: Hilt (Dagger-based)
- **Asynchronous**: Kotlin Coroutines + Flow
- **Local Database**: Room (SQLite wrapper)
- **Cloud Backend**: Supabase (Optional, opt-in)
- **Charts/Visualization**: Vico or Compose Charts
- **Navigation**: Jetpack Navigation Compose

### Why Native Android?
1. **Performance**: Real-time pharmacokinetic calculations require efficiency
2. **Native APIs**: Direct access to notifications, background services, health integrations
3. **Best UX**: Polished Android experience with Material Design 3
4. **Maintainability**: Full Google ecosystem support
5. **Future-Proof**: Jetpack Compose is Android's future

---

## Development Phases

### Phase 1: Project Setup & Foundation (Week 1)

**Objective**: Create a solid foundation with modern Android architecture

#### Tasks:
- [ ] Initialize Android Studio project (Kotlin + Compose)
- [ ] Configure build.gradle with dependencies:
  - Jetpack Compose BOM
  - Room Database
  - Hilt for DI
  - Kotlin Coroutines
  - Navigation Compose
  - Material Design 3
- [ ] Set up project structure (modular architecture):
  ```
  app/
  ├── data/          # Data layer (Room, repositories)
  ├── domain/        # Business logic (use cases, models)
  ├── ui/            # Presentation layer (Compose screens, ViewModels)
  └── utils/         # Helpers, extensions
  ```
- [ ] Configure Hilt for dependency injection
- [ ] Set up version catalog (libs.versions.toml)
- [ ] Create base theme with Material Design 3
- [ ] Implement dark/light mode support

**Deliverable**: Empty app that launches with proper architecture

---

### Phase 2: Core Data Layer (Week 2)

**Objective**: Build the data foundation for supplement tracking

#### Database Schema (Room):

**Entities**:
1. **Supplement** - Library of compounds
   - id, name, category, half_life, bioavailability, absorption_rate, notes
2. **IntakeLog** - User's intake history
   - id, supplement_id, dose_mg, timestamp, notes
3. **UserThreshold** - Personalized limits
   - id, supplement_id, min_threshold, max_threshold, alert_enabled
4. **CircadianProfile** - Sleep-wake cycle
   - id, sleep_time, wake_time, timezone

#### Tasks:
- [ ] Define Room entities with proper annotations
- [ ] Create DAOs (Data Access Objects) for each entity
- [ ] Build Room database with migrations strategy
- [ ] Implement Repository pattern for data access
- [ ] Pre-populate database with common supplements:
  - Caffeine (half-life: 5h, bioavailability: 99%)
  - L-Theanine (half-life: 3h, bioavailability: ~100%)
  - Ginseng (half-life: varies, bioavailability: ~16%)
  - Rhodiola, Ashwagandha, etc.
- [ ] Create data models (domain layer)
- [ ] Write unit tests for repositories

**Deliverable**: Functional data layer with pre-populated supplement library

---

### Phase 3: Pharmacokinetic Engine (Week 3)

**Objective**: Implement the mathematical core for concentration calculations

#### Pharmacokinetic Model:
Using **One-Compartment Model** with first-order kinetics:

```
C(t) = (F × D × ka) / (V × (ka - ke)) × (e^(-ke×t) - e^(-ka×t))

Where:
- C(t) = Concentration at time t
- F = Bioavailability (0-1)
- D = Dose (mg)
- ka = Absorption rate constant
- ke = Elimination rate constant (0.693 / half-life)
- V = Volume of distribution
- t = Time since intake
```

#### Tasks:
- [ ] Create `PharmacokineticCalculator` class
- [ ] Implement concentration calculation for single dose
- [ ] Implement multi-dose accumulation (superposition principle)
- [ ] Create `ConcentrationTimeline` data class
- [ ] Build use case: `CalculateConcentrationUseCase`
- [ ] Add support for different absorption models (immediate, delayed)
- [ ] Optimize calculations for real-time updates
- [ ] Write comprehensive unit tests with known values
- [ ] Document mathematical formulas in code comments

**Deliverable**: Tested calculation engine that produces accurate concentration curves

---

### Phase 4: UI - Intake Logging (Week 4)

**Objective**: Allow users to log supplement intake

#### Screens:
1. **Home Screen** - Dashboard with quick actions
2. **Log Intake Screen** - Form to add new intake
3. **Supplement Library Screen** - Browse available supplements
4. **Intake History Screen** - List of past intakes

#### Tasks:
- [ ] Design UI mockups (Figma or sketch)
- [ ] Implement Navigation graph
- [ ] Create HomeScreen composable
- [ ] Build LogIntakeScreen with:
  - Supplement selector (dropdown/search)
  - Dose input (number field with unit)
  - Time picker (default: now)
  - Notes field (optional)
  - Submit button
- [ ] Create SupplementLibraryScreen (LazyColumn)
- [ ] Build IntakeHistoryScreen with:
  - Grouped by date
  - Swipe-to-delete
  - Edit functionality
- [ ] Implement ViewModels for each screen
- [ ] Add form validation
- [ ] Create reusable UI components

**Deliverable**: Functional app where users can log and view supplement intake

---

### Phase 5: UI - Visualization (Week 5-6)

**Objective**: Display real-time concentration curves

#### Visualization Components:
1. **Concentration Chart** - Line graph showing levels over time
2. **Multi-Compound Overlay** - Multiple lines on same chart
3. **Time Range Selector** - 6h, 12h, 24h, 48h views
4. **Current Level Indicator** - Real-time concentration value

#### Tasks:
- [ ] Evaluate charting libraries (Vico vs Compose Charts)
- [ ] Implement `ConcentrationChartScreen`
- [ ] Create chart composable with:
  - X-axis: Time (hours)
  - Y-axis: Concentration (mg/L or custom unit)
  - Multiple series support
  - Smooth curves (interpolation)
  - Touch interactions (zoom, pan)
- [ ] Add legend for multiple compounds
- [ ] Implement real-time updates (Flow-based)
- [ ] Create time range selector UI
- [ ] Add current concentration badges
- [ ] Optimize rendering performance
- [ ] Add loading states and error handling
- [ ] Implement chart customization (colors, line styles)

**Deliverable**: Beautiful, interactive concentration visualization

---

### Phase 6: Circadian Integration (Week 7)

**Objective**: Align supplement tracking with sleep-wake cycle

#### Features:
1. **Circadian Profile Setup** - User inputs sleep/wake times
2. **Visual Overlay** - Shaded regions on chart (sleep = dark, wake = light)
3. **Optimal Timing Suggestions** - Recommend intake times

#### Tasks:
- [ ] Create CircadianProfileScreen for setup
- [ ] Implement time picker for sleep/wake times
- [ ] Calculate circadian phases (morning, afternoon, evening, night)
- [ ] Add circadian overlay to concentration chart
- [ ] Create `OptimalTimingCalculator` use case
- [ ] Display timing recommendations on HomeScreen
- [ ] Add circadian rhythm visualization (separate chart)
- [ ] Implement timezone handling
- [ ] Write tests for circadian calculations

**Deliverable**: Circadian-aware supplement tracking

---

### Phase 7: Notifications & Alerts (Week 8)

**Objective**: Notify users when thresholds are exceeded

#### Notification Types:
1. **Threshold Alerts** - Too high/low concentration
2. **Intake Reminders** - Scheduled supplement reminders
3. **Optimal Window** - Best time to take next dose

#### Tasks:
- [ ] Set up notification channels (Android 8+)
- [ ] Create `NotificationManager` wrapper
- [ ] Implement threshold monitoring service
- [ ] Use WorkManager for background checks
- [ ] Create notification UI (expandable, actions)
- [ ] Add notification preferences screen
- [ ] Implement "Snooze" and "Dismiss" actions
- [ ] Add notification history
- [ ] Test notification delivery reliability
- [ ] Handle notification permissions (Android 13+)

**Deliverable**: Smart notification system for threshold management

---

### Phase 8: Synergistic Analysis (Week 9-10)

**Objective**: Analyze compound interactions

#### Interaction Types:
1. **Synergistic** - Enhanced effects (e.g., Caffeine + L-Theanine)
2. **Antagonistic** - Reduced effects
3. **Additive** - Combined effects
4. **Neutral** - No interaction

#### Tasks:
- [ ] Create interaction database (Room entity)
- [ ] Pre-populate known interactions:
  - Caffeine + L-Theanine (synergistic)
  - Caffeine + Adenosine (antagonistic)
  - Multiple stimulants (additive)
- [ ] Implement `SynergyCalculator` use case
- [ ] Create interaction scoring algorithm (0-100)
- [ ] Build InteractionAnalysisScreen
- [ ] Display interaction warnings on LogIntakeScreen
- [ ] Add interaction badges to HomeScreen
- [ ] Create detailed interaction info cards
- [ ] Write tests for interaction logic

**Deliverable**: Intelligent interaction analysis system

---

### Phase 9: Cloud Sync (Optional) (Week 11)

**Objective**: Enable backup and multi-device synchronization

#### Supabase Integration:
1. **Authentication** - Email/password or anonymous
2. **Database Sync** - PostgreSQL backend
3. **Real-time Updates** - Cross-device sync

#### Tasks:
- [ ] Set up Supabase project
- [ ] Create database schema (mirrors Room)
- [ ] Implement authentication flow
- [ ] Create `SyncRepository`
- [ ] Build sync logic (conflict resolution)
- [ ] Use WorkManager for background sync
- [ ] Add sync status indicators
- [ ] Create settings screen for sync preferences
- [ ] Implement manual backup/restore
- [ ] Add data export (JSON/CSV)
- [ ] Test offline-first behavior
- [ ] Add privacy policy and data handling disclosure

**Deliverable**: Optional cloud backup with privacy controls

---

### Phase 10: Polish & Testing (Week 12+)

**Objective**: Refine UX and ensure quality

#### Tasks:
- [ ] Comprehensive UI/UX review
- [ ] Add animations and transitions
- [ ] Implement empty states
- [ ] Create onboarding flow for new users
- [ ] Add app shortcuts (long-press icon)
- [ ] Create home screen widgets
- [ ] Optimize app performance (profiling)
- [ ] Write integration tests
- [ ] Conduct accessibility audit (TalkBack, large text)
- [ ] Add analytics (privacy-friendly, opt-in)
- [ ] Create app icon and splash screen
- [ ] Write user documentation
- [ ] Beta testing with real users
- [ ] Bug fixes and refinements
- [ ] Prepare for Play Store release

**Deliverable**: Production-ready Android application

---

## Technical Architecture

### Layers:

```
┌─────────────────────────────────────┐
│         UI Layer (Compose)          │
│  Screens, ViewModels, UI Components │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│       Domain Layer (Use Cases)      │
│  Business Logic, Calculations       │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│      Data Layer (Repositories)      │
│  Room, Supabase, Data Sources       │
└─────────────────────────────────────┘
```

### Key Patterns:
- **MVVM**: Separation of concerns
- **Repository Pattern**: Abstract data sources
- **Use Cases**: Encapsulate business logic
- **Dependency Injection**: Hilt for testability
- **Reactive Streams**: Flow for data updates

---

## Data Models

### Core Domain Models:

```kotlin
data class Supplement(
    val id: String,
    val name: String,
    val category: SupplementCategory,
    val halfLife: Double,        // hours
    val bioavailability: Double, // 0.0 - 1.0
    val absorptionRate: Double,  // per hour
    val volumeOfDistribution: Double // L/kg
)

data class IntakeLog(
    val id: String,
    val supplementId: String,
    val doseMg: Double,
    val timestamp: Instant,
    val notes: String?
)

data class ConcentrationPoint(
    val timestamp: Instant,
    val concentrationMgL: Double
)

data class CircadianProfile(
    val sleepTime: LocalTime,
    val wakeTime: LocalTime,
    val timezone: ZoneId
)
```

---

## Success Metrics

### MVP Success Criteria:
- [ ] Users can log supplement intake in < 10 seconds
- [ ] Concentration chart updates in real-time (< 100ms)
- [ ] App works 100% offline
- [ ] No crashes in normal usage
- [ ] Pharmacokinetic calculations accurate within 5% of reference values

### Long-term Goals:
- 1,000+ active users
- 4.5+ star rating on Play Store
- < 1% crash rate
- Positive user feedback on accuracy and usefulness

---

## Risk Mitigation

### Technical Risks:
1. **Performance**: Real-time calculations may be slow
   - *Mitigation*: Cache calculated values, optimize algorithms
2. **Accuracy**: Pharmacokinetic models are simplified
   - *Mitigation*: Clearly communicate limitations, cite sources
3. **Battery Drain**: Background monitoring
   - *Mitigation*: Use WorkManager efficiently, allow user control

### Product Risks:
1. **Medical Liability**: Users may rely on app for health decisions
   - *Mitigation*: Clear disclaimers, not medical advice
2. **Data Privacy**: Sensitive health data
   - *Mitigation*: Local-first, encryption, transparent privacy policy

---

## Next Steps

1. **Review this roadmap** and provide feedback
2. **Prioritize features** - Confirm Phase 1-5 as MVP
3. **Set up development environment** - Android Studio, Git
4. **Begin Phase 1** - Initialize project with proper architecture

---

*Last Updated: 2025-10-26*

