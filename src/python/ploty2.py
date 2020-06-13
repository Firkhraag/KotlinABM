import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

v = [1.4,   1,  1.9,  4.4,  5.6,  2.6, 3.2]

x = np.arange(0, 7, 1)
width = 0.5
plt.bar(x, v, width, color=(0.9, 0.2, 0.2, 0.8))
plt.xticks(x, ["FluA", "FluB", "RV", "RSV", "AdV", "PIV", "CoV"])
plt.ylabel('Продолжительность, дней')
plt.xlabel('Этиология')
plt.title('Средняя вирусная нагрузка в зависимости от этиологии заболевания и возраста')
plt.show()

v = [8.8,   7.8,  11.4,  9.3,  9.0,  8.0, 8.0]
v2 = [4.8,   3.7,  10.1,  7.4,  8.0,  7.0, 7.0]

x = np.arange(0, 14, 2)
width = 0.66
plt.bar(x, v, width, color=(0.9, 0.2, 0.2, 0.8), label='0-15')
plt.bar(x + width, v2, width, color=(0.2, 0.2, 0.9, 0.8), label='16+')
plt.xticks(x  + width / 2, ["FluA", "FluB", "RV", "RSV", "AdV", "PIV", "CoV"])
plt.ylabel('Продолжительность, дней')
plt.xlabel('Этиология')
plt.title('Средняя вирусная нагрузка в зависимости от этиологии заболевания и возраста')
plt.legend()
plt.show()
#
# vl2 = [4.6,   4.7,  3.5,  6.0,  4.1,  4.7, 4.93]
# vl14 = [3.45, 3.53, 2.63, 4.5,  3.08, 3.53, 3.7]
# vl90 = [2.3,  2.35, 1.75, 2.25, 2.05, 2.35, 2.47]
#
# x = np.arange(0, 21, 3)
# width = 0.75
# plt.bar(x, vl2, width, color=(0.9, 0.2, 0.2, 0.8), label='0-2')
# plt.bar(x+width, vl14, width, color=(0.4, 0.9, 0.4, 0.8), label='3-15')
# plt.bar(x+2*width, vl90, width, color=(0.2, 0.4, 0.9, 0.8), label='16+')
# plt.xticks(x + width, ["FluA", "FluB", "RV", "RSV", "AdV", "PIV", "CoV"])
# plt.ylabel('Вирусная нагрузка, log(копий/мл)')
# plt.xlabel('Этиология')
# plt.title('Средняя вирусная нагрузка в зависимости от этиологии заболевания и возраста')
# plt.legend()
# plt.show()
#
igG = [913.85, 429.5, 482.43, 536.79, 726.79, 786.41, 823.19, 982.86, 1016.12, 1123.56, 1277.20, 1449.2, 1579.63, 1421.67]
igM = [16.89, 34.21, 69.05, 73.42, 115.25, 104.66, 115.6, 108.05, 104.95, 119.16, 130.6, 148.19, 161.53, 145.38]
igA = [6.77, 9.58, 17.23, 23.63, 34.09, 48.87, 62.75, 97.38, 102.27, 112.16, 179.21, 203.35, 221.65, 199.49]
igAll = np.array(igG) + np.array(igM) + np.array(igA)
x = np.arange(0, 28, 2)
width = 0.75
plt.bar(x, igAll, width, color=(0.9, 0.2, 0.2, 0.8))
# plt.bar(x+width, igM, width, color=(0.4, 0.9, 0.4, 0.8), label='IgM')
# plt.bar(x+2*width, igA, width, color=(0.2, 0.4, 0.9, 0.8), label='IgA')
plt.xticks(x, ["0м", "1-3м", "4-6м", "7-12м", "1", "2", "3-5", "6-8", "9-11", "12-16", "17-18", "19-29", "30-59", "60+"])
plt.ylabel('Уровень иммуноглобулина, мг/дл')
plt.xlabel('Возраст')
plt.title('Средний уровень иммуноглобулина в зависимости от класса и возраста')
plt.show()
#
children = [0.05,   0.05,  0.3,  0.26,  0.17,  0.12, 0.05]
adult = [0.33, 0.18, 0.18, 0.08,  0.06, 0.09, 0.08]

x = np.arange(0, 21, 3)
width = 0.75
plt.bar(x, children, width, color=(0.9, 0.2, 0.2, 0.8), label='0-15')
plt.bar(x+width, adult, width, color=(0.4, 0.9, 0.4, 0.8), label='16+')
plt.xticks(x + width / 2, ["FluA", "FluB", "RV", "RSV", "AdV", "PIV", "CoV"])
plt.ylabel('Вероятность')
plt.xlabel('Этиология')
plt.title('Вероятность выбора этиологии при случайном инфицировании в зависимости от возраста')
plt.legend()
plt.show()

all = [0.0,   0.0,  0.6,  0.0,  0.25,  0.15, 0.0 ]

x = np.arange(0, 21, 3)
width = 0.75
plt.bar(x, all, width, color=(0.9, 0.2, 0.2, 0.8))
plt.xticks(x, ["FluA", "FluB", "RV", "RSV", "AdV", "PIV", "CoV"])
plt.ylabel('Вероятность')
plt.xlabel('Этиология')
plt.title('Вероятность выбора этиологии при случайном инфицировании в зависимости от возраста')
plt.show()

# all = [0.6, 0.25, 0.15]
#
# x = np.arange(0, 3, 1)
# width = 0.75
# plt.bar(x, all, width, color=(0.9, 0.2, 0.2, 0.8))
# plt.xticks(x, ["RV", "AdV", "PIV"])
# plt.ylabel('Вероятность')
# plt.xlabel('Этиология')
# plt.title('Вероятность выбора этиологии летом и в начале осени при случайном инфицировании в зависимости от возраста')
# plt.show()



# igG = [913.85, 429.5, 482.43, 536.79, 726.79, 786.41, 823.19, 982.86, 1016.12, 1123.56, 1277.20, 1449.2, 1579.63, 1421.67]
# igM = [16.89, 34.21, 69.05, 73.42, 115.25, 104.66, 115.6, 108.05, 104.95, 119.16, 130.6, 148.19, 161.53, 145.38]
# igA = [6.77, 9.58, 17.23, 23.63, 34.09, 48.87, 62.75, 97.38, 102.27, 112.16, 179.21, 203.35, 221.65, 199.49]
# ig = np.array(igG) + np.array(igM) + np.array(igA)
# x = np.arange(0, 42, 3)
# width = 0.75
# plt.bar(x, ig, width, color=(0.9, 0.2, 0.2, 0.8), label='IgG')
# # plt.bar(x+width, igM, width, color=(0.4, 0.9, 0.4, 0.8), label='IgM')
# # plt.bar(x+2*width, igA, width, color=(0.2, 0.4, 0.9, 0.8), label='IgA')
# plt.xticks(x, ["0м", "1-3м", "4-6м", "7-12м", "1", "2", "3-5", "6-8", "9-11", "12-16", "17-18", "19-29", "30-59", "60+"])
# plt.ylabel('Вирусная нагрузка, log(копий/мл)')
# plt.xlabel('Возраст')
# plt.title('Средний уровень иммуноглобулина в зависимости от класса и возраста')
# plt.legend()
# plt.show()