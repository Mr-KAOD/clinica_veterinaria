package com.veterinaria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
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
                    System.out.println("*****Opción no válida*****");
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
                    case "VETERINARIO" -> veterinarios.add(new Veterinario(partesLineaTexto[1], partesLineaTexto[2], partesLineaTexto[3]));

                    case "MEDICAMENTO" -> medicamentos.add(new Medicamento(partesLineaTexto[1], Double.parseDouble(partesLineaTexto[2])));

                    case "EXAMEN" -> examenes.add(new Examen(partesLineaTexto[1], Double.parseDouble(partesLineaTexto[2])));

                    case "PROPIETARIO" -> propietarios.add(new Propietario(partesLineaTexto[1], partesLineaTexto[2], partesLineaTexto[3], partesLineaTexto[4], partesLineaTexto[5]));
                    
                    case "MASCOTA" -> 
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
        int opcion;
        boolean continuar;
        do {
            System.out.println("\n1. Administrar Veterinarios");
            System.out.println("2. Administrar Medicamentos");
            System.out.println("3. Administrar Exámenes");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt(); scan.nextLine();
            switch (opcion) {
                case 1 -> 
                    {
                        do {
                            continuar = administrarVeterinarios(scan);
                        } while(continuar);
                    } 
                case 2 -> 
                    {
                        do {
                            continuar = administrarMedicamentos(scan);
                        } while(continuar);
                    }
                case 3 -> 
                    {
                        do {
                            continuar = administrarExamenes(scan);
                        } while(continuar);
                    }
                case 4 -> System.out.println("\nVolviendo al menú principal...");
                default -> System.out.println("\n*****Opción no válida*****");

            }
        } while (opcion != 4);
    }

    static boolean administrarVeterinarios(Scanner scan) {

        System.out.println("\nVeterinarios");
        System.out.println("*****" + "1. Listar");
        System.out.println("*****" + "2. Agregar");
        System.out.println("*****" + "3. Editar");
        System.out.println("*****" + "4. Borrar");
        System.out.println("*****" + "5. Volver");
        System.out.print("Seleccione una opción: ");
        int opcion = scan.nextInt();
        scan.nextLine();
        System.out.println(""); // Espacio antes de cada funcion

        switch (opcion) {
            case 1 -> // Listar
                {
                    for (Veterinario v : veterinarios) {
                        System.out.println("-> " + v.toString());
                    }
                    return true;
                }
            case 2 -> // Agregar
                {
                    System.out.println("Escriba los datos del veterinario ");
                    System.out.print("Nombre: ");
                    String nombre = normalizarTexto(scan.nextLine());
                    System.out.print("Especialidad: ");
                    String especialidad = normalizarTexto(scan.nextLine());
                    System.out.print("ID: ");
                    String id;
                    boolean validacionId;

                    // Validacion ID
                    do {
                        id = normalizarTexto(scan.nextLine());
                        validacionId = true;

                        if (id.length() != 8) {
                            System.out.println("\nError: Debe tener exactamente 8 caracteres");
                            validacionId = false;
                        } else {
                            for (char c : id.toCharArray()) {
                                if (!Character.isLetterOrDigit(c)) {
                                    System.out.println("\nError: Solo se permiten letras y números");
                                    validacionId = false;
                                    break;
                                }
                            }
                        }

                        if (!validacionId) {
                            System.out.print("\nIngrese de nuevo el ID: ");
                        }
                    } while(!validacionId);

                    veterinarios.add(new Veterinario( nombre, especialidad, id));
                    System.out.println("\n*****Registro agregado exitosamente*****");

                    return true;
                }
            case 3 -> // Editar
                {
                    System.out.print("Ingrese el ID del vetrinario que desea editar: ");                    
                    String veterinarioAEditadar = scan.nextLine();

                    boolean encontrado = false;

                    for (Veterinario v: veterinarios) {
                        if (v.getId().equalsIgnoreCase(veterinarioAEditadar)) {
                            System.out.println("Ingrese los nuevos datos");
                            System.out.print("Nombre: ");
                            String nombreEditado = normalizarTexto(scan.nextLine());
                            System.out.print("Especialidad: ");
                            String especialidadEditada = normalizarTexto(scan.nextLine());

                            v.setNombre(nombreEditado);
                            v.setEspecialidad(especialidadEditada);

                            System.out.println("\n*****Registro agregado exitosamente*****");

                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                            System.out.println("\n*****No se encontró un veterinario con ese ID*****");
                    }

                    return true;
                }
            case 4 -> // Borrar
                {
                    System.out.print("Ingrese el ID del vetrinario que desea borrar: ");                    
                    String veterinarioABorrar = scan.nextLine();

                    boolean encontrado = false;

                    for (int i = 0; i < veterinarios.size(); i++) {
                        Veterinario veterinarioSeleccionado = veterinarios.get(i);
                        if (veterinarioSeleccionado.getId().equalsIgnoreCase(veterinarioABorrar)) {
                            System.out.println("Vetrinario seleccionado: ");
                            System.out.println("-> " + veterinarioSeleccionado.toString());
                            System.out.print("Desea borrar este registro (Si/No): ");
                            String borrar = scan.nextLine();

                            if (borrar.equalsIgnoreCase("si")) {
                                veterinarios.remove(i);
                                System.out.println("\n*****Registro eliminado exitosamente*****");
                            } else {
                                System.out.println("\n*****Operación cancelada*****");
                            }

                            encontrado = true;
                            break;
                        }

                        
                    }
                    if (!encontrado) {
                            System.out.println("\n*****No se encontró un veterinario con ese ID*****");
                    }
                    return true;                    
                }
            case 5 -> 
                {
                    return false;
                }
            default -> 
                {
                    System.out.println("\n*****Opción no válida*****");
                    return true;
                }
        }
    }


    static boolean administrarMedicamentos(Scanner scan) {
        System.out.println("\nMedicamentos");
        System.out.println("*****" + "1. Listar");
        System.out.println("*****" + "2. Agregar");
        System.out.println("*****" + "3. Editar");
        System.out.println("*****" + "4. Borrar");
        System.out.println("*****" + "5. Volver");
        System.out.print("Seleccione una opción: ");
        int opcion = scan.nextInt();
        scan.nextLine();
        System.out.println(""); // Espacio antes de cada funcion

        switch (opcion) {
            case 1 -> // Listar
                {
                    for (Medicamento m: medicamentos) {
                        System.out.println("-> " + m.toString());
                    }
                    return true;
                }
            case 2 -> // Agregar
                {
                    System.out.print("Nombre medicamento: ");
                    String nombreMedicamento = normalizarTexto(scan.nextLine());
                    System.out.print("Costo: ");
                    double costoMedicamento = scan.nextDouble();
                    medicamentos.add(new Medicamento(nombreMedicamento, costoMedicamento));
                    
                    System.out.println("\n*****Registro agregado exitosamente*****");

                    return true;
                }
            case 3 -> // Editar
                {
                    System.out.print("Ingrese el nombre del medicamento que desea editar: ");
                    String medicamentoAEditar = scan.nextLine();

                    boolean encontrado = false;

                    for (Medicamento m : medicamentos) {
                        if(m.getNombre().equalsIgnoreCase(medicamentoAEditar)) {
                            System.out.println("Ingrese los nuevos datos");
                            System.out.print("Nombre: ");
                            String nombreEditado = normalizarTexto(scan.nextLine());
                            System.out.print("Costo: ");
                            double costoEditado = scan.nextDouble();

                            m.setNombre(nombreEditado);
                            m.setCosto(costoEditado);

                            System.out.println("\n*****Registro agregado exitosamente*****");

                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("\n*****No se encontró un medicamento por ese nombre*****");
                    }

                    return true;
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
                            System.out.println("-> " + medicamentoSeleccionado.toString());
                            System.out.print("Desea borrar este registro (Si/No): ");
                            String borrar = scan.nextLine();

                            if (borrar.equalsIgnoreCase(borrar)) {
                                medicamentos.remove(i);
                                System.out.println("*****Registro eliminado exitosamente*****");
                            } else {
                                System.out.println("*****Operación cancelada*****");
                            }

                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("\n*****No se encontró un medicamento por ese nombre*****");
                    }
                    return true;
                }
            case 5 -> 
                {
                    return false;
                }
            default -> 
                {
                    System.out.println("\n*****Opción no válida*****");
                    return true;
                }
        }
    }

    static boolean administrarExamenes(Scanner scan) {
        System.out.println("\nExámenes");
        System.out.println("*****" + "1. Listar");
        System.out.println("*****" + "2. Agregar");
        System.out.println("*****" + "3. Editar");
        System.out.println("*****" + "4. Borrar");
        System.out.println("*****" + "5. Volver");
        System.out.print("Seleccione una opción: ");
        int opcion = scan.nextInt();
        scan.nextLine();
        System.out.println(""); // Espacio antes de cada funcion

        switch (opcion) {
            case 1 -> // Listar
                {
                    for (Examen e : examenes) {
                        System.out.println("-> " + e.toString());
                    }

                    return true;
                }
            case 2 -> // Agregar
                {
                    System.out.print("Nombre examen: ");
                    String nombre = normalizarTexto(scan.nextLine());
                    System.out.print("Costo: ");
                    double costo = scan.nextDouble();

                    examenes.add(new Examen(nombre, costo));
                    System.out.println("\n*****Registro agregado exitosamente*****");

                    return true;
                }
            case 3 -> // Editar
                {
                    System.out.print("Ingrese el nombre del examen que desea editar: ");                    
                    String examenAEditadar = scan.nextLine();

                    boolean encontrado = false;

                    for (Examen e : examenes) {
                        if (e.getNombre().equalsIgnoreCase(examenAEditadar)) {
                            System.out.println("Ingrese los nuevos datos");
                            System.out.print("Nombre: ");
                            String nombreEditado = normalizarTexto(scan.nextLine());
                            System.out.print("Costo: ");
                            double costoEditado = scan.nextDouble();

                            e.setNombre(nombreEditado);
                            e.setCosto(costoEditado);

                            System.out.println("\n*****Registro agregado exitosamente*****");

                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("\n*****No se encontró un examen con ese nombre*****");
                    }


                    return true;
                }
            case 4 -> //borrar
                {
                    System.out.print("Ingrese el nombre del examen que desea borrar: ");                    
                    String examenABorrar = scan.nextLine();

                    boolean encontrado = false;

                    for (int i = 0; i < examenes.size(); i++) {
                        Examen examenSeleccionado = examenes.get(i);
                        if (examenSeleccionado.getNombre().equalsIgnoreCase(examenABorrar)) {
                            System.out.println("Examen seleccionado: ");
                            System.out.println("-> " + examenSeleccionado.toString());
                            System.out.print("Desea borrar este registro (Si/No): ");
                            String borrar = scan.nextLine();

                            if (borrar.equalsIgnoreCase("si")) {
                                examenes.remove(i);
                                System.out.println("\n*****Registro eliminado exitosamente*****");
                            } else {
                                System.out.println("\n*****Operación cancelada*****");
                            }

                            encontrado = true;
                            break;
                        }
                    }

                    if (!encontrado) {
                        System.out.println("\n*****No se encontró un examen con ese nombre*****");
                    }
                    return true;
                }
            
            case 5 -> 
                {
                    return false;
                }
            default -> 
                {
                    System.out.println("\n*****Opción no válida*****");
                    return true;
                }
        } 
    }

    static void administrarConsulta(Scanner scan) {
        int opcion;
        do {
            System.out.println("\n1. Agregar consulta");
            System.out.println("2. Agregar examen a consulta");
            System.out.println("3. Agregar tratamiento a consulta");
            System.out.println("4. Ver factura consulta");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scan.nextInt(); 
            scan.nextLine();

            switch (opcion) {
                case 1 -> agregarConsulta(scan);
                case 2 -> agregarExamen(scan);
                case 3 -> agregarTratamiento(scan);
                case 4 -> verFacturaConsulta(scan);
                case 5 -> System.out.println("\nVolviendo al menú principal...");
                default -> System.out.println("\n*****Opción no válida*****");
            }

            if (opcion != 5) {
                System.out.println("\nPresione Enter para continuar...");
                scan.nextLine();
            }
        } while (opcion != 4);

    }

    static void agregarConsulta(Scanner scan) {
        System.out.println("\nVeterinarios disponibles:");
        for (Veterinario v: veterinarios) {
            System.out.println("-> " + v.toString());
        }
        System.out.print("\nIngrese el nombre del veterinario encargado: ");
        Veterinario veterinario = buscarVeterinario(normalizarTexto(scan.nextLine()));

        Mascota mascota = agregarMascota(scan);

        Consulta consulta = new Consulta(veterinario, mascota, new Date());

        System.out.print("\nIngrese diagnóstico: ");
        String diag = normalizarTexto(scan.nextLine());
        consulta.agregarDiagnostico(new Diagnostico(diag, new Date()));

        consultas.add(consulta);
        Factura factura = new Factura(consulta);
        System.out.println("Costo total consulta: " + factura.getTotal());
    }

    static void agregarExamen(Scanner scan) {
        if (consultas.isEmpty()) {
            System.out.println("\nNo hay consultas registradas. Primero agregue una consulta.");
            return;
        }
        
        System.out.print("\n¿Agregar exámenes? (si/no): ");
        if (scan.nextLine().equalsIgnoreCase("si")) {
            // Mostrar consultas disponibles
            System.out.println("\nConsultas disponibles:");
            for (int i = 0; i < consultas.size(); i++) {
                //System.out.println((i + 1) + ". " + consultas.get(i).toString());
                System.out.println("Consulta");
                System.out.println(((i + 1) + ". " + consultas.get(i).getVeterinario() + " " + consultas.get(i).getMascota()));
            }

            System.out.print("Seleccione consulta: ");
            int consultaIndex = scan.nextInt(); scan.nextLine();
            Consulta consulta = consultas.get(consultaIndex - 1);
            
            System.out.println("\nExámenes disponibles:");
            for (int i = 0; i < examenes.size(); i++) {
                System.out.println((i + 1) + ". " + examenes.get(i).getNombre() + " - $" + examenes.get(i).getCosto());
            }
            System.out.print("Seleccione examen: ");
            int examenSeleccionado = scan.nextInt(); scan.nextLine();
            consulta.agregarExamen(examenes.get(examenSeleccionado - 1));
            System.out.println("\nExamen agregado correctamente.");
        }
    }

    static void agregarTratamiento(Scanner scan) {
        if (consultas.isEmpty()) {
            System.out.println("\nNo hay consultas registradas. Primero agregue una consulta.");
            return;
        }
        
        System.out.print("\n¿Agregar tratamiento? (si/no): ");
        if (scan.nextLine().equalsIgnoreCase("si")) {
            // Mostrar consultas disponibles
            System.out.println("\nConsultas disponibles:");
            for (int i = 0; i < consultas.size(); i++) {
                System.out.println((i + 1) + ". " + consultas.get(i).toString());
            }
            System.out.print("\nSeleccione consulta: ");
            int consultaIndex = scan.nextInt(); scan.nextLine();
            Consulta consulta = consultas.get(consultaIndex - 1);
            
            Tratamiento t = new Tratamiento();
            while (true) {
                System.out.println("\nLista de medicamentos:");
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
                if (!scan.nextLine().equalsIgnoreCase("si")) {
                    break;
                }
            }
            consulta.agregarTratamiento(t);
            System.out.println("\n*****Tratamiento agregado correctamente*****");
        }
    }

    static void verFacturaConsulta(Scanner scan) {
        if (consultas.isEmpty()) {
            System.out.println("\nNo hay consultas registradas.");
            return;
        }
        
        System.out.println("\nConsultas disponibles:");
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println((i + 1) + ". " + consultas.get(i).toString());
            System.out.println();
        }
        System.out.print("Seleccione consulta: ");
        int consultaIndex = scan.nextInt(); 
        scan.nextLine();
        Consulta consulta = consultas.get(consultaIndex - 1);
        
        Factura factura = new Factura(consulta);
        System.out.println("\n=== DETALLE DE FACTURA ===");
        System.out.println(factura.toString());
    }

    static Mascota agregarMascota(Scanner scan) {
        System.out.println("\nEspecie de la mascota: ");
        System.out.println("1. Perro");
        System.out.println("2. Gato");
        System.out.print("Seleccione una opción: ");
        String opcion = normalizarTexto(scan.nextLine().trim());
        Mascota mascotaCreada = null;

        switch (opcion) {
            case "1" -> {
                System.out.println("\nIngrese los siguientes datos de la mascota");
                System.out.print("Nombre: ");
                String nombreMascota = normalizarTexto(scan.nextLine());
                System.out.print("Raza: ");
                String razaMascota = normalizarTexto(scan.nextLine());
                System.out.print("Edad: ");
                int edadMascota = scan.nextInt(); scan.nextLine();
                System.out.print("Genero (Macho/Hembra): ");
                String generoMascota = normalizarTexto(scan.nextLine());
                System.out.print("Peso (Kg): ");
                double pesoMascota = scan.nextDouble(); scan.nextLine();
                System.out.print("Altura (cm): ");
                double alturaMascota = scan.nextDouble(); scan.nextLine();
                System.out.print("Última fecha de vacunación DD-MM-AAAA: ");
                String fechaUtlimaVacunacionMascota = normalizarTexto(scan.nextLine());

                // Datos propietario
                System.out.println("\nIngrese los siguientes datos del propietario");
                System.out.print("Nombre: ");
                String nombrePropietario = normalizarTexto(scan.nextLine());
                System.out.print("ID: ");
                String idPropietario;
                boolean validacionId;

                // Validacion ID
                do {
                    idPropietario = normalizarTexto(scan.nextLine());
                    validacionId = true;

                    for (char c : idPropietario.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            System.out.println("\nError: Solo se permiten números");
                            validacionId = false;
                            break;
                        }
                    }

                    if (!validacionId) {
                        System.out.print("\nIngrese de nuevo el ID: ");
                    }
                } while(!validacionId);

                System.out.print("Dirección: ");
                String direccionPropietario = normalizarTexto(scan.nextLine());
                System.out.print("Correo: ");
                String correoPropietario = normalizarTexto(scan.nextLine());
                System.out.print("Teléfono: ");
                String telefonoPropietario = normalizarTexto(scan.nextLine());

                Propietario propietario = new Propietario(idPropietario, nombrePropietario, 
                    direccionPropietario, correoPropietario, telefonoPropietario);
                propietarios.add(propietario);
                
                mascotaCreada = new Perro(nombreMascota, razaMascota, edadMascota, generoMascota, 
                    pesoMascota, alturaMascota, propietario, fechaUtlimaVacunacionMascota);
                mascotas.add(mascotaCreada);
            }
            case "2" -> {
                System.out.println("\nIngrese los siguientes datos de la mascota");
                System.out.print("Nombre: ");
                String nombreMascota = normalizarTexto(scan.nextLine());
                System.out.print("Raza: ");
                String razaMascota = normalizarTexto(scan.nextLine());
                System.out.print("Edad: ");
                int edadMascota = scan.nextInt(); scan.nextLine();
                System.out.print("Genero (Macho/Hembra): ");
                String generoMascota = normalizarTexto(scan.nextLine());
                System.out.print("Peso (Kg): ");
                double pesoMascota = scan.nextDouble(); scan.nextLine();
                System.out.print("Altura (cm): ");
                double alturaMascota = scan.nextDouble(); scan.nextLine();
                System.out.print("Esterilizado (Si/No): ");
                String estaEsterilizado = normalizarTexto(scan.nextLine());
                boolean esterilizado = estaEsterilizado.equalsIgnoreCase("si");

                // Datos propietario
                System.out.println("\nIngrese los siguientes datos del propietario");
                System.out.print("Nombre: ");
                String nombrePropietario = normalizarTexto(scan.nextLine());
                System.out.print("ID: ");
                String idPropietario;
                boolean validacionId;

                // Validacion ID
                do {
                    idPropietario = normalizarTexto(scan.nextLine());
                    validacionId = true;

                    for (char c : idPropietario.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            System.out.println("\nError: Solo se permiten números");
                            validacionId = false;
                            break;
                        }
                    }

                    if (!validacionId) {
                        System.out.print("\nIngrese de nuevo el ID: ");
                    }
                } while(!validacionId);

                System.out.print("Dirección: ");
                String direccionPropietario = normalizarTexto(scan.nextLine());
                System.out.print("Correo: ");
                String correoPropietario = normalizarTexto(scan.nextLine());
                System.out.print("Teléfono: ");
                String telefonoPropietario = normalizarTexto(scan.nextLine());

                Propietario propietario = new Propietario(idPropietario, nombrePropietario, 
                    direccionPropietario, correoPropietario, telefonoPropietario);
                propietarios.add(propietario);
                
                mascotaCreada = new Gato(nombreMascota, razaMascota, edadMascota, generoMascota, 
                    pesoMascota, alturaMascota, propietario, esterilizado);
                mascotas.add(mascotaCreada);
            }
            default -> System.out.println("\n*****Opción no válida*****");
        }
        return mascotaCreada;
    }

    static void generarReportes() {
        // 1. Datos básicos
        int totalConsultas = consultas.size();
        System.out.println("\n=== REPORTES ESTADÍSTICOS ===");
        System.out.println("Total de consultas realizadas: " + totalConsultas);

        if (totalConsultas == 0) {
            System.out.println("\nNo hay consultas registradas para generar reportes.");
            return;
        }

        // 2. Mascotas atendidas por tipo
        Map<String, Integer> mascotasPorTipo = new HashMap<>();
        for (Consulta c : consultas) {
            String tipo = c.getMascota().especie;
            mascotasPorTipo.put(tipo, mascotasPorTipo.getOrDefault(tipo, 0) + 1);
        }

        System.out.println("\n--- Mascotas atendidas por tipo ---");
        mascotasPorTipo.forEach((tipo, cantidad) -> 
            System.out.printf("- %s: %d (%.1f%%)%n", 
                tipo, cantidad, (cantidad * 100.0 / totalConsultas)));

        // 3. Consultas por veterinario
        Map<String, Integer> consultasPorVeterinario = new HashMap<>();
        for (Consulta c : consultas) {
            String veterinario = c.getVeterinario().getNombre();
            consultasPorVeterinario.put(veterinario, 
                consultasPorVeterinario.getOrDefault(veterinario, 0) + 1);
        }

        System.out.println("\n--- Consultas por veterinario ---");
        consultasPorVeterinario.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> System.out.printf("- %s: %d consultas%n", 
                entry.getKey(), entry.getValue()));

        // 4. Medicamentos más suministrados
        Map<String, Integer> medicamentosSuministrados = new HashMap<>();
        for (Consulta c : consultas) {
            if (c.getTratamiento() != null) {
                for (Medicamento m : c.getTratamiento().getMedicamentos()) {
                    medicamentosSuministrados.put(m.getNombre(), 
                        medicamentosSuministrados.getOrDefault(m.getNombre(), 0) + 1);
                }
            }
        }

        System.out.println("\n--- Medicamentos más suministrados ---");
        if (!medicamentosSuministrados.isEmpty()) {
            medicamentosSuministrados.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5) // Muestra solo los top 5
                .forEach(entry -> System.out.printf("- %s: %d veces%n", 
                    entry.getKey(), entry.getValue()));
        } else {
            System.out.println("No se registraron medicamentos suministrados.");
        }

        // 5. Mascota más recurrente
        Map<String, Integer> visitasPorMascota = new HashMap<>();
        for (Consulta c : consultas) {
            String mascota = c.getMascota().getNombre() + " (" + c.getMascota().especie + ")";
            visitasPorMascota.put(mascota, 
                visitasPorMascota.getOrDefault(mascota, 0) + 1);
        }

        System.out.println("\n--- Mascotas más recurrentes ---");
        if (!visitasPorMascota.isEmpty()) {
            visitasPorMascota.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3) // Muestra solo las top 3
                .forEach(entry -> System.out.printf("- %s: %d visitas%n", 
                    entry.getKey(), entry.getValue()));
        } else {
            System.out.println("No hay datos de mascotas recurrentes.");
        }
    }
    static String normalizarTexto(String input) {
        String textNormalizado = (input == null) ? null : 
            Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();

        return textNormalizado;
    }
}