import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy.interpolate import interp1d

r0 = pd.read_excel(r'..\output\r0.xlsx', header=None)

x = np.arange(10, 365, 30.415)


for j in range(0, 7):
    r0_mean = np.empty(0)
    for i in range(1, 13):
        r0_mean = np.append(r0_mean, np.mean(r0.iloc[((i-1) * 100):(i * 100) + 1, j]))
    r0_mean = np.append(r0_mean[7:], r0_mean[:7])
    if j == 0:
        plt.plot(x, r0_mean, label="FluA", c='r')
    elif j == 1:
        plt.plot(x, r0_mean, label="FluB", c='b')
    elif j == 2:
        plt.plot(x, r0_mean, label="RV", c='g')
    elif j == 3:
        plt.plot(x, r0_mean, label="RSV", c='m')
    elif j == 4:
        plt.plot(x, r0_mean, label="AdV", c='y')
    elif j == 5:
        plt.plot(x, r0_mean, label="PIV", c='k')
    elif j == 6:
        plt.plot(x, r0_mean, label="CoV", c='c')


plt.xticks(np.linspace(1, 365, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.ylabel('R0')
plt.xlabel('Месяц')
plt.title('R0 для ОРЗ')
plt.legend()
plt.show()

# x = np.arange(10, 365, 30.415)
# x2 = np.arange(10, 730, 30.415)
# xnew = np.arange(10, 365, 1)
#
#
# for j in range(0, 7):
#     r0_mean = np.empty(0)
#     for i in range(1, 13):
#         r0_mean = np.append(r0_mean, np.mean(r0.iloc[((i-1) * 100):(i * 100) + 1, j]))
#     for i in range(1, 13):
#         r0_mean = np.append(r0_mean, np.mean(r0.iloc[((i-1) * 100):(i * 100) + 1, j]))
#     f2 = interp1d(x2, r0_mean, kind='cubic', fill_value="extrapolate")
#     if j == 0:
#         plt.plot(xnew, f2(xnew), label="FluA", c='r')
#     elif j == 1:
#         plt.plot(xnew, f2(xnew), label="FluB", c='b')
#     elif j == 2:
#         plt.plot(xnew, f2(xnew), label="RV", c='g')
#     elif j == 3:
#         plt.plot(xnew, f2(xnew), label="RSV", c='m')
#     elif j == 4:
#         plt.plot(xnew, f2(xnew), label="AdV", c='y')
#     elif j == 5:
#         plt.plot(xnew, f2(xnew), label="PIV", c='k')
#     elif j == 6:
#         plt.plot(xnew, f2(xnew), label="CoV", c='c')
#
#
# plt.xticks(np.linspace(1, 365, 13), ('Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв'))
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для ОРЗ')
# plt.legend()
# plt.show()

# plt.plot(x, r0.iloc[:, 0], label="FluA")
# plt.plot(x, r0.iloc[:, 1], label="FluB")
# plt.plot(x, r0.iloc[:, 2], label="RV")
# plt.plot(x, r0.iloc[:, 3], label="RSV")
# plt.plot(x, r0.iloc[:, 4], label="AdV")
# plt.plot(x, r0.iloc[:, 5], label="PIV")
# plt.plot(x, r0.iloc[:, 6], label="CoV")
# # plt.plot(x, r0_all, color='r', label="Mean")
#
# plt.xticks(np.linspace(1, 365, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
# # plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для ОРЗ')
# plt.legend()
# plt.show()