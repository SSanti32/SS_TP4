import matplotlib.pyplot as plt

# Abrir archivos
file1 = open("../resources/verletAlg.txt", "r")
file2 = open("../resources/beemanAlg.txt", "r")
file3 = open("../resources/gearAlg.txt", "r")
file4 = open("../resources/analyticalVerlet.txt", "r")

# Leer archivos y almacenar datos
x1 = [float(line) for line in file1]
x2 = [float(line) for line in file2]
x3 = [float(line) for line in file3]
x4 = [float(line) for line in file4]

# Obtener valores máximos y mínimos de cada función
x1_max = max(x1)
x1_min = min(x1)
x2_max = max(x2)
x2_min = min(x2)
x3_max = max(x3)
x3_min = min(x3)
x4_max = max(x4)
x4_min = min(x4)

# Cerrar archivos
file1.close()
file2.close()
file3.close()
file4.close()

# Crear variable t
t = list(range(len(x1)))
t_2 = list(range(len(x2)))
t_3 = list(range(len(x3)))
t_4 = list(range(len(x4)))

# Graficar funciones
plt.semilogy(t, x1, label="Función 1")
plt.semilogy(t_2, x2, label="Función 2")
plt.semilogy(t_3, x3, label="Función 3")
plt.semilogy(t_4, x4, label="Función 4")
# plt.ylim([min(x1_min, x2_min, x3_min, x4_min), max(x1_max, x2_max, x3_max, x4_max)])

# Agregar leyenda y etiquetas
plt.legend()
plt.xlabel("Tiempo")
plt.ylabel("Posición")

# Mostrar gráfico
plt.show()
