# Pharmacokinetic Modeling for NeuroDose

## Overview

This document explains the mathematical foundation for calculating supplement concentrations in NeuroDose. Understanding these models is critical for accurate implementation.

---

## Fundamental Concepts

### ADME Process

Pharmacokinetics describes how the body processes substances through four phases:

1. **Absorption (A)**: Supplement enters bloodstream from gut
2. **Distribution (D)**: Spreads throughout body tissues
3. **Metabolism (M)**: Liver breaks down the compound
4. **Elimination (E)**: Kidneys remove metabolites

---

## One-Compartment Model

### Why This Model?

For NeuroDose MVP, we use the **one-compartment model** because:
- Simpler to implement and understand
- Sufficient accuracy for most nootropics
- Well-documented in literature
- Computationally efficient for real-time updates

### Assumptions:
- Body acts as single, well-mixed compartment
- First-order kinetics (rate proportional to concentration)
- Instantaneous distribution after absorption
- Linear pharmacokinetics (dose-proportional)

---

## Mathematical Formulas

### 1. Single Dose Concentration

For a single oral dose with first-order absorption and elimination:

```
C(t) = (F × D × ka) / (Vd × (ka - ke)) × (e^(-ke×t) - e^(-ka×t))
```

**Where:**
- `C(t)` = Plasma concentration at time t (mg/L)
- `F` = Bioavailability (fraction absorbed, 0-1)
- `D` = Dose administered (mg)
- `ka` = Absorption rate constant (1/hour)
- `ke` = Elimination rate constant (1/hour)
- `Vd` = Volume of distribution (L)
- `t` = Time since dose (hours)
- `e` = Euler's number (2.71828...)

### 2. Elimination Rate Constant

```
ke = 0.693 / t½
```

**Where:**
- `t½` = Half-life (hours)
- `0.693` = ln(2)

**Example**: Caffeine has t½ = 5 hours
```
ke = 0.693 / 5 = 0.1386 per hour
```

### 3. Absorption Rate Constant

For most oral supplements, absorption is faster than elimination:

```
ka ≈ 3 × ke  (rule of thumb)
```

Or use empirical values from literature.

### 4. Volume of Distribution

Relates dose to concentration:

```
Vd = D / C₀
```

For simplicity, we can use population averages:
- **Caffeine**: ~0.6 L/kg body weight
- **L-Theanine**: ~0.7 L/kg
- **Default**: 0.6 L/kg (assume 70kg person = 42L)

---

## Multi-Dose Calculations

### Superposition Principle

When multiple doses are taken, concentrations add linearly:

```
C_total(t) = Σ C_i(t - t_i)
```

**Where:**
- `C_i` = Concentration from dose i
- `t_i` = Time when dose i was taken
- Sum over all doses where `t > t_i`

### Implementation Strategy:

```kotlin
fun calculateTotalConcentration(
    intakeLogs: List<IntakeLog>,
    currentTime: Instant,
    supplement: Supplement
): Double {
    return intakeLogs
        .filter { it.timestamp <= currentTime }
        .sumOf { log ->
            val timeSinceDose = currentTime - log.timestamp // hours
            calculateSingleDoseConcentration(
                dose = log.doseMg,
                timeSinceDose = timeSinceDose,
                supplement = supplement
            )
        }
}
```

---

## Example Calculations

### Example 1: Caffeine (100mg at t=0)

**Given:**
- Dose (D) = 100 mg
- Bioavailability (F) = 0.99
- Half-life (t½) = 5 hours
- Volume of distribution (Vd) = 42 L (70kg person)
- ke = 0.693 / 5 = 0.1386 /hr
- ka = 3 × 0.1386 = 0.4158 /hr

**Calculate C(t) at t = 2 hours:**

```
C(2) = (0.99 × 100 × 0.4158) / (42 × (0.4158 - 0.1386)) × (e^(-0.1386×2) - e^(-0.4158×2))

C(2) = (41.16) / (42 × 0.2772) × (e^(-0.2772) - e^(-0.8316))

C(2) = (41.16) / (11.64) × (0.7580 - 0.4354)

C(2) = 3.536 × 0.3226

C(2) ≈ 1.14 mg/L
```

**Peak concentration** occurs at:
```
t_max = ln(ka/ke) / (ka - ke)
t_max = ln(0.4158/0.1386) / (0.4158 - 0.1386)
t_max ≈ 1.2 hours
```

### Example 2: Multiple Doses

**Scenario**: 
- 100mg caffeine at t=0
- 100mg caffeine at t=4 hours
- Calculate concentration at t=6 hours

```
C_total(6) = C_dose1(6) + C_dose2(2)

C_dose1(6) = ... (calculate with t=6)
C_dose2(2) = ... (calculate with t=2)

C_total(6) = C_dose1(6) + C_dose2(2)
```

---

## Supplement-Specific Parameters

### Caffeine
```kotlin
Supplement(
    name = "Caffeine",
    halfLife = 5.0,              // hours
    bioavailability = 0.99,      // 99%
    absorptionRate = 0.42,       // per hour
    volumeOfDistribution = 0.6   // L/kg
)
```

