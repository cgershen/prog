import sys
import socket

import shortest_path

def sendRequest(a_x, a_y, b_x, b_y):

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(('127.0.0.1', 8011))

    message = "%s %s %s %s" % (a_x, a_y, b_x, b_y)

    shortest_path.replyWith(sock, message)
    reply = shortest_path.recieveMessage(sock)

    print(reply)

sendRequest(-99.116625,19.33599, -99.201833,19.309861)
