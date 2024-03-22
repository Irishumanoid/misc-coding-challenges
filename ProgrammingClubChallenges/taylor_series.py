import matplotlib.pyplot as plt
import matplotlib.animation as animation
import numpy as np
import sympy as sp

counter = 2
def calculate_approximation(func: str, deg: int, center: int):
    expr = sp.sympify(func)
    num = counter - deg
    dydx = sp.sympify(str(sp.diff(expr/np.math.factorial(num+1))).replace("x", "(x-" + str(center)+")"))
    print(dydx)
    if deg > 0:
        return dydx + calculate_approximation(dydx, deg-1, center)
    return dydx


function = "sin(x)+x*cos(x)"
out = calculate_approximation(function, counter, 1)
print(out)
x = sp.symbols('x')
print(out.subs(x, 0).evalf())

fig, ax = plt.subplots()
x_inputs = np.linspace(0, 10, 100)
z = [sp.sympify(function).subs(x, v).evalf() for v in x_inputs]
z_approx = [out.subs(x, v).evalf() for v in x_inputs]

line1, = ax.plot(x_inputs, z, label="actual")
line2, = ax.plot(x_inputs, z_approx, label="approx")  
ax.set(xlim=[0, 10], ylim=[-4, 10])
ax.legend()

def update(frame):
    # update the line plot only with stuff in current frame
    for l in [line1, line2]:
        l.set_xdata(x_inputs[:frame])
    line1.set_ydata(z[:frame])
    line2.set_ydata(z_approx[:frame])
    return (line1, line2)

ani = animation.FuncAnimation(fig=fig, func=update, frames=100, interval=30)
plt.show()
