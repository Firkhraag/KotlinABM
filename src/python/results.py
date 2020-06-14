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


df1 = pd.read_excel(r'..\output\results1.xlsx', header=None)
dfAge1 = pd.read_excel(r'..\output\resultsByAge1.xlsx', header=None)
dfEti1 = pd.read_excel(r'..\output\resultsByEtiology1.xlsx', header=None)

df2 = pd.read_excel(r'..\output\results2.xlsx', header=None)
dfAge2 = pd.read_excel(r'..\output\resultsByAge2.xlsx', header=None)
dfEti2 = pd.read_excel(r'..\output\resultsByEtiology2.xlsx', header=None)

df3 = pd.read_excel(r'..\output\results3.xlsx', header=None)
dfAge3 = pd.read_excel(r'..\output\resultsByAge3.xlsx', header=None)
dfEti3 = pd.read_excel(r'..\output\resultsByEtiology3.xlsx', header=None)

df4 = pd.read_excel(r'..\output\results4.xlsx', header=None)
dfAge4 = pd.read_excel(r'..\output\resultsByAge4.xlsx', header=None)
dfEti4 = pd.read_excel(r'..\output\resultsByEtiology4.xlsx', header=None)

df5 = pd.read_excel(r'..\output\results5.xlsx', header=None)
dfAge5 = pd.read_excel(r'..\output\resultsByAge5.xlsx', header=None)
dfEti5 = pd.read_excel(r'..\output\resultsByEtiology5.xlsx', header=None)

df6 = pd.read_excel(r'..\output\results6.xlsx', header=None)
dfAge6 = pd.read_excel(r'..\output\resultsByAge6.xlsx', header=None)
dfEti6 = pd.read_excel(r'..\output\resultsByEtiology6.xlsx', header=None)

df7 = pd.read_excel(r'..\output\results7.xlsx', header=None)
dfAge7 = pd.read_excel(r'..\output\resultsByAge7.xlsx', header=None)
dfEti7 = pd.read_excel(r'..\output\resultsByEtiology7.xlsx', header=None)

df8 = pd.read_excel(r'..\output\results8.xlsx', header=None)
dfAge8 = pd.read_excel(r'..\output\resultsByAge8.xlsx', header=None)
dfEti8 = pd.read_excel(r'..\output\resultsByEtiology8.xlsx', header=None)

df9 = pd.read_excel(r'..\output\results9.xlsx', header=None)
dfAge9 = pd.read_excel(r'..\output\resultsByAge9.xlsx', header=None)
dfEti9 = pd.read_excel(r'..\output\resultsByEtiology9.xlsx', header=None)

df10 = pd.read_excel(r'..\output\results10.xlsx', header=None)
dfAge10 = pd.read_excel(r'..\output\resultsByAge10.xlsx', header=None)
dfEti10 = pd.read_excel(r'..\output\resultsByEtiology10.xlsx', header=None)

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

# plt.figure()
# plt.plot(np.linspace(1, 365, 365), temp_arr, c='y')
# plt.title("Среднесуточная температура воздуха в Москве")
# plt.xlabel("Месяц")
# plt.xticks(np.linspace(1, 365, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
# plt.ylabel("Температура, °C")
# plt.show()

flu = pd.read_csv(r'..\tables\flu.csv', index_col=0)
flu = flu.loc[1999:, :]
for year in range(1999, 2003):
    for week in range(1, 53):
        flu.loc[year, str(week)] /= find_num_of_people_each_year(year)

mean_flu = flu.mean(axis=0)

# Find mean values for each week in different groups
res_recorded1 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded1[j] += df1.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded1[j] = res_recorded1[j] / 9863
        j += 1

res_recorded2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded2[j] += df2.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded2[j] = res_recorded2[j] / 9863
        j += 1

res_recorded3 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded3[j] += df3.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded3[j] = res_recorded3[j] / 9863
        j += 1

res_recorded4 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded4[j] += df4.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded4[j] = res_recorded4[j] / 9863
        j += 1

res_recorded5 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded5[j] += df5.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded5[j] = res_recorded5[j] / 9863
        j += 1

res_recorded6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded6[j] += df6.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded6[j] = res_recorded6[j] / 9863
        j += 1

res_recorded7 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded7[j] += df7.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded7[j] = res_recorded7[j] / 9863
        j += 1

