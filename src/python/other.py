import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
# import osmium as osm


# class OSMHandler(osm.SimpleHandler):
#     def __init__(self):
#         osm.SimpleHandler.__init__(self)
#         self.osm_data = []
#
#     def tag_inventory(self, elem, elem_type):
#         for tag in elem.tags:
#             self.osm_data.append([elem_type,
#                                   elem.id,
#                                   elem.version,
#                                   elem.visible,
#                                   pd.Timestamp(elem.timestamp),
#                                   elem.uid,
#                                   elem.user,
#                                   elem.changeset,
#                                   len(elem.tags),
#                                   tag.k,
#                                   tag.v])
#
#     def node(self, n):
#         self.tag_inventory(n, "node")
#
#     def way(self, w):
#         self.tag_inventory(w, "way")
#
#     def relation(self, r):
#         self.tag_inventory(r, "relation")


# def read_osm(file):
#     osmhandler = OSMHandler()
#     # scan the input file and fills the handler list accordingly
#     osmhandler.apply_file(file)
#
#     # transform the list into a pandas DataFrame
#     data_colnames = ['type', 'id', 'version', 'visible', 'ts', 'uid',
#                      'user', 'chgset', 'ntags', 'tagkey', 'tagvalue']
#     df_osm = pd.DataFrame(osmhandler.osm_data, columns=data_colnames)
#     df_osm = df_osm[['tagkey', 'tagvalue']]
#     df_osm_X = df_osm[df_osm['tagkey'] == 'X']
#     df_osm_Y = df_osm[df_osm['tagkey'] == 'Y']
#     out_coordinates = []
#     for i in range(len(df_osm_X['tagvalue'])):
#         out_coordinates.append(np.array((float(df_osm_X.iloc[i, 1].replace(',', '.')),
#                                          float(df_osm_Y.iloc[i, 1].replace(',', '.')))))
#     return np.array(out_coordinates)


def deg2rad(deg):
    return deg * (np.pi/180)


def get_distance_from_lat_lon(lat1, lon1, lat2, lon2):
    R = 6371
    dLat = deg2rad(lat2-lat1)
    dLon = deg2rad(lon2-lon1)
    a = np.sin(dLat/2) * np.sin(dLat/2) + np.cos(deg2rad(lat1)) * np.cos(deg2rad(lat2)) * np.sin(dLon/2) * np.sin(dLon/2)
    c = 2 * np.arctan2(np.sqrt(a), np.sqrt(1-a))
    d = R * c
    return d


def find_closest_metro_stations():
    metro = pd.read_excel(r'..\tables\metro_coordinates.xlsx', header=None)
    homes = pd.read_excel(r'..\tables\homes.xlsx', header=None)
    metro_indexes = []
    for home in homes.values:
        min_norm = 99999
        k = 0
        min_dist_metro = 0
        for coord in metro.values:
            n = get_distance_from_lat_lon(home[1], home[0], coord[1], coord[0])
            if n < min_norm:
                min_norm = n
                min_dist_metro = k
            k += 1
        metro_indexes.append(min_dist_metro)
    pd.Series(metro_indexes).to_excel(r'..\tables\metro_homes.xlsx', index=False, header=None)


def find_closest_kindergartens():
    kindergartens = pd.read_excel(r'..\tables\kindergarten_coordinates.xlsx', header=None)
    homes = pd.read_excel(r'..\tables\homes.xlsx', header=None)
    kindergarten_indexes = []
    for home in homes.values:
        min_norm = 99999
        k = 0
        min_dist_kindergarten = 0
        for coord in kindergartens.values:
            n = get_distance_from_lat_lon(home[1], home[0], coord[1], coord[0])
            if n < min_norm:
                min_norm = n
                min_dist_kindergarten = k
            k += 1
        kindergarten_indexes.append(min_dist_kindergarten)
    pd.Series(kindergarten_indexes).to_excel(r'..\tables\kindergarten_homes.xlsx', index=False, header=None)


def find_closest_schools():
    schools = pd.read_excel(r'..\tables\school_coordinates.xlsx', header=None)
    homes = pd.read_excel(r'..\tables\homes.xlsx', header=None)
    school_indexes = []
    for home in homes.values:
        min_norm = 99999
        k = 0
        min_dist_school = 0
        for coord in schools.values:
            n = get_distance_from_lat_lon(home[1], home[0], coord[1], coord[0])
            if n < min_norm:
                min_norm = n
                min_dist_school = k
            k += 1
        school_indexes.append(min_dist_school)
    pd.Series(school_indexes).to_excel(r'..\tables\school_homes.xlsx', index=False, header=None)


