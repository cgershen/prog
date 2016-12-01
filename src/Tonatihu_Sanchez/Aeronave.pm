#!/usr/bin/perl
use strict;
use warnings;

package Aeronave;

sub new
{
	my $class = shift;
	my $type = shift;#Comercial, Combate, Privada, Espacial
	my $id= shift; #Boeing 757, F-18, Citation CJ2
	my $capacity = shift; #pasajeros
	our $self = {};
	bless($self, $class);
	$self->{tipo} = $type;
	$self->{identificador} = $id;
	$self->{capacidad} = $capacity;
	return $self;
}
sub informaCaracteristicas{
	our $self;
	print "Esta aeronave es de tipo $self->{tipo}, es un $self->{identificador} con capacidad para $self->{capacidad} personas \n"; 
}

sub despegar { print " -despegar- no está implementado aún en esta clase\n" }
sub aterrizar { print " aterrizar- no está implementado aún en esta clase\n" }

1;