res_recorded8 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded8[j] += df8.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded8[j] = res_recorded8[j] / 9863
        j += 1

res_recorded9 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded9[j] += df9.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded9[j] = res_recorded9[j] / 9863
        j += 1

res_recorded10 = np.zeros(53)
j = 0
for i in range(0, 365):
    res_recorded10[j] += df10.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res_recorded10[j] = res_recorded10[j] / 9863
        j += 1

res1_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_0_2[j] += dfAge1.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res1_0_2[j] = res1_0_2[j] / 9863
        j += 1

res2_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_0_2[j] += dfAge2.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res2_0_2[j] = res2_0_2[j] / 9863
        j += 1

res3_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_0_2[j] += dfAge3.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res3_0_2[j] = res3_0_2[j] / 9863
        j += 1

res4_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_0_2[j] += dfAge4.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res4_0_2[j] = res4_0_2[j] / 9863
        j += 1

res5_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_0_2[j] += dfAge5.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res5_0_2[j] = res5_0_2[j] / 9863
        j += 1

res6_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_0_2[j] += dfAge6.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res6_0_2[j] = res6_0_2[j] / 9863
        j += 1

res7_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_0_2[j] += dfAge7.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res7_0_2[j] = res7_0_2[j] / 9863
        j += 1

res8_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_0_2[j] += dfAge8.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res8_0_2[j] = res8_0_2[j] / 9863
        j += 1

res9_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_0_2[j] += dfAge9.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res9_0_2[j] = res9_0_2[j] / 9863
        j += 1

res10_0_2 = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_0_2[j] += dfAge10.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res10_0_2[j] = res10_0_2[j] / 9863
        j += 1

res1_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_3_6[j] += dfAge1.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res1_3_6[j] = res1_3_6[j] / 9863
        j += 1

res2_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_3_6[j] += dfAge2.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res2_3_6[j] = res2_3_6[j] / 9863
        j += 1

res3_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_3_6[j] += dfAge3.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res3_3_6[j] = res3_3_6[j] / 9863
        j += 1

res4_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_3_6[j] += dfAge4.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res4_3_6[j] = res4_3_6[j] / 9863
        j += 1

res5_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_3_6[j] += dfAge5.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res5_3_6[j] = res5_3_6[j] / 9863
        j += 1

res6_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_3_6[j] += dfAge6.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res6_3_6[j] = res6_3_6[j] / 9863
        j += 1

res7_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_3_6[j] += dfAge7.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res7_3_6[j] = res7_3_6[j] / 9863
        j += 1

res8_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_3_6[j] += dfAge8.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res8_3_6[j] = res8_3_6[j] / 9863
        j += 1

res9_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_3_6[j] += dfAge9.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res9_3_6[j] = res9_3_6[j] / 9863
        j += 1

res10_3_6 = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_3_6[j] += dfAge10.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res10_3_6[j] = res10_3_6[j] / 9863
        j += 1

res1_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_7_14[j] += dfAge1.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res1_7_14[j] = res1_7_14[j] / 9863
        j += 1

res2_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_7_14[j] += dfAge2.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res2_7_14[j] = res2_7_14[j] / 9863
        j += 1

res3_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_7_14[j] += dfAge3.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res3_7_14[j] = res3_7_14[j] / 9863
        j += 1

res4_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_7_14[j] += dfAge4.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res4_7_14[j] = res4_7_14[j] / 9863
        j += 1

res5_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_7_14[j] += dfAge5.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res5_7_14[j] = res5_7_14[j] / 9863
        j += 1

res6_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_7_14[j] += dfAge6.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res6_7_14[j] = res6_7_14[j] / 9863
        j += 1

res7_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_7_14[j] += dfAge7.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res7_7_14[j] = res7_7_14[j] / 9863
        j += 1

res8_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_7_14[j] += dfAge8.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res8_7_14[j] = res8_7_14[j] / 9863
        j += 1

res9_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_7_14[j] += dfAge9.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res9_7_14[j] = res9_7_14[j] / 9863
        j += 1

res10_7_14 = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_7_14[j] += dfAge10.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res10_7_14[j] = res10_7_14[j] / 9863
        j += 1

res1_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_15[j] += dfAge1.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res1_15[j] = res1_15[j] / 9863
        j += 1

