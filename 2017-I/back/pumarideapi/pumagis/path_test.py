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
print sendRequest(-99.1660,19.3585, -99.18, 19.33)

# Calles de uno sentido
print sendRequest(-99.186096,19.478058, -99.18697, 19.478362)
