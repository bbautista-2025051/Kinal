package com.kinalApplication.Kinal.util;

import com.kinalApplication.Kinal.model.*;
import com.kinalApplication.Kinal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private LecturaRepository lecturaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private OpcionRepository opcionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar si no hay carreras
        if (carreraRepository.count() == 0) {

            // ========== CARRERA ==========
            Carrera informatica = new Carrera("Perito en Informática",
                    "Desarrollo de software, bases de datos, redes y programación orientada a objetos.");
            carreraRepository.save(informatica);

            // ========== LECTURA 1: Java Avanzado (ya existente) ==========
            String textoLargo1 =
                    " Programación Orientada a Objetos en Java - Nivel Intermedio\n\n" +
                            "Java es un lenguaje de programación orientado a objetos que sigue los principios de encapsulamiento, herencia, polimorfismo y abstracción. " +
                            "Estos pilares permiten crear código modular, reutilizable y fácil de mantener.\n\n" +
                            "**Encapsulamiento**: Consiste en ocultar los detalles internos de una clase y exponer solo lo necesario mediante métodos públicos (getters/setters). " +
                            "Se logra usando modificadores de acceso como `private`, `protected` y `public`.\n\n" +
                            "**Herencia**: Permite que una clase (hija) herede atributos y métodos de otra clase (padre) usando la palabra clave `extends`. " +
                            "Java soporta herencia simple, pero se puede simular múltiples herencias con interfaces.\n\n" +
                            "**Polimorfismo**: Es la capacidad de un objeto para tomar muchas formas. Se manifiesta mediante la sobrecarga de métodos (mismo nombre, diferentes parámetros) " +
                            "y la sobreescritura de métodos (redefinir un método heredado).\n\n" +
                            "**Abstracción**: Se enfoca en ocultar la complejidad y mostrar solo las características esenciales. Se logra con clases abstractas e interfaces.\n\n" +
                            "Además, Java cuenta con **colecciones** como ArrayList, HashMap, LinkedList, que facilitan el manejo de grupos de objetos. " +
                            "El manejo de excepciones permite controlar errores en tiempo de ejecución usando bloques `try-catch-finally`.\n\n" +
                            "Java también incorpora **programación funcional** desde Java 8 con expresiones lambda y streams, que permiten procesar datos de forma declarativa.\n\n" +
                            "Por último, el sistema de **entrada/salida (I/O)** y el API de **concurrencia** (hilos) son fundamentales para aplicaciones robustas.\n\n" +
                            "En resumen, dominar estos conceptos te permitirá desarrollar software profesional y escalable.";

            Lectura lecturaJava = new Lectura("Programación Orientada a Objetos en Java (Nivel Intermedio)", textoLargo1, informatica);
            lecturaRepository.save(lecturaJava);

            // Crear 15 preguntas para Lectura 1
            // Pregunta 1
            Pregunta p1 = new Pregunta("¿Cuál de los siguientes NO es un pilar de la Programación Orientada a Objetos?", lecturaJava);
            preguntaRepository.save(p1);
            Opcion p1a = new Opcion("Encapsulamiento", p1);
            Opcion p1b = new Opcion("Herencia", p1);
            Opcion p1c = new Opcion("Polimorfismo", p1);
            Opcion p1d = new Opcion("Compilación", p1);
            opcionRepository.save(p1a); opcionRepository.save(p1b); opcionRepository.save(p1c); opcionRepository.save(p1d);
            p1.setRespuestaCorrecta(p1d);
            preguntaRepository.save(p1);

            // Pregunta 2
            Pregunta p2 = new Pregunta("¿Qué modificador de acceso permite que un atributo sea visible solo dentro de la misma clase?", lecturaJava);
            preguntaRepository.save(p2);
            Opcion p2a = new Opcion("public", p2);
            Opcion p2b = new Opcion("private", p2);
            Opcion p2c = new Opcion("protected", p2);
            Opcion p2d = new Opcion("default", p2);
            opcionRepository.save(p2a); opcionRepository.save(p2b); opcionRepository.save(p2c); opcionRepository.save(p2d);
            p2.setRespuestaCorrecta(p2b);
            preguntaRepository.save(p2);

            // Pregunta 3
            Pregunta p3 = new Pregunta("¿Qué palabra clave se usa para heredar de una clase en Java?", lecturaJava);
            preguntaRepository.save(p3);
            Opcion p3a = new Opcion("implements", p3);
            Opcion p3b = new Opcion("extends", p3);
            Opcion p3c = new Opcion("inherit", p3);
            Opcion p3d = new Opcion("super", p3);
            opcionRepository.save(p3a); opcionRepository.save(p3b); opcionRepository.save(p3c); opcionRepository.save(p3d);
            p3.setRespuestaCorrecta(p3b);
            preguntaRepository.save(p3);

            // Pregunta 4
            Pregunta p4 = new Pregunta("El polimorfismo por sobrecarga de métodos se caracteriza por:", lecturaJava);
            preguntaRepository.save(p4);
            Opcion p4a = new Opcion("Mismo nombre y diferentes parámetros", p4);
            Opcion p4b = new Opcion("Mismo nombre y mismos parámetros", p4);
            Opcion p4c = new Opcion("Diferente nombre y mismos parámetros", p4);
            Opcion p4d = new Opcion("Redefinir un método heredado", p4);
            opcionRepository.save(p4a); opcionRepository.save(p4b); opcionRepository.save(p4c); opcionRepository.save(p4d);
            p4.setRespuestaCorrecta(p4a);
            preguntaRepository.save(p4);

            // Pregunta 5
            Pregunta p5 = new Pregunta("¿Cuál de estas es una interfaz de colecciones en Java?", lecturaJava);
            preguntaRepository.save(p5);
            Opcion p5a = new Opcion("ArrayList", p5);
            Opcion p5b = new Opcion("HashMap", p5);
            Opcion p5c = new Opcion("List", p5);
            Opcion p5d = new Opcion("LinkedList", p5);
            opcionRepository.save(p5a); opcionRepository.save(p5b); opcionRepository.save(p5c); opcionRepository.save(p5d);
            p5.setRespuestaCorrecta(p5c);
            preguntaRepository.save(p5);

            // Pregunta 6
            Pregunta p6 = new Pregunta("¿Qué bloque se ejecuta siempre, haya o no excepción?", lecturaJava);
            preguntaRepository.save(p6);
            Opcion p6a = new Opcion("try", p6);
            Opcion p6b = new Opcion("catch", p6);
            Opcion p6c = new Opcion("finally", p6);
            Opcion p6d = new Opcion("throw", p6);
            opcionRepository.save(p6a); opcionRepository.save(p6b); opcionRepository.save(p6c); opcionRepository.save(p6d);
            p6.setRespuestaCorrecta(p6c);
            preguntaRepository.save(p6);

            // Pregunta 7
            Pregunta p7 = new Pregunta("Desde qué versión de Java se introdujeron las expresiones lambda?", lecturaJava);
            preguntaRepository.save(p7);
            Opcion p7a = new Opcion("Java 5", p7);
            Opcion p7b = new Opcion("Java 7", p7);
            Opcion p7c = new Opcion("Java 8", p7);
            Opcion p7d = new Opcion("Java 11", p7);
            opcionRepository.save(p7a); opcionRepository.save(p7b); opcionRepository.save(p7c); opcionRepository.save(p7d);
            p7.setRespuestaCorrecta(p7c);
            preguntaRepository.save(p7);

            // Pregunta 8
            Pregunta p8 = new Pregunta("¿Qué permite la abstracción en Java?", lecturaJava);
            preguntaRepository.save(p8);
            Opcion p8a = new Opcion("Ocultar la complejidad", p8);
            Opcion p8b = new Opcion("Reutilizar código", p8);
            Opcion p8c = new Opcion("Crear múltiples instancias", p8);
            Opcion p8d = new Opcion("Mejorar el rendimiento", p8);
            opcionRepository.save(p8a); opcionRepository.save(p8b); opcionRepository.save(p8c); opcionRepository.save(p8d);
            p8.setRespuestaCorrecta(p8a);
            preguntaRepository.save(p8);

            // Pregunta 9
            Pregunta p9 = new Pregunta("¿Cuál es la clase padre de todas las clases en Java?", lecturaJava);
            preguntaRepository.save(p9);
            Opcion p9a = new Opcion("Object", p9);
            Opcion p9b = new Opcion("Class", p9);
            Opcion p9c = new Opcion("Main", p9);
            Opcion p9d = new Opcion("System", p9);
            opcionRepository.save(p9a); opcionRepository.save(p9b); opcionRepository.save(p9c); opcionRepository.save(p9d);
            p9.setRespuestaCorrecta(p9a);
            preguntaRepository.save(p9);

            // Pregunta 10
            Pregunta p10 = new Pregunta("¿Qué método se usa para iniciar un hilo en Java?", lecturaJava);
            preguntaRepository.save(p10);
            Opcion p10a = new Opcion("run()", p10);
            Opcion p10b = new Opcion("start()", p10);
            Opcion p10c = new Opcion("init()", p10);
            Opcion p10d = new Opcion("execute()", p10);
            opcionRepository.save(p10a); opcionRepository.save(p10b); opcionRepository.save(p10c); opcionRepository.save(p10d);
            p10.setRespuestaCorrecta(p10b);
            preguntaRepository.save(p10);

            // Pregunta 11
            Pregunta p11 = new Pregunta("¿Qué permite la palabra clave 'super'?", lecturaJava);
            preguntaRepository.save(p11);
            Opcion p11a = new Opcion("Acceder a métodos de la clase hija", p11);
            Opcion p11b = new Opcion("Acceder a miembros de la clase padre", p11);
            Opcion p11c = new Opcion("Crear una nueva instancia", p11);
            Opcion p11d = new Opcion("Finalizar un objeto", p11);
            opcionRepository.save(p11a); opcionRepository.save(p11b); opcionRepository.save(p11c); opcionRepository.save(p11d);
            p11.setRespuestaCorrecta(p11b);
            preguntaRepository.save(p11);

            // Pregunta 12
            Pregunta p12 = new Pregunta("¿Cuál es la diferencia entre ArrayList y LinkedList?", lecturaJava);
            preguntaRepository.save(p12);
            Opcion p12a = new Opcion("ArrayList es más rápido para inserciones en medio", p12);
            Opcion p12b = new Opcion("LinkedList implementa List y Deque", p12);
            Opcion p12c = new Opcion("ArrayList usa memoria contigua", p12);
            Opcion p12d = new Opcion("Todas las anteriores son correctas", p12);
            opcionRepository.save(p12a); opcionRepository.save(p12b); opcionRepository.save(p12c); opcionRepository.save(p12d);
            p12.setRespuestaCorrecta(p12d);
            preguntaRepository.save(p12);

            // Pregunta 13
            Pregunta p13 = new Pregunta("¿Qué excepción se lanza al acceder a un índice fuera del rango en un array?", lecturaJava);
            preguntaRepository.save(p13);
            Opcion p13a = new Opcion("NullPointerException", p13);
            Opcion p13b = new Opcion("ArrayIndexOutOfBoundsException", p13);
            Opcion p13c = new Opcion("IndexOutOfBoundsException", p13);
            Opcion p13d = new Opcion("IllegalArgumentException", p13);
            opcionRepository.save(p13a); opcionRepository.save(p13b); opcionRepository.save(p13c); opcionRepository.save(p13d);
            p13.setRespuestaCorrecta(p13b);
            preguntaRepository.save(p13);

            // Pregunta 14
            Pregunta p14 = new Pregunta("¿Cuál es el propósito de la interfaz 'Runnable'?", lecturaJava);
            preguntaRepository.save(p14);
            Opcion p14a = new Opcion("Definir una tarea ejecutable en un hilo", p14);
            Opcion p14b = new Opcion("Manejar eventos de teclado", p14);
            Opcion p14c = new Opcion("Serializar objetos", p14);
            Opcion p14d = new Opcion("Conectar a bases de datos", p14);
            opcionRepository.save(p14a); opcionRepository.save(p14b); opcionRepository.save(p14c); opcionRepository.save(p14d);
            p14.setRespuestaCorrecta(p14a);
            preguntaRepository.save(p14);

            // Pregunta 15
            Pregunta p15 = new Pregunta("¿Qué método de Stream se usa para filtrar elementos?", lecturaJava);
            preguntaRepository.save(p15);
            Opcion p15a = new Opcion("map()", p15);
            Opcion p15b = new Opcion("filter()", p15);
            Opcion p15c = new Opcion("reduce()", p15);
            Opcion p15d = new Opcion("collect()", p15);
            opcionRepository.save(p15a); opcionRepository.save(p15b); opcionRepository.save(p15c); opcionRepository.save(p15d);
            p15.setRespuestaCorrecta(p15b);
            preguntaRepository.save(p15);

            // ========== LECTURA 2: Bases de Datos SQL ==========
            String textoLargo2 =
                    " Fundamentos de Bases de Datos SQL\n\n" +
                            "SQL (Structured Query Language) es el lenguaje estándar para gestionar bases de datos relacionales. Permite consultar, insertar, actualizar y eliminar datos.\n\n" +
                            "**Principales comandos DDL (Data Definition Language):**\n" +
                            "- CREATE: crear tablas, índices, vistas.\n" +
                            "- ALTER: modificar la estructura de una tabla.\n" +
                            "- DROP: eliminar objetos de la base de datos.\n\n" +
                            "**Comandos DML (Data Manipulation Language):**\n" +
                            "- SELECT: recuperar datos de una o más tablas.\n" +
                            "- INSERT: agregar nuevas filas.\n" +
                            "- UPDATE: modificar datos existentes.\n" +
                            "- DELETE: eliminar filas.\n\n" +
                            "**Cláusulas importantes:**\n" +
                            "- WHERE: filtrar registros.\n" +
                            "- JOIN: combinar tablas relacionadas (INNER JOIN, LEFT JOIN, RIGHT JOIN).\n" +
                            "- GROUP BY: agrupar filas con valores idénticos.\n" +
                            "- ORDER BY: ordenar resultados.\n\n" +
                            "**Restricciones de integridad:** PRIMARY KEY, FOREIGN KEY, UNIQUE, NOT NULL, CHECK.\n\n" +
                            "Las **transacciones** (BEGIN, COMMIT, ROLLBACK) garantizan atomicidad, consistencia, aislamiento y durabilidad (ACID).\n\n" +
                            "Un buen dominio de SQL es esencial para cualquier desarrollador o administrador de bases de datos.";

            Lectura lecturaSQL = new Lectura("Fundamentos de Bases de Datos SQL", textoLargo2, informatica);
            lecturaRepository.save(lecturaSQL);

            // Crear 15 preguntas para Lectura 2
            // Pregunta 1
            Pregunta sql1 = new Pregunta("¿Qué comando SQL se usa para eliminar una tabla completa?", lecturaSQL);
            preguntaRepository.save(sql1);
            Opcion sql1a = new Opcion("DELETE TABLE", sql1);
            Opcion sql1b = new Opcion("DROP TABLE", sql1);
            Opcion sql1c = new Opcion("REMOVE TABLE", sql1);
            Opcion sql1d = new Opcion("TRUNCATE TABLE", sql1);
            opcionRepository.save(sql1a); opcionRepository.save(sql1b); opcionRepository.save(sql1c); opcionRepository.save(sql1d);
            sql1.setRespuestaCorrecta(sql1b);
            preguntaRepository.save(sql1);

            // Pregunta 2
            Pregunta sql2 = new Pregunta("¿Cuál de las siguientes es una restricción de integridad referencial?", lecturaSQL);
            preguntaRepository.save(sql2);
            Opcion sql2a = new Opcion("PRIMARY KEY", sql2);
            Opcion sql2b = new Opcion("FOREIGN KEY", sql2);
            Opcion sql2c = new Opcion("UNIQUE", sql2);
            Opcion sql2d = new Opcion("CHECK", sql2);
            opcionRepository.save(sql2a); opcionRepository.save(sql2b); opcionRepository.save(sql2c); opcionRepository.save(sql2d);
            sql2.setRespuestaCorrecta(sql2b);
            preguntaRepository.save(sql2);

            // Pregunta 3
            Pregunta sql3 = new Pregunta("¿Qué cláusula se usa para filtrar grupos después de GROUP BY?", lecturaSQL);
            preguntaRepository.save(sql3);
            Opcion sql3a = new Opcion("WHERE", sql3);
            Opcion sql3b = new Opcion("HAVING", sql3);
            Opcion sql3c = new Opcion("FILTER", sql3);
            Opcion sql3d = new Opcion("GROUP FILTER", sql3);
            opcionRepository.save(sql3a); opcionRepository.save(sql3b); opcionRepository.save(sql3c); opcionRepository.save(sql3d);
            sql3.setRespuestaCorrecta(sql3b);
            preguntaRepository.save(sql3);

            // Pregunta 4
            Pregunta sql4 = new Pregunta("¿Qué tipo de JOIN devuelve solo los registros que coinciden en ambas tablas?", lecturaSQL);
            preguntaRepository.save(sql4);
            Opcion sql4a = new Opcion("LEFT JOIN", sql4);
            Opcion sql4b = new Opcion("RIGHT JOIN", sql4);
            Opcion sql4c = new Opcion("INNER JOIN", sql4);
            Opcion sql4d = new Opcion("FULL OUTER JOIN", sql4);
            opcionRepository.save(sql4a); opcionRepository.save(sql4b); opcionRepository.save(sql4c); opcionRepository.save(sql4d);
            sql4.setRespuestaCorrecta(sql4c);
            preguntaRepository.save(sql4);

            // Pregunta 5
            Pregunta sql5 = new Pregunta("¿Cuál comando se usa para deshacer una transacción?", lecturaSQL);
            preguntaRepository.save(sql5);
            Opcion sql5a = new Opcion("COMMIT", sql5);
            Opcion sql5b = new Opcion("SAVEPOINT", sql5);
            Opcion sql5c = new Opcion("ROLLBACK", sql5);
            Opcion sql5d = new Opcion("UNDO", sql5);
            opcionRepository.save(sql5a); opcionRepository.save(sql5b); opcionRepository.save(sql5c); opcionRepository.save(sql5d);
            sql5.setRespuestaCorrecta(sql5c);
            preguntaRepository.save(sql5);

            // Pregunta 6
            Pregunta sql6 = new Pregunta("¿Qué operador se usa para buscar un patrón en una columna de texto?", lecturaSQL);
            preguntaRepository.save(sql6);
            Opcion sql6a = new Opcion("=", sql6);
            Opcion sql6b = new Opcion("LIKE", sql6);
            Opcion sql6c = new Opcion("IN", sql6);
            Opcion sql6d = new Opcion("BETWEEN", sql6);
            opcionRepository.save(sql6a); opcionRepository.save(sql6b); opcionRepository.save(sql6c); opcionRepository.save(sql6d);
            sql6.setRespuestaCorrecta(sql6b);
            preguntaRepository.save(sql6);

            // Pregunta 7
            Pregunta sql7 = new Pregunta("¿Cuál es la función de agregación que cuenta el número de filas?", lecturaSQL);
            preguntaRepository.save(sql7);
            Opcion sql7a = new Opcion("SUM()", sql7);
            Opcion sql7b = new Opcion("AVG()", sql7);
            Opcion sql7c = new Opcion("COUNT()", sql7);
            Opcion sql7d = new Opcion("MAX()", sql7);
            opcionRepository.save(sql7a); opcionRepository.save(sql7b); opcionRepository.save(sql7c); opcionRepository.save(sql7d);
            sql7.setRespuestaCorrecta(sql7c);
            preguntaRepository.save(sql7);

            // Pregunta 8
            Pregunta sql8 = new Pregunta("¿Qué comando DDL modifica la estructura de una tabla?", lecturaSQL);
            preguntaRepository.save(sql8);
            Opcion sql8a = new Opcion("UPDATE", sql8);
            Opcion sql8b = new Opcion("MODIFY", sql8);
            Opcion sql8c = new Opcion("ALTER", sql8);
            Opcion sql8d = new Opcion("CHANGE", sql8);
            opcionRepository.save(sql8a); opcionRepository.save(sql8b); opcionRepository.save(sql8c); opcionRepository.save(sql8d);
            sql8.setRespuestaCorrecta(sql8c);
            preguntaRepository.save(sql8);

            // Pregunta 9
            Pregunta sql9 = new Pregunta("¿Cuál de estos comandos pertenece a DML?", lecturaSQL);
            preguntaRepository.save(sql9);
            Opcion sql9a = new Opcion("CREATE", sql9);
            Opcion sql9b = new Opcion("DROP", sql9);
            Opcion sql9c = new Opcion("INSERT", sql9);
            Opcion sql9d = new Opcion("ALTER", sql9);
            opcionRepository.save(sql9a); opcionRepository.save(sql9b); opcionRepository.save(sql9c); opcionRepository.save(sql9d);
            sql9.setRespuestaCorrecta(sql9c);
            preguntaRepository.save(sql9);

            // Pregunta 10
            Pregunta sql10 = new Pregunta("¿Qué propiedad ACID garantiza que una transacción se ejecute completamente o no se ejecute?", lecturaSQL);
            preguntaRepository.save(sql10);
            Opcion sql10a = new Opcion("Consistencia", sql10);
            Opcion sql10b = new Opcion("Atomicidad", sql10);
            Opcion sql10c = new Opcion("Aislamiento", sql10);
            Opcion sql10d = new Opcion("Durabilidad", sql10);
            opcionRepository.save(sql10a); opcionRepository.save(sql10b); opcionRepository.save(sql10c); opcionRepository.save(sql10d);
            sql10.setRespuestaCorrecta(sql10b);
            preguntaRepository.save(sql10);

            // Pregunta 11
            Pregunta sql11 = new Pregunta("¿Qué cláusula ordena los resultados de forma ascendente por defecto?", lecturaSQL);
            preguntaRepository.save(sql11);
            Opcion sql11a = new Opcion("ORDER BY", sql11);
            Opcion sql11b = new Opcion("GROUP BY", sql11);
            Opcion sql11c = new Opcion("SORT BY", sql11);
            Opcion sql11d = new Opcion("ARRANGE BY", sql11);
            opcionRepository.save(sql11a); opcionRepository.save(sql11b); opcionRepository.save(sql11c); opcionRepository.save(sql11d);
            sql11.setRespuestaCorrecta(sql11a);
            preguntaRepository.save(sql11);

            // Pregunta 12
            Pregunta sql12 = new Pregunta("¿Qué operador permite comparar un valor con una lista de valores?", lecturaSQL);
            preguntaRepository.save(sql12);
            Opcion sql12a = new Opcion("BETWEEN", sql12);
            Opcion sql12b = new Opcion("LIKE", sql12);
            Opcion sql12c = new Opcion("IN", sql12);
            Opcion sql12d = new Opcion("EXISTS", sql12);
            opcionRepository.save(sql12a); opcionRepository.save(sql12b); opcionRepository.save(sql12c); opcionRepository.save(sql12d);
            sql12.setRespuestaCorrecta(sql12c);
            preguntaRepository.save(sql12);

            // Pregunta 13
            Pregunta sql13 = new Pregunta("¿Cuál es la función de agregación que devuelve el valor más alto?", lecturaSQL);
            preguntaRepository.save(sql13);
            Opcion sql13a = new Opcion("MIN()", sql13);
            Opcion sql13b = new Opcion("MAX()", sql13);
            Opcion sql13c = new Opcion("TOP()", sql13);
            Opcion sql13d = new Opcion("HIGHEST()", sql13);
            opcionRepository.save(sql13a); opcionRepository.save(sql13b); opcionRepository.save(sql13c); opcionRepository.save(sql13d);
            sql13.setRespuestaCorrecta(sql13b);
            preguntaRepository.save(sql13);

            // Pregunta 14
            Pregunta sql14 = new Pregunta("¿Qué comando elimina todas las filas de una tabla sin registrar cada eliminación?", lecturaSQL);
            preguntaRepository.save(sql14);
            Opcion sql14a = new Opcion("DELETE", sql14);
            Opcion sql14b = new Opcion("DROP", sql14);
            Opcion sql14c = new Opcion("TRUNCATE", sql14);
            Opcion sql14d = new Opcion("REMOVE", sql14);
            opcionRepository.save(sql14a); opcionRepository.save(sql14b); opcionRepository.save(sql14c); opcionRepository.save(sql14d);
            sql14.setRespuestaCorrecta(sql14c);
            preguntaRepository.save(sql14);

            // Pregunta 15
            Pregunta sql15 = new Pregunta("¿Qué tipo de JOIN devuelve todos los registros de la tabla izquierda y los coincidentes de la derecha?", lecturaSQL);
            preguntaRepository.save(sql15);
            Opcion sql15a = new Opcion("INNER JOIN", sql15);
            Opcion sql15b = new Opcion("RIGHT JOIN", sql15);
            Opcion sql15c = new Opcion("FULL JOIN", sql15);
            Opcion sql15d = new Opcion("LEFT JOIN", sql15);
            opcionRepository.save(sql15a); opcionRepository.save(sql15b); opcionRepository.save(sql15c); opcionRepository.save(sql15d);
            sql15.setRespuestaCorrecta(sql15d);
            preguntaRepository.save(sql15);

            // ========== LECTURA 3: Redes de Computadoras ==========
            String textoLargo3 =
                    " Introducción a Redes de Computadoras\n\n" +
                            "Una red de computadoras es un conjunto de dispositivos interconectados que comparten recursos e información.\n\n" +
                            "**Modelo OSI (7 capas):**\n" +
                            "1. Física: bits, cables, señales.\n" +
                            "2. Enlace de datos: tramas, direcciones MAC.\n" +
                            "3. Red: paquetes, direcciones IP, enrutamiento.\n" +
                            "4. Transporte: segmentos, puertos, TCP/UDP.\n" +
                            "5. Sesión: gestión de diálogos.\n" +
                            "6. Presentación: cifrado, compresión.\n" +
                            "7. Aplicación: HTTP, FTP, SMTP, DNS.\n\n" +
                            "**Modelo TCP/IP (4 capas):**\n" +
                            "- Capa de Acceso a Red\n" +
                            "- Capa de Internet (IP)\n" +
                            "- Capa de Transporte (TCP, UDP)\n" +
                            "- Capa de Aplicación\n\n" +
                            "**Protocolos importantes:**\n" +
                            "- HTTP/HTTPS: web.\n" +
                            "- FTP: transferencia de archivos.\n" +
                            "- DNS: resolución de nombres.\n" +
                            "- DHCP: asignación dinámica de IP.\n" +
                            "- ARP: resolución de direcciones MAC.\n\n" +
                            "**Direccionamiento IP:** IPv4 (32 bits, ej. 192.168.1.1) e IPv6 (128 bits).\n\n" +
                            "Las redes se clasifican por tamaño: PAN, LAN, MAN, WAN.\n\n" +
                            "Conocer estos fundamentos es clave para administrar infraestructura de TI.";

            Lectura lecturaRedes = new Lectura("Introducción a Redes de Computadoras", textoLargo3, informatica);
            lecturaRepository.save(lecturaRedes);

            // 15 preguntas para Redes
            Pregunta red1 = new Pregunta("¿Cuál es la capa del modelo OSI encargada del direccionamiento IP y el enrutamiento?", lecturaRedes);
            preguntaRepository.save(red1);
            Opcion red1a = new Opcion("Capa de Transporte", red1);
            Opcion red1b = new Opcion("Capa de Red", red1);
            Opcion red1c = new Opcion("Capa de Enlace", red1);
            Opcion red1d = new Opcion("Capa de Sesión", red1);
            opcionRepository.save(red1a); opcionRepository.save(red1b); opcionRepository.save(red1c); opcionRepository.save(red1d);
            red1.setRespuestaCorrecta(red1b);
            preguntaRepository.save(red1);

            Pregunta red2 = new Pregunta("¿Qué protocolo se usa para la transferencia de páginas web?", lecturaRedes);
            preguntaRepository.save(red2);
            Opcion red2a = new Opcion("FTP", red2);
            Opcion red2b = new Opcion("SMTP", red2);
            Opcion red2c = new Opcion("HTTP", red2);
            Opcion red2d = new Opcion("SSH", red2);
            opcionRepository.save(red2a); opcionRepository.save(red2b); opcionRepository.save(red2c); opcionRepository.save(red2d);
            red2.setRespuestaCorrecta(red2c);
            preguntaRepository.save(red2);

            Pregunta red3 = new Pregunta("¿Cuántos bits tiene una dirección IPv4?", lecturaRedes);
            preguntaRepository.save(red3);
            Opcion red3a = new Opcion("16 bits", red3);
            Opcion red3b = new Opcion("32 bits", red3);
            Opcion red3c = new Opcion("64 bits", red3);
            Opcion red3d = new Opcion("128 bits", red3);
            opcionRepository.save(red3a); opcionRepository.save(red3b); opcionRepository.save(red3c); opcionRepository.save(red3d);
            red3.setRespuestaCorrecta(red3b);
            preguntaRepository.save(red3);

            Pregunta red4 = new Pregunta("¿Qué protocolo de transporte es orientado a conexión y confiable?", lecturaRedes);
            preguntaRepository.save(red4);
            Opcion red4a = new Opcion("UDP", red4);
            Opcion red4b = new Opcion("IP", red4);
            Opcion red4c = new Opcion("TCP", red4);
            Opcion red4d = new Opcion("ICMP", red4);
            opcionRepository.save(red4a); opcionRepository.save(red4b); opcionRepository.save(red4c); opcionRepository.save(red4d);
            red4.setRespuestaCorrecta(red4c);
            preguntaRepository.save(red4);

            Pregunta red5 = new Pregunta("¿Qué comando se usa en Windows para probar conectividad mediante ICMP?", lecturaRedes);
            preguntaRepository.save(red5);
            Opcion red5a = new Opcion("netstat", red5);
            Opcion red5b = new Opcion("ipconfig", red5);
            Opcion red5c = new Opcion("tracert", red5);
            Opcion red5d = new Opcion("ping", red5);
            opcionRepository.save(red5a); opcionRepository.save(red5b); opcionRepository.save(red5c); opcionRepository.save(red5d);
            red5.setRespuestaCorrecta(red5d);
            preguntaRepository.save(red5);

            Pregunta red6 = new Pregunta("¿Cuál es la dirección MAC?", lecturaRedes);
            preguntaRepository.save(red6);
            Opcion red6a = new Opcion("Dirección lógica de la capa de red", red6);
            Opcion red6b = new Opcion("Dirección física de la tarjeta de red", red6);
            Opcion red6c = new Opcion("Dirección de puerto de transporte", red6);
            Opcion red6d = new Opcion("Dirección de sesión", red6);
            opcionRepository.save(red6a); opcionRepository.save(red6b); opcionRepository.save(red6c); opcionRepository.save(red6d);
            red6.setRespuestaCorrecta(red6b);
            preguntaRepository.save(red6);

            Pregunta red7 = new Pregunta("¿Qué capa del modelo OSI se encarga de la compresión y el cifrado?", lecturaRedes);
            preguntaRepository.save(red7);
            Opcion red7a = new Opcion("Sesión", red7);
            Opcion red7b = new Opcion("Presentación", red7);
            Opcion red7c = new Opcion("Aplicación", red7);
            Opcion red7d = new Opcion("Transporte", red7);
            opcionRepository.save(red7a); opcionRepository.save(red7b); opcionRepository.save(red7c); opcionRepository.save(red7d);
            red7.setRespuestaCorrecta(red7b);
            preguntaRepository.save(red7);

            Pregunta red8 = new Pregunta("¿Qué dispositivo conecta redes diferentes y funciona en la capa de red?", lecturaRedes);
            preguntaRepository.save(red8);
            Opcion red8a = new Opcion("Switch", red8);
            Opcion red8b = new Opcion("Hub", red8);
            Opcion red8c = new Opcion("Router", red8);
            Opcion red8d = new Opcion("Bridge", red8);
            opcionRepository.save(red8a); opcionRepository.save(red8b); opcionRepository.save(red8c); opcionRepository.save(red8d);
            red8.setRespuestaCorrecta(red8c);
            preguntaRepository.save(red8);

            Pregunta red9 = new Pregunta("¿Qué protocolo asigna direcciones IP automáticamente?", lecturaRedes);
            preguntaRepository.save(red9);
            Opcion red9a = new Opcion("DNS", red9);
            Opcion red9b = new Opcion("DHCP", red9);
            Opcion red9c = new Opcion("ARP", red9);
            Opcion red9d = new Opcion("NAT", red9);
            opcionRepository.save(red9a); opcionRepository.save(red9b); opcionRepository.save(red9c); opcionRepository.save(red9d);
            red9.setRespuestaCorrecta(red9b);
            preguntaRepository.save(red9);

            Pregunta red10 = new Pregunta("¿Qué rango de puertos es considerado 'bien conocido' (well-known)?", lecturaRedes);
            preguntaRepository.save(red10);
            Opcion red10a = new Opcion("0-1023", red10);
            Opcion red10b = new Opcion("1024-49151", red10);
            Opcion red10c = new Opcion("49152-65535", red10);
            Opcion red10d = new Opcion("1-255", red10);
            opcionRepository.save(red10a); opcionRepository.save(red10b); opcionRepository.save(red10c); opcionRepository.save(red10d);
            red10.setRespuestaCorrecta(red10a);
            preguntaRepository.save(red10);

            Pregunta red11 = new Pregunta("¿Qué tipo de red abarca una ciudad?", lecturaRedes);
            preguntaRepository.save(red11);
            Opcion red11a = new Opcion("LAN", red11);
            Opcion red11b = new Opcion("MAN", red11);
            Opcion red11c = new Opcion("WAN", red11);
            Opcion red11d = new Opcion("PAN", red11);
            opcionRepository.save(red11a); opcionRepository.save(red11b); opcionRepository.save(red11c); opcionRepository.save(red11d);
            red11.setRespuestaCorrecta(red11b);
            preguntaRepository.save(red11);

            Pregunta red12 = new Pregunta("¿Qué protocolo resuelve nombres de dominio a direcciones IP?", lecturaRedes);
            preguntaRepository.save(red12);
            Opcion red12a = new Opcion("HTTP", red12);
            Opcion red12b = new Opcion("DNS", red12);
            Opcion red12c = new Opcion("FTP", red12);
            Opcion red12d = new Opcion("SMTP", red12);
            opcionRepository.save(red12a); opcionRepository.save(red12b); opcionRepository.save(red12c); opcionRepository.save(red12d);
            red12.setRespuestaCorrecta(red12b);
            preguntaRepository.save(red12);

            Pregunta red13 = new Pregunta("¿Cuál es la unidad de datos en la capa de transporte?", lecturaRedes);
            preguntaRepository.save(red13);
            Opcion red13a = new Opcion("Bits", red13);
            Opcion red13b = new Opcion("Tramas", red13);
            Opcion red13c = new Opcion("Paquetes", red13);
            Opcion red13d = new Opcion("Segmentos", red13);
            opcionRepository.save(red13a); opcionRepository.save(red13b); opcionRepository.save(red13c); opcionRepository.save(red13d);
            red13.setRespuestaCorrecta(red13d);
            preguntaRepository.save(red13);

            Pregunta red14 = new Pregunta("¿Qué comando muestra la tabla de enrutamiento en Windows?", lecturaRedes);
            preguntaRepository.save(red14);
            Opcion red14a = new Opcion("ipconfig /all", red14);
            Opcion red14b = new Opcion("route print", red14);
            Opcion red14c = new Opcion("net view", red14);
            Opcion red14d = new Opcion("arp -a", red14);
            opcionRepository.save(red14a); opcionRepository.save(red14b); opcionRepository.save(red14c); opcionRepository.save(red14d);
            red14.setRespuestaCorrecta(red14b);
            preguntaRepository.save(red14);

            Pregunta red15 = new Pregunta("¿Qué topología de red conecta cada nodo a dos vecinos formando un círculo?", lecturaRedes);
            preguntaRepository.save(red15);
            Opcion red15a = new Opcion("Bus", red15);
            Opcion red15b = new Opcion("Estrella", red15);
            Opcion red15c = new Opcion("Anillo", red15);
            Opcion red15d = new Opcion("Malla", red15);
            opcionRepository.save(red15a); opcionRepository.save(red15b); opcionRepository.save(red15c); opcionRepository.save(red15d);
            red15.setRespuestaCorrecta(red15c);
            preguntaRepository.save(red15);

            // ========== LECTURA 4: Desarrollo Web con Spring Boot ==========
            String textoLargo4 =
                    " Desarrollo Web con Spring Boot\n\n" +
                            "Spring Boot es un framework Java que simplifica la creación de aplicaciones web y microservicios. Ofrece configuración automática, servidor embebido (Tomcat) y starter dependencies.\n\n" +
                            "**Anotaciones clave:**\n" +
                            "- `@SpringBootApplication`: combina @Configuration, @EnableAutoConfiguration, @ComponentScan.\n" +
                            "- `@RestController`: indica que la clase maneja peticiones REST.\n" +
                            "- `@RequestMapping`, `@GetMapping`, `@PostMapping`, etc.\n" +
                            "- `@Autowired`: inyección de dependencias.\n" +
                            "- `@Entity`, `@Table`, `@Id`: para JPA.\n\n" +
                            "**Arquitectura por capas:**\n" +
                            "- Controlador (presentación)\n" +
                            "- Servicio (lógica de negocio)\n" +
                            "- Repositorio (acceso a datos)\n\n" +
                            "**Persistencia:** Spring Data JPA permite trabajar con bases de datos relacionales usando interfaces Repository.\n\n" +
                            "**Seguridad:** Spring Security proporciona autenticación y autorización.\n\n" +
                            "Spring Boot es ideal para construir APIs REST, aplicaciones web y sistemas empresariales de manera rápida y eficiente.";

            Lectura lecturaSpring = new Lectura("Desarrollo Web con Spring Boot", textoLargo4, informatica);
            lecturaRepository.save(lecturaSpring);

            // 15 preguntas para Spring Boot
            Pregunta spr1 = new Pregunta("¿Qué anotación combina @Configuration, @EnableAutoConfiguration y @ComponentScan?", lecturaSpring);
            preguntaRepository.save(spr1);
            Opcion spr1a = new Opcion("@SpringBootApplication", spr1);
            Opcion spr1b = new Opcion("@SpringApplication", spr1);
            Opcion spr1c = new Opcion("@EnableSpringBoot", spr1);
            Opcion spr1d = new Opcion("@BootApplication", spr1);
            opcionRepository.save(spr1a); opcionRepository.save(spr1b); opcionRepository.save(spr1c); opcionRepository.save(spr1d);
            spr1.setRespuestaCorrecta(spr1a);
            preguntaRepository.save(spr1);

            Pregunta spr2 = new Pregunta("¿Qué servidor web embebido usa Spring Boot por defecto?", lecturaSpring);
            preguntaRepository.save(spr2);
            Opcion spr2a = new Opcion("Jetty", spr2);
            Opcion spr2b = new Opcion("Undertow", spr2);
            Opcion spr2c = new Opcion("Tomcat", spr2);
            Opcion spr2d = new Opcion("Netty", spr2);
            opcionRepository.save(spr2a); opcionRepository.save(spr2b); opcionRepository.save(spr2c); opcionRepository.save(spr2d);
            spr2.setRespuestaCorrecta(spr2c);
            preguntaRepository.save(spr2);

            Pregunta spr3 = new Pregunta("¿Qué anotación se usa para inyectar dependencias en Spring?", lecturaSpring);
            preguntaRepository.save(spr3);
            Opcion spr3a = new Opcion("@Inject", spr3);
            Opcion spr3b = new Opcion("@Resource", spr3);
            Opcion spr3c = new Opcion("@Autowired", spr3);
            Opcion spr3d = new Opcion("@Component", spr3);
            opcionRepository.save(spr3a); opcionRepository.save(spr3b); opcionRepository.save(spr3c); opcionRepository.save(spr3d);
            spr3.setRespuestaCorrecta(spr3c);
            preguntaRepository.save(spr3);

            Pregunta spr4 = new Pregunta("¿Qué anotación convierte una clase en un bean de servicio?", lecturaSpring);
            preguntaRepository.save(spr4);
            Opcion spr4a = new Opcion("@Repository", spr4);
            Opcion spr4b = new Opcion("@Service", spr4);
            Opcion spr4c = new Opcion("@Controller", spr4);
            Opcion spr4d = new Opcion("@Component", spr4);
            opcionRepository.save(spr4a); opcionRepository.save(spr4b); opcionRepository.save(spr4c); opcionRepository.save(spr4d);
            spr4.setRespuestaCorrecta(spr4b);
            preguntaRepository.save(spr4);

            Pregunta spr5 = new Pregunta("¿Qué anotación mapea peticiones HTTP GET a un método?", lecturaSpring);
            preguntaRepository.save(spr5);
            Opcion spr5a = new Opcion("@RequestMapping(method=GET)", spr5);
            Opcion spr5b = new Opcion("@GetMapping", spr5);
            Opcion spr5c = new Opcion("@PostMapping", spr5);
            Opcion spr5d = new Opcion("@RequestParam", spr5);
            opcionRepository.save(spr5a); opcionRepository.save(spr5b); opcionRepository.save(spr5c); opcionRepository.save(spr5d);
            spr5.setRespuestaCorrecta(spr5b);
            preguntaRepository.save(spr5);

            Pregunta spr6 = new Pregunta("¿Cuál es el archivo de configuración principal de Spring Boot?", lecturaSpring);
            preguntaRepository.save(spr6);
            Opcion spr6a = new Opcion("application.properties", spr6);
            Opcion spr6b = new Opcion("spring.xml", spr6);
            Opcion spr6c = new Opcion("boot.properties", spr6);
            Opcion spr6d = new Opcion("config.yml", spr6);
            opcionRepository.save(spr6a); opcionRepository.save(spr6b); opcionRepository.save(spr6c); opcionRepository.save(spr6d);
            spr6.setRespuestaCorrecta(spr6a);
            preguntaRepository.save(spr6);

            Pregunta spr7 = new Pregunta("¿Qué anotación indica que una clase es una entidad JPA?", lecturaSpring);
            preguntaRepository.save(spr7);
            Opcion spr7a = new Opcion("@Document", spr7);
            Opcion spr7b = new Opcion("@Table", spr7);
            Opcion spr7c = new Opcion("@Entity", spr7);
            Opcion spr7d = new Opcion("@Model", spr7);
            opcionRepository.save(spr7a); opcionRepository.save(spr7b); opcionRepository.save(spr7c); opcionRepository.save(spr7d);
            spr7.setRespuestaCorrecta(spr7c);
            preguntaRepository.save(spr7);

            Pregunta spr8 = new Pregunta("¿Qué interfaz de Spring Data JPA proporciona métodos CRUD básicos?", lecturaSpring);
            preguntaRepository.save(spr8);
            Opcion spr8a = new Opcion("JpaRepository", spr8);
            Opcion spr8b = new Opcion("CrudRepository", spr8);
            Opcion spr8c = new Opcion("PagingAndSortingRepository", spr8);
            Opcion spr8d = new Opcion("Repository", spr8);
            opcionRepository.save(spr8a); opcionRepository.save(spr8b); opcionRepository.save(spr8c); opcionRepository.save(spr8d);
            spr8.setRespuestaCorrecta(spr8b);
            preguntaRepository.save(spr8);

            Pregunta spr9 = new Pregunta("¿Qué anotación se usa para definir una consulta personalizada en un repositorio?", lecturaSpring);
            preguntaRepository.save(spr9);
            Opcion spr9a = new Opcion("@Query", spr9);
            Opcion spr9b = new Opcion("@SQL", spr9);
            Opcion spr9c = new Opcion("@NamedQuery", spr9);
            Opcion spr9d = new Opcion("@Select", spr9);
            opcionRepository.save(spr9a); opcionRepository.save(spr9b); opcionRepository.save(spr9c); opcionRepository.save(spr9d);
            spr9.setRespuestaCorrecta(spr9a);
            preguntaRepository.save(spr9);

            Pregunta spr10 = new Pregunta("¿Qué anotación permite inyectar el valor de una propiedad del archivo application.properties?", lecturaSpring);
            preguntaRepository.save(spr10);
            Opcion spr10a = new Opcion("@PropertySource", spr10);
            Opcion spr10b = new Opcion("@Value", spr10);
            Opcion spr10c = new Opcion("@ConfigurationProperties", spr10);
            Opcion spr10d = new Opcion("@Environment", spr10);
            opcionRepository.save(spr10a); opcionRepository.save(spr10b); opcionRepository.save(spr10c); opcionRepository.save(spr10d);
            spr10.setRespuestaCorrecta(spr10b);
            preguntaRepository.save(spr10);

            Pregunta spr11 = new Pregunta("¿Cuál es la anotación para manejar excepciones globalmente en Spring?", lecturaSpring);
            preguntaRepository.save(spr11);
            Opcion spr11a = new Opcion("@ExceptionHandler", spr11);
            Opcion spr11b = new Opcion("@ControllerAdvice", spr11);
            Opcion spr11c = new Opcion("@ResponseStatus", spr11);
            Opcion spr11d = new Opcion("@Throw", spr11);
            opcionRepository.save(spr11a); opcionRepository.save(spr11b); opcionRepository.save(spr11c); opcionRepository.save(spr11d);
            spr11.setRespuestaCorrecta(spr11b);
            preguntaRepository.save(spr11);

            Pregunta spr12 = new Pregunta("¿Qué dependencia starter se usa para desarrollar aplicaciones web REST?", lecturaSpring);
            preguntaRepository.save(spr12);
            Opcion spr12a = new Opcion("spring-boot-starter-data-jpa", spr12);
            Opcion spr12b = new Opcion("spring-boot-starter-web", spr12);
            Opcion spr12c = new Opcion("spring-boot-starter-security", spr12);
            Opcion spr12d = new Opcion("spring-boot-starter-thymeleaf", spr12);
            opcionRepository.save(spr12a); opcionRepository.save(spr12b); opcionRepository.save(spr12c); opcionRepository.save(spr12d);
            spr12.setRespuestaCorrecta(spr12b);
            preguntaRepository.save(spr12);

            Pregunta spr13 = new Pregunta("¿Qué anotación se coloca sobre un método para que Spring lo ejecute al iniciar la aplicación?", lecturaSpring);
            preguntaRepository.save(spr13);
            Opcion spr13a = new Opcion("@PostConstruct", spr13);
            Opcion spr13b = new Opcion("@EventListener", spr13);
            Opcion spr13c = new Opcion("@Bean", spr13);
            Opcion spr13d = new Opcion("@Component", spr13);
            opcionRepository.save(spr13a); opcionRepository.save(spr13b); opcionRepository.save(spr13c); opcionRepository.save(spr13d);
            spr13.setRespuestaCorrecta(spr13a);
            preguntaRepository.save(spr13);

            Pregunta spr14 = new Pregunta("¿Qué anotación se usa para indicar que un parámetro de método debe extraerse de la URL?", lecturaSpring);
            preguntaRepository.save(spr14);
            Opcion spr14a = new Opcion("@RequestParam", spr14);
            Opcion spr14b = new Opcion("@PathVariable", spr14);
            Opcion spr14c = new Opcion("@RequestBody", spr14);
            Opcion spr14d = new Opcion("@ModelAttribute", spr14);
            opcionRepository.save(spr14a); opcionRepository.save(spr14b); opcionRepository.save(spr14c); opcionRepository.save(spr14d);
            spr14.setRespuestaCorrecta(spr14b);
            preguntaRepository.save(spr14);

            Pregunta spr15 = new Pregunta("¿Qué archivo de construcción se usa comúnmente en proyectos Spring Boot?", lecturaSpring);
            preguntaRepository.save(spr15);
            Opcion spr15a = new Opcion("build.gradle", spr15);
            Opcion spr15b = new Opcion("pom.xml", spr15);
            Opcion spr15c = new Opcion("settings.gradle", spr15);
            Opcion spr15d = new Opcion("project.xml", spr15);
            opcionRepository.save(spr15a); opcionRepository.save(spr15b); opcionRepository.save(spr15c); opcionRepository.save(spr15d);
            spr15.setRespuestaCorrecta(spr15b);
            preguntaRepository.save(spr15);

            // ========== LECTURA 5: Estructuras de Datos ==========
            String textoLargo5 =
                    " Estructuras de Datos Fundamentales\n\n" +
                            "Las estructuras de datos organizan y almacenan datos eficientemente. Su elección impacta el rendimiento de los algoritmos.\n\n" +
                            "**Arreglos (Arrays):** Colección de elementos del mismo tipo, acceso por índice O(1). Tamaño fijo.\n\n" +
                            "**Listas enlazadas (Linked List):** Nodos con datos y punteros al siguiente/anterior. Inserción/eliminación eficiente O(1) en cabeza/cola.\n\n" +
                            "**Pilas (Stack):** LIFO (Last In, First Out). Operaciones: push, pop, peek.\n\n" +
                            "**Colas (Queue):** FIFO (First In, First Out). Operaciones: enqueue, dequeue.\n\n" +
                            "**Árboles (Trees):** Estructura jerárquica. Árbol binario: cada nodo tiene hasta dos hijos. Árbol binario de búsqueda (BST): izquierda < raíz < derecha.\n\n" +
                            "**Grafos (Graphs):** Nodos (vértices) y aristas. Pueden ser dirigidos/no dirigidos, ponderados. Recorridos: BFS (amplitud) y DFS (profundidad).\n\n" +
                            "**Tablas hash (HashMap):** Almacenan pares clave-valor. Función hash para índice. Búsqueda promedio O(1).\n\n" +
                            "**Heaps (Montículos):** Árbol binario completo. Max-heap: raíz mayor que hijos; min-heap: raíz menor. Usado en colas de prioridad.\n\n" +
                            "Dominar estas estructuras es crucial para programación competitiva y sistemas eficientes.";

            Lectura lecturaEstructuras = new Lectura("Estructuras de Datos Fundamentales", textoLargo5, informatica);
            lecturaRepository.save(lecturaEstructuras);

            // 15 preguntas para Estructuras de Datos
            Pregunta est1 = new Pregunta("¿Qué estructura de datos opera bajo el principio LIFO?", lecturaEstructuras);
            preguntaRepository.save(est1);
            Opcion est1a = new Opcion("Cola", est1);
            Opcion est1b = new Opcion("Pila", est1);
            Opcion est1c = new Opcion("Lista enlazada", est1);
            Opcion est1d = new Opcion("Árbol", est1);
            opcionRepository.save(est1a); opcionRepository.save(est1b); opcionRepository.save(est1c); opcionRepository.save(est1d);
            est1.setRespuestaCorrecta(est1b);
            preguntaRepository.save(est1);

            Pregunta est2 = new Pregunta("¿Cuál es la complejidad temporal de acceso por índice en un arreglo?", lecturaEstructuras);
            preguntaRepository.save(est2);
            Opcion est2a = new Opcion("O(n)", est2);
            Opcion est2b = new Opcion("O(log n)", est2);
            Opcion est2c = new Opcion("O(1)", est2);
            Opcion est2d = new Opcion("O(n log n)", est2);
            opcionRepository.save(est2a); opcionRepository.save(est2b); opcionRepository.save(est2c); opcionRepository.save(est2d);
            est2.setRespuestaCorrecta(est2c);
            preguntaRepository.save(est2);

            Pregunta est3 = new Pregunta("¿En una lista enlazada simple, cada nodo contiene?", lecturaEstructuras);
            preguntaRepository.save(est3);
            Opcion est3a = new Opcion("Dato y puntero al nodo anterior", est3);
            Opcion est3b = new Opcion("Dato y puntero al siguiente nodo", est3);
            Opcion est3c = new Opcion("Solo el dato", est3);
            Opcion est3d = new Opcion("Dato y dos punteros", est3);
            opcionRepository.save(est3a); opcionRepository.save(est3b); opcionRepository.save(est3c); opcionRepository.save(est3d);
            est3.setRespuestaCorrecta(est3b);
            preguntaRepository.save(est3);

            Pregunta est4 = new Pregunta("¿Qué estructura de datos se usa típicamente para implementar una cola de prioridad?", lecturaEstructuras);
            preguntaRepository.save(est4);
            Opcion est4a = new Opcion("Pila", est4);
            Opcion est4b = new Opcion("Heap", est4);
            Opcion est4c = new Opcion("Árbol binario de búsqueda", est4);
            Opcion est4d = new Opcion("Lista enlazada", est4);
            opcionRepository.save(est4a); opcionRepository.save(est4b); opcionRepository.save(est4c); opcionRepository.save(est4d);
            est4.setRespuestaCorrecta(est4b);
            preguntaRepository.save(est4);

            Pregunta est5 = new Pregunta("¿Qué recorrido de árbol binario visita primero la raíz, luego izquierdo, luego derecho?", lecturaEstructuras);
            preguntaRepository.save(est5);
            Opcion est5a = new Opcion("Inorden", est5);
            Opcion est5b = new Opcion("Preorden", est5);
            Opcion est5c = new Opcion("Postorden", est5);
            Opcion est5d = new Opcion("Nivel", est5);
            opcionRepository.save(est5a); opcionRepository.save(est5b); opcionRepository.save(est5c); opcionRepository.save(est5d);
            est5.setRespuestaCorrecta(est5b);
            preguntaRepository.save(est5);

            Pregunta est6 = new Pregunta("¿En un HashMap, la función hash se usa para?", lecturaEstructuras);
            preguntaRepository.save(est6);
            Opcion est6a = new Opcion("Ordenar las claves", est6);
            Opcion est6b = new Opcion("Calcular el índice del arreglo", est6);
            Opcion est6c = new Opcion("Encriptar los valores", est6);
            Opcion est6d = new Opcion("Comparar claves", est6);
            opcionRepository.save(est6a); opcionRepository.save(est6b); opcionRepository.save(est6c); opcionRepository.save(est6d);
            est6.setRespuestaCorrecta(est6b);
            preguntaRepository.save(est6);

            Pregunta est7 = new Pregunta("¿Qué algoritmo de recorrido de grafos usa una cola?", lecturaEstructuras);
            preguntaRepository.save(est7);
            Opcion est7a = new Opcion("DFS", est7);
            Opcion est7b = new Opcion("BFS", est7);
            Opcion est7c = new Opcion("Dijkstra", est7);
            Opcion est7d = new Opcion("Prim", est7);
            opcionRepository.save(est7a); opcionRepository.save(est7b); opcionRepository.save(est7c); opcionRepository.save(est7d);
            est7.setRespuestaCorrecta(est7b);
            preguntaRepository.save(est7);

            Pregunta est8 = new Pregunta("¿Qué propiedad cumple un árbol binario de búsqueda (BST)?", lecturaEstructuras);
            preguntaRepository.save(est8);
            Opcion est8a = new Opcion("Todos los nodos tienen dos hijos", est8);
            Opcion est8b = new Opcion("Para cada nodo, los valores izquierdos son menores y los derechos mayores", est8);
            Opcion est8c = new Opcion("Los nodos están ordenados por nivel", est8);
            Opcion est8d = new Opcion("Es completamente balanceado", est8);
            opcionRepository.save(est8a); opcionRepository.save(est8b); opcionRepository.save(est8c); opcionRepository.save(est8d);
            est8.setRespuestaCorrecta(est8b);
            preguntaRepository.save(est8);

            Pregunta est9 = new Pregunta("¿Qué operación tiene complejidad O(1) en una pila?", lecturaEstructuras);
            preguntaRepository.save(est9);
            Opcion est9a = new Opcion("Buscar un elemento", est9);
            Opcion est9b = new Opcion("Insertar al inicio (push)", est9);
            Opcion est9c = new Opcion("Insertar en medio", est9);
            Opcion est9d = new Opcion("Ordenar", est9);
            opcionRepository.save(est9a); opcionRepository.save(est9b); opcionRepository.save(est9c); opcionRepository.save(est9d);
            est9.setRespuestaCorrecta(est9b);
            preguntaRepository.save(est9);

            Pregunta est10 = new Pregunta("¿Qué estructura de datos permite almacenar pares clave-valor con búsqueda rápida?", lecturaEstructuras);
            preguntaRepository.save(est10);
            Opcion est10a = new Opcion("Lista enlazada", est10);
            Opcion est10b = new Opcion("Árbol binario", est10);
            Opcion est10c = new Opcion("Tabla hash", est10);
            Opcion est10d = new Opcion("Cola", est10);
            opcionRepository.save(est10a); opcionRepository.save(est10b); opcionRepository.save(est10c); opcionRepository.save(est10d);
            est10.setRespuestaCorrecta(est10c);
            preguntaRepository.save(est10);

            Pregunta est11 = new Pregunta("¿Cuál es la complejidad promedio de búsqueda en una tabla hash bien diseñada?", lecturaEstructuras);
            preguntaRepository.save(est11);
            Opcion est11a = new Opcion("O(n)", est11);
            Opcion est11b = new Opcion("O(log n)", est11);
            Opcion est11c = new Opcion("O(1)", est11);
            Opcion est11d = new Opcion("O(n^2)", est11);
            opcionRepository.save(est11a); opcionRepository.save(est11b); opcionRepository.save(est11c); opcionRepository.save(est11d);
            est11.setRespuestaCorrecta(est11c);
            preguntaRepository.save(est11);

            Pregunta est12 = new Pregunta("¿Qué estructura de datos se recomienda para implementar un sistema de deshacer (undo)?", lecturaEstructuras);
            preguntaRepository.save(est12);
            Opcion est12a = new Opcion("Cola", est12);
            Opcion est12b = new Opcion("Lista", est12);
            Opcion est12c = new Opcion("Pila", est12);
            Opcion est12d = new Opcion("Árbol", est12);
            opcionRepository.save(est12a); opcionRepository.save(est12b); opcionRepository.save(est12c); opcionRepository.save(est12d);
            est12.setRespuestaCorrecta(est12c);
            preguntaRepository.save(est12);

            Pregunta est13 = new Pregunta("¿En un grafo no dirigido, las aristas son?", lecturaEstructuras);
            preguntaRepository.save(est13);
            Opcion est13a = new Opcion("Unidireccionales", est13);
            Opcion est13b = new Opcion("Bidireccionales", est13);
            Opcion est13c = new Opcion("Con peso", est13);
            Opcion est13d = new Opcion("Sin dirección", est13);
            opcionRepository.save(est13a); opcionRepository.save(est13b); opcionRepository.save(est13c); opcionRepository.save(est13d);
            est13.setRespuestaCorrecta(est13b);
            preguntaRepository.save(est13);

            Pregunta est14 = new Pregunta("¿Qué estructura de datos es ideal para implementar una cola de impresión?", lecturaEstructuras);
            preguntaRepository.save(est14);
            Opcion est14a = new Opcion("Pila", est14);
            Opcion est14b = new Opcion("Heap", est14);
            Opcion est14c = new Opcion("Cola FIFO", est14);
            Opcion est14d = new Opcion("Lista doblemente enlazada", est14);
            opcionRepository.save(est14a); opcionRepository.save(est14b); opcionRepository.save(est14c); opcionRepository.save(est14d);
            est14.setRespuestaCorrecta(est14c);
            preguntaRepository.save(est14);

            Pregunta est15 = new Pregunta("¿Cuál es la altura de un árbol con un solo nodo?", lecturaEstructuras);
            preguntaRepository.save(est15);
            Opcion est15a = new Opcion("0", est15);
            Opcion est15b = new Opcion("1", est15);
            Opcion est15c = new Opcion("2", est15);
            Opcion est15d = new Opcion("No definida", est15);
            opcionRepository.save(est15a); opcionRepository.save(est15b); opcionRepository.save(est15c); opcionRepository.save(est15d);
            est15.setRespuestaCorrecta(est15a);
            preguntaRepository.save(est15);

            System.out.println("Datos iniciales cargados: 5 lecturas con 15 preguntas cada una.");
        }
    }
}