res2_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_15[j] += dfAge2.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res2_15[j] = res2_15[j] / 9863
        j += 1

res3_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_15[j] += dfAge3.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res3_15[j] = res3_15[j] / 9863
        j += 1

res4_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_15[j] += dfAge4.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res4_15[j] = res4_15[j] / 9863
        j += 1

res5_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_15[j] += dfAge5.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res5_15[j] = res5_15[j] / 9863
        j += 1

res6_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_15[j] += dfAge6.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res6_15[j] = res6_15[j] / 9863
        j += 1

res7_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_15[j] += dfAge7.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res7_15[j] = res7_15[j] / 9863
        j += 1

res8_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_15[j] += dfAge8.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res8_15[j] = res8_15[j] / 9863
        j += 1

res9_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_15[j] += dfAge9.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res9_15[j] = res9_15[j] / 9863
        j += 1

res10_15 = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_15[j] += dfAge10.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res10_15[j] = res10_15[j] / 9863
        j += 1

# Find mean values for each week in different etiologies
res1_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_A[j] += dfEti1.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res1_A[j] = res1_A[j] / 9863
        j += 1

res2_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_A[j] += dfEti2.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res2_A[j] = res2_A[j] / 9863
        j += 1

res3_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_A[j] += dfEti3.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res3_A[j] = res3_A[j] / 9863
        j += 1

res4_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_A[j] += dfEti4.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res4_A[j] = res4_A[j] / 9863
        j += 1

res5_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_A[j] += dfEti5.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res5_A[j] = res5_A[j] / 9863
        j += 1

res6_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_A[j] += dfEti6.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res6_A[j] = res6_A[j] / 9863
        j += 1

res7_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_A[j] += dfEti7.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res7_A[j] = res7_A[j] / 9863
        j += 1

res8_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_A[j] += dfEti8.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res8_A[j] = res8_A[j] / 9863
        j += 1

res9_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_A[j] += dfEti9.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res9_A[j] = res9_A[j] / 9863
        j += 1

res10_A = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_A[j] += dfEti10.iloc[i, 0]
    if (i + 1) % 7 == 0:
        res10_A[j] = res10_A[j] / 9863
        j += 1

res1_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_B[j] += dfEti1.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res1_B[j] = res1_B[j] / 9863
        j += 1

res2_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_B[j] += dfEti2.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res2_B[j] = res2_B[j] / 9863
        j += 1

res3_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_B[j] += dfEti3.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res3_B[j] = res3_B[j] / 9863
        j += 1

res4_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_B[j] += dfEti4.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res4_B[j] = res4_B[j] / 9863
        j += 1

res5_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_B[j] += dfEti5.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res5_B[j] = res5_B[j] / 9863
        j += 1

res6_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_B[j] += dfEti6.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res6_B[j] = res6_B[j] / 9863
        j += 1

res7_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_B[j] += dfEti7.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res7_B[j] = res7_B[j] / 9863
        j += 1

res8_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_B[j] += dfEti8.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res8_B[j] = res8_B[j] / 9863
        j += 1

res9_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_B[j] += dfEti9.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res9_B[j] = res9_B[j] / 9863
        j += 1

res10_B = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_B[j] += dfEti10.iloc[i, 1]
    if (i + 1) % 7 == 0:
        res10_B[j] = res10_B[j] / 9863
        j += 1

res1_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_RV[j] += dfEti1.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res1_RV[j] = res1_RV[j] / 9863
        j += 1

res2_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_RV[j] += dfEti2.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res2_RV[j] = res2_RV[j] / 9863
        j += 1

res3_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_RV[j] += dfEti3.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res3_RV[j] = res3_RV[j] / 9863
        j += 1

res4_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_RV[j] += dfEti4.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res4_RV[j] = res4_RV[j] / 9863
        j += 1

res5_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_RV[j] += dfEti5.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res5_RV[j] = res5_RV[j] / 9863
        j += 1

res6_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_RV[j] += dfEti6.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res6_RV[j] = res6_RV[j] / 9863
        j += 1

res7_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_RV[j] += dfEti7.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res7_RV[j] = res7_RV[j] / 9863
        j += 1

res8_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_RV[j] += dfEti8.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res8_RV[j] = res8_RV[j] / 9863
        j += 1

