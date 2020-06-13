import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

etiologies = pd.read_excel(r'..\tables\etiologies.xlsx', header=None)

x = np.linspace(1, 52, 52)

fluA = np.array(etiologies.iloc[:, 0]) * 0.6
fluB = np.array(etiologies.iloc[:, 0]) * 0.4

plt.plot(x, fluA, c='r', label="FluA")
plt.plot(x, fluB, c='b', label="FluB")
plt.plot(x, etiologies.iloc[:, 1], c='g', label="RV")
plt.plot(x, etiologies.iloc[:, 2], c='m', label="RSV")
plt.plot(x, etiologies.iloc[:, 3], c='y', label="AdV")
plt.plot(x, etiologies.iloc[:, 4], c='k', label="PIV")
plt.plot(x, etiologies.iloc[:, 5], c='c', label="CoV")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.ylabel('Вероятность')
plt.xlabel('Месяц')
plt.title('Выбор этиологии при случайном инфицировании')
plt.legend()
plt.show()
