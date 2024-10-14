import matplotlib.pyplot as plt
import numpy as np
from matplotlib.animation import FuncAnimation
import math
from filterpy.discrete_bayes import predict
from filterpy.discrete_bayes import update


'''
belief: list of initial probability of being in a certain state 
move_dist: the distance moved per iteration
p_correct: probability sensor reading is correct
p_under: probability sensor reading underestimates
p_over: probability sensor reading overestimates
'''
def predict_move(belief, move_dist, p_correct, p_under, p_over):
    n = len(belief)
    prior = np.zeros(n)
    for i in range(n):
        prior[i] = belief[i - move_dist] * p_correct + belief[i - move_dist - 1] * p_over + belief[i - move_dist + 1] * p_under
    return prior

def predict_move_convolution(prob_dist, offset, kernel):
    N = len(prob_dist)
    kN = len(kernel)
    width = math.floor(kN / 2)
    prior = np.zeros(N)
    for i in range(N):
        for k in range(kN):
            index = (i + (width-k) - offset) % N
            prior[i] += prob_dist[index] * kernel[k]
    return prior


def simulate_prediction(belief, p_correct, p_under, p_over):
    predicted_beliefs = []
    for _ in range(100):
        #belief = predict_move_convolution(belief, 1, [0.1, 0.8, 0.1])
        belief = predict_move(belief, 1, p_correct, p_over, p_under)
        predicted_beliefs.append(belief)


    fig, ax = plt.subplots()
    x = np.arange(len(belief))
    bar_container = ax.bar(x, predicted_beliefs[0])

    def update(step):
        for bar, height in zip(bar_container, predicted_beliefs[step]):
            bar.set_height(height)
        ax.set_title(f'Step {step}')

    animation = FuncAnimation(fig, update, frames=len(predicted_beliefs), repeat=False)
    plt.show()


'''
kernel: update probabilities applied on offset shift
measurements: sensor measurements
z_prob: probability sensor reading is correct
sensor_dist: distribution of sensor locations
n: number of sensor locations
'''
def discrete_bayes_sim(kernel, measurements, z_prob, sensor_dist, n):
    posterior = np.array([1/n] * n)
    priors, posteriors = [], []
    for _, z in enumerate(measurements):
        prior = predict(posterior, offset=1, kernel=kernel)
        priors.append(prior)

        try:
            scale = z_prob / (1.0 - z_prob)
        except ZeroDivisionError:
            scale = 1e8
        likelihood = np.ones(len(sensor_dist))
        likelihood[sensor_dist==z] *= scale #scale by sensor location

        posterior = update(likelihood, prior)
        posteriors.append(posterior)
    return priors, posteriors


def animate_discrete_bayes(priors, posteriors, num_locs):
    fig, ax = plt.subplots()
    x = np.arange(num_locs)
    bar_container = ax.bar(x, priors[0])

    def update(step):
        step -= 1
        i = step // 2
        
        if i % 2 == 0:
            cur = priors[i]
        else:
            cur = posteriors[i]

        for bar, height in zip(bar_container, cur):
            bar.set_height(height)
        ax.set_title(f'Step {step}')
    
    animation = FuncAnimation(fig, update, frames=len(priors), repeat=False)
    plt.show()

'''belief = [0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0]
p_correct, p_over, p_under = 0.8, 0.1, 0.1
simulate_prediction(belief, p_correct, p_under, p_over)'''


kernel = [.1, .8, .1]
z_prob = 1.0
hallway = np.array([1, 1, 0, 0, 0, 0, 0, 0, 1, 0])
zs = [hallway[i % len(hallway)] for i in range(50)]

priors, posteriors = discrete_bayes_sim(kernel, zs, z_prob, hallway, n=1)
animate_discrete_bayes(priors, posteriors, len(hallway))