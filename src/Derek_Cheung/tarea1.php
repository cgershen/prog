<?php

    /**
     * Programacion Avanzada
     * Tarea 1
     *
     * @author Derek Cheung
     */

    ////////////////
    //
    // Definir classes y interfaces
    //

    /**
     * Interface que define como se puede interacionar con clases que 
     * implementan este interface
     */
    interface Transmisor {

        /**
         * @return Nada
         */
        function difundir();

    }

    /**
     * Clase que implementa el interface Transmisor, difunde usando echo
     */
    class Orador implements Transmisor {

        /**
         * Proporciona una frase
         *
         * @return String
         */
        function obtener_frase() {
            return "Hola mundo!\n";
        }

        /**
         * Difundir usando echo, este function implementa el interface
         *
         * @return Nada, se usa echo en vez
         */
        function difundir() {
            $frase = $this->obtener_frase();
            echo $frase;
        }

    }

    /**
     * Clase que extenda Orador, difunde en mayusculos
     */
    class Megafono extends Orador {

        /**
         * Difundir en mayusculos usando echo
         *
         * @return Nada, se usa echo en vez
         */
        function difundir() {

            // La funcion obtener_frase esta heredado del pariente Orador
            $frase = $this->obtener_frase();

            // Convirtir a mayusculos
            $frase_mayusculas = strtoupper($frase);

            echo $frase_mayusculas;

        }

    }

    ////////////////
    //
    // Usar esos clases
    //

    // crear un nuevo objecto
    $transmisor_alfa = new Orador();
    // usarlo de acuerdo con el interface
    $transmisor_alfa->difundir();

    // crear un nuevo objecto que extiende otra clase
    $transmisor_beta = new Megafono();
    // usarlo de acuerdo con el interface
    $transmisor_beta->difundir();

?>
