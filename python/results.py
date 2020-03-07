import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from scipy import interpolate
from scipy.stats import pearsonr


def find_num_of_people_each_year(x):
    x_points = [1920, 1923, 1926, 1939, 1959, 1970, 1979, 1989, 2002, 2010, 2012]
    y_points = [1027.3, 1542.9, 2025.9, 4137.0, 5085.6, 7061.0, 7931.6, 8875.6, 10382.8, 11503.5, 11612.9]

    tck = interpolate.splrep(x_points, y_points)
    return interpolate.splev(x, tck)


df = pd.read_csv(r'..\output\results.csv', header=None)
dfAge = pd.read_csv(r'..\output\resultsByAge.csv', header=None)
dfEti = pd.read_csv(r'..\output\resultsByEtiology.csv', header=None)

# Starting from 31th week: after 98 12
rsvData = [5, 6, 3, 5, 4, 3, 5, 6, 6, 4, 6, 5, 6, 7, 5, 8, 12, 17, 12, 17, 20, 12, 6, 9, 22, 18, 32, 80, 62, 50, 64,
           98, 130, 150, 110, 108, 88, 70, 55, 58, 56, 42, 33, 37, 33, 28, 21, 9, 7, 6, 5, 4]

temp = {213: 19.1,
        214: 19.0, 215: 18.9, 216: 18.8, 217: 18.7, 218: 18.6, 219: 18.5, 220: 18.4, 221: 18.3,
        222: 18.2, 223: 18.0, 224: 17.9, 225: 17.7, 226: 17.6, 227: 17.4, 228: 17.2, 229: 17.1,
        230: 16.9, 231: 16.7, 232: 16.5, 233: 16.3, 234: 16.1, 235: 15.9, 236: 15.7, 237: 15.5,
        238: 15.3, 239: 15.1, 240: 14.9, 241: 14.7, 242: 14.5, 243: 14.3, 244: 14.1, 245: 13.9,
        246: 13.7, 247: 13.5, 248: 13.3, 249: 13.1, 250: 12.8, 251: 12.6, 252: 12.4, 253: 12.2,
        254: 12.1, 255: 11.9, 256: 11.7, 257: 11.5, 258: 11.3, 259: 11.1, 260: 10.9, 261: 10.7,
        262: 10.6, 263: 10.4, 264: 10.2, 265: 10.0, 266: 9.9, 267: 9.7, 268: 9.5, 269: 9.4,
        270: 9.2, 271: 9.0, 272: 8.9, 273: 8.7, 274: 8.5, 275: 8.3, 276: 8.2, 277: 8.0,
        278: 7.8, 279: 7.7, 280: 7.5, 281: 7.3, 282: 7.1, 283: 6.9, 284: 6.8, 285: 6.6,
        286: 6.4, 287: 6.2, 288: 6.0, 289: 5.8, 290: 5.6, 291: 5.4, 292: 5.2, 293: 4.9,
        294: 4.7, 295: 4.5, 296: 4.3, 297: 4.0, 298: 3.8, 299: 3.6, 300: 3.3, 301: 3.1,
        302: 2.9, 303: 2.6, 304: 2.4, 305: 2.1, 306: 1.9, 307: 1.6, 308: 1.4, 309: 1.1,
        310: 0.9, 311: 0.7, 312: 0.4, 313: 0.2, 314: -0.1, 315: -0.3, 316: -0.5, 317: -0.8,
        318: -1.0, 319: -1.2, 320: -1.5, 321: -1.7, 322: -1.9, 323: -2.1, 324: -2.3, 325: -2.5,
        326: -2.7, 327: -2.9, 328: -3.0, 329: -3.2, 330: -3.4, 331: -3.5, 332: -3.7, 333: -3.8,
        334: -4.0, 335: -4.1, 336: -4.2, 337: -4.3, 338: -4.4, 339: -4.5, 340: -4.6, 341: -4.7,
        342: -4.8, 343: -4.9, 344: -5.0, 345: -5.0, 346: -5.1, 347: -5.2, 348: -5.2, 349: -5.3,
        350: -5.3, 351: -5.4, 352: -5.4, 353: -5.4, 354: -5.5, 355: -5.5, 356: -5.5, 357: -5.6,
        358: -5.6, 359: -5.6, 360: -5.7, 361: -5.7, 362: -5.7, 363: -5.7, 364: -5.8, 365: -5.8,
        0: -5.8, 1: -5.9, 2: -5.9, 3: -5.9, 4: -6.0, 5: -6.0, 6: -6.1, 7: -6.1,
        8: -6.2, 9: -6.2, 10: -6.2, 11: -6.3, 12: -6.3, 13: -6.4, 14: -6.5, 15: -6.5, 16: -6.6,
        17: -6.6, 18: -6.7, 19: -6.7, 20: -6.8, 21: -6.8, 22: -6.9, 23: -6.9, 24: -7.0, 25: -7.0,
        26: -7.0, 27: -7.1, 28: -7.1, 29: -7.1, 30: -7.1, 31: -7.2, 32: -7.2, 33: -7.2, 34: -7.2,
        35: -7.2, 36: -7.2, 37: -7.1, 38: -7.1, 39: -7.1, 40: -7.0, 41: -7.0, 42: -6.9, 43: -6.8,
        44: -6.8, 45: -6.7, 46: -6.6, 47: -6.5, 48: -6.4, 49: -6.3, 50: -6.1, 51: -6.0, 52: -5.9,
        53: -5.7, 54: -5.6, 55: -5.4, 56: -5.2, 57: -5.0, 58: -4.8, 60: -4.7, 61: -4.5,
        62: -4.2, 63: -4.0, 64: -3.8, 65: -3.6, 66: -3.4, 67: -3.1, 68: -2.9, 69: -2.7, 70: -2.4,
        71: -2.2, 72: -1.9, 73: -1.7, 74: -1.4, 75: -1.2, 76: -0.9, 77: -0.6, 78: -0.4, 79: -0.1,
        80: 0.2, 81: 0.4, 82: 0.7, 83: 1.0, 84: 1.2, 85: 1.5, 86: 1.8, 87: 2.0, 88: 2.3, 89: 2.5,
        90: 2.8, 91: 3.1, 92: 3.3, 93: 3.6, 94: 3.9, 95: 4.1, 96: 4.4, 97: 4.6, 98: 4.9, 99: 5.1,
        100: 5.4, 101: 5.6, 102: 5.9, 103: 6.1, 104: 6.4, 105: 6.6, 106: 6.9, 107: 7.1, 108: 7.4,
        109: 7.6, 110: 7.8, 111: 8.1, 112: 8.3, 113: 8.5, 114: 8.8, 115: 9.0, 116: 9.2, 117: 9.4,
        118: 9.7, 119: 9.9, 120: 10.1, 121: 10.3, 122: 10.5, 123: 10.7, 124: 11.0, 125: 11.2,
        126: 11.4, 127: 11.6, 128: 11.8, 129: 12.0, 130: 12.1, 131: 12.3, 132: 12.5, 133: 12.7,
        134: 12.9, 135: 13.1, 136: 13.2, 137: 13.4, 138: 13.6, 139: 13.7, 140: 13.9, 141: 14.0,
        142: 14.2, 143: 14.3, 144: 14.5, 145: 14.6, 146: 14.8, 147: 14.9, 148: 15.0, 149: 15.2,
        150: 15.3, 151: 15.4, 152: 15.5, 153: 15.6, 154: 15.8, 155: 15.9, 156: 16.0, 157: 16.1,
        158: 16.2, 159: 16.3, 160: 16.4, 161: 16.5, 162: 16.6, 163: 16.7, 164: 16.8, 165: 16.9,
        166: 17.0, 167: 17.1, 168: 17.2, 169: 17.2, 170: 17.3, 171: 17.4, 172: 17.5, 173: 17.6,
        174: 17.7, 175: 17.8, 176: 17.9, 177: 17.9, 178: 18.0, 179: 18.1, 180: 18.2, 181: 18.3,
        182: 18.4, 183: 18.4, 184: 18.5, 185: 18.6, 186: 18.7, 187: 18.7, 188: 18.8, 189: 18.9,
        190: 18.9, 191: 19.0, 192: 19.1, 193: 19.1, 194: 19.2, 195: 19.2, 196: 19.3, 197: 19.3,
        198: 19.3, 199: 19.4, 200: 19.4, 201: 19.4, 202: 19.4, 203: 19.4, 204: 19.4, 205: 19.4,
        206: 19.4, 207: 19.4, 208: 19.3, 209: 19.3, 210: 19.3, 211: 19.2, 212: 19.1}

