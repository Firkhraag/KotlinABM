import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.interpolate import make_interp_spline, BSpline

# #                                                             49  50  51  52  1   2   3    4   5   6   7   8   9
# fluData = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 15, 26, 65, 26, 79, 150, 300, 375, 360, 300, 220, 175,
#            # 10 11   12   13   14   15  16  17  18  19  20  21  22
#            158, 110, 84, 73, 55, 44, 31, 27, 19, 15, 12, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
#                                                             49  50  51  52  1   2   3    4   5   6   7   8   9
fluData = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 25, 36, 75, 36, 59, 130, 200, 240, 260, 250, 200, 155,
           # 10 11   12   13   14   15  16  17  18  19  20  21  22
           138, 90, 74, 53, 45, 34, 31, 27, 19, 15, 12, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
rsvData = [6, 3, 5, 4, 3, 5, 6, 6, 4, 6, 5, 6, 7, 5, 8, 17, 27, 22, 27, 40, 32, 26, 19, 32, 38, 52, 90, 72, 60, 74,
           108, 140, 160, 120, 118, 98, 80, 65, 68, 66, 52, 43, 47, 43, 38, 31, 19, 7, 6, 5, 4, 5]
rvData = [33, 41, 45, 47, 75, 100, 122, 123, 134, 125, 128, 118, 131, 124, 90, 100, 112, 128, 100, 81, 57, 35, 22, 40,
          60, 80, 96, 90, 69, 60, 74, 80, 86, 99, 80, 75, 62, 64, 60, 50, 48, 60, 69, 40, 47, 38, 37, 39, 23, 30, 27, 32]
advData = [16, 24, 20, 22, 20, 18, 15, 19, 23, 15, 20, 30, 28, 37, 39, 40, 32, 40, 42, 54, 45, 31, 8, 13,
           31, 40, 40, 60, 62, 33, 28, 30, 34, 32, 31, 12, 17, 19, 21, 20, 17, 24, 25, 18, 19, 12, 10, 11, 21, 23, 23, 19]
pivData = [11, 14, 17, 14, 23, 28, 29, 30, 49, 35, 37, 38, 38, 54, 46, 35, 51, 47, 50, 30, 50, 20, 17, 38,
           32, 33, 50, 41, 23, 18, 11, 13, 18, 20, 14, 17, 18, 21, 17, 8, 12, 19, 16, 17, 16, 16, 14, 20, 13, 11, 10, 9]
covData = [1, 2, 4, 1, 4, 2, 1, 2, 4, 2, 1, 5, 7, 4, 5, 7, 8, 5, 6, 15, 13, 6, 1, 7,
           9, 15, 20, 19, 25, 15, 9, 8, 6, 18, 20, 15, 12, 10, 5, 3, 1, 5, 7, 8, 3, 2, 1, 1, 1, 1, 1, 1]

arr = np.arange(52)
xnew = np.linspace(arr.min(), arr.max(), 300)

spl = make_interp_spline(arr, rsvData, k=3)
power_smooth = spl(xnew)

# plt.figure()
# plt.plot(xnew, power_smooth, c='m', label='RSV')

plt.figure()
plt.plot(np.arange(52), fluData, c='r', label='Flu')
plt.plot(np.arange(52), rvData, c='g', label='RV')
plt.plot(np.arange(52), rsvData, c='m', label='RSV')
plt.plot(np.arange(52), advData, c='y', label='AdV')
plt.plot(np.arange(52), pivData, c='k', label='PIV')
plt.plot(np.arange(52), covData, c='c', label='CoV')
plt.title("Примерная заболеваемость для различных этиологий из статьи")
plt.xlabel("Месяц")
plt.ylabel("Человек")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()

plt.figure()
plt.plot(np.arange(52), rvData, c='g', label='RV')
plt.plot(np.arange(52), rsvData, c='m', label='RSV')
plt.plot(np.arange(52), advData, c='y', label='AdV')
plt.plot(np.arange(52), pivData, c='k', label='PIV')
plt.plot(np.arange(52), covData, c='c', label='CoV')
plt.title("ОРЗ негриппозной этиологии на основе данных")
plt.xlabel("Неделя")
plt.ylabel("Количество случаев на 1000 чел.")
plt.legend()
plt.show()

