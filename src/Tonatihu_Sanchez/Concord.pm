#!/usr/bin/perl

package Concord;

use strict;
use warnings;

use parent qw(Aeronave);

sub despegar
{
	print "encendiendo motores...\n";
	print "solicitando permiso para despegar...\n";
	print "aceleración requerida alcanzada, despegando...\n";
	return;
}

sub aterrizar
{
	print "solicitando permiso para aterrizar...\n";
	print "aceleración reducida a lo requerido...\n";
	print "aterrizando...\n";
	print "apagando motores...\n";
	return;
}

sub mach1{
	print "acelerando hacia velocidad del sonido...\n";
	print "<<<sonic boom>>> mach1 alcanzado...\n";
	return;
}

1;
