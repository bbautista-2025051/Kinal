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
        if (carreraRepository.count() == 0) {
            // ==================== CARRERAS ====================
            Carrera informatica = new Carrera("Perito en Informática",
                    "Desarrollo de software, bases de datos, redes y programación orientada a objetos.");
            carreraRepository.save(informatica);

            Carrera mecanica = new Carrera("Mecánica Automotriz",
                    "Estudio de la mecánica de vehículos, motores diésel y gasolina, diagnóstico y reparación.");
            carreraRepository.save(mecanica);

            Carrera electricidad = new Carrera("Electricidad",
                    "Fundamentos de electricidad, circuitos, ley de Ohm, corriente alterna y continua, seguridad eléctrica.");
            carreraRepository.save(electricidad);

            // ==================== LECTURAS INFORMÁTICA ====================
            crearLecturaJava(informatica);
            crearLecturaHTML(informatica);
            crearLecturaPython(informatica);
            crearLecturaInformaticaGeneral(informatica);
            crearLecturaAlgoritmos(informatica);
            crearLecturaIA(informatica);

            // ==================== LECTURAS MECÁNICA ====================
            crearLecturaMecanicaGeneral(mecanica);
            crearLecturaMecanicaDiesel(mecanica);
            crearLecturaMecanicaGasolina(mecanica);

            // ==================== LECTURAS ELECTRICIDAD ====================
            crearLecturaElectricidad1(electricidad);
            crearLecturaElectricidad2(electricidad);
            crearLecturaElectricidad3(electricidad);
            crearLecturaElectricidad4(electricidad);
            crearLecturaElectricidad5(electricidad);

            System.out.println("Datos iniciales cargados correctamente.");
        }
    }

    // ================================================================
    // MÉTODOS AUXILIARES PARA CADA LECTURA
    // ================================================================

    private void crearLecturaJava(Carrera c) {
        String texto = "Introducción a Java: El Lenguaje Universal\n\n" +
                "Java es un lenguaje de programación de alto nivel, orientado a objetos y diseñado para tener la menor cantidad posible de dependencias de implementación. " +
                "Lanzado originalmente por Sun Microsystems en 1995, su filosofía principal se resume en la frase: \"Write Once, Run Anywhere\" (Escríbelo una vez, ejecútalo en cualquier lugar). " +
                "Esto significa que el código compilado de Java puede ejecutarse en todas las plataformas que admitan Java sin necesidad de volver a compilarlo.\n\n" +
                "¿Qué es Java técnicamente?\n\n" +
                "A diferencia de otros lenguajes que se compilan directamente a código máquina (específico para un procesador), Java se compila en un formato intermedio llamado Bytecode. " +
                "Este Bytecode no es ejecutado directamente por el hardware, sino por la Máquina Virtual de Java (JVM). La JVM actúa como un intérprete o traductor en tiempo real, lo que otorga a Java su característica de portabilidad. " +
                "Además, es un lenguaje fuertemente tipado y cuenta con un \"Recolector de Basura\" (Garbage Collector), que gestiona la memoria automáticamente, evitando errores comunes de programación.\n\n" +
                "¿Para qué sirve?\n\n" +
                "Java es uno de los lenguajes más versátiles y utilizados en el mundo tecnológico actual. Sus usos principales incluyen:\n" +
                "Aplicaciones Móviles: Es el lenguaje nativo histórico de Android.\n" +
                "Desarrollo Empresarial: Grandes corporaciones y bancos lo usan por su robustez y seguridad.\n" +
                "Sistemas Embebidos: Se encuentra en tarjetas SIM, televisores inteligentes y electrodomésticos.\n" +
                "Big Data y Ciencia: Herramientas como Apache Hadoop están escritas en Java.\n" +
                "Computación en la Nube: Fundamental para servicios de alta disponibilidad.\n\n" +
                "En resumen, Java no es solo un lenguaje, sino una plataforma tecnológica completa.";
        Lectura l = new Lectura("Introducción a Java", texto, c);
        lecturaRepository.save(l);

        // 10 preguntas
        agregarPregunta(l, "El lema \"Write Once, Run Anywhere\" sugiere principalmente que:",
                new String[]{"El código de Java es imposible de hackear.",
                        "Java elimina la necesidad de adaptar el software a diferentes sistemas operativos.",
                        "Java es el único lenguaje que existe para computadoras Mac.",
                        "El programador solo debe escribir el código una vez porque no permite correcciones."}, 1);
        agregarPregunta(l, "¿Cuál es la función estratégica de la Máquina Virtual de Java (JVM)?",
                new String[]{"Funcionar como un antivirus integrado dentro del código.",
                        "Traducir el código directamente a lenguaje humano.",
                        "Actuar como un puente que permite la portabilidad entre hardware distinto.",
                        "Almacenar de forma permanente los archivos del usuario en la nube."}, 2);
        agregarPregunta(l, "Si una empresa decide usar Java para su sistema bancario, ¿cuál es la razón crítica más probable?",
                new String[]{"Es el lenguaje más nuevo y moderno del mercado.",
                        "Su gestión automática de memoria y robustez ofrecen mayor estabilidad y seguridad.",
                        "Java es el lenguaje más barato de instalar.",
                        "Es el único lenguaje que permite usar colores en la interfaz."}, 1);
        agregarPregunta(l, "¿Qué se puede inferir sobre el \"Bytecode\" en el proceso de ejecución?",
                new String[]{"Es un lenguaje que solo los humanos pueden leer con facilidad.",
                        "Es el paso final antes de que el programa se borre.",
                        "Es un estado intermedio que permite que el código sea universal antes de llegar a la JVM.",
                        "Es el código que se descarga directamente de la Play Store."}, 2);
        agregarPregunta(l, "El uso de Java en electrodomésticos y tarjetas SIM demuestra que:",
                new String[]{"Java requiere computadoras muy potentes para funcionar.",
                        "El lenguaje es lo suficientemente eficiente para ejecutarse en dispositivos con recursos limitados.",
                        "Los electrodomésticos ahora pueden navegar por internet como una PC.",
                        "Java pronto reemplazará a todos los demás lenguajes."}, 1);
        agregarPregunta(l, "¿Cuál es el impacto del \"Garbage Collector\" en el desarrollo de software a gran escala?",
                new String[]{"Elimina archivos que el usuario ya no necesita en su disco duro.",
                        "Previene errores críticos de sistema al gestionar automáticamente los recursos de memoria.",
                        "Hace que el programa sea más pesado y difícil de descargar.",
                        "Permite que el programador escriba código sin seguir ninguna regla."}, 1);
        agregarPregunta(l, "Al ser un lenguaje \"fuertemente tipado\", se deduce que Java:",
                new String[]{"Permite escribir código muy rápido sin preocuparse por los detalles.",
                        "Es ideal para personas que no saben nada de lógica de programación.",
                        "Exige una estructura rigurosa que ayuda a detectar errores antes de que el programa se ejecute.",
                        "Solo funciona si el usuario escribe muy rápido en el teclado."}, 2);
        agregarPregunta(l, "En el contexto de Android, ¿qué importancia tiene Java para la economía digital?",
                new String[]{"Es irrelevante, ya que existen otros lenguajes para teléfonos.",
                        "Es un pilar fundamental, pues la mayoría de la infraestructura de apps móviles se construyó con él.",
                        "Sirve únicamente para juegos, pero no para aplicaciones de trabajo.",
                        "Solo sirve para el diseño visual de los iconos."}, 1);
        agregarPregunta(l, "¿Qué relación hay entre el concepto \"Orientado a Objetos\" y el trabajo en equipo?",
                new String[]{"Impide que dos personas trabajen en el mismo proyecto al mismo tiempo.",
                        "Facilita la división del trabajo al permitir crear componentes independientes y reutilizables.",
                        "Obliga a todos los programadores a pensar exactamente de la misma manera.",
                        "No tiene ninguna relación con el trabajo humano, solo con las máquinas."}, 1);
        agregarPregunta(l, "¿Cuál es la conclusión más lógica sobre el futuro de Java según el texto?",
                new String[]{"Desaparecerá pronto porque es un lenguaje viejo de los años 90.",
                        "Seguirá siendo vital debido a su presencia en la infraestructura crítica (bancos, servidores, nubes).",
                        "Solo se usará en escuelas para enseñar a niños, pero no en empresas.",
                        "Se convertirá en un sistema operativo que reemplazará a Windows."}, 1);
    }

    private void crearLecturaHTML(Carrera c) {
        String texto = "EL ESQUELETO DE LA WEB: COMPRENDIENDO HTML\n\n" +
                "HTML (HyperText Markup Language o Lenguaje de Marcado de Hipertexto) es el estándar universal que define la estructura y el contenido de las páginas web. " +
                "A diferencia de lo que muchos creen, no es un lenguaje de programación, sino un lenguaje de marcado. " +
                "Esto significa que utiliza una serie de \"etiquetas\" para indicarle al navegador cómo debe organizar la información: qué es un título, qué es un párrafo, dónde va una imagen o un enlace.\n\n" +
                "¿Qué es HTML técnicamente? El lenguaje funciona mediante una jerarquía de etiquetas que encierran el contenido. " +
                "Por ejemplo, una etiqueta <h1> le dice al navegador: \"Este texto es el título principal\". " +
                "Una de las características más importantes de HTML es el hipertexto, que permite conectar un documento con otro a través de enlaces, creando la red interconectada que hoy conocemos como Internet.\n\n" +
                "¿Para qué sirve? HTML es la base indispensable de cualquier sitio en línea. Sus funciones principales son:\n" +
                "Estructuración de contenido, Accesibilidad, Optimización en buscadores (SEO), Integración multimedia, Interoperabilidad. " +
                "Sin HTML, la web no sería más que un conjunto de archivos aislados sin orden ni conexión.";
        Lectura l = new Lectura("HTML: El esqueleto de la web", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "Si HTML no es un lenguaje de programación, ¿cuál es su función principal frente a una computadora?",
                new String[]{"Realizar cálculos matemáticos complejos.",
                        "Dar órdenes directas al procesador para ejecutar programas.",
                        "Organizar y etiquetar los datos para que el navegador sepa cómo presentarlos.",
                        "Crear bases de datos para guardar contraseñas."}, 2);
        agregarPregunta(l, "¿Qué se puede inferir sobre una página web que no utiliza etiquetas HTML correctamente?",
                new String[]{"No tendrá colores ni fuentes bonitas.",
                        "Los buscadores como Google tendrán dificultades para entender su contenido y clasificarla.",
                        "El sitio web será más rápido de cargar.",
                        "La página se borrará automáticamente de internet."}, 1);
        agregarPregunta(l, "¿Por qué el concepto de \"Hipertexto\" es vital para la existencia de la World Wide Web?",
                new String[]{"Porque permite que el texto sea de color azul.",
                        "Porque permite la conexión entre documentos distintos, creando una red global de información.",
                        "Porque hace que el texto sea más fácil de leer en pantallas pequeñas.",
                        "Porque permite traducir el texto a diferentes idiomas."}, 1);
        agregarPregunta(l, "Si comparamos un edificio con una página web, el HTML representaría:",
                new String[]{"La pintura y la decoración de las paredes.",
                        "La instalación eléctrica y los ascensores en movimiento.",
                        "Los planos, los cimientos y las vigas estructurales.",
                        "El terreno donde se construye el edificio."}, 2);
        agregarPregunta(l, "¿Cómo beneficia el uso correcto de HTML a una persona con discapacidad visual?",
                new String[]{"Hace que la pantalla brille más para que vea mejor.",
                        "Permite que los lectores de pantalla identifiquen qué partes son títulos y cuáles son párrafos.",
                        "Corrige automáticamente la visión del usuario.",
                        "No tiene ningún beneficio para estas personas."}, 1);
        agregarPregunta(l, "Se afirma que HTML es \"indispensable\". ¿Qué sucedería si un navegador intentara abrir un archivo que solo tiene texto pero no HTML?",
                new String[]{"El navegador mostraría una imagen en lugar de texto.",
                        "El texto aparecería como una masa desorganizada sin jerarquía ni formato.",
                        "El navegador se cerraría por un error de seguridad.",
                        "El texto se convertiría automáticamente en un video."}, 1);
        agregarPregunta(l, "¿Qué relación se deduce entre HTML, CSS y JavaScript a partir del texto?",
                new String[]{"HTML es el líder y los otros dos ya no son necesarios.",
                        "Son tecnologías independientes que nunca deben usarse juntas.",
                        "Son complementarias: HTML pone la estructura, mientras las otras ponen el diseño y la acción.",
                        "Son exactamente lo mismo con diferentes nombres."}, 2);
        agregarPregunta(l, "Si un desarrollador quiere mejorar el SEO de su página, ¿en qué debería enfocarse respecto al HTML?",
                new String[]{"En usar la mayor cantidad de imágenes posibles.",
                        "En usar las etiquetas de forma lógica para que Google sepa qué es lo más importante del sitio.",
                        "En ocultar el código para que nadie lo pueda ver.",
                        "En hacer que el código HTML sea lo más largo posible."}, 1);
        agregarPregunta(l, "¿Por qué es importante la \"Interoperabilidad\" mencionada en el texto?",
                new String[]{"Para que las empresas puedan cobrar más por sus sitios web.",
                        "Para asegurar que la información llegue a cualquier usuario, sin importar el dispositivo que use.",
                        "Para evitar que las personas usen computadoras viejas.",
                        "Para que las páginas solo funcionen en teléfonos celulares."}, 1);
        agregarPregunta(l, "De la frase \"cada página web es un documento de texto plano\", se puede concluir que:",
                new String[]{"Crear una web básica no requiere de herramientas de software extremadamente complejas o pesadas.",
                        "El texto plano es más seguro que cualquier otro tipo de archivo.",
                        "Las páginas web no pueden contener fotos ni sonidos.",
                        "HTML es un lenguaje obsoleto que ya no se usa."}, 0);
    }

    private void crearLecturaPython(Carrera c) {
        String texto = "PYTHON: EL LENGUAJE DE LA SIMPLICIDAD Y EL PODER\n\n" +
                "Python es un lenguaje de programación de alto nivel, interpretado y de propósito general que se ha convertido en uno de los pilares de la tecnología moderna. " +
                "Fue creado por Guido van Rossum y lanzado en 1991 con una filosofía clara: la legibilidad del código es fundamental. " +
                "A diferencia de otros lenguajes que utilizan símbolos complejos (como llaves o puntos y coma), Python utiliza la sangría (espaciado) para definir la estructura del código.\n\n" +
                "¿Qué es Python técnicamente? Es un lenguaje interpretado, lo que significa que el código se ejecuta línea por línea por un intérprete. " +
                "También es multiparadigma, permitiendo trabajar con diferentes estilos (objetos, funcional). " +
                "Su gran ventaja es su extenso ecosistema de bibliotecas.\n\n" +
                "¿Para qué sirve? Inteligencia Artificial y Ciencia de Datos, Desarrollo Web (Backend), Automatización de Tareas, Educación, Prototipado. " +
                "En esencia, Python ha democratizado la programación.";
        Lectura l = new Lectura("Python: El lenguaje de la simplicidad y el poder", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Por qué se dice que Python tiene una sintaxis \"limpia\"?",
                new String[]{"Porque el código se borra automáticamente después de ser ejecutado.",
                        "Debido a que utiliza espacios y palabras simples en lugar de símbolos complejos.",
                        "Porque requiere que el programador limpie la memoria de la computadora manualmente.",
                        "Porque no permite que se escriban errores en el código."}, 1);
        agregarPregunta(l, "Si Python es un lenguaje \"interpretado\", ¿qué se puede inferir sobre su ejecución?",
                new String[]{"Es el lenguaje más rápido del mundo para videojuegos de alta resolución.",
                        "El código se traduce a lenguaje máquina antes de que el usuario lo compre.",
                        "Permite probar cambios rápidamente, ya que se ejecuta línea por línea.",
                        "Requiere de un traductor humano para que la computadora lo entienda."}, 2);
        agregarPregunta(l, "El uso de Python en la Inteligencia Artificial sugiere que:",
                new String[]{"Python es capaz de pensar por sí mismo sin ayuda humana.",
                        "Su sintaxis es tan compleja que solo las máquinas la entienden.",
                        "Sus bibliotecas facilitan el manejo de cálculos matemáticos avanzados.",
                        "Fue inventado específicamente para crear robots humanoides."}, 2);
        agregarPregunta(l, "¿Qué ventaja ofrece a una empresa el uso de Python para \"prototipado rápido\"?",
                new String[]{"Ahorro de tiempo y dinero al validar ideas antes de su desarrollo final.",
                        "Que el programa final nunca tendrá errores de ningún tipo.",
                        "La capacidad de vender el producto sin haberlo terminado realmente.",
                        "Que no es necesario contratar programadores expertos."}, 0);
        agregarPregunta(l, "¿Cómo influye la \"sangría\" (identación) en el trabajo de un programador de Python?",
                new String[]{"Es opcional y solo sirve para que el código se vea más ordenado.",
                        "Es obligatoria para que el código funcione y sea legible.",
                        "Hace que el código sea mucho más difícil de leer para otros humanos.",
                        "Sirve para que la computadora sepa qué color ponerle al texto."}, 1);
        agregarPregunta(l, "Se afirma que Python es \"multiparadigma\". Esto significa que el lenguaje es:",
                new String[]{"Único.", "Rígido.", "Flexible.", "Lento."}, 2);
        agregarPregunta(l, "Si un administrativo usa Python para organizar archivos, está aprovechando su función de:",
                new String[]{"Inteligencia Artificial.", "Desarrollo Web.", "Automatización.", "Ciencia de Datos."}, 2);
        agregarPregunta(l, "¿Qué se puede deducir del gran \"ecosistema de bibliotecas\" que posee Python?",
                new String[]{"Que los programadores de Python suelen leer muchos libros de tecnología.",
                        "Que el lenguaje es incompleto y siempre necesita parches externos.",
                        "Que permite solucionar problemas complejos usando soluciones ya probadas.",
                        "Que es obligatorio pagar una suscripción para usar cada función extra."}, 2);
        agregarPregunta(l, "¿Por qué Python es ideal para personas que no son ingenieros de software?",
                new String[]{"Porque no requiere el uso de una computadora para aprenderlo.",
                        "Debido a que su estructura es similar al lenguaje natural.",
                        "Porque es un lenguaje que se programa solo.",
                        "Porque es totalmente gratuito para quienes no tienen título."}, 1);
        agregarPregunta(l, "Del texto se infiere que la filosofía de Python prioriza:",
                new String[]{"La velocidad de la máquina por encima de todo.",
                        "El bienestar del programador y la claridad del código.",
                        "El uso de símbolos matemáticos oscuros.",
                        "El desarrollo exclusivo de aplicaciones para teléfonos."}, 1);
    }

    private void crearLecturaInformaticaGeneral(Carrera c) {
        String texto = "LA INFORMÁTICA: EL MOTOR DE LA ERA DIGITAL\n\n" +
                "La informática es la ciencia que estudia el tratamiento automático y racional de la información mediante sistemas computacionales. " +
                "La palabra proviene de la contracción de los términos información y automática. " +
                "No se limita solo a las computadoras físicas, sino que abarca el desarrollo de software, la gestión de redes, la inteligencia artificial y la arquitectura de los datos.\n\n" +
                "Breve recorrido histórico: La Máquina Analítica de Charles Babbage, Ada Lovelace considerada la primera programadora, " +
                "Alan Turing y el concepto de algoritmo, las generaciones de computadoras desde los tubos al vacío hasta el microprocesador.\n\n" +
                "¿Para qué sirve? Optimizar el tiempo, Gestión de conocimiento, Comunicación global, Simulación y Ciencia. " +
                "En conclusión, la informática transforma datos crudos en decisiones inteligentes y soluciones prácticas.";
        Lectura l = new Lectura("La Informática: El motor de la era digital", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "El término \"Informática\" sugiere que la información debe ser tratada de forma:",
                new String[]{"Manual.", "Lógica.", "Lenta.", "Visual."}, 1);
        agregarPregunta(l, "¿Qué se puede inferir sobre el papel de Ada Lovelace en el siglo XIX?",
                new String[]{"Que las mujeres no tenían interés en la ciencia.",
                        "Que la programación existía antes que las computadoras electrónicas.",
                        "Que ella inventó la electricidad para las máquinas.",
                        "Que su trabajo no tuvo importancia hasta la llegada de internet."}, 1);
        agregarPregunta(l, "¿Cuál fue el impacto más directo de la invención del transistor?",
                new String[]{"El aumento del precio de los componentes.",
                        "La miniaturización y eficiencia de los equipos.",
                        "La invención de la primera pantalla táctil.",
                        "La prohibición de las máquinas mecánicas."}, 1);
        agregarPregunta(l, "Según el texto, un algoritmo es comparable a:",
                new String[]{"Una pieza de hardware de metal.",
                        "Una serie de instrucciones lógicas.",
                        "Un tipo de cable para internet.",
                        "El monitor de una computadora."}, 1);
        agregarPregunta(l, "Si la informática ayuda a predecir el clima, se infiere que su ventaja principal es:",
                new String[]{"El control sobre la naturaleza.",
                        "La capacidad de procesamiento de datos.",
                        "La invención de satélites artificiales.",
                        "Que las computadoras nunca se equivocan."}, 1);
        agregarPregunta(l, "¿Qué significa que la informática sea una herramienta \"transversal\"?",
                new String[]{"Que solo se usa en ingeniería.",
                        "Que se aplica en todas las áreas.",
                        "Que es difícil de aprender.",
                        "Que se usa de lado."}, 1);
        agregarPregunta(l, "¿Por qué el microprocesador cambió la sociedad en los años 70?",
                new String[]{"Porque hizo las computadoras más grandes.",
                        "Porque permitió la computación personal.",
                        "Porque eliminó la necesidad de usar software.",
                        "Porque fue el fin de la era digital."}, 1);
        agregarPregunta(l, "De la relación entre \"datos crudos\" y \"decisiones inteligentes\", se deduce que:",
                new String[]{"Los datos por sí solos no tienen valor sin procesamiento.",
                        "Una computadora puede tomar decisiones sin necesidad de datos.",
                        "Los datos siempre son inteligentes desde que se crean.",
                        "Las decisiones humanas son menos valiosas que las de una PC."}, 0);
        agregarPregunta(l, "¿Qué habría ocurrido con la informática si Alan Turing no hubiera definido el \"algoritmo\"?",
                new String[]{"La informática no tendría una base lógica sólida.",
                        "Las computadoras serían exclusivamente de madera.",
                        "El hardware funcionaría más rápido de lo normal.",
                        "Internet se habría inventado mucho antes."}, 0);
        agregarPregunta(l, "La frase \"tratamiento automático y racional\" implica que la computadora:",
                new String[]{"Tiene sentimientos.", "Sigue reglas.", "Es humana.", "Piensa sola."}, 1);
    }

    private void crearLecturaAlgoritmos(Carrera c) {
        String texto = "ALGORITMOS Y ESTRUCTURAS DE DATOS: EL CORAZÓN DE LA COMPUTACIÓN\n\n" +
                "En el mundo de la informática, si el hardware es el cuerpo de una máquina, los algoritmos y las estructuras de datos son su cerebro y su memoria organizada. " +
                "Estos dos conceptos son inseparables: mientras que los algoritmos dictan los pasos para resolver un problema, " +
                "las estructuras de datos definen cómo se organiza la información para que esos pasos sean lo más eficientes posible.\n\n" +
                "¿Qué es un Algoritmo? Una secuencia lógica, finita y definida de pasos destinados a resolver un problema o realizar una tarea. " +
                "No es exclusivo de las computadoras; una receta de cocina o las instrucciones para armar un mueble son algoritmos.\n\n" +
                "¿Qué son las Estructuras de Datos? Formas específicas de organizar y almacenar datos en una computadora para que puedan ser utilizados de manera eficiente. " +
                "Lista, Árbol, Tabla Hash, etc.\n\n" +
                "¿Para qué sirven? Optimizar recursos, Escalabilidad, Resolución de problemas complejos. " +
                "Dominar estos conceptos es lo que diferencia a un verdadero ingeniero de software.";
        Lectura l = new Lectura("Algoritmos y Estructuras de Datos", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "Si un algoritmo es \"definido\", esto implica que:",
                new String[]{"Siempre es muy largo.", "No admite ambigüedad.", "Nunca puede fallar.", "Se escribe en inglés."}, 1);
        agregarPregunta(l, "¿Cuál es la relación principal entre un algoritmo y una estructura de datos?",
                new String[]{"Son enemigos naturales.", "El algoritmo organiza el hardware.", "Son herramientas complementarias.", "La estructura de datos es un algoritmo."}, 2);
        agregarPregunta(l, "Un algoritmo que nunca termina violaría la característica de ser:",
                new String[]{"Preciso.", "Eficiente.", "Finito.", "Útil."}, 2);
        agregarPregunta(l, "¿Por qué elegir la estructura de datos correcta afecta la \"escalabilidad\"?",
                new String[]{"Porque permite que el sistema crezca sin colapsar.", "Porque hace que el monitor se vea más grande.", "Porque borra los datos que ya no sirven.", "Porque el código se escribe más rápido."}, 0);
        agregarPregunta(l, "¿Qué se infiere sobre el uso de un GPS al leer este texto?",
                new String[]{"El GPS solo usa mapas, no algoritmos.", "Usa algoritmos para calcular la ruta más rápida.", "Los datos se guardan sin ningún orden.", "Solo funciona con estructuras de datos de tipo \"Lista\"."}, 1);
        agregarPregunta(l, "Si un programa funciona pero es extremadamente lento, el problema es de:",
                new String[]{"Hardware.", "Eficiencia.", "Color.", "Memoria."}, 1);
        agregarPregunta(l, "Una \"jerarquía de carpetas\" en tu PC es un ejemplo de:",
                new String[]{"Algoritmo de cocina.", "Estructura de datos.", "Pasos lógicos finitos.", "Memoria RAM vacía."}, 1);
        agregarPregunta(l, "Del texto se deduce que los algoritmos existen:",
                new String[]{"Solo desde 1990.", "Únicamente en Python.", "Fuera de las computadoras.", "Solo para matemáticos."}, 2);
        agregarPregunta(l, "¿Qué pasaría si intentamos procesar millones de datos con una estructura inadecuada?",
                new String[]{"La computadora explotará.", "El proceso será ineficiente.", "Los datos se volverán falsos.", "El algoritmo se volverá infinito."}, 1);
        agregarPregunta(l, "La \"elegancia\" en una solución informática mencionada al final se refiere a:",
                new String[]{"Que el código tenga colores bonitos.", "La simplicidad y eficacia del diseño.", "Usar el lenguaje más caro del mundo.", "Que el programador use traje al trabajar."}, 1);
    }

    private void crearLecturaIA(Carrera c) {
        String texto = "INTELIGENCIA ARTIFICIAL Y APRENDIZAJE AUTOMÁTICO: LA NUEVA FRONTERA\n\n" +
                "La Inteligencia Artificial (IA) es la rama de las ciencias de la computación que busca crear sistemas capaces de realizar tareas que, normalmente, requerirían de la inteligencia humana. " +
                "El Aprendizaje Automático (Machine Learning) es una disciplina específica que permite que las computadoras \"aprendan\" a partir de datos sin ser programadas explícitamente.\n\n" +
                "Un poco de historia: El concepto nació formalmente en 1956. En sus inicios se basaba en reglas lógicas rígidas. " +
                "En la década de 2010, gracias al Big Data y a la potencia de procesamiento, el Aprendizaje Automático despegó.\n\n" +
                "¿Para qué sirve? Asistentes virtuales, Medicina, Transporte (vehículos autónomos), Recomendaciones.\n\n" +
                "Pros y Contras: Aumenta la productividad, elimina tareas peligrosas, reduce errores. " +
                "Pero puede causar desplazamiento de empleos, dilemas éticos de privacidad y decisiones sesgadas.\n\n" +
                "En conclusión, la IA no busca reemplazar al ser humano, sino ampliar sus capacidades, y exige una regulación ética responsable.";
        Lectura l = new Lectura("Inteligencia Artificial y Aprendizaje Automático", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cuál es la diferencia fundamental entre la IA de 1956 y el Aprendizaje Automático actual?",
                new String[]{"La IA actual no necesita electricidad para funcionar.",
                        "El Aprendizaje Automático depende de patrones en datos, no solo de reglas fijas.",
                        "La IA antigua era más inteligente que la actual.",
                        "No hay diferencia; ambos funcionan exactamente igual."}, 1);
        agregarPregunta(l, "Si una IA aprende de datos \"sesgados\" o con prejuicios, se infiere que:",
                new String[]{"La máquina corregirá los errores por su cuenta.",
                        "El sistema se apagará para no cometer injusticias.",
                        "Los resultados serán discriminatorios.",
                        "La IA dejará de ser una tecnología digital."}, 2);
        agregarPregunta(l, "¿Por qué el \"Big Data\" fue clave para el desarrollo del Aprendizaje Automático?",
                new String[]{"Porque ocupa mucho espacio físico.",
                        "Proporciona el volumen de ejemplos necesario para que la máquina aprenda.",
                        "Permite que las computadoras sean más baratas.",
                        "Es el nombre del primer robot que aprendió solo."}, 1);
        agregarPregunta(l, "El uso de IA en la medicina sugiere que esta tecnología:",
                new String[]{"Reemplazará a todos los médicos pronto.",
                        "Es una herramienta de apoyo al diagnóstico.",
                        "Solo sirve para tomar fotografías.",
                        "Es menos precisa que un humano."}, 1);
        agregarPregunta(l, "Un \"pro\" de la IA relacionado con la seguridad laboral es:",
                new String[]{"Que las máquinas no cobran sueldo.",
                        "Que pueden realizar labores riesgosas.",
                        "Que las computadoras nunca se rompen.",
                        "El aumento de vacaciones para los jefes."}, 1);
        agregarPregunta(l, "¿Qué significa que la IA \"amplía las capacidades humanas\"?",
                new String[]{"Que nos convierte en robots.",
                        "Que nos permite hacer más cosas en menos tiempo.",
                        "Que ya no necesitaremos estudiar.",
                        "Que mejora nuestra visión física."}, 1);
        agregarPregunta(l, "Del texto se deduce que los vehículos autónomos:",
                new String[]{"No necesitan mapas para circular.",
                        "Usan IA para tomar decisiones en el camino.",
                        "Son imposibles de chocar.",
                        "Funcionan con reglas de 1956."}, 1);
        agregarPregunta(l, "Una crítica ética que se menciona en el texto sobre la IA es:",
                new String[]{"El alto costo de las computadoras.",
                        "La posible pérdida de privacidad.",
                        "Que las máquinas son muy lentas.",
                        "El color de los cables utilizados."}, 1);
        agregarPregunta(l, "Si una tarea es repetitiva y aburrida, se infiere que:",
                new String[]{"Es ideal para ser automatizada con IA.",
                        "Ninguna máquina podrá hacerla jamás.",
                        "Solo un científico de 1956 podría resolverla.",
                        "La IA se aburrirá y dejará de funcionar."}, 0);
        agregarPregunta(l, "La conclusión del texto enfatiza que el futuro de la IA debe ser:",
                new String[]{"Libre y sin reglas.", "Ético y regulado.", "Solo para expertos.", "Controlado por máquinas."}, 1);
    }

    // ==================== MECÁNICA AUTOMOTRIZ ====================
    private void crearLecturaMecanicaGeneral(Carrera c) {
        String texto = "QUÉ ES LA MECÁNICA AUTOMOTRIZ\n\n" +
                "La mecánica automotriz es una de las ramas de la mecánica que se encarga de estudiar las formas de generación y transmisión del movimiento de un vehículo. " +
                "Para lograr este propósito aplica los principios propios de la física y la mecánica para optimizar el proceso de movimiento en todo vehículo motorizado. " +
                "Este movimiento o movimientos se generan gracias al diseño de una diversidad de autopartes que conforman la estructura del vehículo.\n\n" +
                "HISTORIA DE LA MECÁNICA AUTOMOTRIZ\n\n" +
                "Desde Arquímedes en la antigua Grecia, Herón de Alejandría con la primera máquina de vapor, Ma Jung con el diferencial de engranajes, " +
                "los ingenieros musulmanes como Al Jazarí, hasta Isaac Newton con sus tres leyes.\n\n" +
                "ELEMENTOS QUE ESTUDIA LA MECÁNICA AUTOMOTRIZ: Motor, Árbol de levas, Embrague, Cigüeñal, Correa de distribución.\n\n" +
                "IMPORTANCIA DE LA MECÁNICA AUTOMOTRIZ: Inspeccionar, diagnosticar y reparar.\n\n" +
                "QUÉ HACE UN MECÁNICO AUTOMOTRIZ: Diagnosticar, presupuestar, desmontar, reemplazar, ensamblar, orientar al cliente.";
        Lectura l = new Lectura("Qué es la Mecánica Automotriz", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "Si la mecánica automotriz aplica principios de la física para optimizar el movimiento, se puede inferir que:",
                new String[]{"El movimiento de un auto es un proceso puramente azaroso.",
                        "La eficiencia del vehículo depende del cumplimiento de leyes naturales.",
                        "La física solo se aplica cuando el motor está apagado.",
                        "Un mecánico no necesita entender de ciencia, solo de herramientas."}, 1);
        agregarPregunta(l, "A partir de los aportes de Herón de Alejandría y Ma Jung, se deduce que la mecánica automotriz:",
                new String[]{"Fue inventada por una sola persona en un año específico.",
                        "Es el resultado de una evolución tecnológica acumulativa de siglos.",
                        "Solo empezó a existir cuando se inventó la gasolina.",
                        "Es una ciencia moderna que no tiene raíces en la antigüedad."}, 1);
        agregarPregunta(l, "¿Qué importancia crítica tienen las Leyes de Newton para un mecánico hoy en día?",
                new String[]{"Permiten entender las fuerzas que generan y detienen el movimiento.",
                        "Sirven para calcular el costo de los repuestos en el taller.",
                        "Son reglas obsoletas que ya no se aplican a los motores nuevos.",
                        "Ayudan exclusivamente a decorar el manual de usuario del vehículo."}, 0);
        agregarPregunta(l, "El texto describe al motor como el \"corazón\" del vehículo. Bajo esta analogía, el embrague funcionaría como:",
                new String[]{"El combustible que alimenta al cuerpo.",
                        "Un interruptor que controla el flujo de energía hacia las extremidades.",
                        "Los pulmones que permiten la entrada de aire al sistema.",
                        "La estructura ósea que sostiene todo el peso del coche."}, 1);
        agregarPregunta(l, "¿Qué sucedería si el cigüeñal no pudiera convertir el movimiento alternativo en giratorio?",
                new String[]{"El vehículo se movería más rápido en línea recta.",
                        "El motor funcionaría pero las ruedas no girarían.",
                        "El auto solo podría encenderse con electricidad.",
                        "El combustible se gastaría de forma más lenta."}, 1);
        agregarPregunta(l, "Si un mecánico nota que las válvulas no abren ni cierran en el momento exacto, ¿qué pieza debería inspeccionar primero según el texto?",
                new String[]{"El embrague.", "El cigüeñal.", "La correa de distribución.", "El chasis."}, 2);
        agregarPregunta(l, "Del texto se infiere que la mecánica automotriz es \"preventiva\" porque:",
                new String[]{"Solo actúa cuando el motor ya ha dejado de funcionar.",
                        "Permite anticiparse a fallas antes de que ocurran daños mayores.",
                        "Es una disciplina que prohíbe el uso de vehículos antiguos.",
                        "Obliga al conductor a comprar un auto nuevo cada año."}, 1);
        agregarPregunta(l, "¿Cuál de las siguientes funciones del mecánico requiere de mayor capacidad de comunicación y ética?",
                new String[]{"Desmontar las partes del motor.",
                        "Orientar al cliente sobre el funcionamiento del vehículo.",
                        "Reemplazar partes dañadas de forma segura.",
                        "Elaborar el presupuesto de las reparaciones."}, 1);
        agregarPregunta(l, "Si la mecánica automotriz abarca una \"diversidad de elementos que funcionan como uno mismo\", esto significa que:",
                new String[]{"Si una pieza falla, el sistema integral se ve comprometido.",
                        "Todas las piezas de un auto hacen exactamente la misma función.",
                        "Se puede quitar cualquier pieza sin que el auto deje de moverse.",
                        "El motor puede trabajar solo, sin necesidad de otras partes."}, 0);
        agregarPregunta(l, "¿Por qué se considera la mecánica automotriz como una \"excelente área de emprendimiento\"?",
                new String[]{"Porque los mecánicos no necesitan estudiar para trabajar.",
                        "Por la necesidad constante de mantenimiento en el transporte moderno.",
                        "Porque las piezas de los autos son regaladas por las fábricas.",
                        "Debido a que ya no existen talleres mecánicos en el mundo."}, 1);
    }

    private void crearLecturaMecanicaDiesel(Carrera c) {
        String texto = "LA MECÁNICA DIÉSEL: POTENCIA, EFICIENCIA Y ROBUSTEZ\n\n" +
                "La mecánica diésel es la rama especializada de la ingeniería automotriz que se ocupa del estudio, diagnóstico, mantenimiento y reparación de los motores de encendido por compresión. " +
                "A diferencia de los motores de gasolina (que utilizan una chispa para iniciar la combustión), el motor diésel opera bajo un principio físico distinto.\n\n" +
                "El Principio de Funcionamiento: Encendido por Compresión (Admisión, Compresión, Combustión, Escape). " +
                "La alta relación de compresión otorga torque.\n\n" +
                "Componentes Clave: Sistema de Inyección Common Rail, Bomba de Inyección, Turbocompresor, Bujías de Precalentamiento.\n\n" +
                "Importancia y Aplicaciones: Transporte Pesado, Maquinaria Pesada, Sector Marítimo, Generación Eléctrica.\n\n" +
                "Ventajas y Desafíos: Eficiencia térmica, larga vida útil, pero emisiones contaminantes; se usan DPF y AdBlue.";
        Lectura l = new Lectura("La Mecánica Diésel", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿A qué temperatura aproximada llega el aire dentro del cilindro debido a la compresión extrema?",
                new String[]{"100°C", "500°C", "1,000°C", "250°C"}, 1);
        agregarPregunta(l, "¿Qué componente se encarga de aprovechar los gases de escape para aumentar la potencia?",
                new String[]{"Inyector", "Common Rail", "Turbocompresor", "AdBlue"}, 2);
        agregarPregunta(l, "¿Cuál es la función principal de las bujías incandescentes en un motor diésel?",
                new String[]{"Generar la chispa.", "Enfriar el motor.", "Precalentar el aire.", "Filtrar el hollín."}, 2);
        agregarPregunta(l, "Si un motor diésel consume menos combustible que uno de gasolina para mover la misma carga, se deduce que:",
                new String[]{"El diésel es más barato.", "Tiene mayor eficiencia térmica.", "El motor es más ligero.", "No utiliza pistones."}, 1);
        agregarPregunta(l, "¿Por qué es necesario que las piezas de un motor diésel sean más robustas que las de uno de gasolina?",
                new String[]{"Por el peso del combustible.", "Para evitar el óxido.", "Por la alta presión interna.", "Para correr más rápido."}, 2);
        agregarPregunta(l, "¿Qué efecto tendría en el medio ambiente un motor diésel que tiene el filtro DPF dañado?",
                new String[]{"Mayor ahorro de diésel.", "Aumento de gases y hollín.", "Menor ruido del motor.", "El motor no encendería."}, 1);
        agregarPregunta(l, "Un motor diésel que no logra encender en una mañana muy fría probablemente tiene un fallo en:",
                new String[]{"El turbocompresor.", "El sistema AdBlue.", "Las bujías de precalentamiento.", "El tanque de combustible."}, 2);
        agregarPregunta(l, "Ante la crisis climática actual, ¿cuál es el mayor desafío ético de la mecánica diésel?",
                new String[]{"Fabricar motores más pequeños.", "Reducir las emisiones tóxicas.", "Aumentar la velocidad del transporte.", "Eliminar el uso de computadoras."}, 1);
        agregarPregunta(l, "¿Qué opinión se puede formar sobre la importancia de la mecánica diésel en la economía mundial?",
                new String[]{"Es una tecnología obsoleta.", "Es el pilar del transporte logístico.", "Solo sirve para autos de lujo.", "No influye en el comercio actual."}, 1);
        agregarPregunta(l, "Al comparar un sistema de inyección antiguo con un Common Rail moderno, ¿cuál es la mejora más significativa?",
                new String[]{"El uso de más metal.", "La precisión electrónica.", "El tamaño del tanque.", "El color de las piezas."}, 1);
    }

    private void crearLecturaMecanicaGasolina(Carrera c) {
        String texto = "LA MECÁNICA DE GASOLINA: VELOCIDAD, PRECISIÓN Y CHISPA\n\n" +
                "La mecánica de gasolina es la especialidad de la ingeniería automotriz que se dedica al estudio y mantenimiento de los motores de combustión interna que funcionan bajo el ciclo Otto. " +
                "A diferencia de los motores diésel, que priorizan la fuerza bruta, los motores de gasolina están diseñados para ofrecer un funcionamiento más suave, mayores revoluciones y una respuesta más ágil.\n\n" +
                "El Ciclo Otto: Admisión, Compresión, Explosión (chispa de bujía), Escape.\n\n" +
                "Componentes Vitales: Sistema de Encendido (bobinas y bujías), Inyectores, Cuerpo de Aceleración, Sensores y ECU.\n\n" +
                "Ventajas y Evolución: Más silenciosos, menos vibración, mantenimiento menos costoso. " +
                "Evolución hacia la hibridación y materiales ligeros como el aluminio.";
        Lectura l = new Lectura("La Mecánica de Gasolina", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Qué componente es el responsable de generar la chispa para iniciar la combustión?",
                new String[]{"El inyector.", "La bujía.", "El pistón.", "La batería."}, 1);
        agregarPregunta(l, "¿Cómo se llama el ciclo de funcionamiento de los motores de gasolina?",
                new String[]{"Ciclo Diésel.", "Ciclo de Vapor.", "Ciclo Otto.", "Ciclo de Newton."}, 2);
        agregarPregunta(l, "¿Qué sucede durante el tiempo de \"Compresión\" en el cilindro?",
                new String[]{"Se expulsan los gases.", "Entra aire fresco.", "Se aprieta la mezcla.", "Se genera la chispa."}, 2);
        agregarPregunta(l, "Si un motor de gasolina pierde fuerza y consume mucho combustible, se deduce un fallo en:",
                new String[]{"El sistema de audio.", "La mezcla aire-gasolina.", "El color del aceite.", "La presión de llantas."}, 1);
        agregarPregunta(l, "¿Por qué los motores de gasolina alcanzan mayores RPM que los motores diésel?",
                new String[]{"Son más grandes.", "Tienen piezas ligeras.", "Usan mucha agua.", "Tienen más cables."}, 1);
        agregarPregunta(l, "¿Qué pasaría si la chispa de la bujía ocurre antes de que el pistón termine de subir?",
                new String[]{"El auto corre más.", "Daño interno al motor.", "El motor se apaga solo.", "Se ahorra gasolina."}, 1);
        agregarPregunta(l, "El uso de sensores de oxígeno en el escape permite que el motor sea:",
                new String[]{"Más ruidoso.", "Menos contaminante.", "Mucho más pesado.", "De color brillante."}, 1);
        agregarPregunta(l, "Ante el aumento del precio del combustible, ¿qué técnica mecánica es más crítica hoy?",
                new String[]{"Pintar el motor.", "La inyección directa.", "Usar llantas grandes.", "Quitar los sensores."}, 1);
        agregarPregunta(l, "¿Qué juicio se puede hacer sobre la suavidad de un motor de gasolina frente a un diésel?",
                new String[]{"Es mejor para carga.", "Ideal para uso urbano.", "No sirve para viajar.", "Es tecnología fallida."}, 1);
        agregarPregunta(l, "Al evaluar un motor moderno, ¿qué pesa más: la mecánica pura o la electrónica?",
                new String[]{"La mecánica pura.", "La electrónica de control.", "Los adornos plásticos.", "El tamaño del chasis."}, 1);
    }

    // ==================== ELECTRICIDAD ====================
    // Textos del 1 al 5 con sus preguntas (algunas V/F)
    private void crearLecturaElectricidad1(Carrera c) {
        String texto = "Fundamentos de la Corriente Eléctrica y el Circuito de Carga\n\n" +
                "La electricidad no es una invención humana, sino una fuerza fundamental de la naturaleza que hemos aprendido a canalizar...";
        Lectura l = new Lectura("Fundamentos de la Corriente Eléctrica", texto, c);
        lecturaRepository.save(l);

        // Preguntas (basadas en la tabla: 1C,2B,3V,4F,5B,6C,7F,8B,9V,10F)
        agregarPreguntaVF(l, "¿Cuál es la partícula subatómica cuyo flujo ordenado genera la corriente eléctrica?",
                new String[]{"Protón", "Neutrón", "Electrón", "Átomo"}, 2);
        agregarPreguntaVF(l, "El componente encargado de gestionar el paso de la corriente (abrir o cerrar el circuito) es:",
                new String[]{"El receptor", "El dispositivo de control", "La fuente de energía", "El fusible"}, 1);
        agregarPreguntaVF(l, "El aluminio es mencionado como un buen conductor debido a su baja resistencia.",
                new String[]{"Verdadero", "Falso"}, 0); // V
        agregarPreguntaVF(l, "La \"fuerza electromotriz\" es proporcionada por el receptor del circuito.",
                new String[]{"Verdadero", "Falso"}, 1); // F
        agregarPreguntaVF(l, "Si en la analogía hidráulica el voltaje es la presión, ¿qué representa un cable con mucha resistencia?",
                new String[]{"Un motor potente", "Una tubería con el diámetro muy reducido", "Un tanque de agua lleno", "Una llave de paso abierta"}, 1);
        agregarPreguntaVF(l, "El \"Efecto Joule\" se menciona en relación con la protección. ¿Qué fenómeno físico genera este efecto en los cables?",
                new String[]{"Enfriamiento por flujo", "Magnetismo", "Generación de calor", "Aumento de voltaje"}, 2);
        agregarPreguntaVF(l, "Según el texto, un circuito abierto permite el flujo de electrones libremente.",
                new String[]{"Verdadero", "Falso"}, 1); // F
        agregarPreguntaVF(l, "¿Por qué se considera que la protección es el elemento más crítico en una instalación profesional?",
                new String[]{"Porque es el más caro de comprar", "Porque previene la destrucción de la infraestructura ante fallos", "Porque mejora el brillo de las bombillas", "Porque sustituye a la fuente de energía"}, 1);
        agregarPreguntaVF(l, "Un técnico que comprende la analogía hidráulica tiene mejores bases para predecir el comportamiento del circuito.",
                new String[]{"Verdadero", "Falso"}, 0); // V
        agregarPreguntaVF(l, "En un taller rural, ¿es válido sustituir un fusible quemado por un trozo de alambre de cobre?",
                new String[]{"Verdadero", "Falso"}, 1); // F
    }

    private void crearLecturaElectricidad2(Carrera c) {
        String texto = "LA LEY DE OHM Y LA EFICIENCIA EN INSTALACIONES\n\n" +
                "La Ley de Ohm es la regla de oro para cualquier técnico en electricidad...";
        Lectura l = new Lectura("La Ley de Ohm y la Eficiencia en Instalaciones", texto, c);
        lecturaRepository.save(l);

        // Tabla: 1B,2C,3V,4F,5B,6C,7V,8B,9V,10F
        agregarPreguntaVF(l, "En la fórmula V = I * R, ¿qué representa la letra I?",
                new String[]{"Inductancia", "Intensidad de corriente", "Impedancia interna", "Interruptor"}, 1);
        agregarPreguntaVF(l, "La Ley de Ohm establece que la intensidad es proporcional al voltaje de forma:",
                new String[]{"Inversa", "Aleatoria", "Directa", "Negativa"}, 2);
        agregarPreguntaVF(l, "Si la resistencia de un circuito aumenta y el voltaje se mantiene igual, la corriente disminuye.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "La caída de tensión es un problema deseado para ahorrar energía.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "Un motor que aumenta su consumo de corriente (A) sin cambiar su voltaje suele tener un problema de:",
                new String[]{"Exceso de aislamiento", "Disminución de su resistencia interna", "Aumento de su vida útil", "Voltaje demasiado alto"}, 1);
        agregarPreguntaVF(l, "¿Cuál es la consecuencia directa de intentar pasar mucha intensidad por un cable muy delgado?",
                new String[]{"El cable se enfría", "La resistencia baja a cero", "Generación de calor peligroso por alta resistencia", "El voltaje aumenta automáticamente"}, 2);
        agregarPreguntaVF(l, "El multímetro es la herramienta mencionada para comprobar los cálculos de la Ley de Ohm.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "¿A qué se refiere el autor con desarrollar un \"sentido técnico\"?",
                new String[]{"A memorizar todas las fórmulas de física", "A ser capaz de predecir el comportamiento eléctrico antes de medir", "A usar las manos para sentir la electricidad", "A comprar las herramientas más caras del mercado"}, 1);
        agregarPreguntaVF(l, "Aplicar la Ley de Ohm ayuda a que los dispositivos duren más tiempo.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "¿Es la Ley de Ohm útil para un técnico que solo hace reparaciones estéticas?",
                new String[]{"Verdadero", "Falso"}, 1);
    }

    private void crearLecturaElectricidad3(Carrera c) {
        String texto = "Arquitectura de Circuitos: Serie y Paralelo\n\n" +
                "La disposición física y eléctrica de los componentes determina el comportamiento de toda la red...";
        Lectura l = new Lectura("Arquitectura de Circuitos: Serie y Paralelo", texto, c);
        lecturaRepository.save(l);

        // Tabla: 1C,2B,3F,4V,5B,6B,7V,8B,9V,10V
        agregarPreguntaVF(l, "¿Qué sucede si se quema una bombilla en un circuito en serie?",
                new String[]{"El resto brilla más", "Solo fallan las bombillas que están después", "Todo el circuito deja de funcionar", "La fuente de energía explota"}, 2);
        agregarPreguntaVF(l, "En un circuito en paralelo, el voltaje en cada receptor es:",
                new String[]{"Dividido entre el número de focos", "El mismo de la fuente", "Cero", "Variable según el interruptor"}, 1);
        agregarPreguntaVF(l, "Las instalaciones domésticas se realizan mayoritariamente en serie.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "En un circuito en serie, la intensidad de corriente es la misma en todos los puntos.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "Si agregamos más electrodomésticos a un multicontacto (en paralelo), el riesgo técnico principal es:",
                new String[]{"Que baje el voltaje de la calle", "Una sobrecarga por exceso de intensidad total", "Que los aparatos se vuelvan más lentos", "Que la resistencia total aumente"}, 1);
        agregarPreguntaVF(l, "¿Por qué las lámparas brillan menos al conectarse en serie?",
                new String[]{"Porque hay menos electrones", "Porque el voltaje de la fuente se reparte entre todas", "Porque el cable es más largo", "Porque la resistencia disminuye"}, 1);
        agregarPreguntaVF(l, "Un interruptor de seguridad suele colocarse en serie con la carga que desea proteger.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "Para un sistema de luces de emergencia donde se requiere que si una falla, las demás sigan encendidas, usted instalaría un circuito en:",
                new String[]{"Serie", "Paralelo", "Abierto", "Simple"}, 1);
        agregarPreguntaVF(l, "El circuito paralelo ofrece mayor autonomía e independencia a los dispositivos.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "En serie, la resistencia total del circuito es la suma de todas las resistencias individuales.",
                new String[]{"Verdadero", "Falso"}, 0);
    }

    private void crearLecturaElectricidad4(Carrera c) {
        String texto = "La Guerra de las Corrientes: CA versus CC\n\n" +
                "La historia de la electricidad está marcada por la competencia entre la Corriente Continua (CC) y la Corriente Alterna (CA)...";
        Lectura l = new Lectura("La Guerra de las Corrientes: CA versus CC", texto, c);
        lecturaRepository.save(l);

        // Tabla: 1B,2C,3F,4V,5B,6B,7V,8B,9V,10V
        agregarPreguntaVF(l, "¿Cuál es el tipo de corriente que generan las baterías y paneles solares?",
                new String[]{"Alterna", "Continua", "Bifásica", "Inducida"}, 1);
        agregarPreguntaVF(l, "La principal ventaja de la CA para las ciudades es que puede usarse con:",
                new String[]{"Bombillas de Edison", "Motores de vapor", "Transformadores", "Imanes permanentes"}, 2);
        agregarPreguntaVF(l, "Nikola Tesla fue un fuerte defensor de la Corriente Continua.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "Los cargadores de celulares convierten la CA en CC.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "Si un dispositivo electrónico dice \"Entrada: 100-240V CA\", pero funciona internamente con CC, ¿qué componente debe tener adentro?",
                new String[]{"Un motor de arranque", "Un rectificador/transformador", "Un cable de cobre más grueso", "Una batería de emergencia"}, 1);
        agregarPreguntaVF(l, "¿Por qué fue necesario elevar el voltaje para transportar electricidad a largas distancias?",
                new String[]{"Para que llegara más rápido", "Para reducir las pérdidas de energía por calor", "Para que los cables fueran más pesados", "Para evitar los rayos"}, 1);
        agregarPreguntaVF(l, "La frecuencia de la corriente eléctrica se mide en Hertz (Hz).",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "El término \"mundo híbrido\" en el texto se refiere a:",
                new String[]{"Que los carros ahora son eléctricos", "El uso combinado de CA para transporte y CC para electrónica", "Que la electricidad se mezcla con el agua", "Que Tesla y Edison finalmente trabajaron juntos"}, 1);
        agregarPreguntaVF(l, "Sin la invención del transformador, la electricidad sería mucho más cara en las casas.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "La Corriente Alterna cambia de dirección muchas veces por segundo.",
                new String[]{"Verdadero", "Falso"}, 0);
    }

    private void crearLecturaElectricidad5(Carrera c) {
        String texto = "Seguridad: La Prevención como Herramienta Técnica\n\n" +
                "En electricidad, el error suele pagarse caro. El riesgo eléctrico no es solo el choque directo...";
        Lectura l = new Lectura("Seguridad Eléctrica: La Prevención como Herramienta Técnica", texto, c);
        lecturaRepository.save(l);

        // Tabla: 1B,2B,3F,4V,5D,6B,7F,8B,9V,10F
        agregarPreguntaVF(l, "¿Cuál es el objetivo principal de la \"puesta a tierra\"?",
                new String[]{"Ahorrar energía eléctrica", "Desviar corrientes de falla hacia el suelo", "Hacer que los equipos pesen más", "Mejorar la señal de radio"}, 1);
        agregarPreguntaVF(l, "El calzado de seguridad para un electricista debe ser:",
                new String[]{"Con punta de acero descubierta", "Dieléctrico (aislante)", "De tela cómoda", "Sandalias de goma"}, 1);
        agregarPreguntaVF(l, "Las \"Cinco Reglas de Oro\" son sugerencias opcionales para ganar tiempo.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "El cuerpo humano es un buen conductor de electricidad.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "¿Por qué es fundamental \"verificar la ausencia de tensión\" antes de tocar un cable, aunque ya hayamos bajado el interruptor?",
                new String[]{"Porque el interruptor podría estar defectuoso", "Porque el cable podría tener carga estática o retorno", "Para cumplir con el protocolo legal", "Todas las anteriores son correctas"}, 3); // D
        agregarPreguntaVF(l, "Según el texto, ¿por qué la corriente \"prefiere\" ir por un cable de tierra que por una persona?",
                new String[]{"Porque el cobre es de color rojo", "Porque la corriente siempre busca el camino de menor resistencia", "Porque la tierra atrae a los electrones como un imán", "Porque el cable de tierra es más corto"}, 1);
        agregarPreguntaVF(l, "Un arco eléctrico solo ocurre cuando hay contacto físico directo.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "Un técnico que decide no usar guantes porque \"le quitan sensibilidad\" para trabajar está siendo:",
                new String[]{"Muy profesional y experimentado", "Negligente, poniendo en riesgo su vida", "Eficiente, al terminar más rápido", "Valiente ante el peligro"}, 1);
        agregarPreguntaVF(l, "Bloquear los dispositivos de mando impide que alguien más energice el circuito mientras trabajamos.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "El uso de agua es el método más seguro para apagar incendios eléctricos.",
                new String[]{"Verdadero", "Falso"}, 1);
    }

    private void agregarPregunta(Lectura lectura, String enunciado, String[] textosOpciones, int indiceCorrecta) {
        Pregunta p = new Pregunta(enunciado, lectura);
        preguntaRepository.save(p);
        Opcion[] opciones = new Opcion[textosOpciones.length];
        for (int i = 0; i < textosOpciones.length; i++) {
            opciones[i] = new Opcion(textosOpciones[i], p);
            opcionRepository.save(opciones[i]);
        }
        p.setRespuestaCorrecta(opciones[indiceCorrecta]);
        preguntaRepository.save(p);
    }

    // Versión para preguntas de Verdadero/Falso (dos opciones)
    private void agregarPreguntaVF(Lectura lectura, String enunciado, String[] textosOpciones, int indiceCorrecta) {
        // Si solo hay 2 opciones usamos el mismo método, funciona igual
        agregarPregunta(lectura, enunciado, textosOpciones, indiceCorrecta);
    }
}