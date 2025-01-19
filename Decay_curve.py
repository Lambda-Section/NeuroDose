import numpy as np
import matplotlib.pyplot as plt

# Define parameters
half_life = 6  # Half-life in hours (e.g., caffeine)
dose = 200  # Initial dose in mg
time_hours = 24  # Time range to visualize (e.g., 24 hours)

# Calculate decay
time = np.linspace(0, time_hours, 1000)  # Time points (0 to 24 hours)
lambda_const = np.log(2) / half_life  # Elimination rate constant
concentration = dose * np.exp(-lambda_const * time)  # Stimulant levels

# Plotting
plt.figure(figsize=(10, 6))
plt.plot(time, concentration, label=f'Half-life: {half_life} hrs')
plt.axhline(y=dose / 2, color='r', linestyle='--', label='50% concentration (half-life)')
plt.title('Stimulant Decay Curve')
plt.xlabel('Time (hours)')
plt.ylabel('Concentration (mg)')
plt.legend()
plt.grid()
plt.show()
