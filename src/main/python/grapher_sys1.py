import matplotlib.pyplot as plt

def plot_positions(file1, file2, file3, file4):
    # Leer archivos y almacenar datos
    verletAlg = [float(line) for line in file1]
    beemanAlg = [float(line) for line in file2]
    gearAlg = [float(line) for line in file3]
    analytical = [float(line) for line in file4]

    # Crear variable t
    t = list(range(len(verletAlg)))
    t_2 = list(range(len(beemanAlg)))
    t_3 = list(range(len(gearAlg)))
    t_4 = list(range(len(analytical)))

    #Graficar funciones
    plt.plot(t, verletAlg, label="Verlet")
    plt.plot(t_2, beemanAlg, label="Beeman", linestyle="dashdot")
    plt.plot(t_3, gearAlg, label="Gear", linestyle="dashed")
    plt.plot(t_4, analytical, label="Analítica", linestyle='dashed')

    # Agregar leyenda y etiquetas
    plt.legend()
    plt.xlabel("Tiempo (ms)")
    plt.ylabel("Posición (m)")
    plt.show()


def graph_errors(t, verletAlg, beemanAlg, gearAlg, analytical):
    verlet_mse = 0
    beeman_mse = 0
    gear_mse = 0
    verletError = [0 for i in range(len(verletAlg))]
    for i in range(0, len(verletAlg)):
        verletError[i] = pow(analytical[i] - verletAlg[i],2)
        verlet_mse += verletError[i]
    verlet_mse /= len(verletAlg)

    beemanError = [0 for i in range(len(beemanAlg))]
    for i in range(0, len(beemanAlg)):
        beemanError[i] = pow(analytical[i] - beemanAlg[i],2)
        beeman_mse += beemanError[i]
    beeman_mse /= len(beemanAlg)

    gearError = [0 for i in range(len(gearAlg))]
    for i in range(0, len(gearAlg)):
        gearError[i] = pow(analytical[i] - gearAlg[i], 2)
        gear_mse += gearError[i]
    gear_mse /= len(gearAlg)
    print("verlet MSE: " + str(verlet_mse))
    print("beeman MSE: " + str(beeman_mse))
    print("gear MSE: " + str(gear_mse))

    plt.plot(t, verletError, label="Verlet")
    plt.plot(t, beemanError, label="Beeman")
    plt.plot(t, gearError, label="Gear")
    plt.show()

def plot_mse(dt, verlet, beeman, gear):
    plt.plot(dt, verlet, label="Verlet", marker="o")
    plt.plot(dt, beeman, label="Beeman", marker="o")
    plt.plot(dt, gear, label="Gear", marker="o")
    plt.yscale('log')
    plt.xscale('log')
    plt.legend()
    plt.xlabel("dt")
    plt.ylabel("Error Cuadrático (m²)")
    plt.show()



# Abrir archivos
file1 = open("../resources/verletAlg.txt", "r")
file2 = open("../resources/beemanAlg.txt", "r")
file3 = open("../resources/gearAlg.txt", "r")
file4 = open("../resources/analyticalVerlet.txt", "r")

# Cerrar archivos
file1.close()
file2.close()
file3.close()
file4.close()
