import matplotlib
matplotlib.use('TkAgg')
from pylab import *
import networkx as nx
import random as rd
import threading
shared_resource_lock = threading.Lock()


def initialize():
	global g, nextg
	g = nx.karate_club_graph()
	g.pos = nx.spring_layout(g)
	for i in g.nodes():
		g.node[i]['state'] = 1 if random() < 0.5 else 0
def observe():
	global g
	shared_resource_lock.acquire()
	cla()
	nx.draw(g, cmap = cm.binary, vmin = 0, vmax = 1, node_color =
		[g.node[i]['state'] for i in g.nodes()], pos = g.pos)

	show()
	shared_resource_lock.release()
def speak():
	global g, speaker
	shared_resource_lock.acquire()

	speaker = rd.choice([i for i in g.nodes])
	shared_resource_lock.release()

	

def listen():
	global g, speaker
	shared_resource_lock.acquire()
	listener = rd.choice([i for i in g.neighbors(speaker)])
	g.node[listener]['state'] = g.node[speaker]['state']
	shared_resource_lock.release()

if __name__ == "__main__":
	initialize()
	for i in range(25):
		observe()
		#t1 = threading.Thread(target = observe)
		t2 = threading.Thread(target = speak)
		t3 = threading.Thread(target = listen)
		#t1.start()
		t2.start()
		t3.start()
		#t1.join()
		t2.join()
		t3.join()
			

	
	
	

#import pycxsimulator
#pycxsimulator.GUI().start(func=[initialize, observe, update])
