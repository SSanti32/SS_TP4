import matplotlib.pyplot as plt

# Abrir archivos
file1 = open("../resources/verletAlg.txt", "r")
file2 = open("../resources/beemanAlg.txt", "r")
file3 = open("../resources/gearAlg.txt", "r")
file4 = open("../resources/analyticalVerlet.txt", "r")

# Leer archivos y almacenar datos
verletAlg = [float(line) for line in file1]
beemanAlg = [float(line) for line in file2]
gearAlg = [float(line) for line in file3]
analytical = [float(line) for line in file4]

# Obtener valores máximos y mínimos de cada función
verletAlg_max = max(verletAlg)
verletAlg_min = min(verletAlg)
beemanAlg_max = max(beemanAlg)
beemanAlg_min = min(beemanAlg)
gearAlg_max = max(gearAlg)
gearAlg_min = min(gearAlg)
analytical_max = max(analytical)
analytical_min = min(analytical)

# Cerrar archivos
file1.close()
file2.close()
file3.close()
file4.close()

# Crear variable t
t = list(range(len(verletAlg)))
t_2 = list(range(len(beemanAlg)))
t_3 = list(range(len(gearAlg)))
t_4 = list(range(len(analytical)))

# Graficar funciones
plt.semilogy(t, verletAlg, label="verletAlg")
plt.semilogy(t_2, beemanAlg, label="beemanAlg")
plt.semilogy(t_3, gearAlg, label="gearAlg")
plt.semilogy(t_4, analytical, label="analytical")
# plt.ylim([min(verletAlg_min, beemanAlg_min, gearAlg_min, analytical_min), max(verletAlg_max, beemanAlg_max, gearAlg_max, analytical_max)])

# Agregar leyenda y etiquetas
plt.legend()
plt.xlabel("Tiempo")
plt.ylabel("Posición")

# Mostrar gráfico
plt.show()
