# -*- coding: utf-8 -*-
import matplotlib
matplotlib.use('TkAgg')
from pylab import *

n = 100 #tamaño de la cuadricula
p = 0.5 #probabilidad de estar vivo o muerto

def initialize():
	global config, nextconfig
	config = zeros([n,n])
	for x in xrange(n):
		for y in xrange(n):
			config[x, y] = 1 if random() < p else 0
		nextconfig = zeros([n,n])

def observe():
	global config, nextconfig
	cla()
	imshow(config, vmin = 0, vmax = 1, cmap = cm.binary)
def update():
	global config, nextconfig
	for x in xrange(n):
		for y in xrange(n):
			count = 0
			for dx in [-1, 0, 1]:
				for dy in [-1, 0, 1]:
					count += config[(x + dx) % n, (y + dy) % n]
			if nextconfig[x,y] == 1:
				nextconfig[x,y] = 1 if (count == 3 or count ==2) else 0
			else:
				nextconfig[x,y] = 0 if count != 3 else 1
			#nextconfig[x, y] = 1 if count >= 4 else 0

	config, nextconfig = nextconfig, config

import pycxsimulator
pycxsimulator.GUI().start(func = [initialize, observe, update])