res9_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_RV[j] += dfEti9.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res9_RV[j] = res9_RV[j] / 9863
        j += 1

res10_RV = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_RV[j] += dfEti10.iloc[i, 2]
    if (i + 1) % 7 == 0:
        res10_RV[j] = res10_RV[j] / 9863
        j += 1

res1_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_RSV[j] += dfEti1.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res1_RSV[j] = res1_RSV[j] / 9863
        j += 1

res2_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_RSV[j] += dfEti2.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res2_RSV[j] = res2_RSV[j] / 9863
        j += 1

res3_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_RSV[j] += dfEti3.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res3_RSV[j] = res3_RSV[j] / 9863
        j += 1

res4_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_RSV[j] += dfEti4.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res4_RSV[j] = res4_RSV[j] / 9863
        j += 1

res5_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_RSV[j] += dfEti5.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res5_RSV[j] = res5_RSV[j] / 9863
        j += 1

res6_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_RSV[j] += dfEti6.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res6_RSV[j] = res6_RSV[j] / 9863
        j += 1

res7_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_RSV[j] += dfEti7.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res7_RSV[j] = res7_RSV[j] / 9863
        j += 1

res8_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_RSV[j] += dfEti8.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res8_RSV[j] = res8_RSV[j] / 9863
        j += 1

res9_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_RSV[j] += dfEti9.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res9_RSV[j] = res9_RSV[j] / 9863
        j += 1

res10_RSV = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_RSV[j] += dfEti10.iloc[i, 3]
    if (i + 1) % 7 == 0:
        res10_RSV[j] = res10_RSV[j] / 9863
        j += 1

res1_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_AdV[j] += dfEti1.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res1_AdV[j] = res1_AdV[j] / 9863
        j += 1

res2_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_AdV[j] += dfEti2.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res2_AdV[j] = res2_AdV[j] / 9863
        j += 1

res3_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_AdV[j] += dfEti3.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res3_AdV[j] = res3_AdV[j] / 9863
        j += 1

res4_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_AdV[j] += dfEti4.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res4_AdV[j] = res4_AdV[j] / 9863
        j += 1

res5_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_AdV[j] += dfEti5.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res5_AdV[j] = res5_AdV[j] / 9863
        j += 1

res6_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_AdV[j] += dfEti6.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res6_AdV[j] = res6_AdV[j] / 9863
        j += 1

res7_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_AdV[j] += dfEti7.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res7_AdV[j] = res7_AdV[j] / 9863
        j += 1

res8_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_AdV[j] += dfEti8.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res8_AdV[j] = res8_AdV[j] / 9863
        j += 1

res9_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_AdV[j] += dfEti9.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res9_AdV[j] = res9_AdV[j] / 9863
        j += 1

res10_AdV = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_AdV[j] += dfEti10.iloc[i, 4]
    if (i + 1) % 7 == 0:
        res10_AdV[j] = res10_AdV[j] / 9863
        j += 1

res1_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_PIV[j] += dfEti1.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res1_PIV[j] = res1_PIV[j] / 9863
        j += 1

res2_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_PIV[j] += dfEti2.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res2_PIV[j] = res2_PIV[j] / 9863
        j += 1

res3_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_PIV[j] += dfEti3.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res3_PIV[j] = res3_PIV[j] / 9863
        j += 1

res4_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_PIV[j] += dfEti4.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res4_PIV[j] = res4_PIV[j] / 9863
        j += 1

res5_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_PIV[j] += dfEti5.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res5_PIV[j] = res5_PIV[j] / 9863
        j += 1

res6_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_PIV[j] += dfEti6.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res6_PIV[j] = res6_PIV[j] / 9863
        j += 1

res7_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_PIV[j] += dfEti7.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res7_PIV[j] = res7_PIV[j] / 9863
        j += 1

res8_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_PIV[j] += dfEti8.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res8_PIV[j] = res8_PIV[j] / 9863
        j += 1

res9_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_PIV[j] += dfEti9.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res9_PIV[j] = res9_PIV[j] / 9863
        j += 1

res10_PIV = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_PIV[j] += dfEti10.iloc[i, 5]
    if (i + 1) % 7 == 0:
        res10_PIV[j] = res10_PIV[j] / 9863
        j += 1

res1_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res1_CoV[j] += dfEti1.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res1_CoV[j] = res1_CoV[j] / 9863
        j += 1

