#!/usr/bin/perl

use Concord;

my $aeronave = Concord->new("Comercial","Concorde",144);

$aeronave->informaCaracteristicas;
$aeronave->despegar;
$aeronave->mach1;
$aeronave->aterrizar;
