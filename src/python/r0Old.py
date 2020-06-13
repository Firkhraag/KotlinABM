import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy import interpolate

r0 = pd.read_excel(r'..\output\r0.xlsx', header=None)
x = np.arange(1, 13, 1)

# plt.plot(x, r0.iloc[:, 0], color='r')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для гриппа A в зависимости от месяца')
# plt.show()
#
# plt.plot(x, r0.iloc[:, 1], color='b')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для гриппа B в зависимости от месяца')
# plt.show()
#
# plt.plot(x, r0.iloc[:, 2], color='g')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для RV в зависимости от месяца')
# plt.show()
#
# plt.plot(x, r0.iloc[:, 3], color='m')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для RSV в зависимости от месяца')
# plt.show()
#
# plt.plot(x, r0.iloc[:, 4], color='y')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для AdV в зависимости от месяца')
# plt.show()
#
# plt.plot(x, r0.iloc[:, 5], color='k')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для PIV в зависимости от месяца')
# plt.show()
#
# plt.plot(x, r0.iloc[:, 6], color='c')
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
# plt.ylabel('R0')
# plt.xlabel('Месяц')
# plt.title('R0 для CoV в зависимости от месяца')
# plt.show()

x = np.arange(15, 365, 30.5)

r0_all = (np.array(r0.iloc[:, 0]) + np.array(r0.iloc[:, 1]) + np.array(r0.iloc[:, 2]) + np.array(r0.iloc[:, 3]) +
          np.array(r0.iloc[:, 4]) + np.array(r0.iloc[:, 5]) + np.array(r0.iloc[:, 6])) / 7

tck = interpolate.splrep(x, r0_all)
f = interpolate.splev(x, tck)

# plt.plot(x, f, label="FluA")
r0.iloc[:, 0] = np.append(r0.iloc[7:, 0], r0.iloc[:7, 0])
r0.iloc[:, 1] = np.append(r0.iloc[7:, 1], r0.iloc[:7, 1])
r0.iloc[:, 2] = np.append(r0.iloc[7:, 2], r0.iloc[:7, 2])
r0.iloc[:, 3] = np.append(r0.iloc[7:, 3], r0.iloc[:7, 3])
r0.iloc[:, 4] = np.append(r0.iloc[7:, 4], r0.iloc[:7, 4])
r0.iloc[:, 5] = np.append(r0.iloc[7:, 5], r0.iloc[:7, 5])
r0.iloc[:, 6] = np.append(r0.iloc[7:, 6], r0.iloc[:7, 6])
# rA = np.concatenate(r0.iloc[7:, 0], r0.iloc[:7, 0])

# rA = np.array(r0.iloc[7, 0], r0.iloc[8, 0], r0.iloc[9, 0], r0.iloc[10, 0], r0.iloc[11, 0], r0.iloc[0, 0])

plt.plot(x, r0.iloc[:, 0], label="FluA")
plt.plot(x, r0.iloc[:, 1], label="FluB")
plt.plot(x, r0.iloc[:, 2], label="RV")
plt.plot(x, r0.iloc[:, 3], label="RSV")
plt.plot(x, r0.iloc[:, 4], label="AdV")
plt.plot(x, r0.iloc[:, 5], label="PIV")
plt.plot(x, r0.iloc[:, 6], label="CoV")
# plt.plot(x, r0_all, color='r', label="Mean")

plt.xticks(np.linspace(1, 365, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
# plt.xticks(x, ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"])
plt.ylabel('R0')
plt.xlabel('Месяц')
plt.title('R0 для ОРЗ')
plt.legend()
plt.show()
