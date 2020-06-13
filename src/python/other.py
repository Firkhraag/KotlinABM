import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# df = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\HomeCoordinates.csv', header=None)
# okato = '45277598000'
# df2 = df[df.iloc[:, 3] == okato]
# df2.iloc[:, 1:3].to_csv(r'C:\Users\sigla\Desktop\MasterWork\%s.csv' % okato, index=False, header=None)


# # 5 day - incubation, 8 day - max, 14 day - 0
# viral_load = 8.0
# max_viral_load = 10.0
# k1 = 2.0
# b1 = 8.0
# k2 = -0.333
# b2 = 10.33
# k3 = -1.143
# b3 = 16
# x1 = np.linspace(-4, 1, 100)
# x2 = np.linspace(1, 7, 100)
# x3 = np.linspace(7, 14, 100)
# y1 = (k1*x1 + b1) / max_viral_load
# y2 = (k2*x2 + b2) / max_viral_load
# y3 = (k3*x3 + b3) / max_viral_load
#
# # compose plot
# plt.plot(x1, y1, c="g", label="инкубационный период")
# plt.plot(x2, y2, c="r", label="период болезни")
# plt.plot(x3, y3, c="r")
# # plt.scatter(-3, 0, c="k")
# # plt.scatter(9, 0, c="k")
# # plt.scatter(-1.5, 3.0 / 12.0, c="k")
# # plt.scatter(5, 6.0 / 12.0, c="k")
# plt.title("Вирусная нагрузка в зависимости от отсчета с начала инкубационного периода")
# plt.ylabel('Влияние силы инфекции')
# plt.xlabel('Время, проведенное в инфицированном состоянии, дни')
# plt.legend()
# plt.xticks(np.arange(-4, 15, 3.0))
# plt.show()


# b1 = 2.55
# x = np.linspace(0, 90, 100)
# y = 2 / (1 + np.exp(0.05 * x))
# y2 = 2 / (1 + np.exp(0.1 * x))
#
#
# plt.plot(x, y, c='b', label="без заболеваний")
# plt.plot(x, y2, c='r', label="с заболеваниями")
# plt.title("Вероятность бессимптомной формы")
# plt.ylabel('Вероятность')
# plt.xlabel('Возраст, лет')
# plt.legend()
# plt.show()



# x = np.linspace(0, 24, 100)
# y = 1 / (1 + np.exp(-x + 5.0))
#
# # compose plot
# plt.plot(x, y, c="r")
# plt.title("Влияние продолжительности контакта на вероятность инфицирования")
# plt.ylabel('Влияние продолжительности контакта')
# plt.xlabel('Продолжительность, ч')
# plt.show()

# # Age difference
# ages = np.array([1.0, 1.6, 4.8, 11.6, 13.3, 20.4, 33.2, 6.5, 3.3, 2.7, 1.0, 0.3, 0.3])
# ages = ages * 0.01
# x = np.arange(0, 13, 1)
# width = 0.75
# plt.bar(x, ages, width, color=(0.2, 0.2, 0.9, 0.75))
# # plt.bar(x+width, igM, width, color=(0.4, 0.9, 0.4, 0.8), label='IgM')
# # plt.bar(x+2*width, igA, width, color=(0.2, 0.4, 0.9, 0.8), label='IgA')
# plt.xticks(x, ["M20+", "M15-19", "M10-14", "M6-9", "M4-5", "M2-3", "1", "F2-3", "F4-5", "F6-9", "F10-14", "F15-19", "F20+"])
# plt.ylabel('Вероятность')
# plt.xlabel('Разница в возрасте, лет')
# plt.title('Средний уровень иммуноглобулина в зависимости от класса и возраста')
# plt.show()

children = [0.15,   0.06,  0.27,  0.14,  0.17,  0.15, 0.06]
adult = [0.19, 0.07, 0.34, 0.12,  0.11, 0.12, 0.05]

x = np.arange(0, 21, 3)
width = 0.75
plt.bar(x, children, width, color=(0.9, 0.2, 0.2, 0.8), label='0-15')
plt.bar(x+width, adult, width, color=(0.4, 0.9, 0.4, 0.8), label='16+')
plt.xticks(x + width / 2, ["FluA", "FluB", "RV", "RSV", "AdV", "PIV", "CoV"])
plt.ylabel('Доля')
plt.xlabel('Этиология')
plt.title('Вероятность выбора этиологии при случайном инфицировании в зависимости от возраста')
plt.legend()
plt.show()