temp_arr = temp.values()

flu = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\Flu.csv', index_col=0)
flu = flu.loc[1998:, :]
for year in range(1998, 2003):
    for week in range(1, 53):
        flu.loc[year, str(week)] /= find_num_of_people_each_year(year)

mean_flu = flu.mean(axis=0)

corr, pval = pearsonr(list(temp_arr), list(df.iloc[:, 1]))
print('Pearson correlation for model: %.3f' % corr)
print('P-value: %.5f' % pval)

# Find mean values for each week in different groups
res = np.zeros(53)
j = 0
for i in range(0, 365):
    res[j] += df.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res[j] = res[j] / 9863
        j += 1

res_recorded = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded[j] += df.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded[j] = res_recorded[j] / 9863
        j += 1

res0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res0_2[j] += dfAge.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res0_2[j] = res0_2[j] / 9863
        j += 1
res3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_6[j] += dfAge.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res3_6[j] = res3_6[j] / 9863
        j += 1
res7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_14[j] += dfAge.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res7_14[j] = res7_14[j] / 9863
        j += 1
res15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res15[j] += dfAge.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res15[j] = res15[j] / 9863
        j += 1


# Find mean values for each week in different etiologies
resA = np.zeros(53)
j = 0
for i in range(0, 365):
    resA[j] += dfEti.iloc[i, 0]
    if (i + 1) % 7 == 0:
        resA[j] = resA[j] / 9863
        j += 1
resB = np.zeros(53)
j = 0
for i in range(0, 365):
    resB[j] += dfEti.iloc[i, 1]
    if (i + 1) % 7 == 0:
        resB[j] = resB[j] / 9863
        j += 1
