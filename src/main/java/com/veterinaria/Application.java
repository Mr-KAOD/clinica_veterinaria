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

    static void administrarDatosPrincipales(Scanner scan) {
        System.out.println("\n1. Administrar Veterinarios");
        System.out.println("2. Administrar Medicamentos");
        System.out.println("3. Administrar Examenes");
        System.out.println("4. Volver");
        int opcion = scan.nextInt(); scan.nextLine();
        switch (opcion) {
            case 1 -> administrarVeterinarios(scan);
            case 2 -> administrarMedicamentos(scan);
            case 3:
                System.out.print("Nombre examen: ");
                String nomEx = scan.nextLine();
                System.out.print("Costo: ");
                double costoEx = scan.nextDouble();
                examenes.add(new Examen(nomEx, costoEx));
                break;
            default:
                break;
        }
    }

    static void administrarVeterinarios(Scanner scan) {

        System.out.println("\n1. Listar");
        System.out.println("2. Agregar");
        System.out.println("3. Editar");
        System.out.println("4. Borrar");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = scan.nextInt();
        scan.nextLine();

        switch (opcion) {
            case 1 -> // Listar
                {
                    for (Veterinario v : veterinarios) {
                        System.out.println("- " + v.toString());
                    }
                }
            case 2 -> // Agregar
                {
                    System.out.print("Escriba los datos del veterinario ");
                    System.out.print("Nombre: ");
                    String nombre = scan.nextLine();
                    System.out.print("Especialidad: ");
                    String especialidad = scan.nextLine();
                    System.out.print("ID: ");
                    String id = scan.nextLine();

                    veterinarios.add(new Veterinario( nombre, especialidad, id));
                }
            case 3 -> // Editar
                {
                    System.out.print("Ingrese el registro del vetrinario que desea editar: ");                    
                    String veterinarioAEditadar = scan.nextLine();

                    boolean encontrado = false;

                    for (Veterinario v: veterinarios) {
                        if (v.getId().equals(veterinarioAEditadar)) {
                            System.out.println("Ingrese los nuevos datos");
                            System.out.print("Nombre: ");
                            String nombreEditado = scan.nextLine();
                            System.out.print("Esspecialidad: ");
                            String especialidadEditada = scan.nextLine();

                            v.setNombre(nombreEditado);
                            v.setEspecialidad(especialidadEditada);

                            encontrado = true;
                            break;
                        }

                        if (!encontrado) {
                            System.out.println("No se encontró un veterinario con ese id");
                        }
                    }
                }
            case 4 -> // Borrar
                {
                    System.out.print("Ingrese el ID del vetrinario que desea borrar: ");                    
                    String veterinarioABorrar = scan.nextLine();

                    boolean encontrado = false;

                    for (int i = 0; i < veterinarios.size(); i++) {
                        Veterinario veterinarioSeleccionado = veterinarios.get(i);
                        if (veterinarioSeleccionado.getId().equals(veterinarioABorrar)) {
                            System.out.println("Vetrinario seleccionado: ");
                            System.out.println(veterinarioSeleccionado.toString());
                            System.out.print("Desea borrar este registro (Si/No): ");
                            String borrar = scan.nextLine();

                            if (borrar.equalsIgnoreCase("si")) {
                                veterinarios.remove(i);
                                System.out.println("Registro eliminado exitosamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }

                            encontrado = true;
                            break;
                        }

                        if (!encontrado) {
                            System.out.println("No se encontró un veterinario con ese id");
                        }
                    }
                    
                }
            default -> System.out.println("Saliendo...");
        }
    }


    static void administrarMedicamentos(Scanner scan) {
        System.out.println("\n1. Listar");
        System.out.println("2. Agregar");
        System.out.println("3. Editar");
        System.out.println("4. Borrar");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = scan.nextInt();
        scan.nextLine();

        switch (opcion) {
            case 1 -> // Listar
                {
                    for (Medicamento m: medicamentos) {
                        System.out.println("-> " + m.toString());
                    }
                }
            case 2 -> // Agregar
                {
                    System.out.print("Nombre medicamento: ");
                    String nombreMedicamento = scan.nextLine();
                    System.out.print("Costo: ");
                    double costoMedicamento = scan.nextDouble();
                    medicamentos.add(new Medicamento(nombreMedicamento, costoMedicamento));
                }
            case 3 -> // Editar
                {
                    System.out.print("Ingrese el medicamento que desea editar: ");
                    String medicamentoAEditar = scan.nextLine();

                    boolean encontrado = false;

                    for (Medicamento m : medicamentos) {
                        if(m.getNombre().equals(medicamentoAEditar)) {
                            System.out.println("Ingrese los nuevos datos");
                            System.out.print("Nombre: ");
                            String nombreEditado = scan.nextLine();
                            System.out.print("Costo: ");
                            double costoEditado = scan.nextDouble();

                            m.setNombre(nombreEditado);
                            m.setCosto(costoEditado);

                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("No se encontró un medicamento por ese nombre.");
                    }
                }
            case 4 -> // Borrar
                {
                    System.out.print("Ingrese el nombre del medicamento que desea borrar: ");
                    String medicamentoABorrar = scan.nextLine();

                    boolean encontrado = false;

                    for (int i = 0; i < medicamentos.size(); i++) {
                        Medicamento medicamentoSeleccionado = medicamentos.get(i);

                        if (medicamentoSeleccionado.getNombre().equalsIgnoreCase(medicamentoABorrar)) {
                            System.out.print("Medicamento seleccionado: ");
                            System.out.println(medicamentoSeleccionado.toString());
                            System.out.print("Desea borrar este registro (Si/No): ");
                            String borrar = scan.nextLine();

                            if (borrar.equalsIgnoreCase(borrar)) {
                                medicamentos.remove(i);
                                System.out.println("Registro eliminado exitosamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }

                            encontrado = true;
                            break;
                        }

                        if (!encontrado) {
                            System.out.println("No se encontró un medicamento por ese nombre.");
                        }
                    }
                }
            default -> System.out.println("Saliendo...");
        }
    }

    static void administrarExamenes(Scanner scan) {
        System.out.println("\n1. Listar");
        System.out.println("2. Agregar");
        System.out.println("3. Editar");
        System.out.println("4. Borrar");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = scan.nextInt();
        scan.nextLine();

        switch (opcion) {
            case 1 -> // Listar
                {
                    for (Examen e : examenes) {
                        System.out.println("-> " + e.toString());
                    }
                }
            case 2 -> // Agregar
                {
                    System.out.print("Nombre examen: ");
                    String nomEx = scan.nextLine();
                    System.out.print("Costo: ");
                    double costoEx = scan.nextDouble();
                    examenes.add(new Examen(nomEx, costoEx));
                }
            case 3 -> // Editar
                {
                    System.out.print("Ingrese el nombre del examen que desea editar: ");                    
                    String examenAEditadar = scan.nextLine();

                    boolean encontrado = false;

                    for (Examen e : examenes) {
                        if (e.getNombre().equals(examenAEditadar)) {
                            System.out.println("Ingrese los nuevos datos");
                            System.out.print("Nombre: ");
                            String nombreEditado = scan.nextLine();
                            System.out.print("Costo: ");
                            double costoEditado = scan.nextDouble();

                            e.setNombre(nombreEditado);
                            e.setCosto(costoEditado);

                            encontrado = true;
                            break;
                        }

                        if (!encontrado) {
                            System.out.println("No se encontró un examen con ese nombre");
                        }
                    }
                }
            case 4 -> //borrar
                {
                    System.out.print("Ingrese el nombre del examen que desea borrar: ");                    
                    String examenABorrar = scan.nextLine();

                    boolean encontrado = false;

                    for (int i = 0; i < examenes.size(); i++) {
                        Examen examenSeleccionado = examenes.get(i);
                        if (examenSeleccionado.getNombre().equals(examenABorrar)) {
                            System.out.println("Examen seleccionado: ");
                            System.out.println(examenSeleccionado.toString());
                            System.out.print("Desea borrar este registro (Si/No): ");
                            String borrar = scan.nextLine();

                            if (borrar.equalsIgnoreCase("si")) {
                                examenes.remove(i);
                                System.out.println("Registro eliminado exitosamente.");
                            } else {
                                System.out.println("Operación cancelada.");
                            }

                            encontrado = true;
                            break;
                        }

                        if (!encontrado) {
                            System.out.println("No se encontró un examen con ese nombre");
                        }
                    }
                }
            default -> System.out.println("Saliendo...");
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
}