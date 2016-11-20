import sys
import signal
import socket

import path_server

def sendRequest(a_x, a_y, b_x, b_y):

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(('127.0.0.1', 8011))

    message = "%s %s %s %s" % (a_x, a_y, b_x, b_y)

    path_server.replyWith(sock, message)
    reply = path_server.recieveMessage(sock)

    return(reply)

# Agricola Oriental a IMASS
#print sendRequest(-99.1660,19.3585, -99.18, 19.33)

# Calles de uno sentido
#print sendRequest(-99.186096,19.478058, -99.18697, 19.478362)
#print sendRequest(-99.186096,19.478058, -99.18697, 15.478362)

#print sendRequest( -99.183489,19.330761,-99.330711,19.180191)

#print sendRequest( -99.183489,19.330761,-99.179524,19.328140)
#print sendRequest( -99.183489,19.330564, -99.179524,19.328140)

#print sendRequest( -99.179524,19.328140,-99.183489,19.330761 )
#print sendRequest( -99.179524,19.328140,-99.183489,19.330564 )

print sendRequest( -99.17871,19.33079,-99.18349,19.33062 )
#print sendRequest( -99.184092,19.330498, -99.178941243,19.3308355855)
#print sendRequest(  -99.178941243,19.3308355855, -99.184092,19.330498)

