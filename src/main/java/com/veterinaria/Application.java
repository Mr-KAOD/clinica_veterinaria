package com.veterinaria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {

    static ArrayList<Veterinario> veterinarios = new ArrayList<>();
    static ArrayList<Medicamento> medicamentos = new ArrayList<>();
    static ArrayList<Examen> examenes = new ArrayList<>();
    static ArrayList<Propietario> propietarios = new ArrayList<>();
    static ArrayList<Mascota> mascotas = new ArrayList<>();
    static ArrayList<Consulta> consultas = new ArrayList<>();

    public static void main(String[] args) {
        cargarDatosDesdeArchivo("datos.txt");
        Scanner scan = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n1. Administrar Datos Principales");
            System.out.println("2. Administrar Consulta");
            System.out.println("3. Reportes");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt();
            scan.nextLine();
            switch (opcion) {
                case 1:
                    administrarDatosPrincipales(scan);
                    break;
                case 2:
                    administrarConsulta(scan);
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
        scan.close();
    }

    static void cargarDatosDesdeArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesLineaTexto = linea.split(":");
                switch (partesLineaTexto[0]) {
                    case "Veterinario" -> veterinarios.add(new Veterinario(partesLineaTexto[1], partesLineaTexto[2], partesLineaTexto[3]));

                    case "Medicamento" -> medicamentos.add(new Medicamento(partesLineaTexto[1], Double.parseDouble(partesLineaTexto[2])));

                    case "Examen" -> examenes.add(new Examen(partesLineaTexto[1], Double.parseDouble(partesLineaTexto[2])));

                    case "Propietario" -> propietarios.add(new Propietario(partesLineaTexto[1], partesLineaTexto[2], partesLineaTexto[3], partesLineaTexto[4], partesLineaTexto[5]));
                    
                    case "Mascota" -> 
                        {
                            String especie = partesLineaTexto[2];
                            Propietario propietario = buscarPropietario(partesLineaTexto[8]);
                            if (especie.equalsIgnoreCase("Perro")) {
                                mascotas.add(new Perro(partesLineaTexto[1], partesLineaTexto[3], Integer.parseInt(partesLineaTexto[4]), partesLineaTexto[5],
                                        Double.parseDouble(partesLineaTexto[6]), Double.parseDouble(partesLineaTexto[7]), propietario, partesLineaTexto[9]));
                            } else if (especie.equalsIgnoreCase("Gato")) {
                                mascotas.add(new Gato(partesLineaTexto[1], partesLineaTexto[3], Integer.parseInt(partesLineaTexto[4]), partesLineaTexto[5],
                                        Double.parseDouble(partesLineaTexto[6]), Double.parseDouble(partesLineaTexto[7]), propietario,
                                        partesLineaTexto[9].equalsIgnoreCase("Si")));
                            }
                        }
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
        System.out.println("\n1. Administrar Veterinarios");
        System.out.println("2. Administrar Medicamentos");
        System.out.println("3. Administrar Examenes");
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

    static void administrarConsulta(Scanner scan) {
        System.out.print("Nombre del veterinario: ");
        Veterinario vet = buscarVeterinario(scan.nextLine());
        System.out.print("Nombre de la mascota: ");
        Mascota mas = buscarMascota(scan.nextLine());
        Consulta consulta = new Consulta(vet, mas, new Date());

        System.out.print("Ingrese diagnóstico: ");
        String diag = scan.nextLine();
        consulta.agregarDiagnostico(new Diagnostico(diag, new Date()));

        System.out.print("¿Agregar exámenes? (si/no): ");
        if (scan.nextLine().equalsIgnoreCase("si")) {
            for (int i = 0; i < examenes.size(); i++) {
                System.out.println((i + 1) + ". " + examenes.get(i).getCosto());
            }
            System.out.print("Seleccione examen: ");
            int exSel = scan.nextInt(); scan.nextLine();
            consulta.agregarExamen(examenes.get(exSel - 1));
        }

        System.out.print("¿Agregar tratamiento? (si/no): ");
        if (scan.nextLine().equalsIgnoreCase("si")) {
            Tratamiento t = new Tratamiento();
            while (true) {
                System.out.println("Lista de medicamentos:");
                for (int i = 0; i < medicamentos.size(); i++) {
                    System.out.println((i + 1) + ". " + medicamentos.get(i).getNombre());
                }
                System.out.print("Seleccione número: ");
                int medSel = scan.nextInt(); scan.nextLine();
                System.out.print("Frecuencia (4h/8h/12h/24h): ");
                String frec = scan.nextLine();
                System.out.print("Cantidad (1/2/3 dosis): ");
                String cant = scan.nextLine();
                t.agregarMedicamento(medicamentos.get(medSel - 1), new Dosis(frec, cant));
                System.out.print("¿Agregar otro? (si/no): ");
                if (!scan.nextLine().equalsIgnoreCase("si")) break;
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
    public class TestReportes {
        public static void main(String[] args) {
            // Veterinarios
            Veterinario v1 = new Veterinario("Dr. Pérez", "123", "Cirujano");
            Veterinario v2 = new Veterinario("Dra. Gómez", "456", "General");

            // Propietarios
            Propietario p1 = new Propietario("P01", "Juan", "López", "987654321", "Av. Siempre Viva 123");

            // Mascotas
            Mascota m1 = new Perro("Firulais", "Canino", 5, "Macho", 20.0, 50.0, p1, "Labrador");
            Mascota m2 = new Gato("Michi", "Felino", 3, "Hembra", 4.5, 25.0, p1, true);

            // Medicamentos
            Medicamento med1 = new Medicamento("Vacuna Rabia", 15.0);
            Medicamento med2 = new Medicamento("Desparasitante", 10.0);

            // Examenes
            Examen ex1 = new Examen("Sangre", 30.0);

            // Simular consultas
            Consulta c1 = new Consulta(v1, m1, new Date());
            c1.agregarDiagnostico(new Diagnostico("Gripe", new Date()));
            c1.agregarExamen(ex1);
            Tratamiento t1 = new Tratamiento();
            t1.agregarMedicamento(med1, new Dosis("12h", "2 dosis"));
            c1.agregarTratamiento(t1);

            Consulta c2 = new Consulta(v2, m2, new Date());
            c2.agregarDiagnostico(new Diagnostico("Parásitos", new Date()));
            Tratamiento t2 = new Tratamiento();
            t2.agregarMedicamento(med2, new Dosis("24h", "1 dosis"));
            c2.agregarTratamiento(t2);

            // Cargar datos en listas estáticas
            Application.veterinarios.add(v1);
            Application.veterinarios.add(v2);
            Application.propietarios.add(p1);
            Application.mascotas.add(m1);
            Application.mascotas.add(m2);
            Application.medicamentos.add(med1);
            Application.medicamentos.add(med2);
            Application.examenes.add(ex1);
            Application.consultas.add(c1);
            Application.consultas.add(c2);

            // Ejecutar el reporte
            Application.generarReportes();
        }
    }
}