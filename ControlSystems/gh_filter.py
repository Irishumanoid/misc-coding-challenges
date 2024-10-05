import matplotlib.pyplot as plt

#g to update measurement, h to update measurement over time
def g_h_filter(data, x0, dx, g, h, dt):
    state = x0
    rate = dx
    predictions = []
    for d in data:
        state += rate*dt #update estimate
        predictions.append(state)
        error = d - state # measured_val - predicted_val
        rate += h*(error/dt) #rate + scale_factor * rate_error
        state += g*error # state + scale_factor * (measured_val - predicted_val), g higher if measured_val more accurate
    
    return predictions


weights = [158.0, 164.2, 160.3, 159.9, 162.1, 164.6, 
           169.6, 167.4, 166.4, 171.0, 171.2, 172.6]
data = g_h_filter(data=weights, x0=160., dx=1., g=6./10, h=2./3, dt=1.)
for i in range(len(data)):
    guess = weights[i]
    approx = data[i]
    print(f'error is ${round(abs(guess - approx), 2)} for guess of {guess} and filtered res of {round(approx, 2)}')

plt.plot([i for i in range(len(weights))], data, label="filter output")
plt.scatter([i for i in range(len(weights))], weights, label="raw data")
plt.legend()
plt.show()