<?php

/**
 * Tarea 2
 * Programación Avanzada
 *
 * Derek Cheung
 *
 * 24 agosto 2016
 */

////////////
//
// Excepciones
//

/**
 * Excepción customizada para enteros negativos
 */
class NegativeIntegerException extends Exception {
	public $code = 1;
}

/**
 * Excepción customizada para enteros par
 */
class EvenIntegerException extends Exception {
	public $code = 2;
}

/**
 * Sólo acepta enteros que son ambos par y positivos
 *
 * @throws NegativeIntegerException, EvenIntegerException, Exeption
 * @return Nada
 */
function strictlyPositiveOddIntegers($number) {

	if(!is_int($number)) {
		throw new Exception("Eso no es un entero");
	}

	if($number < 0) { // no acepta negativos
		throw new NegativeIntegerException();
	}

	if($number % 2 == 0) { // no acepta enteros par
		throw new EvenIntegerException();
	}

}

// Utiliza esos excepciones

try {
	// si cualquier de las líneas en este bloque genera excepción, todos las
	// otras líneas en este bloque no va a estar ejecutado
	strictlyPositiveOddIntegers(2); 
	strictlyPositiveOddIntegers(1);
	strictlyPositiveOddIntegers(0);
	strictlyPositiveOddIntegers(-1);

	echo "Todos los numeros pasan";
} catch(EvenIntegerException $e) {
	echo "Excepción " . $e->getCode() . ": No se puede entregar números par\n";
} catch(NegativeIntegerException $e) {
	echo "Excepción " . $e->getCode() . ": No se puede entregar números negativos\n";
} catch(Exception $e) {
	echo "Excepción no previsita ".$e->getMessage()."\n";
}

////////////
//
// Generators
//

/**
 * Genera una serie geometrica de la forma 1/2 + 1/4 + 1/8 + ...
 *
 * @param count (Integer) El número de valores para generar
 * @return (float)
 */
function geometricSeriesGenerator($count) {

	$i = 0;
	$value = 0;

	while($i++ < $count) {
		yield $value;
		$value += (1 / pow(2, $i));
	}

}

$stepsToPrint = 5;

// Utiliza el generador
foreach(geometricSeriesGenerator($stepsToPrint) as $index => $value) {
	printf("A paso %d el valor es %.4f\n", $index, $value);
}

echo "La serie ha sido generado\n";

////////////
//
// Iterators
//

/**
 * Devuelve una sequencia fija de números
 */
class RepeatableSequence implements Iterator {

	private static $data = array(4, 1, 9, 3, 7); // sequencia hard coded
	private $index = 0; // posición actual en la sequencia

	public function __construct() { // constructor
		$this->index = 0;
	}

	public function current() { // devuelve el valor actual en la sequencia
		return self::$data[$this->index];
	}

	public function rewind() { // reiniciar el iterator
		$this->index = 0;
	}

	public function key() { // devuelve la posición actual en la sequencia
		return $this->index;
	}

	public function next() { // avanza al próximo valor en la sequencia
		$this->index++;
	}

	public function valid() { // devuelve true syss hay un valor en la posición 
							  // actual

		// isset() automaticamente devuelve falso si no existe ningún elemento
		return isset(self::$data[$this->index]);
	}

}

// fabrica una nueva instancia del iterator
$sequenceObj = new RepeatableSequence();

// ejemplo
$skipAheadIfValueIs = 9;

// utiliza ese iterador
while($sequenceObj->valid()) {

	$index = $sequenceObj->key();
	$value = $sequenceObj->current();

	printf("El valor a %d es %d\n", $index, $value);
	$sequenceObj->next();

	// ejemplo
	if($value == $skipAheadIfValueIs) {
		$sequenceObj->next();
	}
}

echo "No hay más valores\n";

// otro método de utilizarlo
foreach($sequenceObj as $index => $value) {
	printf("El valor a %d es %d\n", $index, $value);
}

echo "No hay más valores\n";

