import math
import random
from matplotlib import pyplot as plt


def plot_errors(x, y, errors):
    # x = [42,42.5,43,44,45,45.5,46,47,48,48.5,49,50,51,51.5,52,53,54,55,55.5,56]
    # y = [480.692399995544,562.741919993604,466.721399995814,351.344039997929,319.005119998385,280.581399998667,356.559159997748,263.155519999099,258.776599999381,254.464679999538,274.408199999459,232.910239999868,261.58183999929,282.794599998523,365.154199997487,252.808999999511,242.114599999814,302.39807999864,298.299119998756,240.475439999668]
    # errors = [163.774862509934,191.623647366411,200.757786547516,219.541092184623,171.874597928398,182.654992706214,171.826017713125,149.245650476884,134.321172040587,117.891195527561,129.529857650411,102.388219609566,123.712583237794,190.039680761353,180.164608889744,119.270542660976,97.0492204122282,156.688577397178,136.966720374438,126.487931362721]
    plt.errorbar(x, y, errors, fmt='o', color='black', ecolor='lightgray', elinewidth=3, capsize=0, linestyle='solid', linewidth=1, markersize=4)
    plt.xlabel("Posicion Y (cm)")
    plt.ylabel("Tiempo (s)")
    plt.show()


def load_data(file):
    ball_id = 0
    k = {}
    for line in file:
        positions = line.split()
        k[ball_id] = positions
        ball_id += 1
    return k

def calculate_norms(k0, k1):
    norms_k0 = []
    pos_index = 0
    while pos_index < 202:
        sum_norms = 0
        for key in k0.keys():
            pos_x_k0 = float(k0[key][pos_index])
            pos_y_k0 = float(k0[key][pos_index+1])
            pos_x_k1 = float(k1[key][pos_index])
            pos_y_k1 = float(k1[key][pos_index+1])
            sum_norms += math.sqrt(math.pow(pos_x_k1 - pos_x_k0,2) + math.pow(pos_y_k1 - pos_y_k0, 2))
        norms_k0.append(sum_norms)
        pos_index += 2
    return norms_k0


def plot_phi():
    file2 = open("../resources/positions-1-step2.0.txt", "r")
    file3 = open("../resources/positions-1-step3.0.txt", "r")
    file4 = open("../resources/positions-1-step4.0.txt", "r")
    file5 = open("../resources/positions-1-step5.0.txt", "r")
    file6 = open("../resources/positions-1-step6.0.txt", "r")

    k2 = load_data(file2)
    k3 = load_data(file3)
    k4 = load_data(file4)
    k5 = load_data(file5)
    k6 = load_data(file6)

    norms_k2 = calculate_norms(k2, k3)
    norms_k3 = calculate_norms(k3, k4)
    norms_k4 = calculate_norms(k4, k5)
    norms_k5 = calculate_norms(k5, k6)

    t = list(range(len(norms_k2)))
    plt.semilogy(t, norms_k2, label="k=2")
    plt.semilogy(t, norms_k3, label="k=3")
    plt.semilogy(t, norms_k4, label="k=4")
    plt.semilogy(t, norms_k5, label="k=5")
    plt.xlabel("Tiempo (s)")
    plt.ylabel("Φ (cm)")
    plt.legend()
    plt.show()