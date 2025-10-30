package com.neurodose.app.utils.constant

/**
 * Constants used throughout the NeuroDose app.
 * 
 * Centralizing constants makes them easy to find and update.
 */

// Database constants
const val DATABASE_NAME = "neurodose_database"

// Supplement categories
const val CATEGORY_STIMULANT = "Stimulant"
const val CATEGORY_AMINO_ACID = "Amino Acid"
const val CATEGORY_ADAPTOGEN = "Adaptogen"
const val CATEGORY_NOOTROPIC = "Nootropic"

// Pharmacokinetic constants
const val HOURS_6 = 6L * 60 * 60 * 1000  // 6 hours in milliseconds
const val HOURS_12 = 12L * 60 * 60 * 1000  // 12 hours in milliseconds
const val HOURS_24 = 24L * 60 * 60 * 1000  // 24 hours in milliseconds
const val HOURS_48 = 48L * 60 * 60 * 1000  // 48 hours in milliseconds

// Default thresholds (mg/L)
const val DEFAULT_MIN_THRESHOLD = 0.5
const val DEFAULT_MAX_THRESHOLD = 2.0

// UI constants
const val ANIMATION_DURATION_MS = 300