def find_closest_metro_stations_to_public_spaces():
    metro = pd.read_excel(r'..\tables\metro_coordinates.xlsx', header=None)
    publicspaces = pd.read_excel(r'..\tables\publicspaces_coordinates.xlsx', header=None)
    metro_indexes = []
    for publicspace in publicspaces.values:
        min_norm = 99999
        k = 0
        min_dist_metro = 0
        for coord in metro.values:
            n = get_distance_from_lat_lon(publicspace[1], publicspace[0], coord[1], coord[0])
            if n < min_norm:
                min_norm = n
                min_dist_metro = k
            k += 1
        metro_indexes.append(min_dist_metro)
    pd.Series(metro_indexes).to_excel(r'..\tables\metro_publicspaces.xlsx', index=False, header=None)


def matrix_to_list(matrix):
    graph = {}
    for i, node in enumerate(matrix):
        adj = []
        for j, connected in enumerate(node):
            if connected:
                adj.append(j)
        graph[i] = adj
    return graph


def bfs(matrix, v):
    graph = matrix_to_list(matrix)
    all = []
    Q = [v]
    ancestors = [-1]
    while Q:
        v = Q.pop(0)
        all.append(v)
        for n in graph[v]:
            if n not in Q and\
                    n not in all:
                Q.append(n)
                ancestors.append(v)
    return np.array(all), np.array(ancestors)


def find_way(s, e):
    res, anc = bfs(get_metro_adjacency_matrix(), s)
    pos = np.argwhere(res == e).flatten()[0]
    way = [e, anc[pos]]
    while anc[pos] != -1:
        pos = np.argwhere(res == anc[pos]).flatten()[0]
        way.append(anc[pos])
    way.pop(-1)
    return np.array(way)[::-1]


def way_for_each_station_to_csv():
    arr = []
    # for i in range(num_of_stations):
    #     for j in range(num_of_stations):
    #         arr.append(find_way(i, j))
    # b = open(r"C:\Users\sigla\Desktop\MasterWork\ShortestMetroPaths.csv", 'w', newline='')
    # writer = csv.writer(b)
    # writer.writerows(arr)
    # b.close()


