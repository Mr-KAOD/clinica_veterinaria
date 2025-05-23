package com.veterinaria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {

    static List<Veterinario> veterinarios = new ArrayList<>();
    static List<Medicamento> medicamentos = new ArrayList<>();
    static List<Examen> examenes = new ArrayList<>();
    static List<Propietario> propietarios = new ArrayList<>();
    static List<Mascota> mascotas = new ArrayList<>();
    static List<Consulta> consultas = new ArrayList<>();

    public static void main(String[] args) {
        cargarDatosDesdeArchivo("datos.txt");
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n1. Administrar Datos Principales");
            System.out.println("2. Administrar Consulta");
            System.out.println("3. Reportes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    administrarDatosPrincipales(sc);
                    break;
                case 2:
                    administrarConsulta(sc);
                    break;
                case 3:
                    generarReportes();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
        sc.close();
    }

    static void cargarDatosDesdeArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                switch (partes[0]) {
                    case "Veterinario":
                        veterinarios.add(new Veterinario(partes[1], partes[2], partes[3]));
                        break;
                    case "Medicamento":
                        medicamentos.add(new Medicamento(partes[1], Double.parseDouble(partes[2])));
                        break;
                    case "Examen":
                        examenes.add(new Examen(partes[1], Double.parseDouble(partes[2])));
                        break;
                    case "Propietario":
                        propietarios.add(new Propietario(partes[1], partes[2], partes[3], partes[4], partes[5]));
                        break;
                    case "Mascota":
                        String especie = partes[2];
                        Propietario propietario = buscarPropietario(partes[8]);
                        if (especie.equalsIgnoreCase("Perro")) {
                            mascotas.add(new Perro(partes[1], partes[3], Integer.parseInt(partes[4]), partes[5],
                                    Double.parseDouble(partes[6]), Double.parseDouble(partes[7]), propietario, partes[9]));
                        } else if (especie.equalsIgnoreCase("Gato")) {
                            mascotas.add(new Gato(partes[1], partes[3], Integer.parseInt(partes[4]), partes[5],
                                    Double.parseDouble(partes[6]), Double.parseDouble(partes[7]), propietario,
                                    partes[9].equalsIgnoreCase("Si")));
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    static Propietario buscarPropietario(String id) {
        for (Propietario p : propietarios) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    static Mascota buscarMascota(String nombre) {
        for (Mascota m : mascotas) {
            if (m.getNombre().equalsIgnoreCase(nombre)) return m;
        }
        return null;
    }

    static Veterinario buscarVeterinario(String nombre) {
        for (Veterinario v : veterinarios) {
            if (v.getNombre().equalsIgnoreCase(nombre)) return v;
        }
        return null;
    }

    static void administrarDatosPrincipales(Scanner sc) {
        System.out.println("\n1. Listar Veterinarios");
        System.out.println("2. Agregar Medicamento");
        System.out.println("3. Agregar Examen");
        System.out.println("4. Volver");
        int opcion = sc.nextInt(); sc.nextLine();
        switch (opcion) {
            case 1:
                for (Veterinario v : veterinarios) {
                    System.out.println("- " + v.getNombre());
                }
                break;
            case 2:
                System.out.print("Nombre medicamento: ");
                String nomMed = sc.nextLine();
                System.out.print("Costo: ");
                double costoMed = sc.nextDouble();
                medicamentos.add(new Medicamento(nomMed, costoMed));
                break;
            case 3:
                System.out.print("Nombre examen: ");
                String nomEx = sc.nextLine();
                System.out.print("Costo: ");
                double costoEx = sc.nextDouble();
                examenes.add(new Examen(nomEx, costoEx));
                break;
            default:
                break;
        }
    }

    static void administrarConsulta(Scanner sc) {
        System.out.print("Nombre del veterinario: ");
        Veterinario vet = buscarVeterinario(sc.nextLine());
        System.out.print("Nombre de la mascota: ");
        Mascota mas = buscarMascota(sc.nextLine());
        Consulta consulta = new Consulta(vet, mas, new Date());

        System.out.print("Ingrese diagnóstico: ");
        String diag = sc.nextLine();
        consulta.agregarDiagnostico(new Diagnostico(diag, new Date()));

        System.out.print("¿Agregar exámenes? (si/no): ");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            for (int i = 0; i < examenes.size(); i++) {
                System.out.println((i + 1) + ". " + examenes.get(i).getCosto());
            }
            System.out.print("Seleccione examen: ");
            int exSel = sc.nextInt(); sc.nextLine();
            consulta.agregarExamen(examenes.get(exSel - 1));
        }

        System.out.print("¿Agregar tratamiento? (si/no): ");
        if (sc.nextLine().equalsIgnoreCase("si")) {
            Tratamiento t = new Tratamiento();
            while (true) {
                System.out.println("Lista de medicamentos:");
                for (int i = 0; i < medicamentos.size(); i++) {
                    System.out.println((i + 1) + ". " + medicamentos.get(i).getNombre());
                }
                System.out.print("Seleccione número: ");
                int medSel = sc.nextInt(); sc.nextLine();
                System.out.print("Frecuencia (4h/8h/12h/24h): ");
                String frec = sc.nextLine();
                System.out.print("Cantidad (1/2/3 dosis): ");
                String cant = sc.nextLine();
                t.agregarMedicamento(medicamentos.get(medSel - 1), new Dosis(frec, cant));
                System.out.print("¿Agregar otro? (si/no): ");
                if (!sc.nextLine().equalsIgnoreCase("si")) break;
            }
            consulta.agregarTratamiento(t);
        }

        consultas.add(consulta);
        Factura factura = new Factura(consulta);
        System.out.println("Costo total consulta: " + factura.getTotal());
    }

    static void generarReportes() {
        int totalMascotas = consultas.size();
        int perros = 0, gatos = 0;
        Map<String, Integer> consultasPorVet = new HashMap<>();
        Map<String, Integer> medicamentoContador = new HashMap<>();
        Map<String, Integer> mascotaFrecuencia = new HashMap<>();

        for (Consulta c : consultas) {
            if (c.getMascota().especie.equalsIgnoreCase("Perro")) perros++;
            if (c.getMascota().especie.equalsIgnoreCase("Gato")) gatos++;
            String vet = c.getVeterinario().getNombre();
            consultasPorVet.put(vet, consultasPorVet.getOrDefault(vet, 0) + 1);
            String nombreMascota = c.getMascota().getNombre();
            mascotaFrecuencia.put(nombreMascota, mascotaFrecuencia.getOrDefault(nombreMascota, 0) + 1);
            if (c.getTratamiento() != null) {
                for (Medicamento m : c.getTratamiento().getMedicamentos()) {
                    medicamentoContador.put(m.getNombre(), medicamentoContador.getOrDefault(m.getNombre(), 0) + 1);
                }
            }
        }

        System.out.println("Mascotas atendidas: " + totalMascotas);
        if (totalMascotas > 0) {
            System.out.println("Perros: " + (perros * 100 / totalMascotas) + "%");
            System.out.println("Gatos: " + (gatos * 100 / totalMascotas) + "%");
        }

        System.out.println("\nConsultas por veterinario:");
        for (String vet : consultasPorVet.keySet()) {
            System.out.println(vet + ": " + consultasPorVet.get(vet));
        }

        System.out.println("\nMedicamento más suministrado:");
        String medMas = Collections.max(medicamentoContador.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println(medMas + " - " + medicamentoContador.get(medMas) + " veces");

        System.out.println("\nPaciente más recurrente:");
        String masRec = Collections.max(mascotaFrecuencia.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println(masRec + " - " + mascotaFrecuencia.get(masRec) + " visitas");
    }
}