### L-Theanine
```kotlin
Supplement(
    name = "L-Theanine",
    halfLife = 3.0,              // hours (approximate)
    bioavailability = 1.0,       // ~100%
    absorptionRate = 0.69,       // per hour
    volumeOfDistribution = 0.7   // L/kg
)
```

### Ginseng (Ginsenosides)
```kotlin
Supplement(
    name = "Ginseng",
    halfLife = 8.0,              // hours (varies by ginsenoside)
    bioavailability = 0.16,      // 16% (low)
    absorptionRate = 0.26,       // per hour
    volumeOfDistribution = 1.2   // L/kg (high distribution)
)
```

### Rhodiola Rosea (Salidroside)
```kotlin
Supplement(
    name = "Rhodiola",
    halfLife = 4.0,              // hours
    bioavailability = 0.52,      // 52%
    absorptionRate = 0.52,       // per hour
    volumeOfDistribution = 0.8   // L/kg
)
```

---

## Implementation Guidelines

### 1. Calculation Frequency

**Real-time updates**: Recalculate every 1-5 minutes
- Use Kotlin Flow with periodic emissions
- Cache results to avoid redundant calculations

```kotlin
flow {
    while (true) {
        val concentration = calculateCurrentConcentration()
        emit(concentration)
        delay(60_000) // 1 minute
    }
}
```

### 2. Time Range for Charts

**Recommended ranges:**
- **6 hours**: Recent intake, high detail
- **12 hours**: Half-day view
- **24 hours**: Full day cycle
- **48 hours**: Multi-day accumulation

### 3. Precision

**Floating-point precision:**
- Use `Double` for all calculations
- Round display values to 2 decimal places
- Store raw values in database (no rounding)

### 4. Edge Cases

**Handle these scenarios:**
- Zero dose (return 0.0)
- Negative time (return 0.0)
- Very old doses (concentration < 0.01 mg/L, ignore)
- Division by zero (ka = ke, use limit formula)

---

## Limitations & Disclaimers

### Model Limitations:

1. **Individual Variability**: 
   - Actual pharmacokinetics vary by person (genetics, age, health)
   - Our model uses population averages

2. **Simplified Absorption**:
   - Assumes first-order absorption
   - Ignores food effects, gastric emptying

3. **No Metabolism Modeling**:
   - Doesn't account for active metabolites
   - Assumes linear elimination

4. **Single Compartment**:
   - Ignores tissue distribution dynamics
   - Not suitable for all compounds

### User Disclaimer:

**Must display in app:**
> "NeuroDose uses simplified pharmacokinetic models for educational purposes. 
> Actual concentrations may vary significantly based on individual factors. 
> This app is not medical advice. Consult healthcare professionals for 
> supplement guidance."

---

## Validation Strategy

### Testing Approach:

1. **Unit Tests**: Test formulas with known values
2. **Reference Comparison**: Compare to published PK curves
3. **Boundary Testing**: Test edge cases (zero, infinity, negative)
4. **Integration Testing**: Multi-dose scenarios

### Example Test:

```kotlin
@Test
fun `caffeine concentration at 2 hours matches expected value`() {
    val supplement = Supplement(
        name = "Caffeine",
        halfLife = 5.0,
        bioavailability = 0.99,
        absorptionRate = 0.4158,
        volumeOfDistribution = 42.0
    )
    
    val concentration = calculateConcentration(
        dose = 100.0,
        timeSinceDose = 2.0,
        supplement = supplement
    )
    
    // Expected: ~1.14 mg/L (±5% tolerance)
    assertEquals(1.14, concentration, delta = 0.06)
}
```

---

## References

### Scientific Literature:

1. **Caffeine Pharmacokinetics**:
   - Blanchard J, Sawers SJ. "The absolute bioavailability of caffeine in man." 
     Eur J Clin Pharmacol. 1983;24(1):93-8.

2. **L-Theanine**:
   - Unno T, et al. "Stress-reducing function of matcha green tea in animal 
     experiments and clinical trials." Nutrients. 2019;11(10):2362.

3. **Pharmacokinetic Modeling**:
   - Rowland M, Tozer TN. "Clinical Pharmacokinetics and Pharmacodynamics: 
     Concepts and Applications." 4th ed. Lippincott Williams & Wilkins; 2010.

### Online Resources:

- PubChem: https://pubchem.ncbi.nlm.nih.gov/
- DrugBank: https://go.drugbank.com/
- Examine.com: https://examine.com/ (supplement database)

---

## Future Enhancements

### Advanced Models (Post-MVP):

1. **Two-Compartment Model**: 
   - Central + peripheral compartments
   - Better for widely distributed compounds

2. **Michaelis-Menten Kinetics**:
   - Non-linear elimination (saturable metabolism)
   - Important for high doses

3. **Transit Compartment Absorption**:
   - Models delayed/prolonged absorption
   - Better for extended-release formulations

4. **Population Pharmacokinetics**:
   - Adjust for user weight, age, sex
   - Personalized parameters

5. **Metabolite Tracking**:
   - Track active metabolites (e.g., paraxanthine from caffeine)
   - More complete picture

---

*Last Updated: 2025-10-26*