def get_metro_adjacency_matrix():
    df = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\list_of_moscow_metro_stations.csv')
    # metro_adjacency_matrix = np.zeros([num_of_stations, num_of_stations])
    # for i in range(num_of_stations - 1):
    #     if df.loc[i, 'Line'] == df.loc[i + 1, 'Line']:
    #         metro_adjacency_matrix[i, i + 1] = 1
    #         metro_adjacency_matrix[i + 1, i] = 1

    metro_adjacency_matrix = np.zeros([500, 500])

    metro_adjacency_matrix[159, 158] = 1
    metro_adjacency_matrix[158, 157] = 1
    metro_adjacency_matrix[157, 156] = 1
    metro_adjacency_matrix[156, 155] = 1
    metro_adjacency_matrix[155, 154] = 1
    metro_adjacency_matrix[154, 153] = 1
    metro_adjacency_matrix[153, 152] = 1
    metro_adjacency_matrix[152, 151] = 1
    metro_adjacency_matrix[151, 150] = 1
    metro_adjacency_matrix[150, 149] = 1
    metro_adjacency_matrix[149, 148] = 1

    metro_adjacency_matrix[215, 214] = 1
    metro_adjacency_matrix[214, 213] = 1
    metro_adjacency_matrix[213, 212] = 1
    metro_adjacency_matrix[212, 211] = 1
    metro_adjacency_matrix[211, 210] = 1
    metro_adjacency_matrix[210, 209] = 1
    metro_adjacency_matrix[209, 208] = 1
    metro_adjacency_matrix[208, 207] = 1
    metro_adjacency_matrix[207, 206] = 1
    metro_adjacency_matrix[206, 205] = 1
    metro_adjacency_matrix[205, 204] = 1
    metro_adjacency_matrix[204, 203] = 1
    metro_adjacency_matrix[203, 202] = 1
    metro_adjacency_matrix[202, 201] = 1
    metro_adjacency_matrix[201, 200] = 1
    metro_adjacency_matrix[200, 199] = 1
    metro_adjacency_matrix[199, 198] = 1
    metro_adjacency_matrix[198, 197] = 1
    metro_adjacency_matrix[197, 196] = 1
    metro_adjacency_matrix[196, 195] = 1
    metro_adjacency_matrix[195, 194] = 1
    metro_adjacency_matrix[194, 193] = 1
    metro_adjacency_matrix[193, 192] = 1
    metro_adjacency_matrix[192, 191] = 1
    metro_adjacency_matrix[191, 190] = 1
    metro_adjacency_matrix[190, 189] = 1
    metro_adjacency_matrix[189, 188] = 1
    metro_adjacency_matrix[188, 187] = 1
    metro_adjacency_matrix[187, 186] = 1
    metro_adjacency_matrix[186, 185] = 1

    metro_adjacency_matrix[148, 159] = 1
    metro_adjacency_matrix[159, 148] = 1
    metro_adjacency_matrix[185, 215] = 1
    metro_adjacency_matrix[215, 185] = 1

    metro_adjacency_matrix[2, 194] = 1
    metro_adjacency_matrix[194, 2] = 1

    metro_adjacency_matrix[4, 168] = 1
    metro_adjacency_matrix[168, 4] = 1

    metro_adjacency_matrix[5, 152] = 1
    metro_adjacency_matrix[152, 5] = 1
    metro_adjacency_matrix[5, 141] = 1
    metro_adjacency_matrix[141, 5] = 1
    metro_adjacency_matrix[141, 152] = 1
    metro_adjacency_matrix[152, 141] = 1

    metro_adjacency_matrix[6, 17] = 1
    metro_adjacency_matrix[17, 6] = 1
    metro_adjacency_matrix[6, 40] = 1
    metro_adjacency_matrix[40, 6] = 1
    metro_adjacency_matrix[17, 40] = 1
    metro_adjacency_matrix[40, 17] = 1

    metro_adjacency_matrix[9, 213] = 1
    metro_adjacency_matrix[213, 9] = 1

    metro_adjacency_matrix[13, 159] = 1
    metro_adjacency_matrix[159, 13] = 1

    metro_adjacency_matrix[15, 138] = 1
    metro_adjacency_matrix[138, 15] = 1
    metro_adjacency_matrix[15, 114] = 1
    metro_adjacency_matrix[114, 15] = 1
    metro_adjacency_matrix[114, 138] = 1
    metro_adjacency_matrix[138, 114] = 1

    metro_adjacency_matrix[16, 62] = 1
    metro_adjacency_matrix[62, 16] = 1
    metro_adjacency_matrix[16, 81] = 1
    metro_adjacency_matrix[81, 16] = 1
    metro_adjacency_matrix[62, 81] = 1
    metro_adjacency_matrix[81, 62] = 1

    metro_adjacency_matrix[18, 153] = 1
    metro_adjacency_matrix[153, 18] = 1

    metro_adjacency_matrix[19, 200] = 1
    metro_adjacency_matrix[200, 19] = 1

    metro_adjacency_matrix[22, 181] = 1
    metro_adjacency_matrix[181, 22] = 1

    metro_adjacency_matrix[27, 179] = 1
    metro_adjacency_matrix[179, 27] = 1
    # Correct till here

    metro_adjacency_matrix[32, 187] = 1
    metro_adjacency_matrix[187, 32] = 1

    metro_adjacency_matrix[36, 149] = 1
    metro_adjacency_matrix[149, 36] = 1

    metro_adjacency_matrix[38, 60] = 1
    metro_adjacency_matrix[60, 38] = 1
    metro_adjacency_matrix[38, 166] = 1
    metro_adjacency_matrix[166, 38] = 1
    metro_adjacency_matrix[166, 60] = 1
    metro_adjacency_matrix[60, 166] = 1

    metro_adjacency_matrix[39, 140] = 1
    metro_adjacency_matrix[140, 39] = 1

    metro_adjacency_matrix[41, 155] = 1
    metro_adjacency_matrix[155, 41] = 1

    metro_adjacency_matrix[43, 204] = 1
    metro_adjacency_matrix[204, 43] = 1

    metro_adjacency_matrix[53, 190] = 1
    metro_adjacency_matrix[190, 53] = 1

    metro_adjacency_matrix[54, 191] = 1
    metro_adjacency_matrix[191, 54] = 1

    metro_adjacency_matrix[58, 150] = 1
    metro_adjacency_matrix[150, 58] = 1

    metro_adjacency_matrix[61, 139] = 1
    metro_adjacency_matrix[139, 61] = 1

    metro_adjacency_matrix[63, 82] = 1
    metro_adjacency_matrix[82, 63] = 1
    metro_adjacency_matrix[63, 115] = 1
    metro_adjacency_matrix[115, 63] = 1
    metro_adjacency_matrix[63, 101] = 1
    metro_adjacency_matrix[101, 63] = 1
    metro_adjacency_matrix[101, 82] = 1
    metro_adjacency_matrix[82, 101] = 1
    metro_adjacency_matrix[115, 82] = 1
    metro_adjacency_matrix[82, 115] = 1
    metro_adjacency_matrix[115, 101] = 1
    metro_adjacency_matrix[101, 115] = 1

    metro_adjacency_matrix[65, 156] = 1
    metro_adjacency_matrix[156, 65] = 1

    metro_adjacency_matrix[67, 205] = 1
    metro_adjacency_matrix[205, 67] = 1

    metro_adjacency_matrix[76, 192] = 1
    metro_adjacency_matrix[192, 76] = 1

    metro_adjacency_matrix[80, 151] = 1
    metro_adjacency_matrix[151, 80] = 1
    metro_adjacency_matrix[80, 167] = 1
    metro_adjacency_matrix[167, 80] = 1
    metro_adjacency_matrix[167, 151] = 1
    metro_adjacency_matrix[151, 167] = 1

    metro_adjacency_matrix[84, 157] = 1
    metro_adjacency_matrix[157, 84] = 1
    metro_adjacency_matrix[84, 98] = 1
    metro_adjacency_matrix[98, 84] = 1
    metro_adjacency_matrix[98, 157] = 1
    metro_adjacency_matrix[157, 98] = 1

    metro_adjacency_matrix[87, 91] = 1
    metro_adjacency_matrix[91, 87] = 1

    metro_adjacency_matrix[96, 206] = 1
    metro_adjacency_matrix[206, 96] = 1

    metro_adjacency_matrix[101, 102] = 0
    metro_adjacency_matrix[102, 101] = 0
    metro_adjacency_matrix[98, 102] = 1
    metro_adjacency_matrix[102, 98] = 1

    metro_adjacency_matrix[103, 207] = 1
    metro_adjacency_matrix[207, 103] = 1

    metro_adjacency_matrix[107, 186] = 1
    metro_adjacency_matrix[186, 107] = 1

    metro_adjacency_matrix[108, 160] = 1
    metro_adjacency_matrix[160, 108] = 1

    metro_adjacency_matrix[112, 148] = 1
    metro_adjacency_matrix[148, 112] = 1

    metro_adjacency_matrix[113, 165] = 1
    metro_adjacency_matrix[165, 113] = 1

    metro_adjacency_matrix[117, 154] = 1
    metro_adjacency_matrix[154, 117] = 1

    metro_adjacency_matrix[119, 202] = 1
    metro_adjacency_matrix[202, 119] = 1

    metro_adjacency_matrix[122, 182] = 1
    metro_adjacency_matrix[182, 122] = 1

    metro_adjacency_matrix[133, 211] = 1
    metro_adjacency_matrix[211, 133] = 1

    metro_adjacency_matrix[134, 209] = 1
    metro_adjacency_matrix[209, 134] = 1

    metro_adjacency_matrix[137, 158] = 1
    metro_adjacency_matrix[158, 137] = 1

    # metro_adjacency_matrix[148, 178] = 1
    # metro_adjacency_matrix[178, 148] = 1

    metro_adjacency_matrix[142, 169] = 1
    metro_adjacency_matrix[169, 142] = 1

    metro_adjacency_matrix[170, 199] = 1
    metro_adjacency_matrix[199, 170] = 1

    metro_adjacency_matrix[184, 52] = 1
    metro_adjacency_matrix[52, 184] = 1

    return metro_adjacency_matrix


# df = pd.read_csv(r'C:\Users\sigla\Desktop\MasterWork\HomeCoordinates.csv', header=None)
# okato = '45277598000'
# df2 = df[df.iloc[:, 3] == okato]
# df2.iloc[:, 1:3].to_csv(r'C:\Users\sigla\Desktop\MasterWork\%s.csv' % okato, index=False, header=None)

find_closest_metro_stations_to_public_spaces()
# find_closest_metro_stations()
# find_closest_kindergartens()
# find_closest_schools()

# okatos = pd.read_excel(r'..\tables\okato.xlsx', header=None)
# for okato in okatos.values:
#     homes = pd.read_excel(r'..\tables\home_coordinates\%d.xlsx' % okato[0], header=None)
#     print(len(homes.values))