resRV = np.zeros(53)
j = 0
for i in range(0, 365):
    resRV[j] += dfEti.iloc[i, 2]
    if (i + 1) % 7 == 0:
        resRV[j] = resRV[j] / 9863
        j += 1
resRSV = np.zeros(53)
j = 0
for i in range(0, 365):
    resRSV[j] += dfEti.iloc[i, 3]
    if (i + 1) % 7 == 0:
        resRSV[j] = resRSV[j] / 9863
        j += 1
resAdV = np.zeros(53)
j = 0
for i in range(0, 365):
    resAdV[j] += dfEti.iloc[i, 4]
    if (i + 1) % 7 == 0:
        resAdV[j] = resAdV[j] / 9863
        j += 1

resPIV = np.zeros(53)
j = 0
for i in range(0, 365):
    resPIV[j] += dfEti.iloc[i, 5]
    if (i + 1) % 7 == 0:
        resPIV[j] = resPIV[j] / 9863
        j += 1

resCoV = np.zeros(53)
j = 0
for i in range(0, 365):
    resCoV[j] += dfEti.iloc[i, 6]
    if (i + 1) % 7 == 0:
        resCoV[j] = resCoV[j] / 9863
        j += 1


plt.figure()
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), res_recorded[:52], c='b', label="модель")
plt.title("Регистрируемая общая заболеваемость")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()

diff = 0
for week in range(1, 53):
    diff += (mean_flu[str(week)] - res_recorded[week - 1]) * (mean_flu[str(week)] - res_recorded[week - 1])

temp_arr2 = []
for week in range(1, 53):
    temp_arr2.append(0)

tl = list(temp.values())
for week in range(0, 52):
    for j in range(0, 7):
        temp_arr2[week] += tl[week*7 + j]
    temp_arr2[week] /= 7

corr, pval = pearsonr(list(temp_arr2), list(mean_flu))
print('Pearson correlation for data: %.3f' % corr)
print('P-value: %.5f' % pval)

plt.figure()
plt.scatter(temp_arr2, mean_flu, c='r', label="данные")
plt.title("Зависимость регистрируемой заболеваемости от температуры")

plt.scatter(temp_arr2, res_recorded[:52], c='b', label="модель")
plt.xlabel("Температура, ℃")
plt.ylabel("Количество случаев на 1000 чел.")
plt.legend()
plt.show()

mean_flu = flu.mean(axis=1)

plt.figure()
plt.plot(np.linspace(1, 52, 52), resA[:52], c='r', label="FluA")
plt.plot(np.linspace(1, 52, 52), resB[:52], c='b', label="FluB")
plt.plot(np.linspace(1, 52, 52), resRV[:52], c='g', label="RV")
plt.plot(np.linspace(1, 52, 52), resRSV[:52], c='m', label="RSV")
plt.plot(np.linspace(1, 52, 52), resAdV[:52], c='y', label="AdV")
plt.plot(np.linspace(1, 52, 52), resPIV[:52], c='k', label="PIV")
plt.plot(np.linspace(1, 52, 52), resCoV[:52], c='c', label="CoV")
plt.title("Полученная заболеваемость для различных этиологий")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()

flu = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\Flu0-2.csv', index_col=0)
flu = flu.loc[:, str(1998):]
for year in range(1998, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)

mean_flu = flu.mean(axis=1)
plt.figure()
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), res0_2[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 0-2")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()


diff1 = 0
for week in range(1, 53):
    diff1 += (mean_flu[week] - res0_2[week - 1]) * (mean_flu[week] - res0_2[week - 1])

print("Error0: ", diff1)

flu = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\Flu3-6.csv', index_col=0)
flu = flu.loc[:, str(1998):]
for year in range(1998, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)

mean_flu = flu.mean(axis=1)
plt.figure()
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), res3_6[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 3-6")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()


diff2 = 0
for week in range(1, 53):
    diff2 += (mean_flu[week] - res3_6[week - 1]) * (mean_flu[week] - res3_6[week - 1])

print("Error3: ", diff2)

flu = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\Flu7-14.csv', index_col=0)
flu = flu.loc[:, str(1998):]
for year in range(1998, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)

mean_flu = flu.mean(axis=1)
plt.figure()
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), res7_14[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 7-14")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()


diff3 = 0
for week in range(1, 53):
    diff3 += (mean_flu[week] - res7_14[week - 1]) * (mean_flu[week] - res7_14[week - 1])

print("Error7: ", diff3)

flu = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\Flu15+.csv', index_col=0)
flu = flu.loc[:, str(1998):]
for year in range(1998, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)

mean_flu = flu.mean(axis=1)
plt.figure()
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), res15[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 15+")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 12), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл'))
plt.legend()
plt.show()

diff4 = 0
for week in range(1, 53):
    diff4 += (mean_flu[week] - res15[week - 1]) * (mean_flu[week] - res15[week - 1])

print("Error15: ", diff4)


print("Sum of errors: ", diff1 + diff2 + diff3 + diff4)
print("Global error: ", diff)