res2_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res2_CoV[j] += dfEti2.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res2_CoV[j] = res2_CoV[j] / 9863
        j += 1

res3_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res3_CoV[j] += dfEti3.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res3_CoV[j] = res3_CoV[j] / 9863
        j += 1

res4_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res4_CoV[j] += dfEti4.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res4_CoV[j] = res4_CoV[j] / 9863
        j += 1

res5_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res5_CoV[j] += dfEti5.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res5_CoV[j] = res5_CoV[j] / 9863
        j += 1

res6_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res6_CoV[j] += dfEti6.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res6_CoV[j] = res6_CoV[j] / 9863
        j += 1

res7_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res7_CoV[j] += dfEti7.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res7_CoV[j] = res7_CoV[j] / 9863
        j += 1

res8_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res8_CoV[j] += dfEti8.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res8_CoV[j] = res8_CoV[j] / 9863
        j += 1

res9_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res9_CoV[j] += dfEti9.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res9_CoV[j] = res9_CoV[j] / 9863
        j += 1

res10_CoV = np.zeros(53)
j = 0
for i in range(0, 365):
    res10_CoV[j] += dfEti10.iloc[i, 6]
    if (i + 1) % 7 == 0:
        res10_CoV[j] = res10_CoV[j] / 9863
        j += 1

mean_res_recorded = (np.array(res_recorded1[:52]) + np.array(res_recorded2[:52]) +
                     np.array(res_recorded3[:52]) + np.array(res_recorded4[:52]) + np.array(res_recorded5[:52]) +
                     np.array(res_recorded6[:52]) + np.array(res_recorded7[:52]) + np.array(res_recorded8[:52]) +
                     np.array(res_recorded9[:52]) + np.array(res_recorded10[:52])) / 10

plt.figure()
plt.plot(np.linspace(1, 52, 52), res_recorded1[:52], label="модель")
# plt.plot(np.linspace(1, 52, 52), res_recorded2[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded3[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded4[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded5[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded6[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded7[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded8[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded9[:52])
# plt.plot(np.linspace(1, 52, 52), res_recorded10[:52])
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
# plt.plot(np.linspace(1, 52, 52), mean_res_recorded[:52], c='b', label="модель")
plt.title("Регистрируемая общая заболеваемость")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.legend()
plt.show()

mean_res_recorded_daily = (np.array(df1.iloc[:, 1]) + np.array(df2.iloc[:, 1]) +
                           np.array(df3.iloc[:, 1]) + np.array(df4.iloc[:, 1]) + np.array(df5.iloc[:, 1]) +
                           np.array(df6.iloc[:, 1]) + np.array(df7.iloc[:, 1]) + np.array(df8.iloc[:, 1]) +
                           np.array(df9.iloc[:, 1]) + np.array(df10.iloc[:, 1])) / 10

# corr, pval = pearsonr(list(temp_arr), list(mean_res_recorded_daily))
corr, pval = pearsonr(list(temp_arr), list(np.array(df1.iloc[:, 1])))
print('Pearson correlation for model: %.3f' % corr)
print('P-value: %.5f' % pval)


corr, pval = pearsonr(list(temp_arr)[:183], list(np.array(df1.iloc[:183, 1])))
print('Pearson correlation for model 1-st half: %.3f' % corr)
print('P-value: %.5f' % pval)


corr, pval = pearsonr(list(temp_arr)[183:], list(np.array(df1.iloc[183:, 1])))
print('Pearson correlation for model 2-nd half: %.3f' % corr)
print('P-value: %.5f' % pval)


# plt.figure()
# plt.plot(np.linspace(1, 52, 52), mean_flu, c='r')
# plt.title("Общая заболеваемость")
# plt.xlabel("Месяц")
# plt.ylabel("Количество случаев на 1000 чел.")
# plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
# plt.show()

diff = 0
for week in range(1, 53):
    diff += (mean_flu[str(week)] - mean_res_recorded[week - 1]) * (mean_flu[str(week)] - mean_res_recorded[week - 1])
# for week in range(1, 53):
#     diff += (mean_flu[str(week)] - np.array(res_recorded1[:52])[week - 1]) * (mean_flu[str(week)] - np.array(res_recorded1[:52])[week - 1])

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