# plt.figure()
# plt.plot(np.arange(52), rvData, c='g')
# plt.title("RV данные")
# plt.xlabel("Неделя")
# plt.ylabel("Количество случаев на 1000 чел.")
# plt.show()
#
# plt.figure()
# plt.plot(np.arange(52), rsvData, c='m')
# plt.title("RSV данные")
# plt.xlabel("Неделя")
# plt.ylabel("Количество случаев на 1000 чел.")
# plt.show()
#
# plt.figure()
# plt.plot(np.arange(52), advData, c='y')
# plt.title("AdV данные")
# plt.xlabel("Неделя")
# plt.ylabel("Количество случаев на 1000 чел.")
# plt.show()
#
# plt.figure()
# plt.plot(np.arange(52), pivData, c='k')
# plt.title("PIV данные")
# plt.xlabel("Неделя")
# plt.ylabel("Количество случаев на 1000 чел.")
# plt.show()

# influenzaADurationAdult = [0.2, 0.5, 0.2, 0.06, 0.04]
# influenzaADurationChild = [0.23, 0.08, 0.1, 0.05, 0.07, 0.12, 0.14, 0.02, 0.02, 0.03, 0.14]
# influenzaBDurationAdult = [0.1, 0.2, 0.5, 0.15, 0.03, 0.02]
# influenzaBDurationChild = [0.23, 0.18, 0.12, 0.26, 0.04, 0.03, 0.14]
#
# #               RV, RSV, BoV, PIV, MPV, FluA, FluB, AdV
# #               RV, RSV, PIV, MPV, FluA, FluB, AdV
# month1Child = [0.1, 0.24, 0.12, 0.09, 0.07, 0.1, 0.15, 0.13]
# month1Adult = [0.11, 0.25, 0.0, 0.1, 0.08, 0.16, 0.16, 0.14]
# month2Child = [0.14, 0.13, 0.03, 0.15, 0.0, 0.14, 0.27, 0.14]
# month2Adult = [0.14, 0.13, 0.15, 0.0, 0.18, 0.27, 0.13]
# print(sum(month2Adult))
#
# x = np.arange(8)
# width = 0.45
# plt.bar(x, month1Child, width, color='royalblue')
# plt.bar(x+width, month1Adult, width, color='orange')
# plt.xticks(x + width / 2, ["RV", "RSV", "BoV", "PIV", "MPV", "FluA", "FluB", "AdV"])
# plt.ylabel('Вероятность')
# plt.xlabel('Дни')
# plt.title('Продолжительность болезни гриппом A взрослого человека')
# plt.show()
#
# # N = 5
# # menMeans = (20, 35, 30, 35, 27)
# # menStd =   (2, 3, 4, 1, 2)
# #
# # ind = np.arange(N)  # the x locations for the groups
# # width = 0.35       # the width of the bars
# #
# # fig = plt.figure()
# # ax = fig.add_subplot(111)
# # rects1 = ax.bar(ind, menMeans, width, color='royalblue', yerr=menStd)
# #
# # womenMeans = (25, 32, 34, 20, 25)
# # womenStd =   (3, 5, 2, 3, 3)
# # rects2 = ax.bar(ind+width, womenMeans, width, color='seagreen', yerr=womenStd)
# #
# # # add some
# # ax.set_ylabel('Scores')
# # ax.set_title('Scores by group and gender')
# # ax.set_xticks(ind + width / 2)
# # ax.set_xticklabels( ('G1', 'G2', 'G3', 'G4', 'G5') )
# #
# # ax.legend( (rects1[0], rects2[0]), ('Men', 'Women') )
# #
# # plt.show()
#
# # x = np.arange(5)
# # plt.bar(x, height=influenzaADurationAdult)
# # plt.xticks(x, [4, 5, 6, 7, 8])
# # plt.ylabel('Вероятность')
# # plt.xlabel('Дни')
# # plt.title('Продолжительность болезни гриппом A взрослого человека')
# #
# # plt.figure()
# # x = np.arange(6)
# # plt.bar(x, height=influenzaBDurationAdult)
# # plt.xticks(x, [2, 3, 4, 5, 6, 7])
# # plt.ylabel('Вероятность')
# # plt.xlabel('Дни')
# # plt.title('Продолжительность болезни гриппом B взрослого человека')
# #
# # plt.figure()
# # x = np.arange(11)
# # plt.bar(x, height=influenzaADurationChild)
# # plt.xticks(x, [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15])
# # plt.ylabel('Вероятность')
# # plt.xlabel('Дни')
# # plt.title('Продолжительность болезни гриппом A ребенка')
# #
# # plt.figure()
# # x = np.arange(7)
# # plt.bar(x, height=influenzaBDurationChild)
# # plt.xticks(x, [4, 5, 6, 7, 8, 9, 10])
# # plt.ylabel('Вероятность')
# # plt.xlabel('Дни')
# # plt.title('Продолжительность болезни гриппом B ребенка')
#
# # data = [3.5, 4.5, 6.6, 6.6, 6.6, 6.6]
# # plt.plot(np.linspace(1, len(data), len(data)), data)
# # plt.show()
