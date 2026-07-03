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
        // Parche: corregir textos truncados de electricidad si están en la BD
        actualizarTextosElectricidad();

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

            Carrera culturaGeneral = new Carrera("Cultura General",
                    "Historia universal y guatemalteca, eventos relevantes, geografía y comprensión del mundo contemporáneo.");
            carreraRepository.save(culturaGeneral);

            Carrera fantasia = new Carrera("Fantasía",
                    "Cuentos, fábulas y leyendas de Guatemala: narrativa popular, tradición oral y literatura imaginativa del pueblo guatemalteco.");
            carreraRepository.save(fantasia);

            Carrera electronica = new Carrera("Electrónica",
                    "Fundamentos y principios de la electrónica moderna, semiconductores y circuitos.");
            carreraRepository.save(electronica);

            // ==================== LECTURAS ELECTRÓNICA ====================
            crearLecturaElectronica1(electronica);
            crearLecturaElectronica2(electronica);
            crearLecturaElectronica3(electronica);
            crearLecturaElectronica4(electronica);
            crearLecturaElectronica5(electronica);

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

            // ==================== LECTURAS CULTURA GENERAL ====================

            crearLecturaRevolucionIndustrial(culturaGeneral);
            crearLecturaSegundaGuerraMundial(culturaGeneral);
            crearLecturaMuroBerlin(culturaGeneral);
            crearLecturaRevolucion1944(culturaGeneral);
            crearLecturaAcuerdosPaz(culturaGeneral);
            crearLecturaTerremoto1976(culturaGeneral);

            // ==================== LECTURAS FANTASÍA ====================
            crearCuentoSombrerónGuatemala(fantasia);
            crearCuentoXibalbaGuatemala(fantasia);
            crearFabulaQuetzalGuatemala(fantasia);
            crearFabulaConejo(fantasia);
            crearLeyendaLlorona(fantasia);
            crearLeyendaCadejo(fantasia);

            System.out.println("Datos iniciales cargados correctamente.");
        }
    }

    // ================================================================
    // MÉTODOS AUXILIARES PARA CADA LECTURA
    // ================================================================



    // ==================== ELECTRÓNICA ====================

    private void crearLecturaElectronica1(Carrera c) {
        String texto = "Fundamentos de electricidad y electrónica: Naturaleza de la carga y semiconductores\n\n" +
                "El estudio de la electrónica en los primeros años de ingeniería requiere comprender la transición del comportamiento macroscópico de la electricidad hacia el control microscópico de los portadores de carga. " +
                "A diferencia de la ingeniería eléctrica convencional, que se enfoca en la transmisión de potencia mediante conductores, la electrónica se basa en la manipulación precisa de corrientes débiles empleando las propiedades cuánticas y de bandas de energía en los materiales.\n\n" +
                "En un conductor ideal, los electrones de la banda de conducción se mueven libremente ante un campo eléctrico externo. " +
                "En un aislante, la banda de valencia está completamente llena y la brecha de energía o bandgap con la banda de conducción es tan amplia que los electrones no pueden saltar bajo condiciones de operación normales. " +
                "La electrónica moderna surge en el punto intermedio: los semiconductores. " +
                "Materiales como el silicio y el germanio poseen una estructura cristalina covalente donde, a temperatura de cero absoluto, se comportan como aislantes. " +
                "Sin embargo, al recibir energía térmica, algunos electrones rompen sus enlaces, saltando a la banda de conducción y dejando un espacio vacío en la banda de valencia conocido como hueco.\n\n" +
                "El verdadero potencial de los semiconductores se libera mediante el dopaje, que consiste en introducir impurezas pentavalentes como el fósforo para crear un semiconductor tipo N con exceso de electrones, " +
                "o impurezas trivalentes como el boro para crear un semiconductor tipo P con exceso de huecos. " +
                "El movimiento de estos portadores mayoritarios y minoritarios bajo la influencia de campos eléctricos y gradientes de concentración constituye la base del funcionamiento de casi todos los dispositivos de estado sólido.\n\n" +
                "La comprensión de las bandas de energía y los mecanismos de dopaje permite transformar materiales aislantes en elementos activos con conductividad controlable, " +
                "marcando el nacimiento de los dispositivos semiconductores que definen a la electrónica contemporánea.";
        Lectura l = new Lectura("Fundamentos de electricidad y electrónica", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cuál es la diferencia fundamental en el enfoque operativo entre la ingeniería eléctrica convencional y la ingeniería electrónica?",
                new String[]{"La eléctrica utiliza corriente alterna y la electrónica solo continua.",
                        "La eléctrica se basa en conductores y la electrónica en superconductores.",
                        "La eléctrica maneja alta potencia y la electrónica el control de portadores.",
                        "La eléctrica estudia campos estáticos y la electrónica campos dinámicos."}, 2);
        agregarPregunta(l, "A la temperatura de cero absoluto, ¿cómo se comporta un cristal de silicio intrínseco puro?",
                new String[]{"Como un conductor perfecto debido a la ausencia de vibraciones térmicas.",
                        "Como un aislante ideal porque los electrones carecen de energía de excitación.",
                        "Como un material magnético por la alineación de los espines electrónicos.",
                        "Como un semiconductor tipo P debido a la retención intrínseca de huecos."}, 1);
        agregarPregunta(l, "¿Qué fenómeno físico directo genera la aparición de un hueco en la banda de valencia de un semiconductor intrínseco?",
                new String[]{"La inyección artificial de iones trivalentes en la red cristalina.",
                        "La recombinación de un electrón libre con un átomo del grupo cinco.",
                        "La ruptura de un enlace covalente debido al incremento de energía.",
                        "El colapso difusivo de la barrera de potencial en una unión metal-silicio."}, 2);
        agregarPregunta(l, "Si un cristal de silicio se dopa con átomos de boro, ¿cuál es el efecto inmediato en su estructura electrónica?",
                new String[]{"Aumenta la cantidad de electrones libres en la banda de conducción.",
                        "Se generan niveles energéticos donadores cerca de la banda de valencia.",
                        "Se crean espacios vacíos o huecos que actúan como portadores mayoritarios.",
                        "El material adquiere una carga neta positiva que repele los electrones."}, 2);
        agregarPregunta(l, "En un semiconductor tipo N, ¿cuál es el origen principal de los portadores de carga minoritarios?",
                new String[]{"La ionización de los átomos pentavalentes añadidos en el dopaje.",
                        "La generación térmica de pares electrón-hueco en el propio cristal.",
                        "El flujo constante de corriente desde los terminales externos.",
                        "La difusión de electrones provenientes de zonas adyacentes tipo P."}, 1);
        agregarPregunta(l, "¿Qué ocurre con la brecha de energía de un material aislante en comparación con un semiconductor?",
                new String[]{"Es significativamente más estrecha, facilitando el tunelamiento.",
                        "Es idéntica en magnitud, pero difiere en la densidad de estados.",
                        "Es mucho más amplia, impidiendo la transición de electrones.",
                        "Es dinámicamente variable en función del voltaje externo aplicado."}, 2);
        agregarPregunta(l, "El mecanismo de transporte de carga denominado difusión en un semiconductor se produce debido a:",
                new String[]{"La aplicación directa de un campo eléctrico mediante una batería.",
                        "La existencia de un gradiente de concentración de los portadores.",
                        "El movimiento caótico aleatorio causado por la agitación térmica.",
                        "La fuerza de atracción culómbica entre iones de signo opuesto."}, 1);
        agregarPregunta(l, "¿Qué tipo de impureza se requiere para transformar el silicio intrínseco en un material con exceso de electrones libres?",
                new String[]{"Un elemento trivalente que actúe como un aceptor de electrones.",
                        "Un elemento químicamente inerte que expanda la red del cristal.",
                        "Un elemento pentavalente que aporte un electrón adicional no enlazado.",
                        "Un metal de transición que altere la constante dieléctrica local."}, 2);
        agregarPregunta(l, "¿Por qué el silicio ha desplazado en gran medida al germanio en la fabricación de componentes electrónicos modernos?",
                new String[]{"El silicio posee una brecha de energía menor que facilita la conducción rápida.",
                        "El germanio presenta una estabilidad térmica muy superior que no se requiere.",
                        "El silicio genera corrientes de fuga térmicas menores debido a su mayor bandgap.",
                        "El germanio es mecánicamente muy maleable y colapsa ante altas frecuencias."}, 2);
        agregarPregunta(l, "Cuando un electrón libre pierde energía y cae de la banda de conducción a la banda de valencia, el proceso se llama:",
                new String[]{"Ionización por impacto eléctrico.",
                        "Recombinación electrón-hueco.",
                        "Emisión termoiónica cuántica.",
                        "Deriva por campo eléctrico nulo."}, 1);
        agregarPregunta(l, "¿Cuál de las siguientes afirmaciones describe correctamente la carga eléctrica neta de un semiconductor tipo P?",
                new String[]{"Es positiva debido a la gran abundancia de huecos en la red.",
                        "Es negativa debido a los electrones atrapados en los aceptores.",
                        "Es neutra porque las cargas de los portadores equilibran los núcleos.",
                        "Es alternante según el sentido de la corriente eléctrica externa."}, 2);
        agregarPregunta(l, "¿Qué es el movimiento de deriva en el contexto de los portadores de carga de un semiconductor?",
                new String[]{"El desplazamiento de portadores provocado por un gradiente de temperatura.",
                        "El movimiento neto de cargas como respuesta a un campo eléctrico aplicado.",
                        "La oscilación de los electrones alrededor de sus núcleos atómicos fijos.",
                        "El escape de electrones hacia el exterior del material por efecto óptico."}, 1);
        agregarPregunta(l, "En la estructura cristalina del silicio dopado, ¿qué representa un ion aceptor?",
                new String[]{"Un átomo de boro que ha capturado un electrón, adquiriendo carga negativa.",
                        "Un átomo de fósforo que ha cedido un electrón, quedando cargado positivamente.",
                        "Un electrón libre que se mueve a través de los canales de la banda de conducción.",
                        "Un hueco que se desplaza libremente respondiendo a estímulos magnéticos."}, 0);
        agregarPregunta(l, "¿Cómo afecta el incremento de temperatura a la conductividad de un semiconductor intrínseco?",
                new String[]{"La disminuye, ya que las vibraciones de la red dispersan los electrones.",
                        "La mantiene constante porque el bandgap es una propiedad fija e inmutable.",
                        "La aumenta, al elevar la tasa de generación térmica de portadores libres.",
                        "La anula por completo debido a la fusión de los enlaces covalentes débiles."}, 2);
        agregarPregunta(l, "El concepto de nivel de Fermi en la física de semiconductores se define esencialmente como:",
                new String[]{"La energía máxima que puede alcanzar un electrón a temperaturas críticas altas.",
                        "Una medida de la probabilidad de ocupación de los estados electrónicos del sistema.",
                        "El voltaje umbral necesario para que un diodo entre en conducción directa activa.",
                        "La velocidad límite que logran los portadores bajo campos eléctricos intensos."}, 1);
        agregarPregunta(l, "Si un semiconductor tipo N se somete a un campo eléctrico uniforme, ¿cómo se mueven sus portadores?",
                new String[]{"Los electrones van a favor del campo y los huecos se quedan estáticos.",
                        "Los electrones se mueven contra el campo y los huecos a favor de este.",
                        "Ambos portadores se desplazan en el mismo sentido en favor del campo.",
                        "Los huecos se desplazan contra el campo y los electrones perpendicularmente."}, 1);
        agregarPregunta(l, "¿Qué propiedad define fundamentalmente a los electrones que se encuentran en la banda de conducción?",
                new String[]{"Están fuertemente ligados al núcleo atómico imposibilitados de trasladarse.",
                        "Poseen niveles energéticos inferiores a los electrones de la banda de valencia.",
                        "Tienen la libertad de moverse por el cristal y participar en el flujo eléctrico.",
                        "Generan campos magnéticos internos que destruyen los enlaces covalentes."}, 2);
        agregarPregunta(l, "¿Por qué los metales conductores no presentan una brecha de energía o bandgap?",
                new String[]{"Porque sus electrones de valencia están fuertemente unidos al núcleo atómico.",
                        "Porque la banda de conducción y la banda de valencia están superpuestas.",
                        "Porque carecen por completo de una banda de valencia en su estructura cuántica.",
                        "Porque el dopaje natural de los metales elimina las barreras dieléctricas."}, 1);
        agregarPregunta(l, "El proceso de dopaje modifica la conductividad del semiconductor actuando sobre:",
                new String[]{"La constante dieléctrica intrínseca del cristal de silicio purificado.",
                        "La geometría macroscópica y el área de contacto del dispositivo semiconductor.",
                        "La densidad de portadores disponibles a una temperatura dada en el material.",
                        "El punto de fusión del silicio para permitir corrientes térmicas extremas."}, 2);
        agregarPregunta(l, "¿Qué ocurriría si un semiconductor fuera dopado con una cantidad idéntica de impurezas donadoras y aceptoras?",
                new String[]{"Se transformaría en un conductor perfecto debido a la doble inyección de cargas.",
                        "Las impurezas se compensarían mutuamente, comportándose como material intrínseco.",
                        "Explotaría internamente por la violenta recombinación de portadores opuestos.",
                        "Desarrollaría una barrera de potencial oscilante a frecuencias de microondas."}, 1);
    }

    private void crearLecturaElectronica2(Carrera c) {
        String texto = "Ley de Ohm y análisis básico de circuitos: Técnicas analíticas de redes\n\n" +
                "El análisis de circuitos eléctricos y electrónicos se fundamenta en leyes de conservación que gobiernan el comportamiento de las variables de tensión y corriente. " +
                "La ley de Ohm establece una relación lineal local entre la diferencia de potencial aplicada a un conductor, la corriente que lo atraviesa y su resistencia intrínseca. " +
                "Aunque simple en apariencia, esta ley es válida únicamente para materiales óhmicos operando bajo condiciones de temperatura estables, ya que variaciones térmicas alteran la resistividad del material.\n\n" +
                "Para redes complejas con múltiples nodos y mallas, la ley de Ohm es insuficiente. " +
                "Se requiere el empleo de las Leyes de Kirchhoff. La Ley de Kirchhoff de Corrientes, basada en la conservación de la carga eléctrica, " +
                "estipula que la suma algebraica de las corrientes en cualquier nodo de un circuito es igual a cero. " +
                "La Ley de Kirchhoff de Voltajes, fundamentada en la conservación de la energía, establece que la suma algebraica de las caídas y elevaciones de potencial a lo largo de una trayectoria cerrada es nula.\n\n" +
                "A partir de estos principios, se desarrollan métodos sistemáticos como el análisis de nodos y el análisis de mallas. " +
                "El Teorema de Thévenin postula que cualquier red lineal de dos terminales puede sustituirse por un circuito equivalente compuesto por una única fuente de tensión en serie con una resistencia. " +
                "Su dual, el Teorema de Norton, simplifica la red a una fuente de corriente en paralelo con una resistencia. " +
                "Estas herramientas permiten reducir sistemas complejos a modelos manejables para optimizar la transferencia de potencia o acoplar etapas de amplificación.\n\n" +
                "Las leyes de Kirchhoff y los teoremas de linealidad proporcionan el formalismo matemático necesario para predecir con precisión el comportamiento de voltajes y corrientes " +
                "en redes de parámetros concentrados, independientemente de su complejidad estructural.";
        Lectura l = new Lectura("Ley de Ohm y análisis básico de circuitos", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cuál es el principio físico fundamental en el que se sostiene la Ley de Kirchhoff de Corrientes?",
                new String[]{"La conservación de la energía electromagnética en mallas.",
                        "La conservación de la carga eléctrica en un espacio cerrado.",
                        "La proporcionalidad directa entre la resistencia y la temperatura.",
                        "La densidad de flujo magnético constante en un inductor ideal."}, 1);
        agregarPregunta(l, "El Teorema de Thévenin afirma que cualquier red lineal con dos terminales se puede reducir a:",
                new String[]{"Una fuente de corriente ideal en paralelo con una impedancia reactiva.",
                        "Una fuente de tensión en serie con una resistencia equivalente única.",
                        "Un banco de capacitores conectados en estrella con neutro flotante.",
                        "Un circuito resonante sintonizado a la frecuencia fundamental del nodo."}, 1);
        agregarPregunta(l, "¿Bajo qué condición estricta se cumple de forma lineal la Ley de Ohm en un resistor real?",
                new String[]{"El circuito debe operar exclusivamente con señales de alta frecuencia.",
                        "La temperatura del componente debe mantenerse esencialmente constante.",
                        "El valor de la corriente debe superar el umbral térmico del silicio.",
                        "El resistor debe estar conectado en paralelo a una fuente de corriente."}, 1);
        agregarPregunta(l, "La Ley de Kirchhoff de Voltajes expresa matemáticamente que en una trayectoria cerrada:",
                new String[]{"La suma de las corrientes que entran es igual a las que salen.",
                        "El producto de la inductancia por la corriente se mantiene nulo.",
                        "La suma algebraica de las diferencias de potencial es igual a cero.",
                        "La potencia disipada supera a la potencia promedio entregada."}, 2);
        agregarPregunta(l, "En el análisis de nodos, las variables independientes que se buscan resolver directamente son:",
                new String[]{"Las corrientes que atraviesan cada una de las mallas periféricas.",
                        "Las potencias disipadas por los elementos pasivos de la red.",
                        "Los voltajes de cada nodo medidos respecto a un nodo de referencia.",
                        "Los equivalentes de corriente Norton asociados a las fuentes de tensión."}, 2);
        agregarPregunta(l, "¿Cómo se calcula la resistencia de Thévenin de un circuito con fuentes independientes?",
                new String[]{"Abriendo todas las fuentes de tensión y cortocircuitando las de corriente.",
                        "Cortocircuitando las fuentes de tensión y abriendo las fuentes de corriente.",
                        "Sumando de forma aritmética todas las resistencias presentes en el circuito.",
                        "Midiendo la corriente de cortocircuito directa entre los nodos principales."}, 1);
        agregarPregunta(l, "Si un circuito lineal se reduce a su equivalente de Norton, este constará de:",
                new String[]{"Una fuente de voltaje ideal conectada en serie con una conductancia.",
                        "Una fuente de corriente ideal en paralelo con una resistencia Norton.",
                        "Un circuito puente balanceado con elementos puramente inductivos.",
                        "Una impedancia equivalente conectada directamente a tierra flotante."}, 1);
        agregarPregunta(l, "El teorema de la máxima transferencia de potencia establece que una red entrega la mayor energía a una carga cuando:",
                new String[]{"La resistencia de carga es el doble de la resistencia interna de la fuente.",
                        "La resistencia de carga es igual a la resistencia equivalente de Thévenin.",
                        "La resistencia de carga tiende a cero para maximizar la corriente de cortocircuito.",
                        "La resistencia de carga es infinitamente grande para anular las caídas de tensión."}, 1);
        agregarPregunta(l, "¿Qué define conceptualmente a un material óhmico?",
                new String[]{"Aquel cuya resistencia disminuye de forma exponencial al subir el voltaje.",
                        "Aquel que mantiene una relación lineal constante entre tensión y corriente.",
                        "Aquel que bloquea por completo el paso de corriente ante voltajes menores a un voltio.",
                        "Aquel que almacena energía en forma de campo eléctrico sin sufrir pérdidas térmicas."}, 1);
        agregarPregunta(l, "Al realizar el análisis de mallas, la herramienta matemática que se aplica directamente es:",
                new String[]{"La Ley de Kirchhoff de Corrientes aplicada a los nodos esenciales del sistema.",
                        "La Ley de Kirchhoff de Voltajes expresada en función de las corrientes de mallas.",
                        "El Teorema de Sustitución acoplado a la transformada inversa de Laplace.",
                        "La integral de línea del campo magnético alrededor de un conductor rectilíneo."}, 1);
        agregarPregunta(l, "¿Qué sucede con la resistencia equivalente si conectamos múltiples resistores en paralelo?",
                new String[]{"Aumenta, siendo mayor que la resistencia del elemento individual más grande.",
                        "Se mantiene constante e igual al promedio aritmético de todos los elementos.",
                        "Disminuye, siendo menor que la resistencia del elemento individual más pequeño.",
                        "Oscila dinámicamente dependiendo de la dirección espacial de la corriente."}, 2);
        agregarPregunta(l, "Un nodo esencial en un circuito eléctrico se define formalmente como:",
                new String[]{"El punto de conexión donde se interceptan exactamente dos elementos de circuito.",
                        "El terminal positivo de la fuente de alimentación principal del sistema activo.",
                        "Un punto de conexión donde se unen tres o más elementos de circuito.",
                        "La trayectoria cerrada que no contiene ninguna otra trayectoria menor en su interior."}, 2);
        agregarPregunta(l, "El teorema de superposición es aplicable exclusivamente a circuitos que son de tipo:",
                new String[]{"No lineales y variantes en el tiempo.",
                        "Lineales, con fuentes independientes.",
                        "Puramente reactivos de alta potencia.",
                        "Digitales basados en lógica binaria."}, 1);
        agregarPregunta(l, "Al aplicar el teorema de superposición para el efecto de una fuente de tensión, las fuentes de corriente independientes se deben:",
                new String[]{"Cortocircuitar, reemplazándolas por un conductor continuo de resistencia nula.",
                        "Mantener intactas con sus valores nominales inalterados en la red eléctrica.",
                        "Reemplazar por un circuito abierto, interrumpiendo el flujo de su corriente.",
                        "Duplicar en su valor para compensar la ausencia temporal del voltaje bajo estudio."}, 2);
        agregarPregunta(l, "La conductancia, que es el recíproco algebraico de la resistencia, se mide en:",
                new String[]{"Ohmios por metro cuadrado.",
                        "Siemens o hercios inversos.",
                        "Siemens, antiguamente llamados mhos.",
                        "Henrios por unidad de tiempo."}, 2);
        agregarPregunta(l, "En un divisor de voltaje compuesto por dos resistencias en serie conectadas a una fuente de tensión:",
                new String[]{"La mayor caída de tensión se produce siempre en la resistencia de menor valor.",
                        "La corriente se divide proporcionalmente al valor óhmico de cada elemento pasivo.",
                        "La caída de tensión en cada resistencia es directamente proporcional a su valor.",
                        "El voltaje se distribuye equitativamente sin importar la magnitud de las resistencias."}, 2);
        agregarPregunta(l, "En un divisor de corriente con dos resistencias en paralelo alimentadas por una fuente de corriente:",
                new String[]{"La mayor parte de la corriente fluye a través de la resistencia de mayor valor óhmico.",
                        "La corriente se distribuye de manera idéntica entre ambos caminos resistivos paralelos.",
                        "La mayor parte de la corriente fluye a través de la resistencia de menor valor óhmico.",
                        "El voltaje en la resistencia más grande es muy superior al voltaje en la más pequeña."}, 2);
        agregarPregunta(l, "¿Cuál es el equivalente físico de cortocircuitar dos nodos de un circuito en funcionamiento?",
                new String[]{"Introducir una resistencia infinita que detiene el paso de la corriente eléctrica.",
                        "Forzar a que la diferencia de potencial entre ambos nodos sea exactamente cero.",
                        "Generar un campo magnético de gran magnitud que bloquea los nodos adyacentes.",
                        "Elevar el voltaje de manera descontrolada hasta saturar las fuentes de poder."}, 1);
        agregarPregunta(l, "¿Cuál es la relación matemática entre el circuito equivalente de Thévenin y el de Norton?",
                new String[]{"V_Th igual a I_N por R_Th, y además R_Th igual a R_N.",
                        "V_Th igual a I_N dividido entre R_N, y además R_Th igual a 1 dividido entre R_N.",
                        "I_N igual a V_Th por R_Th, y además R_Th igual a menos R_N.",
                        "V_Th igual a I_N por la suma de R_Th más R_N con impedancia nula."}, 0);
        agregarPregunta(l, "Una malla en el análisis de circuitos eléctricos se diferencia de un lazo porque:",
                new String[]{"Un lazo no puede contener elementos pasivos y una malla sí los contiene.",
                        "Una malla es un lazo cerrado que no contiene ningún otro lazo en su interior.",
                        "Un lazo es siempre abierto y una malla representa una trayectoria simétrica.",
                        "Una malla requiere obligatoriamente tener una fuente de tensión en su recorrido."}, 1);
    }

    private void crearLecturaElectronica3(Carrera c) {
        String texto = "Componentes electrónicos pasivos y el diodo semiconductor\n\n" +
                "Los circuitos electrónicos combinan elementos pasivos como resistencias, capacitores e inductores, y elementos activos como el diodo semiconductor. " +
                "Las resistencias limitan la corriente y disipan energía en forma de calor según la ley de Joule. " +
                "Los capacitores e inductores son componentes reactivos que almacenan energía en campos eléctricos y magnéticos, respectivamente.\n\n" +
                "Un capacitor consta de dos placas conductoras separadas por un dieléctrico. " +
                "Su propiedad fundamental, la capacitancia, define la capacidad de almacenar carga por unidad de voltaje. " +
                "En corriente continua, tras un periodo transitorio de carga determinado por la constante de tiempo tau igual a R por C, el capacitor se comporta como un circuito abierto. " +
                "El inductor, constituido por un conductor enrollado en forma de bobina, opone resistencia a los cambios bruscos de corriente debido a la Ley de Faraday de la inducción electromagnética. " +
                "En régimen permanente de corriente continua, un inductor ideal actúa como un cortocircuito.\n\n" +
                "El diodo es el dispositivo semiconductor más elemental, formado por la unión de un cristal tipo P y uno tipo N. " +
                "En la interfaz de la unión se crea una región de agotamiento o deplexión debido a la recombinación inicial de portadores libres, generando un campo eléctrico interno que detiene la difusión. " +
                "Si se aplica una polarización directa que supere el voltaje de barrera, aproximadamente cero punto siete voltios para el silicio, la región de deplexión se reduce y el diodo conduce corriente intensamente. " +
                "Si se polariza en la dirección inversa, la región de deplexión se ensancha, impidiendo el paso de corriente, salvo por una mínima corriente de fuga inversa, hasta alcanzar el voltaje de ruptura.\n\n" +
                "Mientras que los componentes pasivos modelan la respuesta temporal y en frecuencia de los circuitos, el diodo introduce la propiedad de la conducción unidireccional, " +
                "permitiendo procesos críticos como la rectificación de señales.";
        Lectura l = new Lectura("Componentes electrónicos pasivos y el diodo semiconductor", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cómo se comporta un capacitor ideal en un circuito de corriente continua una vez alcanzado el régimen permanente?",
                new String[]{"Como un cortocircuito permanente que absorbe corriente infinita.",
                        "Como un circuito abierto que impide el flujo continuo de carga.",
                        "Como un resistor variable lineal dependiente del campo térmico.",
                        "Como una fuente independiente de tensión oscilante simétrica."}, 1);
        agregarPregunta(l, "¿Cuál es el fenómeno físico que causa la oposición de un inductor a los cambios repentinos de corriente?",
                new String[]{"La disipación térmica masiva regulada por la ley física de Joule.",
                        "La inducción de una fuerza electromotriz inversa según la Ley de Faraday.",
                        "La acumulación de cargas electrostáticas en las paredes dieléctricas.",
                        "La polarización cuántica de los átomos que componen el núcleo metálico."}, 1);
        agregarPregunta(l, "¿Qué ocurre físicamente en la región de deplexión de una unión P-N sin polarización externa?",
                new String[]{"Se acumulan electrones y huecos libres en proporciones masivas idénticas.",
                        "Se crea un campo eléctrico interno debido a los iones fijos que frena la difusión.",
                        "El silicio se funde de forma localizada por corrientes parásitas termoiónicas.",
                        "Los átomos dopantes se desplazan físicamente hacia los bornes del diodo."}, 1);
        agregarPregunta(l, "Para un diodo de silicio en polarización directa, ¿qué voltaje aproximado se requiere para vencer la barrera de potencial?",
                new String[]{"Cero punto uno voltios.",
                        "Cero punto tres voltios.",
                        "Cero punto siete voltios.",
                        "Un punto dos voltios."}, 2);
        agregarPregunta(l, "La constante de tiempo tau en un circuito serie RC representa:",
                new String[]{"El tiempo que tarda el capacitor en descargarse por completo hasta cero voltios.",
                        "El tiempo necesario para que el voltaje del capacitor alcance el 63.2 por ciento de su valor final.",
                        "El periodo de oscilación armónica de la señal de corriente alterna en la malla.",
                        "El intervalo requerido para que la corriente del circuito se duplique exponencialmente."}, 1);
        agregarPregunta(l, "Cuando un diodo se polariza en la dirección inversa, la región de deplexión:",
                new String[]{"Se reduce notablemente hasta desaparecer por el flujo de electrones.",
                        "Permanece inalterada garantizando una resistencia nula en la unión.",
                        "Se ensancha, bloqueando de manera efectiva la conducción mayoritaria.",
                        "Comienza a oscilar emitiendo fotones en el espectro infrarrojo cercano."}, 2);
        agregarPregunta(l, "¿Qué parámetro se incrementa al aumentar el área de las placas paralelas de un capacitor?",
                new String[]{"La rigidez dieléctrica del material aislante interno.",
                        "La inductancia parásita de los hilos de conexión de cobre.",
                        "La capacitancia total del dispositivo de almacenamiento.",
                        "La resistencia óhmica superficial de los terminales."}, 2);
        agregarPregunta(l, "El comportamiento de un inductor ideal en régimen permanente de corriente continua es equivalente a:",
                new String[]{"Un circuito abierto de resistencia infinita.",
                        "Un cortocircuito con caída de tensión nula.",
                        "Un diodo polarizado en sentido inverso activo.",
                        "Una resistencia pura de valor dependiente del tiempo."}, 1);
        agregarPregunta(l, "¿Cuál es la unidad de medida de la inductancia en el Sistema Internacional?",
                new String[]{"Faradio",
                        "Siemens",
                        "Henrio",
                        "Tesla"}, 2);
        agregarPregunta(l, "La corriente de fuga inversa en un diodo semiconductor está compuesta principalmente por:",
                new String[]{"Portadores mayoritarios que logran saltar la enorme barrera ensanchada.",
                        "Portadores minoritarios excitados térmicamente que cruzan la unión P-N.",
                        "Electrones libres que saltan externamente a través del aire del encapsulado.",
                        "Iones dopantes fijos que logran liberarse de la estructura cristalina activa."}, 1);
        agregarPregunta(l, "¿Qué tipo de energía almacena un inductor y en qué forma física lo hace?",
                new String[]{"Energía térmica mediante la vibración de su estructura de cobre.",
                        "Energía eléctrica concentrada en el campo dieléctrico interno.",
                        "Energía magnética almacenada en el campo generado por su corriente.",
                        "Energía potencial química por la reestructuración cuántica molecular."}, 2);
        agregarPregunta(l, "Si la tensión inversa aplicada a un diodo común supera el voltaje de ruptura, ¿qué efecto inmediato se observa?",
                new String[]{"El diodo se apaga por completo interrumpiendo cualquier tipo de flujo eléctrico.",
                        "La corriente inversa se incrementa de forma abrupta y potencialmente destructiva.",
                        "El dispositivo conmuta de manera automática a un modo de oscilación sinusoidal.",
                        "La región de deplexión se congela eliminando de forma permanente los huecos."}, 1);
        agregarPregunta(l, "La relación entre la carga, la capacitancia y el voltaje en un capacitor se define como:",
                new String[]{"V igual a Q por C.",
                        "Q igual a C dividido entre V.",
                        "Q igual a C por V.",
                        "C igual a Q por V al cuadrado."}, 2);
        agregarPregunta(l, "¿Cuál es la función principal de un material dieléctrico situado entre las placas de un capacitor?",
                new String[]{"Permitir el paso directo de la corriente continua a bajas frecuencias.",
                        "Incrementar la capacitancia disminuyendo el campo eléctrico interno neto.",
                        "Actuar como conductor secundario mecánicamente estable a alta temperatura.",
                        "Absorber los electrones excedentes para evitar que el componente se cargue."}, 1);
        agregarPregunta(l, "El diodo ideal se caracteriza por actuar como:",
                new String[]{"Una resistencia lineal de valor constante tanto en directa como en inversa.",
                        "Un interruptor cerrado en polarización directa y abierto en inversa.",
                        "Una fuente de voltaje alterna controlada por la corriente de compuerta.",
                        "Un atenuador de señales armónicas dependiente de la frecuencia base."}, 1);
        agregarPregunta(l, "¿Qué parámetro define la rapidez con la que un circuito RL responde a un escalón de tensión?",
                new String[]{"La constante de tiempo calculada algebraicamente como tau igual a R por L.",
                        "La constante de tiempo calculada algebraicamente como tau igual a L entre R.",
                        "La frecuencia de corte determinada por el inverso de la inductancia pura.",
                        "La conductancia mutua del alambre bobinado respecto al núcleo de aire."}, 1);
        agregarPregunta(l, "¿Qué tipo de diodo semiconductor está diseñado específicamente para operar de forma segura en la región de ruptura inversa?",
                new String[]{"Diodo Rectificador estándar.",
                        "Diodo Zener.",
                        "Diodo Emisor de Luz, también conocido como LED.",
                        "Diodo de Conmutación rápida."}, 1);
        agregarPregunta(l, "En el modelado real de un diodo en conducción, además de la caída de tensión umbral, se debe considerar:",
                new String[]{"La capacitancia infinita del encapsulado plástico exterior rígido.",
                        "La resistencia interna óhmica de las zonas semiconductoras neutras.",
                        "El campo magnético inducido por los hilos de boro de los contactos.",
                        "La destrucción espontánea de portadores por radiación cósmica local."}, 1);
        agregarPregunta(l, "¿Qué sucede con la energía almacenada en un capacitor ideal cuando se desconecta de la fuente?",
                new String[]{"Se destruye de forma instantánea debido a las leyes termodinámicas.",
                        "Se retiene en forma de campo eléctrico estático entre sus placas conductoras.",
                        "Se transforma inmediatamente en ondas electromagnéticas de alta frecuencia.",
                        "Se descarga hacia el aire circundante mediante arcos eléctricos invisibles."}, 1);
        agregarPregunta(l, "Si un inductor real se construye devanando alambre sobre un núcleo de hierro, la resistencia del cable provoca:",
                new String[]{"Una capacitancia parásita de carácter destructivo a bajas frecuencias.",
                        "Pérdidas de energía adicionales por disipación térmica óhmica, efecto Joule.",
                        "Un incremento indefinido en el valor de la inductancia electromagnética neta.",
                        "La anulación completa del flujo magnético confinado en el núcleo ferroso."}, 1);
    }

    private void crearLecturaElectronica4(Carrera c) {
        String texto = "Circuitos analógicos y digitales: La frontera conceptual\n\n" +
                "La electrónica se bifurca en dos metodologías analíticas y de diseño: el procesamiento analógico y el procesamiento digital. " +
                "La distinción radica en cómo se representan y manipulan las señales que codifican la información.\n\n" +
                "Las señales analógicas son variables continuas en el tiempo y en su amplitud. " +
                "Pueden tomar infinitos valores dentro de un rango determinado, reflejando de forma directa magnitudes físicas reales como la temperatura, la presión o el sonido. " +
                "El análisis de circuitos analógicos se centra en la fidelidad, la ganancia, el ancho de banda y la linealidad de componentes como los amplificadores operacionales y los transistores operando en sus zonas activas. " +
                "El principal desafío en este dominio es el ruido eléctrico y las distorsiones, ya que cualquier perturbación altera irreversiblemente la información contenida en la amplitud de la señal.\n\n" +
                "Por el contrario, la electrónica digital discretiza la información. " +
                "Utiliza señales cuantitativas que solo adoptan valores específicos denominados estados lógicos: alto y bajo, mapeados a rangos de voltaje estrictos. " +
                "La unidad fundamental en este campo es la compuerta lógica, construida con transistores operando exclusivamente como interruptores en las regiones de corte y saturación. " +
                "El enfoque digital ofrece una inmunidad al ruido drásticamente superior, puesto que pequeñas variaciones de voltaje no cambian el estado lógico interpretado por el sistema. " +
                "Además, permite el almacenamiento masivo y el procesamiento computacional mediante el álgebra de Boole.\n\n" +
                "Mientras que el mundo real es intrínsecamente analógico y demanda etapas de acondicionamiento de alta precisión, " +
                "la electrónica digital procesa la información con alta inmunidad al ruido, requiriendo interfaces de conversión para comunicar ambos dominios.";
        Lectura l = new Lectura("Circuitos analógicos y digitales", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cuál es la propiedad distintiva fundamental de una señal puramente analógica?",
                new String[]{"Está codificada estrictamente mediante patrones lógicos binarios de unos y ceros.",
                        "Varía de forma continua tanto en el tiempo como en su rango de amplitud.",
                        "Es totalmente inmune a los efectos del ruido electromagnético de los circuitos.",
                        "Se genera únicamente mediante transistores operando en modo de corte total."}, 1);
        agregarPregunta(l, "En electrónica digital, ¿cómo se logra que los sistemas posean una elevada inmunidad al ruido?",
                new String[]{"Utilizando filtros pasivos RC de orden infinito en cada entrada lógica.",
                        "Definiendo rangos de voltaje específicos y separados para los estados lógicos.",
                        "Operando los transistores en el centro de su región activa o de amplificación.",
                        "Eliminando por completo las corrientes de fuga térmicas de los semiconductores."}, 1);
        agregarPregunta(l, "Cuando un transistor BJT o MOSFET se utiliza para implementar compuertas lógicas digitales, ¿en qué regiones de operación trabaja?",
                new String[]{"Exclusivamente en la región activa para asegurar linealidad matemática.",
                        "En las regiones de corte y de saturación, actuando como un interruptor.",
                        "En la zona de ruptura Zener para estabilizar los niveles lógicos fijos.",
                        "En modo de resistencia variable de alta precisión óhmica simétrica."}, 1);
        agregarPregunta(l, "¿Qué ventaja sustancial ofrece el procesamiento digital sobre el analógico?",
                new String[]{"No requiere ningún tipo de fuente de alimentación para retener datos.",
                        "Facilita la reproducción exacta, el almacenamiento masivo y el procesado lógico.",
                        "Es capaz de representar fielmente la naturaleza física sin requerir muestreo.",
                        "Funciona con mayor velocidad al prescindir de portadores de carga libres."}, 1);
        agregarPregunta(l, "Las compuertas lógicas básicas como AND, OR y NOT se fundamentan matemáticamente en:",
                new String[]{"El cálculo infinitesimal y las transformadas integrales de Fourier.",
                        "Las ecuaciones diferenciales parciales de Maxwell para campos libres.",
                        "El álgebra de Boole, orientada a variables discretas binarias.",
                        "El teorema del valor medio aplicado a matrices estocásticas densas."}, 2);
        agregarPregunta(l, "¿Por qué el ruido eléctrico es un problema crítico en los circuitos analógicos?",
                new String[]{"Porque destruye físicamente las compuertas lógicas debido a sobrevoltajes.",
                        "Porque se suma a la señal y modifica de manera irreversible la información.",
                        "Porque altera la frecuencia del reloj maestro forzando desincronización.",
                        "Porque transforma los materiales semiconductores en aislantes puros."}, 1);
        agregarPregunta(l, "Un convertidor analógico-digital tiene la función principal de:",
                new String[]{"Tomar una señal analógica continua y transformarla en un código digital binario.",
                        "Amplificar el voltaje de un sensor analógico para que active un relé de potencia.",
                        "Convertir trenes de pulsos lógicos digitales en ondas senoidales puras de alta tensión.",
                        "Modular la corriente de mallas de un circuito lineal basado en el teorema de Thévenin."}, 0);
        agregarPregunta(l, "En el contexto de las señales lógicas digitales, el estado lógico cero se asocia comúnmente a:",
                new String[]{"Un circuito abierto flotante que carece de conexiones eléctricas definidas.",
                        "Un nivel de voltaje bajo, generalmente cercano al potencial de tierra física.",
                        "Una corriente infinita que fluye en sentido inverso por los diodos zener.",
                        "Una señal armónica senoidal desfasada exactamente ciento ochenta grados."}, 1);
        agregarPregunta(l, "¿Qué dispositivo electrónico es emblemático en el diseño analógico para amplificar señales débiles?",
                new String[]{"La compuerta lógica NAND de tecnología CMOS de alta densidad.",
                        "El Amplificador Operacional configurado en la zona lineal.",
                        "El Registro de Desplazamiento síncronizado por flanco de subida.",
                        "El Microcontrolador programable de arquitectura Harvard reducida."}, 1);
        agregarPregunta(l, "La discretización en amplitud de una señal analógica para llevarla al dominio digital se denomina:",
                new String[]{"Amplificación de potencia.",
                        "Cuantización de la señal.",
                        "Rectificación de onda completa.",
                        "Filtrado pasivo de armónicos."}, 1);
        agregarPregunta(l, "Si un sistema digital opera con cinco voltios para el uno lógico y cero voltios para el cero lógico, ¿qué ocurrirá si ingresa una señal de ruido de cero punto dos voltios en una entrada que debería ser cero?",
                new String[]{"El sistema interpretará erróneamente un uno lógico colapsando la lógica.",
                        "El sistema seguirá interpretando de forma correcta un cero lógico estable.",
                        "La compuerta lógica se quemará debido al exceso de corriente inyectada.",
                        "El circuito entrará en una oscilación armónica incontrolable de alta frecuencia."}, 1);
        agregarPregunta(l, "¿Cuál es el principal inconveniente de la electrónica digital en su interacción con el entorno físico real?",
                new String[]{"Que las señales digitales consumen mucha más potencia térmica que las analógicas.",
                        "Que el mundo físico real es analógico y requiere etapas complejas de conversión.",
                        "Que los circuitos digitales no pueden fabricarse en silicio por restricciones físicas.",
                        "Que la lógica binaria es incapaz de procesar operaciones matemáticas complejas."}, 1);
        agregarPregunta(l, "Una señal de reloj en un circuito digital es un ejemplo de:",
                new String[]{"Una señal analógica senoidal de amplitud infinitamente variable.",
                        "Una señal digital periódica utilizada para sincronizar las operaciones.",
                        "Un ruido eléctrico aleatorio que degrada la calidad de los datos.",
                        "Una tensión continua pura que alimenta las etapas de salida de potencia."}, 1);
        agregarPregunta(l, "En electrónica analógica, el término ancho de banda se refiere esencialmente a:",
                new String[]{"La cantidad de bits por segundo que un cable de cobre puede transmitir sin errores.",
                        "El rango de frecuencias en el cual un circuito opera de manera óptima y predecible.",
                        "La longitud física medida en micrómetros de las pistas internas de un chip.",
                        "El número total de compuertas lógicas que se pueden conectar a un solo nodo."}, 1);
        agregarPregunta(l, "¿Qué define el parámetro de fidelidad en un amplificador analógico?",
                new String[]{"La capacidad de operar sin consumir corriente eléctrica de la fuente de poder.",
                        "El grado en que la señal de salida reproduce la forma de la señal de entrada sin distorsión.",
                        "El tiempo total que el circuito puede funcionar de forma continua sin apagarse.",
                        "La resistencia mecánica del encapsulado contra golpes e interferencias directas."}, 1);
        agregarPregunta(l, "El diseño de circuitos digitales se simplifica en comparación con el analógico debido a:",
                new String[]{"La total ausencia de leyes de Kirchhoff en el comportamiento de las corrientes.",
                        "Que los componentes se tratan como bloques funcionales abstractos basados en reglas lógicas.",
                        "Que las resistencias e inductores aumentan su linealidad ante señales cuadradas.",
                        "La eliminación de los efectos de las capacidades parásitas a altas frecuencias."}, 1);
        agregarPregunta(l, "¿Qué tipo de circuito realiza la operación inversa a un convertidor analógico-digital?",
                new String[]{"Un rectificador puente de diodos de silicio de media potencia.",
                        "Un convertidor digital-analógico.",
                        "Un oscilador sintonizado por puente de Wien pasivo.",
                        "Un multiplexor combinacional de compuertas lógicas de tres estados."}, 1);
        agregarPregunta(l, "En la electrónica analógica actual, un transistor operando en su zona activa se comporta como:",
                new String[]{"Una fuente de voltaje fija que no responde a señales externas de control.",
                        "Una fuente de corriente controlada que permite amplificar variaciones de entrada.",
                        "Un interruptor ideal perfectamente cerrado con resistencia interna nula.",
                        "Un capacitor variable que modifica su dieléctrico en función de la temperatura."}, 1);
        agregarPregunta(l, "¿Cuál es el papel del muestreo en el proceso de digitalización de una señal?",
                new String[]{"Amplificar la amplitud de la señal física analógica para eliminar el ruido térmico.",
                        "Tomar valores discretos de la señal analógica a intervalos de tiempo regulares.",
                        "Estabilizar el voltaje de alimentación del circuito para evitar cortocircuitos.",
                        "Invertir la polaridad de los diodos semiconductores en etapas de conmutación."}, 1);
        agregarPregunta(l, "La afirmación de que la información digital es más fácil de recuperar frente a la degradación del canal se justifica porque:",
                new String[]{"Las señales digitales tienen amplitudes infinitamente más grandes que las analógicas.",
                        "Solo se requiere discernir si la señal se encuentra por encima o por debajo de un umbral.",
                        "Los circuitos digitales eliminan automáticamente las leyes de la termodinámica real.",
                        "El silicio digital repele de forma natural los campos magnéticos parásitos externos."}, 1);
    }

    private void crearLecturaElectronica5(Carrera c) {
        String texto = "Instrumentos de medición y seguridad electrónica: Metrología y prevención\n\n" +
                "La validación experimental de los modelos teóricos en electrónica exige el dominio de la metrología y la aplicación rigurosa de protocolos de seguridad. " +
                "Los instrumentos de medición fundamentales son el multímetro digital y el osciloscopio, cada uno optimizado para diferentes ventanas de análisis.\n\n" +
                "El multímetro es un instrumento versátil que mide magnitudes estáticas o de baja frecuencia como resistencia, tensión y corriente. " +
                "Al medir voltaje, el multímetro se configura como voltímetro y debe conectarse en paralelo al componente, exhibiendo una resistencia interna idealmente infinita para no derivar corriente del circuito. " +
                "Al medir corriente, se configura como amperímetro y se conecta en serie, intercalándose en la trayectoria del flujo eléctrico; " +
                "su resistencia interna debe aproximarse a cero para evitar introducir una caída de tensión que altere las condiciones operacionales de la red.\n\n" +
                "Para el análisis de señales dinámicas de alta frecuencia, el osciloscopio es indispensable. " +
                "Este instrumento despliega gráficamente la evolución temporal del voltaje en una pantalla bidimensional de amplitud versus tiempo. " +
                "Su acoplamiento de entrada y su impedancia de entrada, típicamente un megaohmio, son críticos para prevenir el efecto de carga, " +
                "el cual distorsiona la medición al modificar las impedancias relativas del circuito bajo prueba.\n\n" +
                "La seguridad electrónica mitiga riesgos tanto para el operador como para los componentes. " +
                "El cuerpo humano es un conductor óhmico imperfecto; corrientes superiores a diez miliamperios pueden provocar contracciones musculares involuntarias, " +
                "y por encima de cincuenta miliamperios existe riesgo inminente de fibrilación ventricular. " +
                "La Descarga Electrostática representa una amenaza invisible para los semiconductores integrados, " +
                "donde voltajes estáticos de miles de voltios acumulados en el cuerpo humano pueden perforar capas microscópicas de dióxido de silicio, inutilizando dispositivos de forma permanente. " +
                "El uso de pulseras antiestáticas y herramientas conectadas a tierra es imperativo en entornos de laboratorio.";
        Lectura l = new Lectura("Instrumentos de medición y seguridad electrónica", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "Para medir la diferencia de potencial en una resistencia sin alterar el circuito, el voltímetro debe:",
                new String[]{"Conectarse en serie con el elemento y poseer una resistencia interna nula.",
                        "Conectarse en paralelo con el elemento y poseer una resistencia interna infinita.",
                        "Insertarse de forma flotante entre el nodo de referencia y la tierra del chasis.",
                        "Ajustarse en modo de medición de corriente alterna reactiva de alta frecuencia."}, 1);
        agregarPregunta(l, "¿Qué error de conexión grave causaría un cortocircuito directo si se intenta medir voltaje con un multímetro?",
                new String[]{"Dejar las puntas de prueba del voltímetro tocando el chasis plástico aislado.",
                        "Conectar el multímetro en paralelo teniendo las puntas insertadas en los bornes de amperímetro.",
                        "Configurar el instrumento en modo de medición de ohmios con el circuito desenergizado.",
                        "Conectar la punta de tierra del osciloscopio al nodo de referencia negativo del circuito."}, 1);
        agregarPregunta(l, "Al utilizar el multímetro como amperímetro para medir la corriente de una malla, este debe conectarse en:",
                new String[]{"Paralelo con la fuente de alimentación, mostrando una impedancia interna muy alta.",
                        "Serie con la trayectoria de la corriente, mostrando una impedancia interna cercana a cero.",
                        "En estrella con los capacitores de filtro acoplados a la toma de corriente general.",
                        "En los nodos esenciales terminales manteniendo el circuito completamente apagado."}, 1);
        agregarPregunta(l, "¿Cuál es la función primordial del osciloscopio en el laboratorio de electrónica?",
                new String[]{"Medir la resistencia interna óhmica de diodos polarizados en sentido inverso.",
                        "Visualizar de forma gráfica la evolución temporal del voltaje de una señal eléctrica.",
                        "Almacenar energía en forma de campo magnético para estabilizar mallas complejas.",
                        "Suministrar corrientes de alta potencia a circuitos analógicos basados en transistores."}, 1);
        agregarPregunta(l, "El fenómeno denominado efecto de carga en metrología electrónica ocurre cuando:",
                new String[]{"La fuente de alimentación se sobrecalienta debido a fallos en el puente rectificador.",
                        "El instrumento de medición altera las condiciones del circuito debido a su propia impedancia.",
                        "Los electrones libres se acumulan de forma estática en la superficie externa de los cables.",
                        "Se excede el límite térmico del silicio provocando una recombinación descontrolada."}, 1);
        agregarPregunta(l, "¿A partir de qué rango de corriente eléctrica a través del cuerpo humano existe riesgo de fibrilación ventricular?",
                new String[]{"Menor a un microamperio.",
                        "Superior a los cincuenta miliamperios.",
                        "Exactamente diez nanoamperios.",
                        "Únicamente cuando la corriente supera los cien amperios."}, 1);
        agregarPregunta(l, "¿Qué peligro invisible representa la Descarga Electrostática para los circuitos semiconductores?",
                new String[]{"Provoca la fusión macroscópica de las soldaduras de estaño de las placas de circuito.",
                        "Genera voltajes elevados capaces de perforar capas microscópicas aislantes de los chips.",
                        "Altera de manera irreversible la frecuencia de la red eléctrica de suministro alterno.",
                        "Genera un campo gravitacional local que deforma mecánicamente los hilos de cobre."}, 1);
        agregarPregunta(l, "Para evitar daños por Descarga Electrostática al manipular componentes electrónicos integrados sensibles, se debe:",
                new String[]{"Enfriar los componentes con hielo seco antes de sacarlos de su caja protectora.",
                        "Utilizar una pulsera antiestática debidamente conectada al sistema de tierra.",
                        "Incrementar el voltaje de alimentación del circuito para repeler las cargas estáticas.",
                        "Limpiar los terminales metálicos con paños de lana seca a alta velocidad mecánica."}, 1);
        agregarPregunta(l, "¿Qué impedancia de entrada típica presenta un osciloscopio convencional para reducir el efecto de carga?",
                new String[]{"Cero ohmios.",
                        "Cincuenta ohmios.",
                        "Un megaohmio.",
                        "Diez mil henrios."}, 2);
        agregarPregunta(l, "El acoplamiento de entrada en corriente alterna de un osciloscopio funciona mediante:",
                new String[]{"Un cortocircuito directo que elimina todas las resistencias de entrada del canal analógico.",
                        "Un capacitor en serie que bloquea la componente de corriente continua de la señal.",
                        "Un inductor en paralelo que deriva las corrientes parásitas de alta frecuencia a tierra.",
                        "Un diodo Zener que recorta de forma automática los voltajes que superen los cinco voltios."}, 1);
        agregarPregunta(l, "¿Por qué es fundamental que la resistencia de un amperímetro sea idealmente cero?",
                new String[]{"Para evitar que el instrumento consuma toda la potencia de la fuente de voltaje principal.",
                        "Para que no introduzca una caída de tensión adicional que modifique la corriente real de la malla.",
                        "Para bloquear el paso de ruidos de alta frecuencia provenientes de transistores conmutados.",
                        "Para permitir que el capacitor del circuito se cargue de manera instantánea sin transitorios."}, 1);
        agregarPregunta(l, "Si se desea medir la resistencia de un componente utilizando un multímetro digital, el circuito debe estar:",
                new String[]{"Energizado a su máxima tensión nominal para estabilizar los enlaces covalentes óhmicos.",
                        "Completamente desenergizado y, de ser posible, con el componente aislado de la red.",
                        "Conectado a una señal alterna senoidal de alta frecuencia para medir la reactancia inductiva.",
                        "Polarizado en sentido inverso mediante un diodo Zener auxiliar conectado al nodo base."}, 1);
        agregarPregunta(l, "¿Qué representa el eje horizontal de la pantalla de un osciloscopio en su modo de operación convencional?",
                new String[]{"La amplitud máxima de la señal medida en voltios eficaces por división métrica.",
                        "La base de tiempo, que permite analizar la evolución cronológica de la señal eléctrica.",
                        "La densidad de corriente por unidad de área de los portadores del semiconductor.",
                        "El desfase angular expresado en radianes complejos del vector de Thévenin."}, 1);
        agregarPregunta(l, "La contracción muscular involuntaria que impide a una persona soltarse de un conductor energizado se conoce como:",
                new String[]{"Recombinación termoiónica del sistema motriz.",
                        "Tetanización muscular inducida por corriente eléctrica.",
                        "Fibrilación galvánica de las articulaciones óseas.",
                        "Ruptura por avalancha de los nervios periféricos externos."}, 1);
        agregarPregunta(l, "Al conectar la punta de prueba de un osciloscopio a un circuito, el cable de tierra de la punta debe conectarse a:",
                new String[]{"El terminal positivo de la fuente para capturar los armónicos de alta frecuencia nítidos.",
                        "El nodo de referencia común del circuito, que generalmente comparte el potencial de tierra.",
                        "Cualquiera de los nodos esenciales de manera aleatoria gracias al aislamiento del canal.",
                        "Al terminal de la pulsera antiestática para cerrar el lazo de protección del semiconductor."}, 1);
        agregarPregunta(l, "El acoplamiento de entrada en modo GND en un osciloscopio sirve específicamente para:",
                new String[]{"Derivar descargas atmosféricas de gran intensidad para proteger los fusibles internos del chasis.",
                        "Desconectar la señal de entrada y conectar el canal internamente a la referencia de cero voltios.",
                        "Forzar al circuito bajo prueba a apagar de forma remota todas sus fuentes independientes.",
                        "Medir la resistencia óhmica directa existente entre el suelo del laboratorio y el instrumento."}, 1);
        agregarPregunta(l, "¿Qué especificación de seguridad CAT en un multímetro define su capacidad para resistir transitorios de tensión?",
                new String[]{"La escala de conductancia en Siemens nominales máximos admisibles.",
                        "Las categorías de sobrevoltaje CAT uno, dos, tres y cuatro de las normas de metrología.",
                        "El coeficiente térmico de resistencia del fusible cerámico interno del circuito activo.",
                        "El tipo de silicio dopado con boro empleado en la pantalla de cristal líquido del equipo."}, 1);
        agregarPregunta(l, "¿Por qué las capas microscópicas de dióxido de silicio en los transistores MOSFET son altamente vulnerables a la Descarga Electrostática?",
                new String[]{"Porque el dióxido de silicio es un conductor perfecto que multiplica las corrientes de fuga.",
                        "Porque al ser capas extremadamente delgadas, un voltaje alto genera un campo eléctrico que las perfora.",
                        "Porque reaccionan químicamente con el sudor del operador disolviendo la estructura atómica.",
                        "Porque atrapan de forma magnética los huecos impidiendo la conmutación digital binaria limpia."}, 1);
        agregarPregunta(l, "Si se realiza una medición de corriente con un amperímetro conectado erróneamente en paralelo con una fuente de tensión:",
                new String[]{"La fuente se apagará de forma segura debido a la impedancia infinita que presenta el instrumento.",
                        "Circulará una corriente extremadamente alta que fundirá el fusible protector del amperímetro.",
                        "El amperímetro medirá el equivalente de Thévenin exacto del circuito sin sufrir perturbaciones.",
                        "Se generará una señal digital cuadrada de alta frecuencia debido a la resonancia del nodo."}, 1);
        agregarPregunta(l, "El uso de puntas de prueba atenuadoras de diez por uno en un osciloscopio tiene la ventaja principal de:",
                new String[]{"Multiplicar por diez el voltaje real medido para quemar los armónicos de baja frecuencia parásitos.",
                        "Incrementar por diez la impedancia de entrada vista por el circuito, reduciendo el efecto de carga.",
                        "Transformar señales analógicas en códigos lógicos digitales binarios listos para el álgebra de Boole.",
                        "Eliminar de forma absoluta la necesidad de conectar el cable de tierra de la punta al nodo de referencia."}, 1);
    }

    // ================================================================
    // PARCHE: actualiza textos truncados de electricidad en BD existente
    // ================================================================
    private void actualizarTextosElectricidad() {
        try {
            java.util.List<Lectura> lecturas = lecturaRepository.findAll();
            for (Lectura l : lecturas) {
                String titulo = l.getTitulo();
                String textoActual = l.getContenido();
                String textoNuevo = null;

                if ("Fundamentos de la Corriente Eléctrica".equals(titulo) && textoActual != null && textoActual.endsWith("...")) {
                    textoNuevo = "Fundamentos de la Corriente Eléctrica y el Circuito de Carga\n\n" +
                        "La electricidad no es una invención humana, sino una fuerza fundamental de la naturaleza que hemos aprendido a canalizar. " +
                        "En su nivel más básico, la corriente eléctrica es el flujo ordenado de electrones a través de un material conductor. " +
                        "Para que ese flujo ocurra se necesitan cuatro elementos esenciales: una fuente de energía, un conductor, un receptor y un dispositivo de control.\n\n" +
                        "La fuente de energía, como una batería o un generador, proporciona la fuerza electromotriz que pone a los electrones en movimiento. " +
                        "El conductor, generalmente cobre o aluminio, ofrece poca resistencia al paso de los electrones. " +
                        "El receptor es el dispositivo que aprovecha la energía eléctrica, como una bombilla o un motor. " +
                        "El dispositivo de control, como un interruptor, permite abrir o cerrar el circuito según se necesite.\n\n" +
                        "Un circuito cerrado permite el flujo continuo de electrones, mientras que un circuito abierto interrumpe ese flujo por completo. " +
                        "Para proteger las instalaciones se usan fusibles y disyuntores que aprovechan el Efecto Joule: " +
                        "cuando circula demasiada corriente, el calor generado funde el fusible y corta el circuito antes de que ocurra un daño mayor.\n\n" +
                        "Una analogía útil es la del sistema hidráulico: el voltaje equivale a la presión del agua, la corriente al caudal y la resistencia al diámetro de la tubería. " +
                        "Comprender estos fundamentos es el primer paso para cualquier técnico en electricidad.";
                } else if ("La Ley de Ohm y la Eficiencia en Instalaciones".equals(titulo) && textoActual != null && textoActual.endsWith("...")) {
                    textoNuevo = "LA LEY DE OHM Y LA EFICIENCIA EN INSTALACIONES\n\n" +
                        "La Ley de Ohm es la regla de oro para cualquier técnico en electricidad. " +
                        "Esta ley establece una relación matemática simple y poderosa entre tres magnitudes fundamentales: " +
                        "el voltaje, la intensidad de corriente y la resistencia. " +
                        "La fórmula se expresa como V igual a I por R, donde V es el voltaje en voltios, I es la intensidad en amperios y R es la resistencia en ohmios.\n\n" +
                        "Gracias a esta ley, si conocemos dos de las tres magnitudes, podemos calcular la tercera. " +
                        "Por ejemplo, si un motor funciona a doce voltios y tiene una resistencia de cuatro ohmios, " +
                        "la corriente que consume será de tres amperios. " +
                        "Esto permite al técnico dimensionar correctamente los cables, fusibles y fuentes de alimentación.\n\n" +
                        "Uno de los problemas más comunes en instalaciones es la caída de tensión, " +
                        "que ocurre cuando los cables son demasiado delgados o demasiado largos. " +
                        "Según la Ley de Ohm, una mayor resistencia en el cable significa que parte del voltaje se pierde en él, " +
                        "llegando menos energía al receptor. " +
                        "Esto reduce el rendimiento de los equipos y puede dañarlos con el tiempo.\n\n" +
                        "El multímetro es la herramienta fundamental que permite al técnico medir voltaje, corriente y resistencia " +
                        "para verificar los cálculos teóricos con la realidad del circuito. " +
                        "Desarrollar un sentido técnico para predecir el comportamiento eléctrico antes de medir " +
                        "es lo que distingue a un técnico experto de uno principiante.";
                } else if ("Arquitectura de Circuitos: Serie y Paralelo".equals(titulo) && textoActual != null && textoActual.endsWith("...")) {
                    textoNuevo = "Arquitectura de Circuitos: Serie y Paralelo\n\n" +
                        "La disposición física y eléctrica de los componentes determina el comportamiento de toda la red eléctrica. " +
                        "Existen dos formas básicas de conectar los componentes en un circuito: en serie y en paralelo, " +
                        "cada una con características muy distintas que el técnico debe conocer para diseñar instalaciones seguras y eficientes.\n\n" +
                        "En un circuito en serie, todos los componentes están conectados uno tras otro formando una sola cadena. " +
                        "La corriente es la misma en todos los puntos del circuito, pero el voltaje se reparte entre cada receptor. " +
                        "La gran desventaja del circuito en serie es que si un componente falla, todo el circuito deja de funcionar. " +
                        "Un ejemplo clásico son las antiguas luces navideñas: si una bombilla se quemaba, todas se apagaban.\n\n" +
                        "En un circuito en paralelo, cada componente tiene su propia rama conectada directamente a la fuente. " +
                        "El voltaje en cada receptor es igual al voltaje de la fuente, y si uno falla, los demás continúan funcionando. " +
                        "Esta es la razón por la que las instalaciones domésticas se realizan en paralelo: " +
                        "apagar una lámpara no afecta a las demás.\n\n" +
                        "Sin embargo, el paralelo tiene un riesgo: al agregar más dispositivos, la corriente total aumenta. " +
                        "Si se superan los límites del cableado se produce una sobrecarga que puede causar un incendio. " +
                        "Los interruptores y fusibles se colocan en serie con cada rama para proteger el sistema. " +
                        "Conocer cuándo usar serie o paralelo es una decisión técnica fundamental en cualquier instalación eléctrica.";
                } else if ("La Guerra de las Corrientes: CA versus CC".equals(titulo) && textoActual != null && textoActual.endsWith("...")) {
                    textoNuevo = "La Guerra de las Corrientes: CA versus CC\n\n" +
                        "La historia de la electricidad está marcada por una de las disputas tecnológicas más famosas del siglo diecinueve: " +
                        "la competencia entre la Corriente Continua y la Corriente Alterna. " +
                        "Thomas Edison defendía la Corriente Continua, mientras que Nikola Tesla y George Westinghouse apostaban por la Corriente Alterna. " +
                        "Esta batalla, conocida como la Guerra de las Corrientes, cambió para siempre la forma en que la electricidad llegó a los hogares.\n\n" +
                        "La Corriente Continua fluye siempre en la misma dirección. Las baterías y los paneles solares producen este tipo de corriente. " +
                        "Su mayor desventaja es que no puede transportarse eficientemente a largas distancias sin grandes pérdidas de energía.\n\n" +
                        "La Corriente Alterna, en cambio, cambia de dirección muchas veces por segundo. " +
                        "Su frecuencia se mide en Hertz. En Guatemala y gran parte de América la frecuencia estándar es de sesenta hercios. " +
                        "La ventaja clave de la Corriente Alterna es que puede transformarse fácilmente a voltajes muy altos para el transporte " +
                        "y luego reducirse al voltaje de uso doméstico mediante transformadores, reduciendo enormemente las pérdidas por calor.\n\n" +
                        "Hoy vivimos en un mundo híbrido: la Corriente Alterna se usa para el transporte y distribución de la energía, " +
                        "mientras que la Corriente Continua se usa internamente en casi todos los dispositivos electrónicos. " +
                        "Los cargadores de celulares, las computadoras y los televisores convierten la Corriente Alterna de la red en Corriente Continua " +
                        "mediante circuitos rectificadores. Tesla y Edison tenían razón en contextos distintos.";
                } else if ("Seguridad Eléctrica: La Prevención como Herramienta Técnica".equals(titulo) && textoActual != null && textoActual.endsWith("...")) {
                    textoNuevo = "Seguridad Eléctrica: La Prevención como Herramienta Técnica\n\n" +
                        "En electricidad, el error suele pagarse caro. El riesgo eléctrico no es solo el choque directo por contacto con un cable energizado. " +
                        "También incluye los arcos eléctricos, que pueden ocurrir sin contacto físico y generan temperaturas extremas, " +
                        "los incendios por sobrecalentamiento de cables y los gases tóxicos liberados al quemar aislamientos.\n\n" +
                        "El cuerpo humano es un conductor de electricidad. " +
                        "La corriente busca siempre el camino de menor resistencia para llegar a tierra, " +
                        "y si ese camino pasa por una persona, las consecuencias pueden ser fatales. " +
                        "Por eso la puesta a tierra es fundamental: proporciona un camino seguro para que las corrientes de falla fluyan al suelo " +
                        "sin pasar por las personas ni dañar los equipos.\n\n" +
                        "Todo técnico en electricidad debe conocer y aplicar las Cinco Reglas de Oro antes de intervenir cualquier instalación: " +
                        "desconectar la fuente de energía, bloquear los dispositivos de mando para que nadie los reactive, " +
                        "verificar la ausencia de tensión con un instrumento de medición, " +
                        "poner a tierra y en cortocircuito la instalación, y delimitar y señalizar la zona de trabajo.\n\n" +
                        "El equipo de protección personal es igualmente esencial: guantes dieléctricos, calzado aislante, casco y lentes de seguridad. " +
                        "Un técnico que trabaja sin protección, por comodidad o exceso de confianza, pone en riesgo su vida. " +
                        "En caso de incendio eléctrico, nunca se debe usar agua, pues conduce la electricidad. " +
                        "Se deben usar extintores de tipo C especialmente diseñados para fuegos eléctricos. " +
                        "La prevención es la herramienta más importante del electricista.";
                }

                if (textoNuevo != null) {
                    l.setContenido(textoNuevo);
                    lecturaRepository.save(l);
                    System.out.println("Texto actualizado: " + titulo);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar textos de electricidad: " + e.getMessage());
        }
    }

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
    private void crearLecturaElectricidad1(Carrera c) {
        String texto = "Fundamentos de la Corriente Eléctrica y el Circuito de Carga\n\n" +
                "La electricidad no es una invención humana, sino una fuerza fundamental de la naturaleza que hemos aprendido a canalizar. " +
                "En su nivel más básico, la corriente eléctrica es el flujo ordenado de electrones a través de un material conductor. " +
                "Para que ese flujo ocurra se necesitan cuatro elementos esenciales: una fuente de energía, un conductor, un receptor y un dispositivo de control.\n\n" +
                "La fuente de energía, como una batería o un generador, proporciona la fuerza electromotriz que pone a los electrones en movimiento. " +
                "El conductor, generalmente cobre o aluminio, ofrece poca resistencia al paso de los electrones. " +
                "El receptor es el dispositivo que aprovecha la energía eléctrica, como una bombilla o un motor. " +
                "El dispositivo de control, como un interruptor, permite abrir o cerrar el circuito según se necesite.\n\n" +
                "Un circuito cerrado permite el flujo continuo de electrones, mientras que un circuito abierto interrumpe ese flujo por completo. " +
                "Para proteger las instalaciones se usan fusibles y disyuntores que aprovechan el Efecto Joule: " +
                "cuando circula demasiada corriente, el calor generado funde el fusible y corta el circuito antes de que ocurra un daño mayor.\n\n" +
                "Una analogía útil es la del sistema hidráulico: el voltaje equivale a la presión del agua, la corriente al caudal y la resistencia al diámetro de la tubería. " +
                "Comprender estos fundamentos es el primer paso para cualquier técnico en electricidad.";
        Lectura l = new Lectura("Fundamentos de la Corriente Eléctrica", texto, c);
        lecturaRepository.save(l);

        agregarPreguntaVF(l, "¿Cuál es la partícula subatómica cuyo flujo ordenado genera la corriente eléctrica?",
                new String[]{"Protón", "Neutrón", "Electrón", "Átomo"}, 2);
        agregarPreguntaVF(l, "El componente encargado de gestionar el paso de la corriente (abrir o cerrar el circuito) es:",
                new String[]{"El receptor", "El dispositivo de control", "La fuente de energía", "El fusible"}, 1);
        agregarPreguntaVF(l, "El aluminio es mencionado como un buen conductor debido a su baja resistencia.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "La \"fuerza electromotriz\" es proporcionada por el receptor del circuito.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "Si en la analogía hidráulica el voltaje es la presión, ¿qué representa un cable con mucha resistencia?",
                new String[]{"Un motor potente", "Una tubería con el diámetro muy reducido", "Un tanque de agua lleno", "Una llave de paso abierta"}, 1);
        agregarPreguntaVF(l, "El \"Efecto Joule\" se menciona en relación con la protección. ¿Qué fenómeno físico genera este efecto en los cables?",
                new String[]{"Enfriamiento por flujo", "Magnetismo", "Generación de calor", "Aumento de voltaje"}, 2);
        agregarPreguntaVF(l, "Según el texto, un circuito abierto permite el flujo de electrones libremente.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "¿Por qué se considera que la protección es el elemento más crítico en una instalación profesional?",
                new String[]{"Porque es el más caro de comprar", "Porque previene la destrucción de la infraestructura ante fallos", "Porque mejora el brillo de las bombillas", "Porque sustituye a la fuente de energía"}, 1);
        agregarPreguntaVF(l, "Un técnico que comprende la analogía hidráulica tiene mejores bases para predecir el comportamiento del circuito.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "En un taller rural, ¿es válido sustituir un fusible quemado por un trozo de alambre de cobre?",
                new String[]{"Verdadero", "Falso"}, 1);
    }

    private void crearLecturaElectricidad2(Carrera c) {
        String texto = "LA LEY DE OHM Y LA EFICIENCIA EN INSTALACIONES\n\n" +
                "La Ley de Ohm es la regla de oro para cualquier técnico en electricidad. " +
                "Esta ley establece una relación matemática simple y poderosa entre tres magnitudes fundamentales: " +
                "el voltaje, la intensidad de corriente y la resistencia. " +
                "La fórmula se expresa como V igual a I por R, donde V es el voltaje en voltios, I es la intensidad en amperios y R es la resistencia en ohmios.\n\n" +
                "Gracias a esta ley, si conocemos dos de las tres magnitudes, podemos calcular la tercera. " +
                "Por ejemplo, si un motor funciona a doce voltios y tiene una resistencia de cuatro ohmios, " +
                "la corriente que consume será de tres amperios. " +
                "Esto permite al técnico dimensionar correctamente los cables, fusibles y fuentes de alimentación.\n\n" +
                "Uno de los problemas más comunes en instalaciones es la caída de tensión, " +
                "que ocurre cuando los cables son demasiado delgados o demasiado largos. " +
                "Según la Ley de Ohm, una mayor resistencia en el cable significa que parte del voltaje se pierde en él, " +
                "llegando menos energía al receptor. " +
                "Esto reduce el rendimiento de los equipos y puede dañarlos con el tiempo.\n\n" +
                "El multímetro es la herramienta fundamental que permite al técnico medir voltaje, corriente y resistencia " +
                "para verificar los cálculos teóricos con la realidad del circuito. " +
                "Desarrollar un sentido técnico para predecir el comportamiento eléctrico antes de medir " +
                "es lo que distingue a un técnico experto de uno principiante.";
        Lectura l = new Lectura("La Ley de Ohm y la Eficiencia en Instalaciones", texto, c);
        lecturaRepository.save(l);

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
                "La disposición física y eléctrica de los componentes determina el comportamiento de toda la red eléctrica. " +
                "Existen dos formas básicas de conectar los componentes en un circuito: en serie y en paralelo, " +
                "cada una con características muy distintas que el técnico debe conocer para diseñar instalaciones seguras y eficientes.\n\n" +
                "En un circuito en serie, todos los componentes están conectados uno tras otro formando una sola cadena. " +
                "La corriente es la misma en todos los puntos del circuito, pero el voltaje se reparte entre cada receptor. " +
                "La gran desventaja del circuito en serie es que si un componente falla, todo el circuito deja de funcionar. " +
                "Un ejemplo clásico son las antiguas luces navideñas: si una bombilla se quemaba, todas se apagaban.\n\n" +
                "En un circuito en paralelo, cada componente tiene su propia rama conectada directamente a la fuente. " +
                "El voltaje en cada receptor es igual al voltaje de la fuente, y si uno falla, los demás continúan funcionando. " +
                "Esta es la razón por la que las instalaciones domésticas se realizan en paralelo: " +
                "apagar una lámpara no afecta a las demás.\n\n" +
                "Sin embargo, el paralelo tiene un riesgo: al agregar más dispositivos, la corriente total aumenta. " +
                "Si se superan los límites del cableado se produce una sobrecarga que puede causar un incendio. " +
                "Los interruptores y fusibles se colocan en serie con cada rama para proteger el sistema. " +
                "Conocer cuándo usar serie o paralelo es una decisión técnica fundamental en cualquier instalación eléctrica.";
        Lectura l = new Lectura("Arquitectura de Circuitos: Serie y Paralelo", texto, c);
        lecturaRepository.save(l);

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
                "La historia de la electricidad está marcada por una de las disputas tecnológicas más famosas del siglo diecinueve: " +
                "la competencia entre la Corriente Continua y la Corriente Alterna. " +
                "Thomas Edison defendía la Corriente Continua, mientras que Nikola Tesla y George Westinghouse apostaban por la Corriente Alterna. " +
                "Esta batalla, conocida como la Guerra de las Corrientes, cambió para siempre la forma en que la electricidad llegó a los hogares.\n\n" +
                "La Corriente Continua fluye siempre en la misma dirección. Las baterías y los paneles solares producen este tipo de corriente. " +
                "Su mayor desventaja es que no puede transportarse eficientemente a largas distancias sin grandes pérdidas de energía.\n\n" +
                "La Corriente Alterna, en cambio, cambia de dirección muchas veces por segundo. " +
                "Su frecuencia se mide en Hertz. En Guatemala y gran parte de América la frecuencia estándar es de sesenta hercios. " +
                "La ventaja clave de la Corriente Alterna es que puede transformarse fácilmente a voltajes muy altos para el transporte " +
                "y luego reducirse al voltaje de uso doméstico mediante transformadores, reduciendo enormemente las pérdidas por calor.\n\n" +
                "Hoy vivimos en un mundo híbrido: la Corriente Alterna se usa para el transporte y distribución de la energía, " +
                "mientras que la Corriente Continua se usa internamente en casi todos los dispositivos electrónicos. " +
                "Los cargadores de celulares, las computadoras y los televisores convierten la Corriente Alterna de la red en Corriente Continua " +
                "mediante circuitos rectificadores. Tesla y Edison tenían razón en contextos distintos.";
        Lectura l = new Lectura("La Guerra de las Corrientes: CA versus CC", texto, c);
        lecturaRepository.save(l);

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
        String texto = "Seguridad Eléctrica: La Prevención como Herramienta Técnica\n\n" +
                "En electricidad, el error suele pagarse caro. El riesgo eléctrico no es solo el choque directo por contacto con un cable energizado. " +
                "También incluye los arcos eléctricos, que pueden ocurrir sin contacto físico y generan temperaturas extremas, " +
                "los incendios por sobrecalentamiento de cables y los gases tóxicos liberados al quemar aislamientos.\n\n" +
                "El cuerpo humano es un conductor de electricidad. " +
                "La corriente busca siempre el camino de menor resistencia para llegar a tierra, " +
                "y si ese camino pasa por una persona, las consecuencias pueden ser fatales. " +
                "Por eso la puesta a tierra es fundamental: proporciona un camino seguro para que las corrientes de falla fluyan al suelo " +
                "sin pasar por las personas ni dañar los equipos.\n\n" +
                "Todo técnico en electricidad debe conocer y aplicar las Cinco Reglas de Oro antes de intervenir cualquier instalación: " +
                "desconectar la fuente de energía, bloquear los dispositivos de mando para que nadie los reactive, " +
                "verificar la ausencia de tensión con un instrumento de medición, " +
                "poner a tierra y en cortocircuito la instalación, y delimitar y señalizar la zona de trabajo.\n\n" +
                "El equipo de protección personal es igualmente esencial: guantes dieléctricos, calzado aislante, casco y lentes de seguridad. " +
                "Un técnico que trabaja sin protección, por comodidad o exceso de confianza, pone en riesgo su vida. " +
                "En caso de incendio eléctrico, nunca se debe usar agua, pues conduce la electricidad. " +
                "Se deben usar extintores de tipo C especialmente diseñados para fuegos eléctricos. " +
                "La prevención es la herramienta más importante del electricista.";
        Lectura l = new Lectura("Seguridad Eléctrica: La Prevención como Herramienta Técnica", texto, c);
        lecturaRepository.save(l);

        agregarPreguntaVF(l, "¿Cuál es el objetivo principal de la \"puesta a tierra\"?",
                new String[]{"Ahorrar energía eléctrica", "Desviar corrientes de falla hacia el suelo", "Hacer que los equipos pesen más", "Mejorar la señal de radio"}, 1);
        agregarPreguntaVF(l, "El calzado de seguridad para un electricista debe ser:",
                new String[]{"Con punta de acero descubierta", "Dieléctrico (aislante)", "De tela cómoda", "Sandalias de goma"}, 1);
        agregarPreguntaVF(l, "Las \"Cinco Reglas de Oro\" son sugerencias opcionales para ganar tiempo.",
                new String[]{"Verdadero", "Falso"}, 1);
        agregarPreguntaVF(l, "El cuerpo humano es un buen conductor de electricidad.",
                new String[]{"Verdadero", "Falso"}, 0);
        agregarPreguntaVF(l, "¿Por qué es fundamental \"verificar la ausencia de tensión\" antes de tocar un cable, aunque ya hayamos bajado el interruptor?",
                new String[]{"Porque el interruptor podría estar defectuoso", "Porque el cable podría tener carga estática o retorno", "Para cumplir con el protocolo legal", "Todas las anteriores son correctas"}, 3);
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

    // ==================== CULTURA GENERAL ====================

    private void crearLecturaRevolucionIndustrial(Carrera c) {
        String texto = "La Revolución Industrial: El Cambio de Paradigma Global\n\n" +
                "La Revolución Industrial es considerada por los historiadores como el cambio más radical en la vida cotidiana de la humanidad desde la invención de la agricultura en el Neolítico. " +
                "Este proceso, que comenzó en Inglaterra aproximadamente en 1760 y se extendió hasta mediados del siglo XIX, no fue simplemente una acumulación de inventos, sino una reconfiguración total de la economía, la estructura social y la relación del ser humano con la naturaleza. " +
                "Antes de este periodo, el mundo era fundamentalmente agrario y artesanal; las herramientas eran accionadas por la mano del hombre, por animales o por fuerzas naturales limitadas como el viento o el agua.\n\n" +
                "El epicentro de esta transformación fue la sustitución de la energía orgánica por la energía inorgánica. El perfeccionamiento de la máquina de vapor por James Watt en 1769 permitió convertir la energía térmica del carbón en energía mecánica de movimiento. " +
                "Esto eliminó la necesidad de ubicar las industrias cerca de corrientes de agua, permitiendo que las fábricas se establecieran en los centros urbanos. La industria textil fue la pionera, donde máquinas como la \"Spinning Jenny\" de James Hargreaves multiplicaron exponencialmente la capacidad de producción.\n\n" +
                "A medida que la industrialización avanzaba, la demanda de hierro y carbón impulsó la minería y la metalurgia. Esto llevó al desarrollo del ferrocarril. La locomotora a vapor, ideada por George Stephenson, permitió que las materias primas y los productos terminados viajaran a velocidades antes inimaginables, unificando mercados nacionales.\n\n" +
                "Socialmente, la Revolución Industrial provocó un éxodo rural masivo. Las ciudades no estaban preparadas, resultando en barrios obreros hacinados, condiciones de insalubridad y jornadas extenuantes de hasta 16 horas diarias. El trabajo infantil era común.\n\n" +
                "Este contexto de explotación dio origen a la \"cuestión social\". Surgieron los primeros sindicatos (Trade Unions) y corrientes como el socialismo utópico y el marxismo. " +
                "Hoy en día, la humanidad se encuentra en la transición hacia energías limpias, intentando corregir el impacto ambiental que inició precisamente con el humo de las chimeneas en la Inglaterra del siglo XVIII.";
        Lectura l = new Lectura("La Revolución Industrial: El Cambio de Paradigma Global", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cuál fue el invento que permitió a las fábricas establecerse en las ciudades, lejos de los ríos?",
                new String[]{"La locomotora a vapor de Stephenson.",
                        "La máquina de vapor de James Watt.",
                        "El motor de combustión interna.",
                        "La hiladora \"Spinning Jenny\"."}, 1);
        agregarPregunta(l, "Según el texto, ¿en qué país y periodo inició este proceso de transformación?",
                new String[]{"En Francia a finales del siglo XIX.",
                        "En Alemania durante el año 1760.",
                        "En Inglaterra a mediados del siglo XVIII.",
                        "En Estados Unidos tras la Guerra Civil."}, 2);
        agregarPregunta(l, "¿Qué industria fue la pionera en implementar procesos mecanizados durante este periodo?",
                new String[]{"La siderurgia y el hierro.",
                        "La minería de carbón mineral.",
                        "La industria del transporte.",
                        "La industria textil."}, 3);
        agregarPregunta(l, "¿Cuál fue la consecuencia directa de la invención de la locomotora a vapor?",
                new String[]{"La unificación de mercados nacionales.",
                        "La reducción del trabajo en las minas.",
                        "El fin del uso de combustibles fósiles.",
                        "El aumento de la producción artesanal."}, 0);
        agregarPregunta(l, "¿A qué se refiere el autor con el término \"energía inorgánica\"?",
                new String[]{"Al uso de fertilizantes químicos.",
                        "Al calor generado por el sol.",
                        "Al uso del carbón y el vapor.",
                        "A la fuerza de los animales."}, 2);
        agregarPregunta(l, "¿Por qué el ferrocarril cambió la percepción humana del tiempo?",
                new String[]{"Porque las personas podían dormir más.",
                        "Por la necesidad de estandarizar horarios.",
                        "Porque eliminó el uso de los calendarios.",
                        "Por la gran cantidad de accidentes que había."}, 1);
        agregarPregunta(l, "¿Qué factor facilitó que los niños fueran empleados en minas y telares?",
                new String[]{"Su capacidad de liderazgo y disciplina.",
                        "El hecho de poseer un tamaño pequeño.",
                        "Su gran conocimiento sobre las máquinas.",
                        "El deseo de aprender un oficio nuevo."}, 1);
        agregarPregunta(l, "¿Cuál fue la relación entre el éxodo rural y las condiciones de salud en las ciudades?",
                new String[]{"Las ciudades se volvieron más limpias.",
                        "El hacinamiento provocó enfermedades.",
                        "Los campesinos trajeron medicinas nuevas.",
                        "El aire puro del campo llegó a las fábricas."}, 1);
        agregarPregunta(l, "Basándote en el texto, ¿qué ideologías nacieron como respuesta a la \"cuestión social\"?",
                new String[]{"El capitalismo y el liberalismo.",
                        "El socialismo y el marxismo.",
                        "El absolutismo y el feudalismo.",
                        "El catolicismo y el protestantismo."}, 1);
        agregarPregunta(l, "¿Cuál es el planteamiento final del autor respecto al impacto ambiental de la Revolución Industrial?",
                new String[]{"Que las chimeneas no dañan el ambiente.",
                        "Que el humo industrial era señal de éxito.",
                        "Que hoy intentamos corregir ese daño.",
                        "Que el carbón es la mejor energía hoy."}, 2);
    }

    private void crearLecturaSegundaGuerraMundial(Carrera c) {
        String texto = "La Segunda Guerra Mundial: El Conflicto que Redibujó el Planeta\n\n" +
                "La Segunda Guerra Mundial (1939-1945) no fue solo la mayor contienda bélica de la historia, sino el evento que definió la estructura geopolítica, tecnológica y ética del mundo contemporáneo. " +
                "El conflicto se desencadenó formalmente el 1 de septiembre de 1939, cuando las tropas de la Alemania nazi, bajo el mando de Adolf Hitler, invadieron Polonia. " +
                "Este acto obligó a las potencias democráticas, como Francia y el Reino Unido, a declarar la guerra al Tercer Reich.\n\n" +
                "Se consolidaron dos bandos: las Potencias del Eje (Alemania, Italia y Japón) y los Aliados (Reino Unido, Unión Soviética y Estados Unidos). " +
                "Fue una \"guerra total\", donde las naciones movilizaron todos sus recursos. La ciencia se puso al servicio de la destrucción, acelerando el desarrollo de motores a reacción, sistemas de radar y la energía nuclear.\n\n" +
                "El Holocausto fue uno de los capítulos más atroces. El régimen nazi implementó la \"Solución Final\", un sistema industrializado de exterminio que resultó en el asesinato de seis millones de judíos. " +
                "Este horror dio paso a la redacción de la Declaración Universal de los Derechos Humanos. La guerra en el Pacífico culminó en agosto de 1945 con el uso de bombas atómicas sobre Hiroshima y Nagasaki.\n\n" +
                "El conflicto finalizó con la rendición incondicional de Alemania en mayo de 1945 y de Japón en septiembre. " +
                "Europa perdió su papel como centro del poder mundial, dando paso a una bipolaridad entre Estados Unidos y la Unión Soviética, conocida como la Guerra Fría. " +
                "Además, se fundó la Organización de las Naciones Unidas (ONU) con el objetivo de evitar que una tragedia de tal magnitud volviera a repetirse.";
        Lectura l = new Lectura("La Segunda Guerra Mundial: El Conflicto que Redibujó el Planeta", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Qué evento marcó el inicio formal de la Segunda Guerra Mundial?",
                new String[]{"El bombardeo a Pearl Harbor",
                        "La invasión nazi a Polonia",
                        "La creación de las Naciones Unidas",
                        "El ascenso de los regímenes totalitarios"}, 1);
        agregarPregunta(l, "¿Qué nombre recibió la alianza formada por Alemania, Italia y Japón?",
                new String[]{"Los Aliados",
                        "Las Potencias del Eje",
                        "La Sociedad de Naciones",
                        "El Bloque Soviético"}, 1);
        agregarPregunta(l, "¿En qué año se dio por finalizado este conflicto global?",
                new String[]{"1939", "1941", "1945", "1950"}, 2);
        agregarPregunta(l, "¿Qué término describe el uso de todos los recursos de una nación para el esfuerzo bélico?",
                new String[]{"Guerra Fría", "Guerra Relámpago", "Guerra Total", "Guerra Nuclear"}, 2);
        agregarPregunta(l, "¿Cuál fue el principal objetivo de fundar la Organización de las Naciones Unidas (ONU)?",
                new String[]{"Dividir el territorio alemán",
                        "Fomentar la energía nuclear",
                        "Evitar guerras mundiales",
                        "Castigar a los países derrotados"}, 2);
        agregarPregunta(l, "¿Qué consecuencia inmediata tuvo el uso de bombas atómicas en Hiroshima y Nagasaki?",
                new String[]{"El inicio del Holocausto",
                        "La rendición de Japón",
                        "La invasión de Polonia",
                        "El fin del Proyecto Manhattan"}, 1);
        agregarPregunta(l, "¿Qué documento internacional surgió como respuesta ética a los horrores del Holocausto?",
                new String[]{"El Tratado de Versalles",
                        "El Código de Trabajo",
                        "Los Derechos Humanos",
                        "La Constitución de 1945"}, 2);
        agregarPregunta(l, "Se puede inferir que la \"guerra total\" afectó principalmente a:",
                new String[]{"Solo a los soldados",
                        "Exclusivamente a Alemania",
                        "A toda la población civil",
                        "Únicamente a los científicos"}, 2);
        agregarPregunta(l, "Según la lectura, ¿qué provocó el quiebre de la alianza entre los vencedores tras la guerra?",
                new String[]{"Una crisis económica",
                        "Rivalidad ideológica",
                        "Falta de armamento",
                        "La muerte de los líderes"}, 1);
        agregarPregunta(l, "¿Cuál fue el nuevo orden geopolítico que dominó el mundo después de 1945?",
                new String[]{"El Imperio Británico",
                        "El Colonialismo Europeo",
                        "La Guerra Fría",
                        "La Revolución Industrial"}, 2);
    }

    private void crearLecturaMuroBerlin(Carrera c) {
        String texto = "La Caída del Muro de Berlín: El Fin de la Cortina de Hierro\n\n" +
                "Para comprender la trascendencia de la caída del Muro de Berlín, es imperativo retroceder al final de la Segunda Guerra Mundial. " +
                "Tras la derrota de la Alemania nazi en 1945, el territorio alemán fue dividido en cuatro zonas de ocupación. " +
                "En 1949, Alemania se dividió formalmente en dos Estados: la República Federal de Alemania (RFA) en el oeste y la República Democrática Alemana (RDA) en el este.\n\n" +
                "Berlín, la capital, también fue dividida. Durante los años 50, miles de personas huían diariamente hacia el lado occidental. " +
                "Para detener esta fuga, las autoridades de la RDA iniciaron la construcción del muro la madrugada del 13 de agosto de 1961. " +
                "Lo que comenzó como una alambrada se convirtió en una barrera de hormigón de casi cuatro metros de altura, protegida por fosos, perros y la temida \"franja de la muerte\".\n\n" +
                "Durante 28 años, el muro dividió familias. Se estima que al menos 140 personas murieron intentando atravesarlo. " +
                "Hacia finales de la década de 1980, el bloque soviético comenzó a debilitarse. El líder soviético Mijaíl Gorbachov impulsó reformas conocidas como Glasnost y Perestroika.\n\n" +
                "El colapso definitivo ocurrió el 9 de noviembre de 1989 debido a un error burocrático histórico. " +
                "El portavoz Günter Schabowski anunció apresuradamente que las restricciones de viaje se levantarían \"de inmediato\". " +
                "Aquella noche, ciudadanos de ambos lados se fundieron en abrazos sobre el muro, mientras comenzaban a derribarlo.\n\n" +
                "La caída del muro fue el preámbulo de la reunificación de Alemania en 1990 y la posterior disolución de la Unión Soviética en 1991. " +
                "Hoy, es el símbolo universal de la caída de los autoritarismos y el triunfo de la democracia moderna.";
        Lectura l = new Lectura("La Caída del Muro de Berlín: El Fin de la Cortina de Hierro", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Qué evento histórico dio origen a la división de Alemania en dos Estados?",
                new String[]{"La Revolución Industrial",
                        "La Guerra Fría",
                        "La caída del muro",
                        "El Tratado de Versalles"}, 1);
        agregarPregunta(l, "¿En qué fecha se inició la construcción del muro para detener la fuga de ciudadanos?",
                new String[]{"9 de noviembre de 1989",
                        "13 de agosto de 1961",
                        "1 de septiembre de 1939",
                        "20 de octubre de 1944"}, 1);
        agregarPregunta(l, "¿Qué nombre recibía la zona de máxima vigilancia que rodeaba el muro?",
                new String[]{"La Cortina de Hierro",
                        "El Bloque del Este",
                        "La franja de la muerte",
                        "El puesto de control"}, 2);
        agregarPregunta(l, "¿Quién fue el líder soviético que impulsó las reformas Glasnost y Perestroika?",
                new String[]{"Adolf Hitler",
                        "Günter Schabowski",
                        "Mijaíl Gorbachov",
                        "Jacobo Árbenz"}, 2);
        agregarPregunta(l, "¿A qué se debió principalmente el colapso definitivo del muro el 9 de noviembre?",
                new String[]{"Una invasión militar",
                        "Un error burocrático",
                        "Un desastre natural",
                        "Una orden de la ONU"}, 1);
        agregarPregunta(l, "¿Quién fue el portavoz que anunció erróneamente la apertura inmediata de las fronteras?",
                new String[]{"James Watt",
                        "Jorge Ubico",
                        "Günter Schabowski",
                        "George Stephenson"}, 2);
        agregarPregunta(l, "Según el texto, ¿qué simbolizó a nivel mundial la caída de esta estructura?",
                new String[]{"El inicio de la guerra",
                        "El fin del capitalismo",
                        "El triunfo democrático",
                        "La creación de la URSS"}, 2);
        agregarPregunta(l, "Se puede inferir que la principal motivación de la RDA para construir el muro fue:",
                new String[]{"Fomentar el turismo",
                        "Evitar el espionaje",
                        "Controlar a su población",
                        "Embellecer la ciudad"}, 2);
        agregarPregunta(l, "¿Qué acontecimiento importante ocurrió un año después de la caída del muro, en 1990?",
                new String[]{"La Revolución de Octubre",
                        "La reunificación alemana",
                        "El inicio de la Guerra Fría",
                        "El terremoto de Berlín"}, 1);
        agregarPregunta(l, "A partir de la lectura, ¿cuál es el significado simbólico actual del muro de Berlín?",
                new String[]{"Un éxito de ingeniería",
                        "Fracaso del autoritarismo",
                        "Símbolo de la arquitectura",
                        "Un monumento a la guerra"}, 1);
    }

    private void crearLecturaRevolucion1944(Carrera c) {
        String texto = "La Revolución de octubre de 1944: La Primavera Democrática de Guatemala\n\n" +
                "Para dimensionar la importancia de la Revolución de 1944, es fundamental analizar el contexto de opresión que la precedió. " +
                "Durante 14 años, el general Jorge Ubico Castañeda gobernó Guatemala con mano de hierro. " +
                "Su administración se caracterizó por la aplicación de la \"Ley de Vagancia\", que obligaba a los campesinos e indígenas a trabajar gratuitamente si no podían demostrar un número mínimo de días laborados al año. " +
                "Además, Ubico otorgó concesiones extraordinarias a la United Fruit Company (UFCO).\n\n" +
                "El descontento social comenzó en junio de 1944 con manifestaciones de maestros y estudiantes universitarios. " +
                "La maestra María Chinchilla fue asesinada por las fuerzas del orden, convirtiéndose en un símbolo de la lucha civil. " +
                "Ubico renunció el 1 de julio, pero dejó en su lugar al general Federico Ponce Vaides, quien intentó perpetuar la dictadura.\n\n" +
                "La madrugada del 20 de octubre de 1944 cambió el rumbo del país. El capitán Jacobo Árbenz Guzmán y el mayor Francisco Javier Arana, junto con Jorge Toriello Garrido, lograron la rendición de Ponce Vaides. " +
                "Se instauró la Junta Revolucionaria de Gobierno, que convocó las primeras elecciones libres, resultando electo el Dr. Juan José Arévalo Bermejo.\n\n" +
                "El periodo de diez años que siguió se conoce como la \"Década de la Primavera\". Se redactó la Constitución de 1945, se fundó el IGSS, se creó el Comité Nacional de Alfabetización y se promulgó el Código de Trabajo en 1947, " +
                "que estableció por primera vez el derecho al salario mínimo, las vacaciones pagadas, el descanso semanal y la protección para las mujeres trabajadoras.\n\n" +
                "El proyecto revolucionario alcanzó su punto más ambicioso con el Decreto 900 o Ley de Reforma Agraria de Árbenz, que buscaba redistribuir las tierras ociosas. " +
                "Esto provocó que en 1954 una intervención orquestada por la CIA derrocara el gobierno democrático. " +
                "A pesar de su interrupción, la Revolución de 1944 dejó un legado de instituciones que hoy son pilares del Estado guatemalteco.";
        Lectura l = new Lectura("La Revolución de octubre de 1944: La Primavera Democrática de Guatemala", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Cuántos años duró la dictadura del general Jorge Ubico antes de la Revolución?",
                new String[]{"5 años", "10 años", "14 años", "20 años"}, 2);
        agregarPregunta(l, "¿Qué ley obligaba a la población indígena al trabajo forzado durante la dictadura?",
                new String[]{"Ley de Educación", "Ley contra la Vagancia", "Código de Trabajo", "Ley de Seguridad Social"}, 1);
        agregarPregunta(l, "¿Qué figura histórica falleció durante las protestas de junio de 1944?",
                new String[]{"María Chinchilla", "Jacobo Árbenz", "Jorge Ubico", "Ponce Vaides"}, 0);
        agregarPregunta(l, "¿En qué fecha se consolidó el movimiento cívico-militar que derrocó al sucesor de Ubico?",
                new String[]{"25 de junio", "1 de julio", "20 de octubre", "29 de diciembre"}, 2);
        agregarPregunta(l, "¿Cuál fue uno de los logros sociales más importantes creados en este periodo?",
                new String[]{"El Palacio Nacional", "El IGSS", "La UFCO", "El Ferrocarril"}, 1);
        agregarPregunta(l, "¿Qué derecho civil fue otorgado por primera vez a mujeres y analfabetos en 1945?",
                new String[]{"Derecho al trabajo", "Derecho al voto", "Derecho a la salud", "Derecho a viajar"}, 1);
        agregarPregunta(l, "Según el texto, ¿qué institución educativa obtuvo su autonomía en esta época?",
                new String[]{"Ministerio de Educación", "Universidad de San Carlos", "Comité de Alfabetización", "Escuela Normal Central"}, 1);
        agregarPregunta(l, "Se puede inferir que Ponce Vaides no fue aceptado por la población porque:",
                new String[]{"No era militar", "Era extranjero", "Seguía con la dictadura", "No hablaba español"}, 2);
        agregarPregunta(l, "¿Cuál de estos beneficios laborales aparece por primera vez en el Código de Trabajo de 1947?",
                new String[]{"Trabajo sin pago", "Jubilación privada", "El salario mínimo", "Seguro de accidentes"}, 2);
        agregarPregunta(l, "¿Qué representa la Revolución de 1944 para la Guatemala actual?",
                new String[]{"Un evento sin importancia", "La base de la ley social", "Un conflicto sin sentido", "Un simple cambio de líderes"}, 1);
    }

    private void crearLecturaAcuerdosPaz(Carrera c) {
        String texto = "La Firma de los Acuerdos de Paz: El Camino hacia la Reconciliación\n\n" +
                "Guatemala atravesó uno de los periodos más oscuros de su historia republicana entre 1960 y 1996: el Conflicto Armado Interno. " +
                "Esta guerra civil, que duró 36 años, enfrentó al Estado guatemalteco contra grupos insurgentes aglutinados en la Unidad Revolucionaria Nacional Guatemalteca (URNG). " +
                "La confrontación dejó un saldo devastador de más de 200,000 víctimas, entre muertos y desaparecidos, y fracturó el tejido social, especialmente entre las poblaciones indígenas, que sufrieron políticas de \"tierra arrasada\".\n\n" +
                "El proceso para alcanzar la paz no fue sencillo. Las negociaciones formales comenzaron a finales de la década de 1980. " +
                "Países como México, Noruega y España sirvieron como sedes y facilitadores del diálogo. " +
                "Se firmaron acuerdos parciales que abordaban la democratización, los derechos humanos, el reasentamiento de las poblaciones desarraigadas y la identidad y derechos de los pueblos indígenas.\n\n" +
                "El momento culminante ocurrió el 29 de diciembre de 1996, en el Patio de la Paz del Palacio Nacional de la Cultura. " +
                "Se firmó el \"Acuerdo de Paz Firme y Duradera\", que puso fin oficial a las hostilidades. " +
                "Este documento buscaba transformar las causas que originaron la guerra: la exclusión social, la falta de espacios políticos democráticos y la desigualdad económica.\n\n" +
                "A tres décadas de aquel acontecimiento, los Acuerdos de Paz siguen siendo el norte jurídico y moral de Guatemala, aunque su cumplimiento pleno sigue siendo un reto pendiente. " +
                "Para un ciudadano, conocer este proceso es fundamental para valorar la democracia y entender que la paz no es simplemente la ausencia de conflicto, sino la presencia de justicia y oportunidades para todos.";
        Lectura l = new Lectura("La Firma de los Acuerdos de Paz: El Camino hacia la Reconciliación", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿En qué fecha se firmó el Acuerdo de Paz Firme y Duradera en Guatemala?",
                new String[]{"20 de octubre de 1944",
                        "29 de diciembre de 1996",
                        "4 de febrero de 1976",
                        "10 de mayo de 1985"}, 1);
        agregarPregunta(l, "¿Cuántos años duró el Conflicto Armado Interno en el país?",
                new String[]{"10 años", "20 años", "36 años", "50 años"}, 2);
        agregarPregunta(l, "¿Qué instituciones o grupos se enfrentaron durante la guerra civil?",
                new String[]{"La USAC y el Gobierno",
                        "El Ejército y la URNG",
                        "La ONU y los civiles",
                        "México y Guatemala"}, 1);
        agregarPregunta(l, "¿Cuál de estos países fue sede de las negociaciones de paz?",
                new String[]{"Estados Unidos", "México", "Brasil", "Alemania"}, 1);
        agregarPregunta(l, "¿Cómo define el texto la paz en su sentido más profundo?",
                new String[]{"La firma de documentos",
                        "El perdón total",
                        "El cese al fuego",
                        "La presencia de justicia y oportunidades"}, 3);
        agregarPregunta(l, "¿Qué acuerdo reconoció a Guatemala como una nación multiétnica y pluricultural?",
                new String[]{"Acuerdo de Reasentamiento",
                        "Acuerdo de Poder Civil",
                        "Acuerdo sobre Pueblos Indígenas",
                        "Acuerdo de Reformas Agrarias"}, 2);
        agregarPregunta(l, "Según la lectura, ¿qué organización actuó como moderadora en el proceso de paz?",
                new String[]{"El Grupo de Contadora",
                        "La Cruz Roja",
                        "Las Naciones Unidas",
                        "El Gobierno de España"}, 2);
        agregarPregunta(l, "Se puede inferir que los acuerdos no solo buscaban detener las balas, sino también:",
                new String[]{"Cambiar el nombre del país",
                        "Resolver causas sociales",
                        "Castigar a los estudiantes",
                        "Aumentar los impuestos"}, 1);
        agregarPregunta(l, "¿Cuál es uno de los mayores desafíos actuales respecto a los acuerdos?",
                new String[]{"El olvido de las fechas",
                        "Su cumplimiento lento",
                        "La falta de copias físicas",
                        "El cambio de moneda"}, 1);
        agregarPregunta(l, "¿Cuál es la función principal de los Acuerdos de Paz en la actualidad?",
                new String[]{"Servir como hoja de ruta",
                        "Organizar las elecciones",
                        "Controlar al ejército",
                        "Promover el turismo"}, 0);
    }

    private void crearLecturaTerremoto1976(Carrera c) {
        String texto = "El Terremoto de 1976: La Sacudida que Transformó a Guatemala\n\n" +
                "La madrugada del 4 de febrero de 1976 quedó grabada en la memoria colectiva de Guatemala. " +
                "A las 3:01 a.m., se produjo una ruptura en la falla del Motagua, el límite tectónico que separa las placas de Norteamérica y del Caribe. " +
                "El sismo tuvo una magnitud de 7.5 grados en la escala de Richter y una duración aproximada de 39 segundos.\n\n" +
                "El epicentro se localizó cerca de Los Amates, en el departamento de Izabal, pero el impacto más severo se sintió en el altiplano central y la Ciudad de Guatemala. " +
                "Las cifras oficiales reflejaron la magnitud del desastre: aproximadamente 23,000 personas fallecidas, 77,000 heridos y más de un millón de personas que perdieron sus hogares.\n\n" +
                "Uno de los aspectos más reveladores fue su carácter social, lo que llevó a muchos analistas a llamarlo \"el terremoto de los pobres\". " +
                "La gran mayoría de las muertes ocurrieron en los barrios marginales y en los pueblos del área rural, donde las viviendas estaban construidas de adobe y techos de teja pesada.\n\n" +
                "La respuesta ante la emergencia fue un hito de solidaridad. Bajo el lema del presidente Kjell Eugenio Laugerud García: \"Guatemala está herida, pero no de muerte\", se inició un proceso de reconstrucción sin precedentes. " +
                "La sociedad civil se organizó en comités locales, fortaleciendo el movimiento cooperativista. " +
                "La ayuda internacional introdujo en el país el uso de materiales más ligeros y sismorresistentes.\n\n" +
                "A largo plazo, el terremoto cambió la fisonomía de la capital y se crearon instituciones de prevención como el CONE (antecesor de la actual CONRED). " +
                "Hoy, el estudio de este evento es fundamental para la gestión de riesgos, recordándonos que la prevención y la planificación urbana son las únicas herramientas reales para enfrentar la fuerza de la naturaleza.";
        Lectura l = new Lectura("El Terremoto de 1976: La Sacudida que Transformó a Guatemala", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿A qué hora exacta ocurrió el terremoto del 4 de febrero de 1976?",
                new String[]{"A las 12:00 mediodía",
                        "A las 3:01 de la madrugada",
                        "A las 6:15 de la tarde",
                        "A las 11:45 de la noche"}, 1);
        agregarPregunta(l, "¿Cuál fue la falla geológica responsable de activar el sismo?",
                new String[]{"Falla de San Andrés",
                        "Falla del Motagua",
                        "Falla de Jalpatagua",
                        "Falla de Mixco"}, 1);
        agregarPregunta(l, "¿Cuál fue la magnitud del terremoto en la escala de Richter?",
                new String[]{"6.5 grados", "7.0 grados", "7.5 grados", "8.2 grados"}, 2);
        agregarPregunta(l, "¿Cuál fue el saldo aproximado de personas fallecidas según el texto?",
                new String[]{"5,000 personas", "15,000 personas", "23,000 personas", "50,000 personas"}, 2);
        agregarPregunta(l, "¿Qué material de construcción predominante en la época fue responsable de muchos daños?",
                new String[]{"El acero", "El ladrillo", "El adobe", "El block"}, 2);
        agregarPregunta(l, "¿A qué se refiere el texto con la expresión \"el terremoto de los pobres\"?",
                new String[]{"A que nadie tenía dinero",
                        "Al daño desproporcionado en áreas vulnerables",
                        "A que no afectó a los ricos",
                        "Al bajo costo de reconstrucción"}, 1);
        agregarPregunta(l, "¿Qué cambio importante se dio en la construcción tras este desastre?",
                new String[]{"Se dejó de construir casas",
                        "Se usó solo madera",
                        "Se adoptaron sistemas sismorresistentes",
                        "Se prohibieron los edificios"}, 2);
        agregarPregunta(l, "Se puede inferir que el terremoto no solo fue una crisis natural, sino también:",
                new String[]{"Una crisis política",
                        "Una fiesta nacional",
                        "Un evento sin importancia",
                        "Una crisis social"}, 3);
        agregarPregunta(l, "Según la lectura, ¿cuál fue la reacción de la sociedad ante la limitada capacidad del Estado?",
                new String[]{"Abandonar el país",
                        "Organización autónoma en comités",
                        "Esperar la ayuda sentados",
                        "Dejar de trabajar"}, 1);
        agregarPregunta(l, "¿Qué valor humano destaca el texto como resultado de la tragedia?",
                new String[]{"La avaricia", "El egoísmo", "La resiliencia", "La indiferencia"}, 2);
    }

    // ==================== FANTASÍA (CUENTOS, FÁBULAS Y LEYENDAS DE GUATEMALA) ====================

    private void crearCuentoSombrerónGuatemala(Carrera c) {
        String texto = "CUENTO: EL SOMBRERÓM Y LA NIÑA DE LAS TRENZAS\n\n" +
                "En un pequeño pueblo del altiplano guatemalteco, rodeado de pinos y montañas que parecían rozar el cielo, vivía una joven llamada Ixchel. " +
                "Era conocida en toda la aldea por su hermosa cabellera negra y brillante, que trenzaba cada mañana con listones de colores vivos. " +
                "Su abuela siempre le advertía: \"Niña, no salgas a la calle con el cabello suelto cuando cae la tarde, porque el Sombrerón podría fijarse en ti.\" " +
                "Pero Ixchel, curiosa y valiente, no prestaba mucha atención a esas palabras.\n\n" +
                "Una tarde de noviembre, mientras Ixchel volvía del mercado con una canasta de elotes y güisquiles, escuchó el tintineo de campanillas y el galope suave de un caballo. " +
                "Al voltear, vio a un hombre diminuto montado sobre un corcel negro, con un sombrero enorme que casi le tapaba la cara y una guitarra colgada al hombro. " +
                "El hombrecito la miró y sonrió, y Ixchel sintió que sus pies se volvían de piedra.\n\n" +
                "Aquella noche, el Sombrerón comenzó a rondar la casa de Ixchel. Cantaba canciones dulces y melancólicas bajo la ventana, y los caballos del vecino relinchaban inquietos. " +
                "Ixchel no podía dormir; la música la llenaba de una tristeza extraña que no podía explicar. " +
                "Su abuela, al darse cuenta de lo que pasaba, actuó de inmediato. Tomó sal gruesa y la esparció en el umbral de la puerta, colocó ramos de ruda en las ventanas y encendió una vela bendita. " +
                "Luego llamó al sacerdote del pueblo para que rezara en la casa.\n\n" +
                "Por tres noches consecutivas, el Sombrerón insistió, pero al ver que las protecciones eran fuertes y que Ixchel permanecía en casa rezando, finalmente se alejó hacia las montañas en busca de otro lugar. " +
                "Ixchel nunca volvió a salir con el cabello suelto al atardecer. Y cada vez que escuchaba a lo lejos el tintineo de campanillas, daba gracias a su abuela por haberla enseñado a respetar las tradiciones de su pueblo.\n\n" +
                "MORALEJA: Las tradiciones de nuestros ancestros no son solo supersticiones; muchas veces encierran la sabiduría de generaciones que aprendieron a vivir en armonía con el mundo que las rodea.";
        Lectura l = new Lectura("El Sombrerón y la Niña de las Trenzas", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Por qué la abuela le advertía a Ixchel que no saliera con el cabello suelto al atardecer?",
                new String[]{"Porque hacía mucho frío en las tardes.",
                        "Porque el Sombrerón podría fijarse en ella.",
                        "Porque los perros del pueblo se volvían peligrosos.",
                        "Porque era costumbre del pueblo quedarse en casa."}, 1);
        agregarPregunta(l, "¿Qué característica física era más notable en el personaje del Sombrerón?",
                new String[]{"Sus ojos de color rojo y su capa larga.",
                        "Su estatura enorme y su voz grave.",
                        "Su sombrero enorme y su pequeña estatura.",
                        "Su cabello blanco y sus botas de cuero."}, 2);
        agregarPregunta(l, "¿Cómo reaccionó Ixchel al ver al Sombrerón por primera vez?",
                new String[]{"Corrió muy rápido hacia su casa.",
                        "Sintió que sus pies se volvían de piedra.",
                        "Gritó pidiendo auxilio a los vecinos.",
                        "Le lanzó la canasta de verduras."}, 1);
        agregarPregunta(l, "¿Cuál fue el efecto que la música del Sombrerón tenía sobre Ixchel?",
                new String[]{"Le daban ganas de bailar y reír.",
                        "La llenaba de una tristeza extraña que no podía explicar.",
                        "La hacía sentir muy hambrienta.",
                        "Le provocaba un sueño profundo e inmediato."}, 1);
        agregarPregunta(l, "¿Qué elementos utilizó la abuela para proteger la casa del Sombrerón?",
                new String[]{"Agua bendita, flores y música de marimba.",
                        "Sal gruesa, ruda y una vela bendita.",
                        "Copal, barro y plantas medicinales.",
                        "Candados, cadenas y oraciones en voz alta."}, 1);
        agregarPregunta(l, "¿Por qué el Sombrerón finalmente se alejó de la casa de Ixchel?",
                new String[]{"Porque encontró a otra joven más hermosa en el pueblo.",
                        "Porque las protecciones eran fuertes y Ixchel rezaba.",
                        "Porque el sacerdote lo maldijo y lo convirtió en piedra.",
                        "Porque llegó el amanecer y él debía esconderse del sol."}, 1);
        agregarPregunta(l, "¿En qué región de Guatemala se ambienta este cuento?",
                new String[]{"En la costa del Pacífico, entre palmas y arena.",
                        "En las llanuras del Petén, junto a la selva.",
                        "En el altiplano guatemalteco, rodeado de pinos y montañas.",
                        "En la ciudad capital, en un barrio antiguo."}, 2);
        agregarPregunta(l, "¿Qué cambio de comportamiento tuvo Ixchel al final del cuento?",
                new String[]{"Decidió mudarse a vivir con su abuela en otro pueblo.",
                        "Nunca volvió a salir con el cabello suelto al atardecer.",
                        "Dejó de ir al mercado para no encontrarse con el Sombrerón.",
                        "Comenzó a usar sombrero grande para que no la reconocieran."}, 1);
        agregarPregunta(l, "¿Qué instrumento musical llevaba el Sombrerón consigo?",
                new String[]{"Una flauta de caña.", "Un tambor de cuero.", "Una guitarra.", "Una marimba pequeña."}, 2);
        agregarPregunta(l, "¿Cuál es la enseñanza principal de este cuento guatemalteco?",
                new String[]{"Que los jóvenes no deben salir solos de noche en ningún lugar.",
                        "Que las tradiciones de los ancestros encierran sabiduría.",
                        "Que los sombrereros son personas peligrosas en el altiplano.",
                        "Que la música puede controlar la mente de las personas."}, 1);
    }

    private void crearCuentoXibalbaGuatemala(Carrera c) {
        String texto = "CUENTO: EL NIÑO QUE DESAFIÓ A XIBALBÁ\n\n" +
                "Hace muchos siglos, en las tierras del Petén guatemalteco, vivía un joven llamado Ajmaq, cuyo nombre en maya significaba \"el perdonador\". " +
                "Ajmaq era hijo de un curandero y desde pequeño aprendió que el mundo tenía dos caras: la de la luz, donde vivían los hombres, y la del inframundo, conocido como Xibalbá, donde moraban los señores de la oscuridad.\n\n" +
                "Un día, la hermana menor de Ajmaq enfermó gravemente. Los curanderos de la aldea intentaron todo, pero el mal que tenía la niña parecía venir de más allá de este mundo. " +
                "Su padre, ya muy anciano, le dijo a Ajmaq: \"Solo hay un lugar donde encontrarás la medicina que tu hermana necesita: en el jardín de Xibalbá. " +
                "Pero debes saber que los señores del inframundo solo respetan a quien entra sin miedo y actúa con inteligencia, no con fuerza.\"\n\n" +
                "Ajmaq emprendió el viaje siguiendo el camino de una ceiba sagrada que crecía al borde del río Usumacinta. " +
                "Al llegar a la entrada del inframundo, los señores de Xibalbá lo pusieron a prueba: primero en la Casa Oscura, donde debía mantener encendida una antorcha sin que se consumiera; " +
                "luego en la Casa Fría, donde el viento helado intentaba apagar su voluntad; y finalmente en la Casa del Fuego, donde debía encontrar la flor de vida entre las llamas.\n\n" +
                "Ajmaq recordó los consejos de su padre: usó su inteligencia. En la Casa Oscura colocó una luciérnaga en la punta de la antorcha para que simulara la llama. " +
                "En la Casa Fría, cantó las canciones de su madre que calentaban el alma. En la Casa del Fuego, observó que la flor de vida crecía justo donde el fuego no llegaba, en la sombra que generaba la llama más grande.\n\n" +
                "Los señores de Xibalbá, impresionados por su astucia y valentía, le entregaron la flor de vida y le permitieron regresar. " +
                "Ajmaq volvió a su aldea, preparó la medicina con la flor sagrada y su hermana se curó. " +
                "Desde entonces, se dice que en el Petén, cuando florece la ceiba al amanecer, es porque algún alma valiente ha vencido a los señores de Xibalbá con la fuerza más poderosa que existe: el amor a su familia.\n\n" +
                "MORALEJA: La inteligencia y el amor son las armas más poderosas frente a cualquier adversidad. La fuerza bruta nunca vence donde la astucia del corazón puede triunfar.";
        Lectura l = new Lectura("El Niño que Desafió a Xibalbá", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Qué significado tiene el nombre \"Ajmaq\" en el idioma maya?",
                new String[]{"El guerrero invencible.", "El perdonador.", "El hijo del sol.", "El guardián del bosque."}, 1);
        agregarPregunta(l, "¿Por qué Ajmaq decidió bajar a Xibalbá?",
                new String[]{"Porque quería demostrar que era el más valiente del pueblo.",
                        "Para buscar la medicina que curaría a su hermana enferma.",
                        "Porque los señores de Xibalbá lo llamaron en sueños.",
                        "Para recuperar las herramientas de su padre perdidas."}, 1);
        agregarPregunta(l, "¿Qué consejo le dio su padre antes de emprender el viaje?",
                new String[]{"Que llevara una espada de jade para defenderse.",
                        "Que los señores del inframundo solo respetan inteligencia, no fuerza.",
                        "Que corriera todo el tiempo para que no lo atraparan.",
                        "Que no comiera ni bebiera nada dentro de Xibalbá."}, 1);
        agregarPregunta(l, "¿Cómo resolvió Ajmaq el desafío de la Casa Oscura?",
                new String[]{"Encendiendo una gran fogata con ramas de ceiba.",
                        "Colocando una luciérnaga en la punta de la antorcha.",
                        "Gritando en voz alta para ahuyentar a la oscuridad.",
                        "Usando un espejo para reflejar la luz de la luna."}, 1);
        agregarPregunta(l, "¿Qué hizo Ajmaq para sobrevivir en la Casa Fría?",
                new String[]{"Se cubrió con hojas de maíz y barro del río.",
                        "Cantó las canciones de su madre que calentaban el alma.",
                        "Construyó una hoguera con pedazos de piedra volcánica.",
                        "Corrió sin parar para generar calor en su cuerpo."}, 1);
        agregarPregunta(l, "¿Dónde creció la flor de vida dentro de la Casa del Fuego?",
                new String[]{"En el centro de la llama más alta.",
                        "En el techo de la casa donde no llegaba el calor.",
                        "En la sombra que generaba la llama más grande.",
                        "Debajo de las brasas apagadas del piso."}, 2);
        agregarPregunta(l, "¿Qué árbol sagrado sirvió de guía para encontrar la entrada a Xibalbá?",
                new String[]{"Una palma real del Petén.", "Un aguacate milenario.", "Una ceiba sagrada.", "Un árbol de chicozapote."}, 2);
        agregarPregunta(l, "¿Cuál fue la reacción de los señores de Xibalbá ante las acciones de Ajmaq?",
                new String[]{"Se enojaron mucho y lo encerraron para siempre.",
                        "Quedaron impresionados y le entregaron la flor de vida.",
                        "Le pusieron más pruebas imposibles de superar.",
                        "Lo convirtieron en un murciélago como castigo."}, 1);
        agregarPregunta(l, "¿Qué fenómeno natural se relaciona con la victoria de Ajmaq al final del cuento?",
                new String[]{"La lluvia que cae sobre el Petén en invierno.",
                        "La floración de la ceiba al amanecer.",
                        "El arco iris que aparece sobre el río Usumacinta.",
                        "El rugido del jaguar en las noches de luna llena."}, 1);
        agregarPregunta(l, "¿Cuál es la moraleja principal de este cuento guatemalteco?",
                new String[]{"Que es mejor no meterse en asuntos del inframundo.",
                        "La inteligencia y el amor son las armas más poderosas ante cualquier adversidad.",
                        "Que los jóvenes deben obedecer siempre a los señores mayores.",
                        "Que la fuerza física es el único camino para superar los obstáculos."}, 1);
    }

    private void crearFabulaQuetzalGuatemala(Carrera c) {
        String texto = "FÁBULA: EL QUETZAL Y EL CUERVO ENVIDIOSO\n\n" +
                "En los bosques nubosos de las Verapaces guatemaltecas, donde la neblina abraza los árboles de pino y liquidámbar, vivía el Quetzal, el ave más bella de todas las tierras. " +
                "Sus plumas verdes resplandecían como esmeraldas mojadas y su pecho de un rojo encendido parecía llevar el color del sol del atardecer. " +
                "El Quetzal era admirado por todos los animales del bosque, pero él nunca presumía de su belleza; vivía tranquilo, alimentándose de frutas silvestres y cantando para el viento.\n\n" +
                "En ese mismo bosque habitaba un Cuervo de plumas negras como la noche. El Cuervo era inteligente, hábil para encontrar alimento y excelente constructor de nidos, " +
                "pero vivía atormentado por la envidia que sentía hacia el Quetzal. Un día, el Cuervo tuvo una idea: iría a donde el Quetzal y le robaría algunas de sus plumas para pegárselas y así ser él también admirado.\n\n" +
                "El Cuervo se acercó al Quetzal con engaños. Le dijo que el gran Dios de la montaña lo había llamado para darle un mensaje urgente. " +
                "Mientras el Quetzal lo escuchaba con atención, el Cuervo le arrancó tres plumas largas de la cola. El Quetzal sintió el dolor, pero en lugar de enojarse, miró al Cuervo con serenidad y le dijo:\n\n" +
                "\"Hermano Cuervo, si hubieras pedido mis plumas con honestidad, te las habría dado con gusto. Pero las plumas que robaste no te darán lo que buscas, porque la verdadera belleza no está en el plumaje, sino en el carácter del que lo lleva.\"\n\n" +
                "El Cuervo, avergonzado, se pegó las plumas verdes en las alas y voló ante los otros animales esperando admiración. Pero en lugar de eso, los animales se rieron, porque las plumas pegadas se veían falsas y ridículas sobre el negro del cuervo. " +
                "Humillado, el Cuervo volvió donde el Quetzal a pedir perdón.\n\n" +
                "El Quetzal lo perdonó y le dijo: \"Tu don, Cuervo, es tu inteligencia. Nunca la cambies por la apariencia. Cada ser tiene un regalo único, y solo quien lo cultiva con honradez encontrará el respeto que busca.\"\n\n" +
                "MORALEJA: La envidia y el engaño nunca producen los frutos que prometen. Cada quien debe valorar y desarrollar sus propios dones en lugar de desear los ajenos.";
        Lectura l = new Lectura("El Quetzal y el Cuervo Envidioso", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿En qué región de Guatemala se ambienta esta fábula?",
                new String[]{"En las playas del Pacífico, junto al mar.",
                        "En los bosques nubosos de las Verapaces.",
                        "En las llanuras del oriente guatemalteco.",
                        "En el centro histórico de la ciudad capital."}, 1);
        agregarPregunta(l, "¿Qué cualidad principal tenía el Cuervo que no le era reconocida?",
                new String[]{"Su hermoso plumaje de colores brillantes.",
                        "Su voz melodiosa y su canto hermoso.",
                        "Su inteligencia y habilidad para construir nidos.",
                        "Su generosidad con los otros animales del bosque."}, 2);
        agregarPregunta(l, "¿Cuál era el sentimiento que atormentaba al Cuervo?",
                new String[]{"La soledad por no tener amigos.", "La envidia hacia el Quetzal.", "El miedo a la oscuridad del bosque.", "La tristeza por no saber cantar."}, 1);
        agregarPregunta(l, "¿Cómo engañó el Cuervo al Quetzal para robarle las plumas?",
                new String[]{"Le dijo que había un incendio en el bosque.",
                        "Le inventó que el Dios de la montaña lo llamaba.",
                        "Le ofreció compartir su nido a cambio de plumas.",
                        "Le pidió que cerrara los ojos para escuchar música."}, 1);
        agregarPregunta(l, "¿Cómo reaccionó el Quetzal cuando el Cuervo le arrancó las plumas?",
                new String[]{"Se enojó mucho y llamó a todos los animales para castigarlo.",
                        "Lo persiguió por todo el bosque para recuperarlas.",
                        "Le respondió con serenidad y una enseñanza sabia.",
                        "Lloró tristemente y se escondió en su árbol."}, 2);
        agregarPregunta(l, "¿Qué pasó cuando el Cuervo se pegó las plumas verdes y se mostró ante los demás animales?",
                new String[]{"Todos lo admiraron y lo nombraron rey del bosque.",
                        "Los animales se rieron porque las plumas se veían falsas.",
                        "El Quetzal lo aplaudió por su ingenioso disfraz.",
                        "El Cuervo se sintió tan feliz que olvidó su tristeza."}, 1);
        agregarPregunta(l, "¿Qué le dijo el Quetzal al Cuervo cuando regresó a pedir perdón?",
                new String[]{"Que nunca más serían amigos por su traición.",
                        "Que debía devolver las plumas y abandonar el bosque.",
                        "Que su don era la inteligencia y debía valorarla con honradez.",
                        "Que tendría que pintarse de verde para compensar el daño."}, 2);
        agregarPregunta(l, "¿Qué hace el Quetzal en esta fábula antes de que el Cuervo lo engañe?",
                new String[]{"Presume su belleza ante todos los animales del bosque.",
                        "Vive tranquilo comiendo frutas y cantando para el viento.",
                        "Compite con otros pájaros para demostrar quién es el más bello.",
                        "Construye el nido más grande del bosque nuboso."}, 1);
        agregarPregunta(l, "¿Cuál es el don que el Quetzal le reconoce al Cuervo al final de la fábula?",
                new String[]{"Su valentía para enfrentar peligros.", "Su inteligencia.", "Su nobleza y generosidad.", "Su fuerza para volar lejos."}, 1);
        agregarPregunta(l, "¿Cuál es la moraleja de esta fábula guatemalteca?",
                new String[]{"Que el más bello siempre gana en la vida.",
                        "La envidia y el engaño no dan los frutos prometidos; hay que valorar los dones propios.",
                        "Que está bien copiar a los demás si uno lo hace con esfuerzo.",
                        "Que el Quetzal es superior a todos los animales del bosque."}, 1);
    }

    private void crearFabulaConejo(Carrera c) {
        String texto = "FÁBULA: EL CONEJO Y EL COYOTE DEL LAGO ATITLÁN\n\n" +
                "A orillas del lago Atitlán, donde los volcanes San Pedro, Tolimán y Atitlán se reflejan en las aguas azules como tres guardianes silenciosos, vivía un Conejo de orejas largas y patas veloces. " +
                "El Conejo era pequeño pero muy ingenioso, y siempre encontraba la manera de conseguir lo que necesitaba. " +
                "Cerca del lago también vivía un Coyote hambriento y arrogante que se creía el más listo de todos los animales.\n\n" +
                "Un día de sequía, las plantas que servían de alimento al Conejo se habían secado y el hambre apretaba. " +
                "El Conejo vio que en una pequeña isla en medio del lago crecían unos hermosos chiles y tomates silvestres. " +
                "Para llegar, necesitaba cruzar el lago, pero el Conejo no sabía nadar.\n\n" +
                "El Coyote, que lo observaba desde la orilla, se acercó con una sonrisa burlona: \"Conejo, ¿sabes cuántos de tus parientes viven en esta región del lago? " +
                "Yo los conté todos el año pasado. Si me dices cuántos crees que son, te llevaré en mi lomo hasta la isla.\" " +
                "El Conejo entendió que era una trampa; el Coyote quería distraerlo para atraparlo mientras cruzaban.\n\n" +
                "Pero el Conejo sonrió y respondió: \"Con mucho gusto, Coyote. Pero primero cuéntame tú cuántas escamas tiene el pez más grande del lago Atitlán, y yo te contaré mis parientes.\" " +
                "El Coyote se rascó la cabeza, incapaz de responder. \"¿Ves?\", dijo el Conejo, \"algunas cosas no se pueden contar. " +
                "Pero sí puedo decirte otra cosa: si nadas hasta la isla y me traes diez chiles, te daré a cambio la dirección del nido de codornices más grande de la ribera, que tiene más huevos de los que jamás has visto.\"\n\n" +
                "El Coyote, codiciando los huevos, aceptó. Nadó hasta la isla, recogió los chiles y los trajo de vuelta. " +
                "El Conejo le dio la dirección, pero cuando el Coyote llegó, encontró solo un viejo nido vacío que ya nadie ocupaba. " +
                "Furioso, regresó al lago, pero el Conejo ya había desaparecido entre los maizales, con el vientre lleno de chiles y tomates.\n\n" +
                "MORALEJA: El arrogante que subestima a los demás por su tamaño o apariencia es el primero en caer en su propia trampa. El ingenio vence a la soberbia.";
        Lectura l = new Lectura("El Conejo y el Coyote del Lago Atitlán", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿En qué lugar de Guatemala se ambienta esta fábula?",
                new String[]{"En las playas de Livingston, junto al Caribe.",
                        "A orillas del lago Atitlán.",
                        "En los mercados de Chichicastenango.",
                        "En los bosques del Parque Nacional Tikal."}, 1);
        agregarPregunta(l, "¿Cuál era el problema principal que enfrentaba el Conejo al inicio de la historia?",
                new String[]{"Que el Coyote le había robado su madriguera.",
                        "Que no podía nadar para llegar a la isla con alimento.",
                        "Que los volcanes habían espantado a todos los animales.",
                        "Que no encontraba agua limpia para beber en la sequía."}, 1);
        agregarPregunta(l, "¿Cuál era la trampa que el Coyote intentó tender al Conejo?",
                new String[]{"Convencerlo de nadar en el lago para ahogarlo.",
                        "Distraerlo con una pregunta mientras cruzaban para atraparlo.",
                        "Hacerlo pelear con otro animal más grande.",
                        "Robarle el alimento que ya había conseguido."}, 1);
        agregarPregunta(l, "¿Cómo respondió el Conejo a la trampa del Coyote?",
                new String[]{"Huyó corriendo hacia el bosque de inmediato.",
                        "Pidió ayuda a los otros animales de la orilla.",
                        "Respondió con otra pregunta imposible de contestar.",
                        "Le mintió diciendo que sí sabía la respuesta exacta."}, 2);
        agregarPregunta(l, "¿Qué prometió el Conejo al Coyote a cambio de traer los chiles de la isla?",
                new String[]{"La ubicación del nido de codornices más grande de la ribera.",
                        "La receta del mejor caldo de frijoles del lago.",
                        "Un mapa de todas las madrigueras del bosque.",
                        "La promesa de no molestarlo nunca más en el lago."}, 0);
        agregarPregunta(l, "¿Qué encontró el Coyote cuando llegó al lugar que el Conejo le indicó?",
                new String[]{"Un nido lleno de huevos frescos de codorniz.",
                        "Un grupo de conejos que lo esperaban para atacarlo.",
                        "Un viejo nido vacío que nadie ocupaba.",
                        "Una trampa de cazadores que lo capturó."}, 2);
        agregarPregunta(l, "¿Qué defecto principal tiene el Coyote en esta fábula?",
                new String[]{"La pereza y el miedo al agua del lago.", "La arrogancia y la codicia.", "La tristeza y la nostalgia.", "La ingenuidad y la timidez."}, 1);
        agregarPregunta(l, "¿Qué virtud principal demuestra el Conejo a lo largo de la historia?",
                new String[]{"Su velocidad para correr por la orilla del lago.", "Su valentía para enfrentarse al Coyote.", "Su ingenio para resolver su problema sin violencia.", "Su fuerza para cargar mucho alimento."}, 2);
        agregarPregunta(l, "¿Cuáles son los tres volcanes mencionados al inicio de la fábula?",
                new String[]{"Santiaguito, Pacaya y Santa María.",
                        "San Pedro, Tolimán y Atitlán.",
                        "Tajumulco, Tacaná y el Agua.",
                        "Fuego, Acatenango y Santa María."}, 1);
        agregarPregunta(l, "¿Cuál es la moraleja de esta fábula del lago Atitlán?",
                new String[]{"Que el más grande siempre derrota al más pequeño.",
                        "El arrogante que subestima a otros por su tamaño es el primero en caer. El ingenio vence a la soberbia.",
                        "Que los animales pequeños deben evitar a los grandes siempre.",
                        "Que la honestidad es más importante que la inteligencia."}, 1);
    }

    private void crearLeyendaLlorona(Carrera c) {
        String texto = "LEYENDA: LA LLORONA DE LOS RÍOS GUATEMALTECOS\n\n" +
                "De todas las leyendas que han poblado la imaginación del pueblo guatemalteco a lo largo de los siglos, pocas son tan arraigadas y extendidas como la de La Llorona. " +
                "Se dice que en tiempos de la Colonia, en los alrededores de la ciudad de Santiago de los Caballeros, hoy conocida como La Antigua Guatemala, " +
                "vivía una joven indígena de nombre Xochilt, de rara belleza y corazón noble. Xochilt se enamoró perdidamente de un conquistador español llamado Don Rodrigo, quien la prometió matrimonio y amor eterno.\n\n" +
                "Sin embargo, Don Rodrigo abandonó a Xochilt para casarse con una mujer española de noble linaje, como dictaban las costumbres y conveniencias de la época. " +
                "Xochilt, rota de dolor y deshonra, perdió la razón. Cuentan los viejos del lugar que en un momento de locura, tomó a sus dos hijos pequeños al borde del río Pensativo y los dejó ir entre las aguas, convencida de que así estarían libres del sufrimiento del mundo.\n\n" +
                "Cuando recuperó la conciencia y comprendió lo que había hecho, Xochilt cayó de rodillas lanzando un grito desgarrador que heló la sangre de todos los que lo escucharon. " +
                "Desde esa noche, su alma no pudo descansar. Se convirtió en un espíritu que vaga eternamente por las orillas de los ríos y barrancos de Guatemala, " +
                "vestida de blanco, con el cabello negro suelto, llorando sin consuelo y gritando: \"¡Aayyy, mis hijos!\"\n\n" +
                "Los ancianos de los pueblos del altiplano y del oriente guatemalteco advierten que La Llorona aparece especialmente en las noches de luna llena y cerca de ríos, " +
                "barrancos y quebradas. Se dice que quien la escucha llorar cerca de su casa debe rezar y no salir, pues el espíritu puede llevar consigo a los niños desobedientes que andan solos de noche. " +
                "También se cuenta que algunos hombres que la han seguido atraídos por su llanto han amanecido perdidos en los barrancos, sin recordar cómo llegaron.\n\n" +
                "Esta leyenda, transmitida de generación en generación alrededor de los fogones de las casas, cumple una función social profunda: " +
                "refuerza los valores del cuidado de los hijos, advierte sobre los peligros de la noche para los niños y narra, desde la óptica popular, el sufrimiento histórico de las mujeres indígenas frente a la injusticia colonial.\n\n" +
                "Versiones de La Llorona se escuchan desde la costa Sur hasta el Petén, adaptándose a los ríos y quebradas de cada región, pero siempre conservando su esencia: un alma en pena que llora eternamente por sus hijos perdidos.";
        Lectura l = new Lectura("La Llorona de los Ríos Guatemaltecos", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿En qué ciudad colonial de Guatemala se origina esta leyenda según el texto?",
                new String[]{"En la ciudad de Guatemala, capital actual del país.",
                        "En Santiago de los Caballeros, hoy La Antigua Guatemala.",
                        "En Quetzaltenango, la ciudad de los Altos.",
                        "En Puerto Barrios, a orillas del Caribe."}, 1);
        agregarPregunta(l, "¿Cuál fue la razón por la que Don Rodrigo abandonó a Xochilt?",
                new String[]{"Porque Xochilt lo traicionó con otro hombre.",
                        "Porque fue enviado de regreso a España por el rey.",
                        "Para casarse con una mujer española de noble linaje.",
                        "Porque Xochilt le confesó que no lo amaba."}, 2);
        agregarPregunta(l, "¿Qué hizo Xochilt con sus hijos en el momento de mayor locura y desesperación?",
                new String[]{"Los dejó al cuidado de una familia de la aldea.",
                        "Los llevó consigo al buscar refugio en el bosque.",
                        "Los dejó ir entre las aguas del río Pensativo.",
                        "Los entregó a un convento para que los cuidaran."}, 2);
        agregarPregunta(l, "¿Cómo se describe físicamente a La Llorona en la leyenda guatemalteca?",
                new String[]{"Vestida de negro, con el cabello cubierto y los pies descalzos.",
                        "Vestida de blanco, con el cabello negro suelto.",
                        "Cubierta de barro del río y con ojos brillantes.",
                        "Con un rebozo rojo y una antorcha en la mano."}, 1);
        agregarPregunta(l, "¿En qué momento y lugar aparece La Llorona con mayor frecuencia según los ancianos?",
                new String[]{"En las mañanas de lluvia, cerca de los cementerios.",
                        "En las noches de luna llena, cerca de ríos y barrancos.",
                        "Al amanecer, en las plazas principales de los pueblos.",
                        "Durante los días festivos, en las iglesias antiguas."}, 1);
        agregarPregunta(l, "¿Qué se dice que le ocurre a los hombres que siguen el llanto de La Llorona?",
                new String[]{"Quedan encantados y se convierten en árboles.",
                        "Encuentran riquezas escondidas bajo el río.",
                        "Amanecen perdidos en los barrancos sin recordar cómo llegaron.",
                        "Son llevados directamente a Xibalbá sin remedio."}, 2);
        agregarPregunta(l, "¿Cuál es una de las funciones sociales que cumple esta leyenda según el texto?",
                new String[]{"Promover el turismo hacia los ríos de Guatemala.",
                        "Explicar el origen de los ríos del altiplano central.",
                        "Advertir sobre los peligros de la noche para los niños.",
                        "Celebrar el mestizaje entre españoles e indígenas."}, 2);
        agregarPregunta(l, "¿Qué río se menciona en el texto como escenario de la tragedia de Xochilt?",
                new String[]{"El río Motagua.", "El río Usumacinta.", "El río Pensativo.", "El río Samalá."}, 2);
        agregarPregunta(l, "¿Por qué el alma de Xochilt no puede descansar según la leyenda?",
                new String[]{"Porque fue maldecida por los señores de Xibalbá.",
                        "Por el remordimiento eterno de haber perdido a sus hijos.",
                        "Porque Don Rodrigo no quiso perdonarla antes de morir.",
                        "Porque la Iglesia colonial le negó el derecho al entierro."}, 1);
        agregarPregunta(l, "¿Qué perspectiva histórica adicional ofrece el texto sobre el significado de esta leyenda?",
                new String[]{"Que fue inventada por los españoles para controlar a los indígenas.",
                        "Que narra el sufrimiento histórico de las mujeres indígenas frente a la injusticia colonial.",
                        "Que es una copia de una leyenda europea traída por los conquistadores.",
                        "Que fue creada por los sacerdotes mayas para proteger los ríos sagrados."}, 1);
    }

    private void crearLeyendaCadejo(Carrera c) {
        String texto = "LEYENDA: EL CADEJO: EL GUARDIÁN DE LAS NOCHES GUATEMALTECAS\n\n" +
                "Entre todas las criaturas que pueblan el imaginario nocturno de Guatemala, ninguna es tan dual y fascinante como el Cadejo. " +
                "A diferencia de muchas leyendas que presentan un solo ser, la tradición popular guatemalteca reconoce la existencia de dos Cadejos: el blanco y el negro, " +
                "dos fuerzas opuestas que en la oscuridad de la noche libran una batalla eterna por el alma de los caminantes.\n\n" +
                "El Cadejo Blanco es descrito como un perro grande de pelaje brillante como la luna, con ojos que brillan con una luz azulada y pezuñas que no hacen ruido al pisar el suelo. " +
                "Aparece junto a aquellas personas que caminan de noche por caminos solitarios, velando por su seguridad y acompañándolas en silencio hasta que llegan a su destino. " +
                "Muchos ancianos del oriente guatemalteco y del altiplano cuentan que en sus años de juventud, cuando debían caminar de noche entre aldeas, sentían la presencia del Cadejo Blanco a su lado, " +
                "y esa presencia los llenaba de calma y les indicaba que iban por el buen camino.\n\n" +
                "El Cadejo Negro, en cambio, es la contraparte oscura. Es un perro de pelaje negro azabache con ojos encendidos como brasas y un olor a azufre que lo delata antes de que se le vea. " +
                "Su misión es perseguir, desorientar y asustar a los borrachos, a los que andan en malos pasos y a los que han tomado decisiones que dañan a su familia o comunidad. " +
                "Se dice que quien es perseguido por el Cadejo Negro en un sueño recurrente debe reflexionar sobre sus acciones, pues la criatura actúa como un espejo del alma.\n\n" +
                "Cuentan en los pueblos de Huehuetenango y de los Cuchumatanes que un hombre que regresaba de noche a su aldea, después de haber bebido en demasía, vio frente a él un perro enorme y negro que bloqueaba el camino. " +
                "Por más que intentaba rodearlo, el animal aparecía siempre frente a él, hasta que el hombre, agotado, se sentó en una piedra a llorar. " +
                "En ese momento, el Cadejo Negro desapareció y fue reemplazado por el Cadejo Blanco, que lo guió con suavidad por el camino hasta la puerta de su casa.\n\n" +
                "Los estudiosos del folklore guatemalteco señalan que la leyenda del Cadejo es una expresión profunda de la cosmovisión indígena: " +
                "la idea de que todo en la naturaleza tiene una dualidad, una fuerza de luz y una fuerza de oscuridad que se equilibran mutuamente. " +
                "Además, la leyenda cumple una función práctica y moral: advierte sobre los peligros de andar de noche solo, especialmente bajo los efectos del alcohol, " +
                "y al mismo tiempo ofrece consuelo al creyente, recordándole que las fuerzas protectoras del universo velan por quien camina con honestidad y bien en el corazón.\n\n" +
                "Hoy en día, la leyenda del Cadejo sigue viva en las tertulias familiares, en los mercados y en las radios comunitarias de los municipios del interior de Guatemala, " +
                "donde la tradición oral sigue siendo el vehículo principal de transmisión de la sabiduría popular.";
        Lectura l = new Lectura("El Cadejo: El Guardián de las Noches Guatemaltecas", texto, c);
        lecturaRepository.save(l);

        agregarPregunta(l, "¿Qué hace única a la leyenda del Cadejo en comparación con otras leyendas guatemaltecas?",
                new String[]{"Que el Cadejo puede transformarse en cualquier animal del bosque.",
                        "Que presenta dos seres opuestos: el Cadejo blanco y el negro.",
                        "Que solo aparece en los departamentos del norte del país.",
                        "Que fue inventada por los mayas para proteger a los sacerdotes."}, 1);
        agregarPregunta(l, "¿Cuál es la función del Cadejo Blanco según la leyenda guatemalteca?",
                new String[]{"Asustar y castigar a quienes hacen el mal en su comunidad.",
                        "Guiar y proteger a las personas que caminan de noche por caminos solitarios.",
                        "Anunciar la llegada de lluvias y tormentas en el altiplano.",
                        "Cuidar los campos de maíz durante las noches de cosecha."}, 1);
        agregarPregunta(l, "¿Qué característica física distingue al Cadejo Negro del Cadejo Blanco?",
                new String[]{"El Cadejo Negro es más pequeño y tiene pelo corto.",
                        "El Cadejo Negro tiene pelaje negro con ojos como brasas y olor a azufre.",
                        "El Cadejo Negro aparece solo durante las lluvias del invierno.",
                        "El Cadejo Negro tiene cuernos y camina en dos patas."}, 1);
        agregarPregunta(l, "¿A quiénes persigue especialmente el Cadejo Negro según la tradición popular?",
                new String[]{"A los niños que no obedecen a sus padres durante el día.",
                        "A los viajeros extranjeros que no conocen los caminos.",
                        "A los borrachos y a quienes andan en malos pasos.",
                        "A los cazadores que entran al bosque sin permiso."}, 2);
        agregarPregunta(l, "¿En qué región de Guatemala se ambienta la historia del hombre que se encontró con el Cadejo Negro?",
                new String[]{"En las playas de Champerico, en la costa del Pacífico.",
                        "En los pueblos de Huehuetenango y los Cuchumatanes.",
                        "En las riveras del lago de Izabal, en el norte.",
                        "En los mercados de Chichicastenango, en el Quiché."}, 1);
        agregarPregunta(l, "¿Qué hizo el hombre cuando el Cadejo Negro bloqueaba constantemente su camino?",
                new String[]{"Arrojó piedras al animal para espantarlo.",
                        "Llamó en voz alta al Cadejo Blanco para que lo rescatara.",
                        "Se sentó agotado en una piedra y comenzó a llorar.",
                        "Corrió hacia el río más cercano para escapar."}, 2);
        agregarPregunta(l, "¿Qué ocurrió después de que el hombre se sentó en la piedra a llorar?",
                new String[]{"El Cadejo Negro lo ató al árbol más cercano hasta el amanecer.",
                        "El Cadejo Negro desapareció y el Cadejo Blanco lo guió a casa.",
                        "Ambos cadejos lucharon y el hombre pudo escapar por el camino.",
                        "El hombre se quedó dormido y amaneció en la orilla del río."}, 1);
        agregarPregunta(l, "¿Qué concepto de la cosmovisión indígena guatemalteca expresa la leyenda del Cadejo según el texto?",
                new String[]{"Que los animales son más importantes que los seres humanos en la naturaleza.",
                        "Que la oscuridad siempre vence a la luz en las noches del altiplano.",
                        "Que todo en la naturaleza tiene una dualidad de luz y oscuridad que se equilibra.",
                        "Que los perros son los únicos animales guardianes del ser humano."}, 2);
        agregarPregunta(l, "¿Cómo se transmite actualmente la leyenda del Cadejo en Guatemala?",
                new String[]{"A través de libros universitarios y museos nacionales.",
                        "En tertulias familiares, mercados y radios comunitarias.",
                        "Exclusivamente en los colegios privados de la capital.",
                        "Por medio de películas y series producidas en Hollywood."}, 1);
        agregarPregunta(l, "¿Qué mensaje práctico y moral refuerza la leyenda del Cadejo en las comunidades guatemaltecas?",
                new String[]{"Que los perros callejeros son peligrosos y deben evitarse en la noche.",
                        "Que es mejor viajar de día y evitar caminar de noche solo, especialmente bajo efectos del alcohol.",
                        "Que quienes no creen en las leyendas serán castigados por los espíritus.",
                        "Que el Cadejo Blanco solo aparece en los pueblos del altiplano, no en la costa."}, 1);
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

    private void agregarPreguntaVF(Lectura lectura, String enunciado, String[] textosOpciones, int indiceCorrecta) {
        agregarPregunta(lectura, enunciado, textosOpciones, indiceCorrecta);
    }
}