corr, pval = pearsonr(list(temp_arr2[:27]), list(mean_flu[:27]))
print('Pearson correlation for data 1-st half: %.3f' % corr)
print('P-value: %.5f' % pval)

print(list(temp_arr2[:27]))
print(list(mean_flu[:27]))

corr, pval = pearsonr(list(temp_arr2[27:]), list(mean_flu[27:]))
print('Pearson correlation for data 2-nd half: %.3f' % corr)
print('P-value: %.5f' % pval)

print(list(temp_arr2[27:]))
print(list(mean_flu[27:]))

plt.figure()
plt.scatter(temp_arr2, mean_flu, c='r', label="данные")
plt.title("Зависимость регистрируемой заболеваемости от температуры")

plt.scatter(temp_arr2, np.array(res_recorded1[:52]), c='b', label="модель")
# plt.scatter(temp_arr2, mean_res_recorded[:52], c='b', label="модель")
plt.xlabel("Температура воздуха, ℃")
plt.ylabel("Количество случаев на 1000 чел.")
plt.legend()
plt.show()

mean_flu = flu.mean(axis=1)

mean_res_A = (np.array(res1_A[:52]) + np.array(res2_A[:52]) +
              np.array(res3_A[:52]) + np.array(res4_A[:52]) + np.array(res5_A[:52]) +
              np.array(res6_A[:52]) + np.array(res7_A[:52]) + np.array(res8_A[:52]) +
              np.array(res9_A[:52]) + np.array(res10_A[:52])) / 10

mean_res_B = (np.array(res1_B[:52]) + np.array(res2_B[:52]) +
              np.array(res3_B[:52]) + np.array(res4_B[:52]) + np.array(res5_B[:52]) +
              np.array(res6_B[:52]) + np.array(res7_B[:52]) + np.array(res8_B[:52]) +
              np.array(res9_B[:52]) + np.array(res10_B[:52])) / 10

mean_res_RV = (np.array(res1_RV[:52]) + np.array(res2_RV[:52]) +
               np.array(res3_RV[:52]) + np.array(res4_RV[:52]) + np.array(res5_RV[:52]) +
               np.array(res6_RV[:52]) + np.array(res7_RV[:52]) + np.array(res8_RV[:52]) +
               np.array(res9_RV[:52]) + np.array(res10_RV[:52])) / 10

mean_res_RSV = (np.array(res1_RSV[:52]) + np.array(res2_RSV[:52]) +
                np.array(res3_RSV[:52]) + np.array(res4_RSV[:52]) + np.array(res5_RSV[:52]) +
                np.array(res6_RSV[:52]) + np.array(res7_RSV[:52]) + np.array(res8_RSV[:52]) +
                np.array(res9_RSV[:52]) + np.array(res10_RSV[:52])) / 10

mean_res_AdV = (np.array(res1_AdV[:52]) + np.array(res2_AdV[:52]) +
                np.array(res3_AdV[:52]) + np.array(res4_AdV[:52]) + np.array(res5_AdV[:52]) +
                np.array(res6_AdV[:52]) + np.array(res7_AdV[:52]) + np.array(res8_AdV[:52]) +
                np.array(res9_AdV[:52]) + np.array(res10_AdV[:52])) / 10

mean_res_PIV = (np.array(res1_PIV[:52]) + np.array(res2_PIV[:52]) +
                np.array(res3_PIV[:52]) + np.array(res4_PIV[:52]) + np.array(res5_PIV[:52]) +
                np.array(res6_PIV[:52]) + np.array(res7_PIV[:52]) + np.array(res8_PIV[:52]) +
                np.array(res9_PIV[:52]) + np.array(res10_PIV[:52])) / 10

mean_res_CoV = (np.array(res1_CoV[:52]) + np.array(res2_CoV[:52]) +
                np.array(res3_CoV[:52]) + np.array(res4_CoV[:52]) + np.array(res5_CoV[:52]) +
                np.array(res6_CoV[:52]) + np.array(res7_CoV[:52]) + np.array(res8_CoV[:52]) +
                np.array(res9_CoV[:52]) + np.array(res10_CoV[:52])) / 10

