from django.conf.urls import url
from rest_framework import routers
from pumagis.views import points_list
from pumagis.views import lines_list
from pumagis.views import line
from pumagis.views import matches

urlpatterns = [
    url(r'^points/$',points_list),
    #url(r'^lines/$',lines_list),
    url(r'^lines/$',line),
    url(r'^lines/(?P<p_ori>.*)/(?P<p_des>.*)$',lines_list),
    #url(r'^lines/.*$',lines_list),
    url(r'^matches/$',matches),
]
