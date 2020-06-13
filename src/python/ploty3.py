import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

k1 = 2.0
b1 = 6.0
k2 = -1.5
b2 = 13.5
x1 = np.linspace(-3, 0, 100)
x2 = np.linspace(1, 2, 100)
x3 = np.linspace(1, 9, 100)
y1 = (k1*x1 + b1) / 12.0
# y2 = np.full(100, 12.0)
y3 = (k2*x3 + b2) / 12.0
y4 = 0.5*x1 + 0.5

# compose plot
plt.plot(x1, y1, c="g", label="инкубационный период") # sin(x)/x
plt.plot(x3, y3, c="r", label="симп. период болезни")
plt.plot(x1, y1, c=(0.22, 0.99, 0.86), label="бессимп. период болезни")
plt.scatter(-3, 0, c="k")
plt.scatter(9, 0, c="k")
plt.scatter(-1.5, 3.0 / 12.0, c="k")
plt.scatter(5, 6.0 / 12.0, c="k")
plt.title("Вирусная нагрузка в зависимости от отсчета с начала инкубационного периода")
plt.ylabel('Влияние силы инфекции')
plt.xlabel('Время, проведенное в инфицированном состоянии, дни')
plt.legend()
plt.show()

b1 = 2.61
b2 = 2.61
b3 = 3.17
b4 = 5.11
b5 = 4.69
b6 = 3.89
b7 = 3.77
x = np.linspace(0, 1, 100)
y1 = 2 / (1 + np.exp(b1 * x))
# y2 = 2 / (1 + np.exp(b2 * x))
y3 = 2 / (1 + np.exp(b3 * x))
y4 = 2 / (1 + np.exp(b4 * x))
y5 = 2 / (1 + np.exp(b5 * x))
y6 = 2 / (1 + np.exp(b6 * x))
y7 = 2 / (1 + np.exp(b7 * x))

plt.plot(x, y1, c='r', label="FluA, FluB")
# plt.plot(x, y2, c='b', label="FluB")
plt.plot(x, y3, c="g", label="RV")
plt.plot(x, y4, c='m', label="RSV")
plt.plot(x, y5, c='y', label="AdV")
plt.plot(x, y6, c='k', label="PIV")
plt.plot(x, y7, c='c', label="CoV")
plt.title("Восприимчивость в зависимости от уровня иммуноглобулина и этиологии")
plt.ylabel('Влияние восприимчивости')
plt.xlabel('Нормализованный суммарный уровень иммуноглобулина')
plt.legend()
plt.show()

# x = np.linspace(0, 1, 100)
# y = x
#
# # compose plot
# plt.plot(x, y, c="r")
# plt.title("Сила инфекции в зависимости от уровня вирусной нагрузки")
# plt.ylabel('Сила инфекции')
# plt.xlabel('Уровень вирусной нагрузки')
# plt.show()


x = np.linspace(0, 24, 100)
y = 1 / (1 + np.exp(-x + 7.05))

# compose plot
plt.plot(x, y, c="r")
plt.title("Влияние продолжительности контакта на вероятность инфицирования")
plt.ylabel('Влияние продолжительности контакта')
plt.xlabel('Продолжительность, ч')
plt.show()

k1 = -0.8
k2 = -0.8
k3 = -0.05
k4 = -0.64
k5 = -0.2
# k6 = -0.05
k7 = -0.8
b = 1.0
x = np.linspace(0, 1, 100)
y1 = k1 * x + b
y2 = k2 * x + b
y3 = k3 * x + b
y4 = k4 * x + b
y5 = k5 * x + b
# y6 = k6 * x + b
y7 = k7 * x + b

plt.plot(x, y1, c='r', label="FluA, FluB, CoV")
# plt.plot(x, y2, c='b', label="FluB")
plt.plot(x, y3, c="g", label="RV, PIV")
plt.plot(x, y4, c='m', label="RSV")
plt.plot(x, y5, c='y', label="AdV")
# plt.plot(x, y6, c='k', label="PIV")
# plt.plot(x, y7, c='c', label="CoV")
plt.title("Влияние уровня температуры в зависимости от этиологии")
plt.ylabel('Влияние температуры воздуха')
plt.xlabel('Нормализованная температура воздуха')
plt.legend()
plt.show()