plt.figure()
# plt.plot(np.linspace(1, 52, 52), res1_A[:52], c='r', label="FluA")
# plt.plot(np.linspace(1, 52, 52), res1_B[:52], c='b', label="FluB")
# plt.plot(np.linspace(1, 52, 52), res1_RV[:52], c='g', label="RV")
# plt.plot(np.linspace(1, 52, 52), res1_RSV[:52], c='m', label="RSV")
# plt.plot(np.linspace(1, 52, 52), res1_AdV[:52], c='y', label="AdV")
# plt.plot(np.linspace(1, 52, 52), res1_PIV[:52], c='k', label="PIV")
# plt.plot(np.linspace(1, 52, 52), res1_CoV[:52], c='c', label="CoV")

plt.plot(np.linspace(1, 52, 52), mean_res_A[:52], c='r', label="FluA")
plt.plot(np.linspace(1, 52, 52), mean_res_B[:52], c='b', label="FluB")
plt.plot(np.linspace(1, 52, 52), mean_res_RV[:52], c='g', label="RV")
plt.plot(np.linspace(1, 52, 52), mean_res_RSV[:52], c='m', label="RSV")
plt.plot(np.linspace(1, 52, 52), mean_res_AdV[:52], c='y', label="AdV")
plt.plot(np.linspace(1, 52, 52), mean_res_PIV[:52], c='k', label="PIV")
plt.plot(np.linspace(1, 52, 52), mean_res_CoV[:52], c='c', label="CoV")
plt.title("Полученная заболеваемость для этиологии ОРЗ")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.legend()
plt.show()

flu = pd.read_csv(r'..\tables\flu0-2.csv', index_col=0)
flu = flu.loc[:, str(1999):]
for year in range(1999, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)


mean_res0_2_recorded = (np.array(res1_0_2[:52]) + np.array(res2_0_2[:52]) +
                        np.array(res3_0_2[:52]) + np.array(res4_0_2[:52]) + np.array(res5_0_2[:52]) +
                        np.array(res6_0_2[:52]) + np.array(res7_0_2[:52]) + np.array(res8_0_2[:52]) +
                        np.array(res9_0_2[:52]) + np.array(res10_0_2[:52])) / 10

mean_flu = flu.mean(axis=1)
plt.figure()
# plt.plot(np.linspace(1, 52, 52), res1_0_2[:52], label="модель")
# plt.plot(np.linspace(1, 52, 52), res2_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res3_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res4_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res5_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res6_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res7_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res8_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res9_0_2[:52])
# plt.plot(np.linspace(1, 52, 52), res10_0_2[:52])
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), mean_res0_2_recorded[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 0-2")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.legend()
plt.show()

diff1 = 0
for week in range(1, 53):
    diff1 += (mean_flu[week] - mean_res0_2_recorded[week - 1]) * (mean_flu[week] - mean_res0_2_recorded[week - 1])
# for week in range(1, 53):
#     diff1 += (mean_flu[week] - np.array(res1_0_2[:52])[week - 1]) * (mean_flu[week] - np.array(res1_0_2[:52])[week - 1])

print("Error0: ", diff1)

flu = pd.read_csv(r'..\tables\flu3-6.csv', index_col=0)
flu = flu.loc[:, str(1999):]
for year in range(1999, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)

mean_res3_6_recorded = (np.array(res1_3_6[:52]) + np.array(res2_3_6[:52]) +
                        np.array(res3_3_6[:52]) + np.array(res4_3_6[:52]) + np.array(res5_3_6[:52]) +
                        np.array(res6_3_6[:52]) + np.array(res7_3_6[:52]) + np.array(res8_3_6[:52]) +
                        np.array(res9_3_6[:52]) + np.array(res10_3_6[:52])) / 10

mean_flu = flu.mean(axis=1)
plt.figure()
# plt.plot(np.linspace(1, 52, 52), res1_3_6[:52], label="модель")
# plt.plot(np.linspace(1, 52, 52), res2_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res3_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res4_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res5_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res6_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res7_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res8_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res9_3_6[:52])
# plt.plot(np.linspace(1, 52, 52), res10_3_6[:52])
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), mean_res3_6_recorded[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 3-6")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.legend()
plt.show()

diff2 = 0
for week in range(1, 53):
    diff2 += (mean_flu[week] - mean_res3_6_recorded[week - 1]) * (mean_flu[week] - mean_res3_6_recorded[week - 1])
# for week in range(1, 53):
#     diff2 += (mean_flu[week] - np.array(res1_3_6[:52])[week - 1]) * (mean_flu[week] - np.array(res1_3_6[:52])[week - 1])

print("Error3: ", diff2)

flu = pd.read_csv(r'..\tables\flu7-14.csv', index_col=0)
flu = flu.loc[:, str(1999):]
for year in range(1999, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)


mean_res7_14_recorded = (np.array(res1_7_14[:52]) + np.array(res2_7_14[:52]) +
                         np.array(res3_7_14[:52]) + np.array(res4_7_14[:52]) + np.array(res5_7_14[:52]) +
                         np.array(res6_7_14[:52]) + np.array(res7_7_14[:52]) + np.array(res8_7_14[:52]) +
                         np.array(res9_7_14[:52]) + np.array(res10_7_14[:52])) / 10

mean_flu = flu.mean(axis=1)
plt.figure()
# plt.plot(np.linspace(1, 52, 52), res1_7_14[:52], label="модель")
# plt.plot(np.linspace(1, 52, 52), res2_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res3_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res4_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res5_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res6_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res7_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res8_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res9_7_14[:52])
# plt.plot(np.linspace(1, 52, 52), res10_7_14[:52])
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), mean_res7_14_recorded[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 7-14")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.legend()
plt.show()

diff3 = 0
for week in range(1, 53):
    diff3 += (mean_flu[week] - mean_res7_14_recorded[week - 1]) * (mean_flu[week] - mean_res7_14_recorded[week - 1])
# for week in range(1, 53):
#     diff3 += (mean_flu[week] - np.array(res1_7_14[:52])[week - 1]) * (mean_flu[week] - np.array(res1_7_14[:52])[week - 1])

print("Error7: ", diff3)

flu = pd.read_csv(r'..\tables\flu15+.csv', index_col=0)
flu = flu.loc[:, str(1999):]
for year in range(1999, 2003):
    for week in range(1, 53):
        flu.loc[week, str(year)] /= find_num_of_people_each_year(year)

mean_res15_recorded = (np.array(res1_15[:52]) + np.array(res2_15[:52]) +
                       np.array(res3_15[:52]) + np.array(res4_15[:52]) + np.array(res5_15[:52]) +
                       np.array(res6_15[:52]) + np.array(res7_15[:52]) + np.array(res8_15[:52]) +
                       np.array(res9_15[:52]) + np.array(res10_15[:52])) / 10

mean_flu = flu.mean(axis=1)
plt.figure()
# plt.plot(np.linspace(1, 52, 52), res1_15[:52], label="модель")
# plt.plot(np.linspace(1, 52, 52), res2_15[:52])
# plt.plot(np.linspace(1, 52, 52), res3_15[:52])
# plt.plot(np.linspace(1, 52, 52), res4_15[:52])
# plt.plot(np.linspace(1, 52, 52), res5_15[:52])
# plt.plot(np.linspace(1, 52, 52), res6_15[:52])
# plt.plot(np.linspace(1, 52, 52), res7_15[:52])
# plt.plot(np.linspace(1, 52, 52), res8_15[:52])
# plt.plot(np.linspace(1, 52, 52), res9_15[:52])
# plt.plot(np.linspace(1, 52, 52), res10_15[:52])
plt.plot(np.linspace(1, 52, 52), mean_flu, c='r', label="данные")
plt.plot(np.linspace(1, 52, 52), mean_res15_recorded[:52], c='b', label="модель")
plt.title("Регистрируемая заболеваемость для возрастной группы 15+")
plt.xlabel("Месяц")
plt.ylabel("Количество случаев на 1000 чел.")
plt.xticks(np.linspace(1, 52, 13), ('Авг', 'Сен', 'Окт', 'Ноя', 'Дек', 'Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн', 'Июл', 'Авг'))
plt.legend()
plt.show()

diff4 = 0
for week in range(1, 53):
    diff4 += (mean_flu[week] - mean_res15_recorded[week - 1]) * (mean_flu[week] - mean_res15_recorded[week - 1])
# for week in range(1, 53):
#     diff4 += (mean_flu[week] - np.array(res1_15[:52])[week - 1]) * (mean_flu[week] - np.array(res1_15[:52])[week - 1])

print("Error15: ", diff4)


print("Sum of errors: ", diff1 + diff2 + diff3 + diff4)
print("Global error: ", diff)
